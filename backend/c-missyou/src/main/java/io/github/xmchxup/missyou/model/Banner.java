package io.github.xmchxup.missyou.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
@Entity
@Getter
@Setter
public class Banner extends BaseEntity {
	@Id
	private Long id;
	private String name;
	private String description;
	private String title;
	private String img;

//	Lazy在返回json数据没生效的原因：
//	在此返回序列化数据时，序列化库会读取所有属性，调用get方法。
//	可以设置VO实体
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name="bannerId")
	private List<BannerItem> items;
}
