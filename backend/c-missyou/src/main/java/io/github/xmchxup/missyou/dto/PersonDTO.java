package io.github.xmchxup.missyou.dto;


import io.github.xmchxup.missyou.dto.validators.PasswordEqual;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
// @Builder
// @RequiredArgsConstructor配合 @NonNull
@PasswordEqual
public class PersonDTO {
	@Length(min = 2, max = 10, message = "xxx")
	private String name;
	private Integer age;
	@Valid
	private SchoolDTO schoolDTO;

	private String password1;
	private String password2;
}
