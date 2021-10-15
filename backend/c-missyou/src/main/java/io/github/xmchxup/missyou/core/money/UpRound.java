package io.github.xmchxup.missyou.core.money;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
@Component
public class UpRound implements IMoneyDiscount {
	@Override
	public BigDecimal discount(BigDecimal original, BigDecimal discount) {
		BigDecimal acutalMoney = original.multiply(discount);
		BigDecimal finalMoney = acutalMoney.setScale(2, RoundingMode.UP);
		return finalMoney;
	}
}
