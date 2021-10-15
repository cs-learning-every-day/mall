package io.github.xmchxup.missyou.core.configuration;

import io.github.xmchxup.missyou.core.hack.AutoPrefixUrlMapping;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
@Component
public class AutoPrefixConfiguration implements WebMvcRegistrations {

	@Override
	public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
		return new AutoPrefixUrlMapping();
	}
}
