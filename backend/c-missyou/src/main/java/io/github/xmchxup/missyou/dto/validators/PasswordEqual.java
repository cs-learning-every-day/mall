package io.github.xmchxup.missyou.dto.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Constraint(validatedBy = PasswordValidator.class)
public @interface PasswordEqual {
	int min() default 4;

	int max() default 6;

	String message() default "passwords are not equal";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}