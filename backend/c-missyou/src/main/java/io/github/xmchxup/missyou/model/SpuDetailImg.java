package io.github.xmchxup.missyou.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
@Getter
@Setter
@Entity
@Table(name = "spu_detail_img", schema = "sleeve", catalog = "")
public class SpuDetailImg extends BaseEntity {
	@Id
	private Long id;
	private String img;
	private Long spuId;
	private Long index;
}
