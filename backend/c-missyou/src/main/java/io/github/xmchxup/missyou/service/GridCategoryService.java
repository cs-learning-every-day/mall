package io.github.xmchxup.missyou.service;

import io.github.xmchxup.missyou.model.GridCategory;
import io.github.xmchxup.missyou.repository.GridCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
@Service
public class GridCategoryService {
	@Autowired
	private GridCategoryRepository gridCategoryRepository;

	public List<GridCategory> getGridCategoryList() {
		return gridCategoryRepository.findAll();
	}
}
