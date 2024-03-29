package io.github.xmchxup.missyou.vo;

import io.github.xmchxup.missyou.model.Activity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.sql.Timestamp;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
@Getter
@Setter
@NoArgsConstructor
public class ActivityPureVO {

	private Long id;
	private String title;
	private String entranceImg;     //活动入口图片
	private Byte online;
	private String remark;
	private Timestamp startTime;
	private Timestamp endTime;

	public ActivityPureVO(Activity activity) {
		BeanUtils.copyProperties(activity, this);
	}

	public ActivityPureVO(Object object) {
		BeanUtils.copyProperties(object, this);
	}


}
