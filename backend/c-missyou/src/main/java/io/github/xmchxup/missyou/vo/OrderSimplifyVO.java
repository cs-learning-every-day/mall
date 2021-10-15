package io.github.xmchxup.missyou.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
@Getter
@Setter
public class OrderSimplifyVO {
	private Long id;
	private String orderNo;
	private BigDecimal totalPrice;
	private Long totalCount;
	private String snapImg;
	private String snapTitle;
	private BigDecimal finalTotalPrice;
	private Integer status;
	private Date expiredTime;
	private Date placedTime;
	private Long period;

}
