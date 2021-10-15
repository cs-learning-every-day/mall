package io.github.xmchxup.missyou.repository;

import io.github.xmchxup.missyou.model.Spu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
public interface SpuRepository extends JpaRepository<Spu, Long> {
	Spu findOneById(Long id);

	Page<Spu> findByCategoryIdOrderByCreateTimeDesc(Long cid, Pageable pageable);
	Page<Spu> findByRootCategoryIdOrderByCreateTimeDesc(Long cid, Pageable pageable);
}
