package io.github.xmchxup.missyou.api.v1;

import io.github.xmchxup.missyou.exception.http.NotFoundException;
import io.github.xmchxup.missyou.model.Banner;
import io.github.xmchxup.missyou.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
@RestController
@RequestMapping("/banner")
@Validated
public class BannerController {

	@Autowired
	private BannerService bannerService;

//	@ScopeLevel
	@GetMapping("/name/{name}")
	public Banner getByName(@PathVariable @NotBlank String name) {
		System.out.println(name);
		Banner banner = bannerService.getByName(name);
		if (banner == null) {
			throw new NotFoundException(30005);
		}
		return banner;
	}


}
