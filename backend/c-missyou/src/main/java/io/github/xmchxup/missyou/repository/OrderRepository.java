package io.github.xmchxup.missyou.repository;

import io.github.xmchxup.missyou.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.Optional;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

	Page<Order> findByExpiredTimeGreaterThanAndStatusAndUserId(Date now,
															   Integer status,
															   Long uid,
															   Pageable pageable);


	Page<Order> findByUserId(Long uid, Pageable pageable);

	Page<Order> findByUserIdAndStatus(Long uid, Integer status, Pageable pageable);

	Optional<Order> findFirstByUserIdAndId(Long uid, Long oid);

	Optional<Order> findFirstByOrderNo(String orderNo);

	@Modifying
	@Query("update Order o set o.status=:status where o.orderNo=:orderNo")
	int updateStatusByOrderNo(String orderNo, int status);
}
