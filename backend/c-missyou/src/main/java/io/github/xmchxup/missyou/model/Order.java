package io.github.xmchxup.missyou.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import io.github.xmchxup.missyou.core.enumeration.OrderStatus;
import io.github.xmchxup.missyou.dto.OrderAddressDTO;
import io.github.xmchxup.missyou.util.CommonUtil;
import io.github.xmchxup.missyou.util.GenericAndJson;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "delete_time is null")
@Table(name = "`Order`") /*Order是mmysql保留的关键字*/
public class Order extends BaseEntity {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;
	private String orderNo;
	private Long userId;
	private BigDecimal totalPrice;  //商品原始价格
	private Long totalCount;

	private String snapImg;
	private String snapTitle;
	private String snapItems;
	private String snapAddress;

	private String prepayId;        //微信支付
	private BigDecimal finalTotalPrice; //订单最终价格
	private Integer status;

	private Date placedTime;
	private Date expiredTime;

	@JsonIgnore
	public OrderStatus getStatusEnum() {
		return OrderStatus.toType(this.status);
	}

	public Boolean needCancel(){
		if(!this.getStatusEnum().equals(OrderStatus.UNPAID)){
			return true;
		}
		return CommonUtil.isOutOfDate(this.getExpiredTime());
	}

	public void setSnapItems(List<OrderSku> orderSkuList){
		if(orderSkuList.isEmpty()){
			return;
		}
		this.snapItems = GenericAndJson.objectToJson(orderSkuList);
	}

	public List<OrderSku> getSnapItems(){
		List<OrderSku> list = GenericAndJson.jsonToObject(this.snapItems,
				new TypeReference<List<OrderSku>>() {
				});
		return list;
	}

	public void setSnapAddress(OrderAddressDTO orderAddressDTO){
		this.snapAddress = GenericAndJson.objectToJson(orderAddressDTO);
	}

	public OrderAddressDTO getSnapAddress(){
		if(null == this.snapAddress){
			return null;
		}
		return GenericAndJson.jsonToObject(this.snapAddress, new TypeReference<OrderAddressDTO>() {
		});
	}

}
