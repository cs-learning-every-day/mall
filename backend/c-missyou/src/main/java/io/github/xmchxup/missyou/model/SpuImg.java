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
@Table(name = "spu_img", schema = "sleeve", catalog = "")
public class SpuImg extends BaseEntity {
	@Id
	private Long id;
	private String img;
	private Long spuId;
}
