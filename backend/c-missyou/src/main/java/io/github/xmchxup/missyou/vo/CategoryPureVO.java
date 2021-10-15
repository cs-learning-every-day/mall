package io.github.xmchxup.missyou.vo;

import io.github.xmchxup.missyou.model.Category;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
@Getter
@Setter
public class CategoryPureVO {
	private Long id;
	private String name;
	private Boolean isRoot;
	private Integer parentId;
	private String img;
	private Integer index;

	public CategoryPureVO(Category category) {
		BeanUtils.copyProperties(category, this);
	}
}
