package io.github.xmchxup.missyou.repository;

import io.github.xmchxup.missyou.model.Sku;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
public interface SkuRepository extends JpaRepository<Sku, Long> {

	List<Sku> findAllByIdIn(List<Long> ids);

//	乐观锁
//	select stock, version from sku
//	version = 1
//	update sku set s.stock=newValue, version = version+1 where version=1
	@Modifying
	@Query("update Sku s \n" +
			"set s.stock = s.stock - :quantity\n" +
			"where s.id = :sid\n" +
			"and s.stock >= :quantity")
	int reduceStock(Long sid, Long quantity);

}
