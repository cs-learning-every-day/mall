package io.github.xmchxup.missyou.service;

import io.github.xmchxup.missyou.model.Activity;
import io.github.xmchxup.missyou.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
@Service
public class ActivityService {

	@Autowired
	private ActivityRepository activityRepository;

	public Optional<Activity> getByName(String name) {
		return activityRepository.findByName(name);
	}
}
