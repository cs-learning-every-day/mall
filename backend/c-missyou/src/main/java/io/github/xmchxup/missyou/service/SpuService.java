package io.github.xmchxup.missyou.service;

import io.github.xmchxup.missyou.model.Spu;
import io.github.xmchxup.missyou.repository.SpuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
@Service
public class SpuService {
	@Autowired
	private SpuRepository spuRepository;

	public Spu getSpu(Long id) {
		return this.spuRepository.findOneById(id);
	}

	public Page<Spu> getLatestPagingSpu(Integer pageNum, Integer size) {
		Pageable pageable = PageRequest.of(pageNum, size,
				Sort.by("createTime").descending());
		return this.spuRepository.findAll(pageable);
	}

	public Page<Spu> getByCategoryId(Long cid, Boolean isRoot,
									 Integer pageNum, Integer size) {
		Pageable pageable = PageRequest.of(pageNum, size);
		if (isRoot) {
			return this.spuRepository.findByRootCategoryIdOrderByCreateTimeDesc(cid, pageable);
		}
		return this.spuRepository.findByCategoryIdOrderByCreateTimeDesc(cid, pageable);
	}
}
