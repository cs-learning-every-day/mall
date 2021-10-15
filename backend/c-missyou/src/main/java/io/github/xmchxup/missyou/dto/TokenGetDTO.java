package io.github.xmchxup.missyou.dto;

import io.github.xmchxup.missyou.core.enumeration.LoginType;
import io.github.xmchxup.missyou.dto.validators.TokenPassword;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
@Getter
@Setter
public class TokenGetDTO {
	@NotBlank(message = "account不允许为空")
	private String account;

	@TokenPassword(max = 30, message = "{token.password}")
	private String password;

	private LoginType type;
}
