package io.github.xmchxup.missyou.api.v1;

import io.github.xmchxup.missyou.exception.http.NotFoundException;
import io.github.xmchxup.missyou.model.Category;
import io.github.xmchxup.missyou.model.GridCategory;
import io.github.xmchxup.missyou.service.CategoryService;
import io.github.xmchxup.missyou.service.GridCategoryService;
import io.github.xmchxup.missyou.vo.CategoriesAllVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;

	@Autowired
	private GridCategoryService gridCategoryService;


	@GetMapping("/all")
	public CategoriesAllVO getAll() {
		Map<Integer, List<Category>> map = categoryService.getAll();
		return new CategoriesAllVO(map);
	}

	@GetMapping("/grid/all")
	public List<GridCategory> getGridCategoryList(){
		List<GridCategory> list = gridCategoryService.getGridCategoryList();
		if(list.isEmpty()){
			throw new NotFoundException(30009);
		}
		return list;
	}

}
