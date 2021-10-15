package io.github.xmchxup.missyou.api.v1;

import io.github.xmchxup.missyou.core.LocalUser;
import io.github.xmchxup.missyou.core.UnifyResponse;
import io.github.xmchxup.missyou.core.enumeration.CouponStatus;
import io.github.xmchxup.missyou.core.interceptors.ScopeLevel;
import io.github.xmchxup.missyou.exception.http.ParameterException;
import io.github.xmchxup.missyou.model.Coupon;
import io.github.xmchxup.missyou.model.User;
import io.github.xmchxup.missyou.service.CouponService;
import io.github.xmchxup.missyou.vo.CouponCategoryVO;
import io.github.xmchxup.missyou.vo.CouponPureVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
@RequestMapping("coupon")
@RestController
public class CouponController {
	@Autowired
	private CouponService couponService;

	@GetMapping("/by/category/{cid}")
	public List<CouponPureVO> getCouponListByCategory(
			@PathVariable Long cid) {
		List<Coupon> coupons = couponService.getByCategory(cid);
		if (coupons.isEmpty()) {
			return Collections.emptyList();
		}
		return CouponPureVO.getList(coupons);
	}

	@GetMapping("/whole_store")
	public List<CouponPureVO> getWholeStoreCouponList() {
		List<Coupon> coupons = couponService.getWholeStoreCoupons();
		if (coupons.isEmpty()) {
			return Collections.emptyList();
		}
		return CouponPureVO.getList(coupons);
	}

	@ScopeLevel
	@PostMapping("/collect/{id}")
	public void collectCoupon(@PathVariable Long id) {
		Long uid = LocalUser.getUser().getId();
		couponService.collectOneCoupon(uid, id);
		UnifyResponse.createSuccess(0);
	}

	@ScopeLevel
	@GetMapping("/myself/by/status/{status}")
	public List<CouponPureVO> getMyCouponByStatus(@PathVariable Integer status) {
		Long uid = LocalUser.getUser().getId();
		List<Coupon> couponList;
		switch (CouponStatus.toType(status)) {
			case AVAILABLE:
				couponList = this.couponService.getMyAvailableCoupons(uid);
				break;
			case USED:
				couponList = this.couponService.getMyUsedCoupons(uid);
				break;
			case EXPIRED:
				couponList = this.couponService.getMyExpiredCoupons(uid);
				break;
			default:
				throw new ParameterException(40001);
		}
		return CouponPureVO.getList(couponList);
	}

	@ScopeLevel
	@GetMapping("/myself/available/with_category")
	public List<CouponCategoryVO> getUserCouponWithCategory(){
		User user = LocalUser.getUser();
		List<Coupon> coupons = couponService.getMyAvailableCoupons(user.getId());
		if (coupons.isEmpty()) {
			return Collections.emptyList();
		}

		return coupons.stream().map(coupon -> {
			CouponCategoryVO vo = new CouponCategoryVO(coupon);
			return vo;
		}).collect(Collectors.toList());
	}



}
