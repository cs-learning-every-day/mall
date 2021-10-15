package io.github.xmchxup.missyou.core;

import io.github.xmchxup.missyou.model.User;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
public class LocalUser {
	private static final ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<>();

	public static void set(User user, String scope) {
		Map<String, Object> map = new HashMap<>();
		map.put("user", user);
		map.put("scope", scope);
		LocalUser.threadLocal.set(map);
	}

	public static void clear() {
		LocalUser.threadLocal.remove();
	}

	public static User getUser() {
		Map<String, Object> map = LocalUser.threadLocal.get();
		return (User) map.get("user");
	}

	public static String getScope() {
		Map<String, Object> map = LocalUser.threadLocal.get();
		return (String) map.get("scope");
	}
}
