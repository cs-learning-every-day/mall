package io.github.xmchxup.missyou.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
@Entity
@Getter
@Setter
public class BannerItem extends BaseEntity {
	@Id
	private Long id;
	private String img;
	private String keyword;
	private short type;
	private Long bannerId;
	private String name;
}
