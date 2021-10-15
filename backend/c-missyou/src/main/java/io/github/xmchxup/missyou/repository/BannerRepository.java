package io.github.xmchxup.missyou.repository;


import io.github.xmchxup.missyou.model.Banner;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
public interface BannerRepository extends JpaRepository<Banner, Long> {
	Banner findOneById(Long id);
	Banner findOneByName(String name);
}
