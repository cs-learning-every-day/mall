package io.github.xmchxup.missyou.core.money;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
//@Component
public class HalfUpRound implements IMoneyDiscount {
	@Override
	public BigDecimal discount(BigDecimal original, BigDecimal discount) {
		BigDecimal acutalMoney = original.multiply(discount);
//		四啥五入
		BigDecimal finalMoney = acutalMoney.setScale(2, RoundingMode.HALF_UP);
		return finalMoney;
	}
}
