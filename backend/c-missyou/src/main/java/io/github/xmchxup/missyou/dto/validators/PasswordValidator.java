package io.github.xmchxup.missyou.dto.validators;


import io.github.xmchxup.missyou.dto.PersonDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
public class PasswordValidator implements
		ConstraintValidator<PasswordEqual, PersonDTO> {
	//第二个：自定义注解修饰的目标的类型

	private int min;
	private int max;

	@Override
	public void initialize(PasswordEqual constraintAnnotation) {
		this.min = constraintAnnotation.min();
		this.max = constraintAnnotation.max();
	}

	@Override
	public boolean isValid(PersonDTO personDTO,
						   ConstraintValidatorContext constraintValidatorContext) {
		String password1 = personDTO.getPassword1();
		String password2 = personDTO.getPassword2();

		return password1.equals(password2);
	}

}
