package io.github.xmchxup.missyou.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
@Getter
@Setter
public class OrderDTO {
	@DecimalMin(value = "0.00", message = "不再合法范围内")
	@DecimalMax(value = "99999999.99", message = "不再合法范围内")
	private BigDecimal totalPrice;

	private BigDecimal finalTotalPrice;

	private Long couponId;

	private List<SkuInfoDTO> skuInfoList;

	private OrderAddressDTO address;
}
