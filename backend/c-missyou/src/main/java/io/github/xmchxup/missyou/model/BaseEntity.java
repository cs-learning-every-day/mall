package io.github.xmchxup.missyou.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {
	@JsonIgnore
	@Column(insertable = false, updatable = false)
	private Timestamp createTime;
	@JsonIgnore
	@Column(insertable = false, updatable = false)
	private Timestamp updateTime;
	@JsonIgnore
	@Column(insertable = false, updatable = false)
	private Timestamp deleteTime;
}
