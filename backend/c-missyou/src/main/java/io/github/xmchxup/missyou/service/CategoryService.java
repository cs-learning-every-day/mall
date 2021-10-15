package io.github.xmchxup.missyou.service;

import io.github.xmchxup.missyou.model.Category;
import io.github.xmchxup.missyou.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
@Service
public class CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;

	public Map<Integer, List<Category>> getAll() {
		List<Category> roots = categoryRepository.findAllByIsRootOrderByIndexAsc(true);
		List<Category> subs = categoryRepository.findAllByIsRootOrderByIndexAsc(false);

		Map<Integer, List<Category>> map = new HashMap<>();
		map.put(1, roots);
		map.put(2, subs);
		return map;
	}
}
