package io.github.xmchxup.missyou.bo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
@Getter
@Setter
@Builder
public class PageCounter {
	private Integer page;
	private Integer count;
}
