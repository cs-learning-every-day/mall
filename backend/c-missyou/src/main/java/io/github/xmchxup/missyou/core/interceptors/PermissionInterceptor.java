package io.github.xmchxup.missyou.core.interceptors;

import com.auth0.jwt.interfaces.Claim;
import io.github.xmchxup.missyou.core.LocalUser;
import io.github.xmchxup.missyou.exception.http.ForbiddenException;
import io.github.xmchxup.missyou.exception.http.UnAuthenticatedException;
import io.github.xmchxup.missyou.model.User;
import io.github.xmchxup.missyou.service.UserService;
import io.github.xmchxup.missyou.util.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
public class PermissionInterceptor extends HandlerInterceptorAdapter {
//	implements HandlerInterceptor

	@Autowired
	private UserService userService;

	public PermissionInterceptor() {
		super();
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
							 HttpServletResponse response,
							 Object handler) {
		Optional<ScopeLevel> scopeLevel = this.getScopeLevel(handler);
		if (scopeLevel.isEmpty()) {
			return true;
		}

		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.isEmpty(bearerToken)) {
			throw new UnAuthenticatedException(10004);
		}
//		Bearer <token>
		if (!bearerToken.startsWith("Bearer")) {
			throw new UnAuthenticatedException(10004);
		}

		String[] token = bearerToken.split(" ");
		if (token.length != 2) {
			throw new UnAuthenticatedException(10004);
		}
		Optional<Map<String, Claim>> optionalMap = JwtToken.getClaims(token[1]);
		Map<String, Claim> map =
				optionalMap.orElseThrow(() -> new UnAuthenticatedException(10004));
		boolean valid = this.hasPermission(scopeLevel.get(), map);
		if (valid) {
			this.setToThreadLocal(map);
		}
		return valid;
	}

	@Override
	public void postHandle(HttpServletRequest request,
						   HttpServletResponse response,
						   Object handler,
						   ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
								HttpServletResponse response,
								Object handler, Exception ex) throws Exception {
		LocalUser.clear();
		super.afterCompletion(request, response, handler, ex);
	}

	private void setToThreadLocal(Map<String, Claim> map) {
		Long uid = map.get("uid").asLong();
		String scope = map.get("scope").asString();
		User user = userService.getUserById(uid);
		LocalUser.set(user, scope);
	}

	private boolean hasPermission(ScopeLevel scopeLevel, Map<String, Claim> map) {
		int level = scopeLevel.value();
		int scope = map.get("scope").asInt();
		if (level > scope) {
			throw new ForbiddenException(10005);
		}
		return true;
	}

	private Optional<ScopeLevel> getScopeLevel(Object handler) {
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			ScopeLevel scopeLevel = handlerMethod.getMethod().getAnnotation(ScopeLevel.class);
			if (scopeLevel == null) {
				return Optional.empty();
			}
			return Optional.of(scopeLevel);
		}
		return Optional.empty();
	}
}
