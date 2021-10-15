package io.github.xmchxup.missyou.repository;

import io.github.xmchxup.missyou.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
public interface ActivityRepository extends JpaRepository<Activity, Long> {

	Optional<Activity> findByName(String name);

	Optional<Activity> findByCouponListId(Long couponId);
}
