package io.github.xmchxup.missyou.service;

import io.github.xmchxup.missyou.core.enumeration.CouponStatus;
import io.github.xmchxup.missyou.exception.http.NotFoundException;
import io.github.xmchxup.missyou.exception.http.ParameterException;
import io.github.xmchxup.missyou.model.Activity;
import io.github.xmchxup.missyou.model.Coupon;
import io.github.xmchxup.missyou.model.UserCoupon;
import io.github.xmchxup.missyou.repository.ActivityRepository;
import io.github.xmchxup.missyou.repository.CouponRepository;
import io.github.xmchxup.missyou.repository.UserCouponRepository;
import io.github.xmchxup.missyou.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
@Service
public class CouponService {

	@Autowired
	private UserCouponRepository userCouponRepository;

	@Autowired
	private ActivityRepository activityRepository;

	@Autowired
	private CouponRepository couponRepository;

	public List<Coupon> getByCategory(Long cid) {
		Date now = new Date();
		return couponRepository.findByCategory(cid, now);
	}

	public List<Coupon> getWholeStoreCoupons() {
		Date now = new Date();
		return couponRepository.findByWholeStore(true, now);
	}

	public List<Coupon> getMyAvailableCoupons(Long uid) {
		Date now = new Date();
		return couponRepository.findMyAvailable(uid, now);
	}

	public List<Coupon> getMyUsedCoupons(Long uid) {
		Date now = new Date();
		return couponRepository.findMyUsed(uid, now);
	}

	public List<Coupon> getMyExpiredCoupons(Long uid) {
		Date now = new Date();
		return couponRepository.findMyExpired(uid, now);
	}

	public void collectOneCoupon(Long uid, Long couponId) {
		this.couponRepository.findById(couponId)
				.orElseThrow(() -> new NotFoundException(40003));
		Activity activity = this.activityRepository.findByCouponListId(couponId)
				.orElseThrow(() -> new NotFoundException(40010));

		Date now = new Date();
		Boolean isIn = CommonUtil.isInTimeLine(now, activity.getStartTime(), activity.getEndTime());
		if (!isIn) {
			throw new ParameterException(40005);
		}

		this.userCouponRepository
				.findFirstByUserIdAndCouponId(uid, couponId)
				.ifPresent((userCoupon) -> {
					throw new ParameterException(40006);
				});

		UserCoupon userCouponNew = UserCoupon.builder()
				.userId(uid)
				.couponId(couponId)
				.createTime(now)
				.status(CouponStatus.AVAILABLE.getValue())
				.build();

		userCouponRepository.save(userCouponNew);
	}
}
