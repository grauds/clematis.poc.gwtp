package org.clematis.web.elearning.shared.domain;

import java.util.List;

public class Teacher extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4679860819229511972L;
	
	private List<Course> courses;

	public List<Course> getCourses() {
		return courses;
	}

	public int getClassId() {
		return 0;
	}

}
