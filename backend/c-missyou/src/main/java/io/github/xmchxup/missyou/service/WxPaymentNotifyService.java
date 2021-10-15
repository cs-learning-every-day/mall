package io.github.xmchxup.missyou.service;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import io.github.xmchxup.missyou.core.enumeration.OrderStatus;
import io.github.xmchxup.missyou.exception.http.ServerErrorException;
import io.github.xmchxup.missyou.model.Order;
import io.github.xmchxup.missyou.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
public class WxPaymentNotifyService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private WxPaymentService wxPaymentService;

	@Transactional
	public void processPayNotify(String data) {
		Map<String, String> dataMap;
		try {
			dataMap = WXPayUtil.xmlToMap(data);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServerErrorException(9999);
		}

		WXPay wxPay = wxPaymentService.assembleWxPayConfig();
		boolean valid;
		try {
			valid = wxPay.isPayResultNotifySignatureValid(dataMap);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServerErrorException(9999);
		}
		if (!valid) {
			throw new ServerErrorException(9999);
		}

		String returnCode = dataMap.get("return_code");
		String orderNo = dataMap.get("out_trade_no");
		String resultCode = dataMap.get("result_code");

		if (!returnCode.equals("SUCCESS") ||
				!resultCode.equals("SUCCESS") ||
				null == orderNo) {
			throw new ServerErrorException(9999);
		}
		this.deal(orderNo);
	}

	private void deal(String orderNo) {
		Optional<Order> orderOptional = orderRepository.findFirstByOrderNo(orderNo);
		Order order = orderOptional.orElseThrow(() -> new ServerErrorException(9999));
		int res = -1;
		if (order.getStatus().equals(OrderStatus.UNPAID.value())
				|| order.getStatus().equals(OrderStatus.CANCELED.value())) {
			res = this.orderRepository.updateStatusByOrderNo(orderNo, OrderStatus.PAID.value());
		}
		if (res != 1) {
			throw new ServerErrorException(9999);
		}
	}
}
