package io.github.xmchxup.missyou.repository;

import io.github.xmchxup.missyou.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByOpenid(String openid);

	User findFirstById(Long id);
}
