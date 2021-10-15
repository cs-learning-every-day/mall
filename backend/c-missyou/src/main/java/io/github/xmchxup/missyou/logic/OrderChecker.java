package io.github.xmchxup.missyou.logic;

import io.github.xmchxup.missyou.bo.SkuOrderBO;
import io.github.xmchxup.missyou.dto.OrderDTO;
import io.github.xmchxup.missyou.dto.SkuInfoDTO;
import io.github.xmchxup.missyou.exception.http.ParameterException;
import io.github.xmchxup.missyou.model.OrderSku;
import io.github.xmchxup.missyou.model.Sku;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
public class OrderChecker {

	private OrderDTO orderDTO;
	private List<Sku> serverSkuList;
	private CouponChecker couponChecker;
	private Integer maxSkuLimit;

	@Getter
	private List<OrderSku> orderSkuList = new ArrayList<>();


	public OrderChecker(OrderDTO orderDTO,
						List<Sku> serverSkuList,
						CouponChecker couponChecker,
						Integer maxSkuLimit) {
		this.orderDTO = orderDTO;
		this.serverSkuList = serverSkuList;
		this.couponChecker = couponChecker;
		this.maxSkuLimit = maxSkuLimit;
	}

	public String getLeaderImg() {
		return this.serverSkuList.get(0).getImg();
	}

	public String getLeaderTitle() {
		return this.serverSkuList.get(0).getTitle();
	}

	public Integer getTotalCount() {
		return this.orderDTO.getSkuInfoList()
				.stream()
				.map(SkuInfoDTO::getCount)
				.reduce(Integer::sum)
				.orElse(0);
	}

	public void isOk() {
		BigDecimal serverTotalPrice = new BigDecimal("0");
		List<SkuOrderBO> skuOrderBOList = new ArrayList<>();

		this.skuNotOnSale(orderDTO.getSkuInfoList().size(),
				serverSkuList.size());


		for (int i = 0; i < this.serverSkuList.size(); i++) {
			Sku sku = this.serverSkuList.get(i);
			SkuInfoDTO skuInfoDTO = this.orderDTO.getSkuInfoList().get(i);
			this.containsSoldOutSku(sku);
			this.beyondSkuStock(sku, skuInfoDTO);
			this.beyondMaxSkuLimit(skuInfoDTO);

			serverTotalPrice = serverTotalPrice.add(
					this.calculateSkuOrderPrice(sku, skuInfoDTO)
			);
			skuOrderBOList.add(
					new SkuOrderBO(sku, skuInfoDTO)
			);
			this.orderSkuList.add(
					new OrderSku(sku, skuInfoDTO)
			);
		}
		this.totalPriceIsOk(orderDTO.getTotalPrice(), serverTotalPrice);
		if (this.couponChecker != null) {
			this.couponChecker.isOk();
			this.couponChecker.canBeUsed(skuOrderBOList, serverTotalPrice);
			this.couponChecker.finalTotalPriceIsOk(orderDTO.getFinalTotalPrice(), serverTotalPrice);
		}
	}

	private void totalPriceIsOk(BigDecimal orderTotalPrice, BigDecimal serverTotalPrice) {
		if (orderTotalPrice.compareTo(serverTotalPrice) != 0) {
			throw new ParameterException(50005);
		}
	}

	private BigDecimal calculateSkuOrderPrice(Sku sku, SkuInfoDTO skuInfoDTO) {
		if (skuInfoDTO.getCount() <= 0) {
			throw new ParameterException(50007);
		}
		return sku.getActualPrice().multiply(
				BigDecimal.valueOf(skuInfoDTO.getCount())
		);
	}

	/**
	 * 判断商品中是否有下架或删除
	 *
	 * @param count1
	 * @param count2
	 */
	private void skuNotOnSale(int count1, int count2) {
		if (count1 != count2) {
			throw new ParameterException(50002);
		}
	}

	/**
	 * 判断商品是否售罄
	 *
	 * @param sku
	 */
	private void containsSoldOutSku(Sku sku) {
		if (sku.getStock() == 0) {
			throw new ParameterException(50001);
		}
	}

	/**
	 * 判断超卖
	 *
	 * @param sku
	 * @param skuInfoDTO
	 */
	private void beyondSkuStock(Sku sku, SkuInfoDTO skuInfoDTO) {
		if (sku.getStock() < skuInfoDTO.getCount()) {
			throw new ParameterException(50003);
		}
	}

	/**
	 * 判断买入的是否超过限制
	 *
	 * @param skuInfoDTO
	 */
	private void beyondMaxSkuLimit(SkuInfoDTO skuInfoDTO) {
		if (skuInfoDTO.getCount() > this.maxSkuLimit) {
			throw new ParameterException(50004);
		}
	}

}
