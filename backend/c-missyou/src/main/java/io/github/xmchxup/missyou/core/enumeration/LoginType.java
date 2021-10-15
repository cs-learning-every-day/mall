package io.github.xmchxup.missyou.core.enumeration;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
public enum LoginType {
	USER_WX(0,"微信登录"),
	USER_EMAIL(1,"邮箱登录");

	private Integer value;
	private String description;

	LoginType(Integer value, String description) {
		this.value = value;
	}
}
