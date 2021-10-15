package io.github.xmchxup.missyou.api.v1;

import io.github.xmchxup.missyou.bo.PageCounter;
import io.github.xmchxup.missyou.core.LocalUser;
import io.github.xmchxup.missyou.core.interceptors.ScopeLevel;
import io.github.xmchxup.missyou.dto.OrderDTO;
import io.github.xmchxup.missyou.exception.http.NotFoundException;
import io.github.xmchxup.missyou.logic.OrderChecker;
import io.github.xmchxup.missyou.model.Order;
import io.github.xmchxup.missyou.service.OrderService;
import io.github.xmchxup.missyou.util.CommonUtil;
import io.github.xmchxup.missyou.vo.OrderIdVO;
import io.github.xmchxup.missyou.vo.OrderPureVO;
import io.github.xmchxup.missyou.vo.OrderSimplifyVO;
import io.github.xmchxup.missyou.vo.PagingDozer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
@RestController
@RequestMapping("order")
@Validated
public class OrderController {

	@Autowired
	private OrderService orderService;

	@Value("${missyou.order.pay-time-limit}")
	private Long payTimeLimit;

	@ScopeLevel()
	@PostMapping("")
	public OrderIdVO placeOrder(@RequestBody OrderDTO orderDTO) {
		Long uid = LocalUser.getUser().getId();
		OrderChecker orderChecker = this.orderService.isOk(uid, orderDTO);

		Long oid = this.orderService.placeOrder(uid, orderDTO, orderChecker);
		return new OrderIdVO(oid);
	}

	@ScopeLevel
	@GetMapping("/status/unpaid")
	public PagingDozer<Order, OrderSimplifyVO> getUnpaid(
			@RequestParam(defaultValue = "0") Integer start,
			@RequestParam(defaultValue = "10") Integer count) {
		PageCounter pageCounter = CommonUtil.convertToPageParameter(start, count);
		Page<Order> orderPage = this.orderService.getUnpaid(pageCounter.getPage(),
				pageCounter.getCount());

		PagingDozer<Order, OrderSimplifyVO> pagingDozer =
				new PagingDozer<>(orderPage, OrderSimplifyVO.class);
		pagingDozer.getItems().forEach(o -> ((OrderSimplifyVO) o).setPeriod(this.payTimeLimit));
		return pagingDozer;
	}

	@ScopeLevel
	@GetMapping("/by/status/{status}")
	public PagingDozer<Order, OrderSimplifyVO> getByStatus(@PathVariable int status,
														   @RequestParam(defaultValue = "0") Integer start,
														   @RequestParam(defaultValue = "10") Integer count) {
		PageCounter page = CommonUtil.convertToPageParameter(start, count);
		Page<Order> orderPage = orderService.getByStatus(status, page.getPage(), page.getCount());
		PagingDozer<Order, OrderSimplifyVO> pagingDozer = new PagingDozer<>(orderPage, OrderSimplifyVO.class);
		pagingDozer.getItems().forEach(o -> ((OrderSimplifyVO) o).setPeriod(payTimeLimit));
		return pagingDozer;
	}

	@ScopeLevel
	@GetMapping("/detail/{id}")
	public OrderPureVO getOrderDetail(@PathVariable(name = "id") Long oid) {
		Optional<Order> orderOptional = orderService.getOrderDetail(oid);
		return orderOptional
				.map(order -> new OrderPureVO(order, this.payTimeLimit))
				.orElseThrow(() -> new NotFoundException(50009));
	}

}
