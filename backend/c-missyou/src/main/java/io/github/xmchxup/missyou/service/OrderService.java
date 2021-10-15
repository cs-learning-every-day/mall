package io.github.xmchxup.missyou.service;

import io.github.xmchxup.missyou.core.LocalUser;
import io.github.xmchxup.missyou.core.enumeration.OrderStatus;
import io.github.xmchxup.missyou.core.money.IMoneyDiscount;
import io.github.xmchxup.missyou.dto.OrderDTO;
import io.github.xmchxup.missyou.dto.SkuInfoDTO;
import io.github.xmchxup.missyou.exception.http.ForbiddenException;
import io.github.xmchxup.missyou.exception.http.NotFoundException;
import io.github.xmchxup.missyou.exception.http.ParameterException;
import io.github.xmchxup.missyou.logic.CouponChecker;
import io.github.xmchxup.missyou.logic.OrderChecker;
import io.github.xmchxup.missyou.model.*;
import io.github.xmchxup.missyou.repository.CouponRepository;
import io.github.xmchxup.missyou.repository.OrderRepository;
import io.github.xmchxup.missyou.repository.SkuRepository;
import io.github.xmchxup.missyou.repository.UserCouponRepository;
import io.github.xmchxup.missyou.util.CommonUtil;
import io.github.xmchxup.missyou.util.OrderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private SkuService skuService;

	@Autowired
	private SkuRepository skuRepository;

	@Autowired
	private CouponRepository couponRepository;

	@Autowired
	private UserCouponRepository userCouponRepository;

	@Autowired
	private IMoneyDiscount iMoneyDiscount;


	@Value("${missyou.order.max-sku-limit}")
	private int maxSkuLimit;

	@Value("${missyou.order.pay-time-limit}")
	private Integer payTimeLimit;

	public Page<Order> getByStatus(Integer status, Integer page, Integer size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("createTime").descending());
		Long uid = LocalUser.getUser().getId();
		if (status == OrderStatus.ALL.value()) {
			return this.orderRepository.findByUserId(uid, pageable);
		}
		return this.orderRepository.findByUserIdAndStatus(uid, status, pageable);
	}

	public Page<Order> getUnpaid(Integer page, Integer size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("createTime").descending());
		Long uid = LocalUser.getUser().getId();
		Date now = new Date();
		return this.orderRepository.findByExpiredTimeGreaterThanAndStatusAndUserId(
				now, OrderStatus.UNPAID.value(), uid, pageable);
	}

	@Transactional
	public Long placeOrder(Long uid, OrderDTO orderDTO, OrderChecker orderChecker) {
		String orderNo = OrderUtil.makeOrderNo();
		Calendar now = Calendar.getInstance();

		Calendar clone = (Calendar) now.clone();
		Date expiredTime = CommonUtil.addSomeSeconds(clone, this.payTimeLimit).getTime();

		Order order = Order.builder()
				.orderNo(orderNo)
				.totalPrice(orderDTO.getTotalPrice())
				.finalTotalPrice(orderDTO.getFinalTotalPrice())
				.userId(uid)
				.totalCount(orderChecker.getTotalCount().longValue())
				.snapImg(orderChecker.getLeaderImg())
				.snapTitle(orderChecker.getLeaderTitle())
				.status(OrderStatus.UNPAID.value())
				.expiredTime(expiredTime)
				.placedTime(now.getTime())
				.build();

		order.setSnapAddress(orderDTO.getAddress());
		order.setSnapItems(orderChecker.getOrderSkuList());


		this.orderRepository.save(order);
		this.reduceStock(orderChecker);
		if (orderDTO.getCouponId() != null) {
			this.writeOffCoupon(orderDTO.getCouponId(), order.getId(), uid);
		}

		return order.getId();
	}

	public Optional<Order> getOrderDetail(Long oid) {
		Long uid = LocalUser.getUser().getId();
		return this.orderRepository.findFirstByUserIdAndId(uid, oid);
	}

	private void writeOffCoupon(Long couponId, Long oid, Long uid) {
		int result = this.userCouponRepository.writeOff(couponId, oid, uid);
		if (result != 1) {
			throw new ForbiddenException(40012);
		}
	}

	private void reduceStock(OrderChecker orderChecker) {
		List<OrderSku> orderSkuList = orderChecker.getOrderSkuList();
		for (OrderSku orderSku : orderSkuList) {
			int result = this.skuRepository.reduceStock(orderSku.getId(), orderSku.getCount().longValue());
			if (result != 1) {
				throw new ParameterException(50003);
			}
		}
	}

	public OrderChecker isOk(Long uid, OrderDTO orderDTO) {
		if (orderDTO.getFinalTotalPrice()
				.compareTo(BigDecimal.ZERO) <= 0) {
			throw new ParameterException(50011);
		}

		List<Long> skuIdList = orderDTO.getSkuInfoList()
				.stream()
				.map(SkuInfoDTO::getId)
				.collect(Collectors.toList());

		List<Sku> skuList = this.skuService.getSkuListByIds(skuIdList);

		Long couponId = orderDTO.getCouponId();
		CouponChecker couponChecker = null;
		if (null != couponId) {
			Coupon coupon = this.couponRepository
					.findById(couponId)
					.orElseThrow(() -> new NotFoundException(40004));

			UserCoupon userCoupon = this.userCouponRepository
					.findFirstByUserIdAndCouponIdAndStatusAndOrderIdIsNull(uid, couponId, 1)
					.orElseThrow(() -> new NotFoundException(50006));

			couponChecker = new CouponChecker(coupon, iMoneyDiscount);
		}
		OrderChecker orderChecker = new OrderChecker(
				orderDTO, skuList, couponChecker, maxSkuLimit
		);
		orderChecker.isOk();
		return orderChecker;
	}

	public void updateOrderPrepayId(Long orderId, String prePayid) {
		Optional<Order> order = this.orderRepository.findById(orderId);
		order.ifPresentOrElse(o -> {
			o.setPrepayId(prePayid);
			this.orderRepository.save(o);
		}, () -> {
			throw new NotFoundException(50009);
		});
	}
}
