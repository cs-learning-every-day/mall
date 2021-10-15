package io.github.xmchxup.missyou.dto.validators;


import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
public class TokenPasswordValidator implements
		ConstraintValidator<TokenPassword, String> {
	//第二个：自定义注解修饰的目标的类型

	private int min;
	private int max;

	@Override
	public void initialize(TokenPassword constraintAnnotation) {
		this.max = constraintAnnotation.max();
		this.min = constraintAnnotation.min();
	}

	@Override
	public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
		if (StringUtils.isEmpty(s)) {
			//小程序登录时不需要密码
			return true;
		}
		return s.length() >= this.min && s.length() <= this.max;
	}
}
