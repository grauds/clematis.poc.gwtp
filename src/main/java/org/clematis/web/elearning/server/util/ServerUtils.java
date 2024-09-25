package org.clematis.web.elearning.server.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.clematis.web.elearning.shared.Utils;

public class ServerUtils {
	public static String dateToString(Date dt) {
		return dt == null ? null : new SimpleDateFormat(Utils.dateFormat).format(dt); 
	}
	
	public static Date stringToDate(String str) {
		try {
			return str == null ? null : new SimpleDateFormat(Utils.dateFormat).parse(str);
		} catch (ParseException e) {
			return null;
		}
	}

	public static Date truncateDate(Date dt) {
		// Delete hours, minutes, seconds.
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.set(Calendar.MILLISECOND, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.HOUR_OF_DAY, 0);
		return c.getTime();
	}

	public static String getMessageOrDefaultError(String message) {
		if (message == null || message.trim().isEmpty()) {
			return "Unknown error. Try to repeat operation. "
				+ "In case the error will persist, please, contact with "
				+ "developers and describe the circumstances in which an error occurs.";
		}
		return message;
	}

	public static int stringToInt(String value, int defaultValue) {
		if (value == null) {
			return defaultValue;
		}
		try {
			return Integer.parseInt(value);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	// Request timeout (Google's feature)
	public final static long requestTimeoutSeconds = 58;
	public final static long taskTimoutSeconds = 10 * 60 - 2; // 10 minutes

}
