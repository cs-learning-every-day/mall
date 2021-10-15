package io.github.xmchxup.missyou.service;

import io.github.xmchxup.missyou.model.Sku;
import io.github.xmchxup.missyou.repository.SkuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
@Service
public class SkuService {
	@Autowired
	private SkuRepository skuRepository;

	public List<Sku> getSkuListByIds(List<Long> ids) {
		return skuRepository.findAllByIdIn(ids);
	}
}
