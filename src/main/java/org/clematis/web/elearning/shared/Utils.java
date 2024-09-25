package org.clematis.web.elearning.shared;

import java.util.Date;

public class Utils {

	public static Date nextDay(Date dt) {
		final long MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
		return dt == null ? null : new Date(dt.getTime() + MILLIS_IN_DAY);
	}

	/**
	 * Format used to pass Date between client and server to
	 * avoid convering from GMT to locale.
	 */
	public static final String dateFormat = "yyyy-MM-dd";

	public final static String EMAIL_REGEXP = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.(?:[a-zA-Z]{2,6})$";

	/**
	 * Validates email
	 * 
	 * @param email
	 * @return <code>true</code> if email is valid
	 */
	public static boolean isEmailValid(String email) {
		if (email == null || email.trim().isEmpty()) {
			return false;
		}
		return email.trim().matches(EMAIL_REGEXP);
	}

	public static boolean isDomainValid(String domain) {
		if (domain == null) {
			return false;
		}
		domain = domain.trim();
		if (domain.isEmpty()) {
			return false;
		}
		if (domain.length() < 3) {
			return false;
		}
		if (!domain.contains(".")) {
			return false;
		}
		if (domain.contains(" ")) {
			return false;
		}
		if (domain.indexOf(".") == 0) {
			return false;
		}
		if (domain.lastIndexOf(".") == domain.length() - 1) {
			return false;
		}
		if (domain.contains("@")) {
			return false;
		}
		return true;
	}
}
