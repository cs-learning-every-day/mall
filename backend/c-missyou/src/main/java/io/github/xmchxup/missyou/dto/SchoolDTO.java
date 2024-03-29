package io.github.xmchxup.missyou.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
@Getter
@Setter
public class SchoolDTO {
	@Length(min = 2)
	private String schoolName;
}
