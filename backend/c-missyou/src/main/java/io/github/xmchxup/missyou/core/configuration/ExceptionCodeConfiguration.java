package io.github.xmchxup.missyou.core.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
@Component
@ConfigurationProperties(prefix = "xmchx")
@PropertySource(value = "classpath:config/exception-code.properties")
public class ExceptionCodeConfiguration {
	private Map<Integer, String> codes = new HashMap<>();

	public String getMessage(int code) {
		return codes.get(code);
	}

	public Map<Integer, String> getCodes() {
		return codes;
	}

	public void setCodes(Map<Integer, String> codes) {
		this.codes = codes;
	}
}
