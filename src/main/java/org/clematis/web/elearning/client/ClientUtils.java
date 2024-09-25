package org.clematis.web.elearning.client;

import java.util.Date;

import org.clematis.web.elearning.shared.Utils;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.i18n.client.constants.NumberConstants;

public class ClientUtils {

	/**
	 * This is the time the client should wait for request result. After this
	 * time request is lost. 
	 */
	public final static int requestTimeoutSeconds = 65;

	public static String dateToString(Date dt) {
		return dt == null ? null : DateTimeFormat.getFormat(Utils.dateFormat).format(dt); 
	}
	
	public static Date stringToDate(String str) {
		return str == null ? null : DateTimeFormat.getFormat(Utils.dateFormat).parse(str);
	}

	/**
	 * http://code.google.com/p/gwt-examples/wiki/gwtDateTime
	 * @param dt
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static Date truncateDate(Date dt) {
		if (dt == null) {
			return null;
		}
        
        Date dateStart = new Date(dt.getTime()); 
        dateStart.setMinutes(0); // minute 0
        dateStart.setHours(0);   // hour 0
        dateStart.setSeconds(0); // second 0
        
        return dateStart;
	}

	public static String getMessageOrDefaultError(String message) {
		if (message == null || message.trim().isEmpty()) {
			return "Неизвестная ошибка. Попробуйте повторить операцию. "
				+ "Если ошибка будет повторяться, обратитесь, пожалуйста, "
				+ "к разработчикам с описанием условий, при которых "
				+ "возникает эта ошибка";
		}
		return message;
	}

	private final static NumberConstants constants = LocaleInfo.getCurrentLocale().getNumberConstants();
	
	public static String removeSeparatorsFromBigDecimalString(String value) {
		return value == null ? null : value.replace(constants.groupingSeparator(), "").replace(constants.decimalSeparator(), ".");
	}

}
