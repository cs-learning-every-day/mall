package io.github.xmchxup.missyou.repository;

import io.github.xmchxup.missyou.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
public interface CouponRepository extends JpaRepository<Coupon, Long> {

//	join一个导航属性表示关联三表（中间表）
	@Query("select c from Coupon c \n" +
			"join c.categoryList ca\n" +
			"join Activity a on a.id = c.activityId\n" +
			"where ca.id = :cid\n" +
			"and a.startTime < :now\n" +
			"and a.endTime > :now")
	List<Coupon> findByCategory(Long cid, Date now);

	@Query("select c from Coupon c\n" +
			"join Activity a on c.activityId = a.id\n" +
			"where c.wholeStore = :isWholeStore\n" +
			"and a.startTime < :now\n" +
			"and a.endTime > :now")
	List<Coupon> findByWholeStore(boolean isWholeStore, Date now);

	@Query("select c from Coupon c\n" +
			"join UserCoupon uc " +
			"on c.id = uc.couponId\n" +
			"join User u " +
			"on u.id = uc.userId\n" +
			"where uc.status = 1\n" +
			"and u.id = :uid\n" +
			"and c.startTime < :now\n" +
			"and c.endTime > :now\n" +
			"and uc.orderId is null")
	List<Coupon> findMyAvailable(Long uid, Date now);

	@Query("select c from Coupon c\n" +
			"join UserCoupon uc\n" +
			"on c.id = uc.couponId\n" +
			"join User u\n" +
			"on u.id = uc.userId\n" +
			"where u.id = :uid\n" +
			"and uc.status = 2\n" +
			"and uc.orderId is not null\n" +
			"and c.startTime < :now\n" +
			"and c.endTime > :now")
	List<Coupon> findMyUsed(@Param("uid") Long uid,@Param("now") Date now);

	@Query("select c from Coupon c\n" +
			"join UserCoupon uc\n" +
			"on c.id = uc.couponId\n" +
			"join User u\n" +
			"on u.id = uc.userId\n" +
			"where u.id = :uid\n" +
			"and uc.orderId is null\n" +
			"and uc.status <> 2\n" +
			"and c.endTime < :now")
	List<Coupon> findMyExpired(@Param("uid") Long uid,@Param("now") Date now);
}
