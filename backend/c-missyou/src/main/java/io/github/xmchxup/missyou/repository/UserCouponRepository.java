package io.github.xmchxup.missyou.repository;

import io.github.xmchxup.missyou.model.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
public interface UserCouponRepository extends JpaRepository<UserCoupon, Long> {
	Optional<UserCoupon> findFirstByUserIdAndCouponId(Long uid, Long couponId);

	Optional<UserCoupon> findFirstByUserIdAndCouponIdAndStatusAndOrderIdIsNull(Long uid,
																			   Long couponId,
																			   Integer status);

	@Modifying
	@Query("update UserCoupon uc \n" +
			"set uc.status = 2, uc.orderId=:oid\n" +
			"where uc.userId = :uid\n" +
			"and uc.couponId = :couponId\n" +
			"and uc.status = 1\n" +
			"and uc.orderId is null")
	int writeOff(Long couponId, Long oid, Long uid);
}
