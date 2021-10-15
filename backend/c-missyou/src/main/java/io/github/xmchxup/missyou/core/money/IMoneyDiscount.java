package io.github.xmchxup.missyou.core.money;

import java.math.BigDecimal;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
public interface IMoneyDiscount {
	BigDecimal discount(BigDecimal original, BigDecimal discount);
}
