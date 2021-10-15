package io.github.xmchxup.missyou.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
@Getter
@Setter
public class OrderAddressDTO {
	private String userName;
	private String province;
	private String city;
	private String county;
	private String mobile;
	private String nationalCode;    //国家码
	private String postalCode;      //邮政编码
	private String detail;
}
