package io.github.xmchxup.missyou.vo;

import io.github.xmchxup.missyou.model.Activity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
@Getter
@Setter
public class ActivityCouponVO extends ActivityPureVO {
	private List<CouponPureVO> coupons;

	public ActivityCouponVO(Activity activity) {
		super(activity);
		coupons = activity.getCouponList()
				.stream()
				.map(CouponPureVO::new)
				.collect(Collectors.toList());
	}
}
