package io.github.xmchxup.missyou.vo;

import io.github.xmchxup.missyou.model.Order;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
@Getter
@Setter
public class OrderPureVO extends Order {
	private Long period;

	public OrderPureVO(Order order, Long period) {
		BeanUtils.copyProperties(order, this);
		this.period = period;
	}
}
