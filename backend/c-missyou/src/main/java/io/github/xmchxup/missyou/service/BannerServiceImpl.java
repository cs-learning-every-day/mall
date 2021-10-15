package io.github.xmchxup.missyou.service;

import io.github.xmchxup.missyou.model.Banner;
import io.github.xmchxup.missyou.repository.BannerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
@Service
public class BannerServiceImpl implements BannerService  {

	@Autowired
	private BannerRepository bannerRepository;

	@Override
	public Banner getByName(String name) {
		return bannerRepository.findOneByName(name);
	}
}
