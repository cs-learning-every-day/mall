package io.github.xmchxup.missyou.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import io.github.xmchxup.missyou.util.GenericAndJson;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
@Getter
@Setter
@Entity
@Where(clause = "delete_time is null and online = 1")
public class Sku extends BaseEntity {
	@Id
	private Long id;
	private BigDecimal price;
	private BigDecimal discountPrice;
	private Boolean online;
	private String img;
	private String title;
	private Long spuId;
	private Long categoryId;
	private Long rootCategoryId;

//	@Convert(converter = MapAndJson.class)
//	private Map<String, Object> test;

	//	@Convert(converter = ListAndJson.class)
//	private List<Spec> specs;
	private String specs;
	private String code;
	private Long stock;

	public BigDecimal getActualPrice() {
		return discountPrice == null ? this.price : this.discountPrice;
	}

	public List<Spec> getSpecs( ) {
		if (this.specs == null) {
			return Collections.emptyList();
		}
		return GenericAndJson.jsonToObject(this.specs, new TypeReference<List<Spec>>() {});
	}

	public void setSpecs(String specs) {
		if(specs.isEmpty()){
			return;
		}
		this.specs = GenericAndJson.objectToJson(specs);
	}

	@JsonIgnore
	public List<String> getSpecValueList() {
		return this.getSpecs().stream()
				.map(Spec::getValue)
				.collect(Collectors.toList());
	}
}
