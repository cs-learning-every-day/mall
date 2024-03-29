package io.github.xmchxup.missyou.core.configuration;

import io.github.xmchxup.missyou.core.interceptors.PermissionInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {

	@Bean
	public HandlerInterceptor getPermissionInterceptor() {
		return new PermissionInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(this.getPermissionInterceptor());
	}
}
