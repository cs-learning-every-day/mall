package io.github.xmchxup.missyou.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
@Getter
@Setter
@Entity
@Where(clause = "delete_time is null and online = 1")
public class Category extends BaseEntity {
	@Id
	private Long id;
	private String name;
	private String description;
	private Boolean isRoot;
	private String parentId;
	private String img;
	private Long index;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "coupon_category",
			joinColumns = @JoinColumn(name = "category_id"),
			inverseJoinColumns = @JoinColumn(name = "coupon_id"))
	private List<Coupon> couponList;

}
