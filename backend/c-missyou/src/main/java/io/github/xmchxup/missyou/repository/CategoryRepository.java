package io.github.xmchxup.missyou.repository;

import io.github.xmchxup.missyou.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {

	List<Category> findAllByIsRootOrderByIndexAsc(Boolean isRoot);
}
