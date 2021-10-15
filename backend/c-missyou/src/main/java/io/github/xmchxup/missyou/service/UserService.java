package io.github.xmchxup.missyou.service;

import io.github.xmchxup.missyou.model.User;
import io.github.xmchxup.missyou.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	public User getUserById(Long id) {
		return userRepository.findFirstById(id);
	}
}
