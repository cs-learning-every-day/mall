package io.github.xmchxup.missyou.api.v1;

import io.github.xmchxup.missyou.exception.http.NotFoundException;
import io.github.xmchxup.missyou.model.Activity;
import io.github.xmchxup.missyou.service.ActivityService;
import io.github.xmchxup.missyou.vo.ActivityCouponVO;
import io.github.xmchxup.missyou.vo.ActivityPureVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
@RequestMapping("activity")
@RestController
public class ActivityController {

	@Autowired
	private ActivityService activityService;

	@GetMapping("/name/{name}")
	public ActivityPureVO getHomeActivity(@PathVariable String name) {
		Optional<Activity> activityOptional = activityService.getByName(name);
		activityOptional.orElseThrow(() -> new NotFoundException(40001));
		return new ActivityPureVO(activityOptional.get());
	}

	@GetMapping("/name/{name}/with_coupon")
	public ActivityCouponVO getActivityWithCoupons(@PathVariable String name) {
		Optional<Activity> activityOptional = activityService.getByName(name);
		activityOptional.orElseThrow(() -> new NotFoundException(40001));
		return new ActivityCouponVO(activityOptional.get());
	}


}
