package io.github.xmchxup.missyou.core.money;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
//@Component
//@Primary
public class HalfEvenRound implements IMoneyDiscount {
	@Override
	public BigDecimal discount(BigDecimal original, BigDecimal discount) {
		BigDecimal acutalMoney = original.multiply(discount);
//		银行家模式
		BigDecimal finalMoney = acutalMoney.setScale(2, RoundingMode.HALF_EVEN);
		return finalMoney;
	}
}
