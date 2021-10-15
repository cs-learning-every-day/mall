package io.github.xmchxup.missyou.core.enumeration;

import java.util.stream.Stream;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
public enum CouponStatus {
	AVAILABLE(1, "未使用"),
	USED(2, "已使用"),
	EXPIRED(3, "已过期");

	private final Integer value;

	public Integer getValue() {
		return this.value;
	}

	CouponStatus(Integer value, String description) {
		this.value = value;
	}

	public static CouponStatus toType(int value) {
		return Stream.of(CouponStatus.values())
				.filter(c -> c.value == value)
				.findAny()
				.orElse(null);
	}
}
