package io.github.xmchxup.missyou.api.v1;

import io.github.xmchxup.missyou.dto.TokenDTO;
import io.github.xmchxup.missyou.dto.TokenGetDTO;
import io.github.xmchxup.missyou.exception.http.NotFoundException;
import io.github.xmchxup.missyou.service.WxAuthenticationService;
import io.github.xmchxup.missyou.util.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
@RequestMapping(value = "token")
@RestController
public class TokenController {

	@Autowired
	private WxAuthenticationService wxAuthenticationService;

	@PostMapping("")
	public Map<String, String> getToken(@RequestBody @Validated TokenGetDTO userData) {
		Map<String, String> map = new HashMap<>();
		String token = "";

		switch (userData.getType()) {
			case USER_WX:
				token = wxAuthenticationService.code2Session(userData.getAccount());
				break;
			case USER_EMAIL:
				break;
			default:
				throw new NotFoundException(10003);
		}
		map.put("token", token);
		return map;
	}

	@PostMapping("/verify")
	public Map<String, Boolean> verify(@RequestBody TokenDTO token) {
		Map<String, Boolean> map = new HashMap<>();
		Boolean valid = JwtToken.verifyToken(token.getToken());
		map.put("is_valid", valid);
		return map;
	}
}
