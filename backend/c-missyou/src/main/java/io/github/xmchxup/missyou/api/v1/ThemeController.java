package io.github.xmchxup.missyou.api.v1;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import io.github.xmchxup.missyou.exception.http.NotFoundException;
import io.github.xmchxup.missyou.model.Theme;
import io.github.xmchxup.missyou.service.ThemeService;
import io.github.xmchxup.missyou.vo.ThemePureVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
@RestController
@RequestMapping("/theme")
public class ThemeController {

	@Autowired
	private ThemeService themeService;

	@GetMapping("/by/names")
	public List<ThemePureVO> getThemeGroupByNames(@RequestParam(name = "names") String names) {
		List<String> nameList = Arrays.asList(names.split(","));
		List<Theme> themes = themeService.findByNames(nameList);

		List<ThemePureVO> list = new ArrayList<>();
		themes.forEach(theme -> {
			Mapper mapper = DozerBeanMapperBuilder.buildDefault();
			ThemePureVO vo = mapper.map(theme, ThemePureVO.class);
			list.add(vo);
		});
		return list;
	}

	@GetMapping("/name/{name}/with_spu")
	public Theme getThemeByNameWithSpu(@PathVariable String name) {
		Optional<Theme> themeOptional = themeService.findByName(name);
		return themeOptional.orElseThrow(() -> new NotFoundException(30003));
	}

}
