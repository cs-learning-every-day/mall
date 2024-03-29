package io.github.xmchxup.missyou.model;

import io.github.xmchxup.missyou.dto.SkuInfoDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
@Getter
@Setter
@NoArgsConstructor
public class OrderSku {

	private Long id;
	private Long spuId;
	private BigDecimal finalPrice;
	private BigDecimal singlePrice;
	private List<String> specValues;
	private Integer count;
	private String img;
	private String title;

	public OrderSku(Sku sku, SkuInfoDTO skuInfoDTO) {
		this.id = sku.getId();
		this.spuId = sku.getSpuId();
		this.singlePrice = sku.getActualPrice();
		this.finalPrice = sku.getActualPrice().multiply(new BigDecimal(skuInfoDTO.getCount()));
		this.count = skuInfoDTO.getCount();
		this.img = sku.getImg();
		this.title = sku.getTitle();
		this.specValues = sku.getSpecValueList();
	}
}
