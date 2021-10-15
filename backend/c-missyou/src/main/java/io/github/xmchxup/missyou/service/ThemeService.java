package io.github.xmchxup.missyou.service;

import io.github.xmchxup.missyou.model.Theme;
import io.github.xmchxup.missyou.repository.ThemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
@Service
public class ThemeService {
	@Autowired
	private ThemeRepository themeRepository;

	public List<Theme> findByNames(List<String> nameList) {
		return themeRepository.findByNames(nameList);
	}

	public Optional<Theme> findByName(String name) {
		return themeRepository.findByName(name);
	}
}
