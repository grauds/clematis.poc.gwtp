package org.clematis.web.elearning.client.place;

public class NameTokens {
	
	public static final String GENERAL = "general";
	
	public static final String MAIN = "main";
	public static final String PAGE_404 = "page404";
	
	public static final String REGISTER = "register";
	public static final String CONFIRM = "confirm";

	public static final String TEACHERS = "teachers";
	public static final String COURSES = "courses";
	
	public static final String CABINET = "cabinet";
	public static final String CABINET_HOLDER = "cabinet_holder";

	public static final String SIGNIN = "signin";

	public static final String STUDENT_VIDEO = "stvideo";
	public static final String TEACHER_VIDEO = "tvideo";

	public static final String TEACHER_SELECT_COURSES = "tcourses";
	public static final String TEACHER_STUDENTS = "tstudents";
	public static final String TEACHER_SCHEDULE = "tschedule";

	public static final String STUDENT_SCHEDULE = "stschedule";

	public static final String STUDENT_BUDGET = "stbudget";

	public static String getStvideo() {
		return STUDENT_VIDEO;
	}

	public static String getTvideo() {
		return TEACHER_VIDEO;
	}

	public static String getCourses() {
		return COURSES;
	}

	public static String getTcourses() {
		return TEACHER_SELECT_COURSES;
	}

	public static String getTstudents() {
		return TEACHER_STUDENTS;
	}

	public static String getTschedule() {
		return TEACHER_SCHEDULE;
	}

	public static String getStschedule() {
		return STUDENT_SCHEDULE;
	}

	public static String getBudget() {
		return STUDENT_BUDGET;
	}
}
