package io.github.xmchxup.missyou.api.v1;

import io.github.xmchxup.missyou.bo.PageCounter;
import io.github.xmchxup.missyou.exception.http.NotFoundException;
import io.github.xmchxup.missyou.model.Spu;
import io.github.xmchxup.missyou.service.SpuService;
import io.github.xmchxup.missyou.util.CommonUtil;
import io.github.xmchxup.missyou.vo.PagingDozer;
import io.github.xmchxup.missyou.vo.SpuSimplifyVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
@RestController
@RequestMapping("/spu")
@Validated
public class SpuController {

	@Autowired
	private SpuService spuService;

	@GetMapping("/id/{id}/detail")
	public Spu getDetail(@PathVariable @Positive Long id) {
		Spu spu = this.spuService.getSpu(id);
		if (spu == null) {
			throw new NotFoundException(30003);
		}
		return spu;
	}

	@GetMapping("/id/{id}/simplify")
	public SpuSimplifyVO getSimplifySpu(@PathVariable @Positive Long id) {
		Spu spu = this.spuService.getSpu(id);
		if (spu == null) {
			throw new NotFoundException(30003);
		}
		// copy list时可以使用dozermapper进行深copy
		SpuSimplifyVO vo = new SpuSimplifyVO();
		BeanUtils.copyProperties(spu, vo);
		return vo;
	}

	@GetMapping("/latest")
	public PagingDozer<Spu, SpuSimplifyVO> getLatestSpuList(@RequestParam(defaultValue = "0") Integer start,
															@RequestParam(defaultValue = "10") Integer count) {
		PageCounter pageCounter = CommonUtil.convertToPageParameter(start, count);
		Page<Spu> page = this.spuService.getLatestPagingSpu(
				pageCounter.getPage(),
				pageCounter.getCount()
		);
//		dozermapper
//		Mapper mapper = DozerBeanMapperBuilder.buildDefault();
//		List<SpuSimplifyVO> result = new ArrayList<>();
//
//		spuList.forEach(s -> {
//			SpuSimplifyVO vo = mapper.map(s, SpuSimplifyVO.class);
//			result.add(vo);
//		});
		return new PagingDozer<>(page, SpuSimplifyVO.class);
	}

	@GetMapping("/by/category/{id}")
	public PagingDozer<Spu, SpuSimplifyVO> getByCategoryId(@PathVariable(name = "id")
														   @Positive(message = "{id.positive}") Long id,
														   @RequestParam(name = "is_root", defaultValue = "false")
																   Boolean isRoot,
														   @RequestParam(name = "start", defaultValue = "0")
																   Integer start,
														   @RequestParam(name = "count", defaultValue = "10")
																   Integer count) {
		PageCounter pageCounter = CommonUtil.convertToPageParameter(start, count);
		Page<Spu> page = this.spuService.getByCategoryId(id, isRoot,
				pageCounter.getPage(), pageCounter.getCount());
		return new PagingDozer<>(page, SpuSimplifyVO.class);
	}

}
