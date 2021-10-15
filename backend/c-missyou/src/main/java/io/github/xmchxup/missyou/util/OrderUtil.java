package io.github.xmchxup.missyou.util;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Calendar;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
@Component
public class OrderUtil {

	// B3230651812529
//    private static String yearCode;
//    @Value("${missyou.year-codes}")
//    public void setYearCodes(String yearCodes) {
//        String[] chars = yearCodes.split(",");
//        OrderUtil.yearCode = chars;
//    }

	public static String makeOrderNo() {
		StringBuffer joiner = new StringBuffer();
		Calendar calendar = Calendar.getInstance();
		char c = (char) (calendar.get(Calendar.YEAR) - 2020 + 65);
		String mills = String.valueOf(calendar.getTimeInMillis());
		String micro = LocalDateTime.now().toString();
		String random = String.valueOf(Math.random() * 1000).substring(0, 2);
		joiner.append(c)
				.append(Integer.toHexString(calendar.get(Calendar.MONTH) + 1).toUpperCase())
				.append(calendar.get(Calendar.DAY_OF_MONTH))
				.append(mills, mills.length() - 5, mills.length() - 1)
				.append(micro, micro.length() - 3, micro.length() - 1)
				.append(random);
		return joiner.toString();
	}
}
