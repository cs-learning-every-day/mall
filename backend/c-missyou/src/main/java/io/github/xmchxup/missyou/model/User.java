package io.github.xmchxup.missyou.model;

import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Builder
@Where(clause = "delete_time is null")
public class User extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String openid;

	private String nickname;

	private Long unifyUid;

	private String email;

	private String password;

	private String mobile;

//	用户 分组 权限   例如钻石组
//	private String group;

//	@Convert(converter = MapAndJson.class)
//	private Map<String, Object> wxProfile;

}
