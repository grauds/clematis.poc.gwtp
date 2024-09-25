package org.clematis.web.elearning.shared.domain;

import java.util.ArrayList;
import java.util.List;

public class CoursesGroup extends BasicEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1903826801729730819L;
	  
	private String name;
	private String description;
	private List<Course> courses;
	
	public CoursesGroup(Course course) {
		getCourses().add(course);
	}
	
	public CoursesGroup() {
		super();
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "CoursesGroup [name=" + name + ", description=" + description
				+ ", courses=" + courses + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((courses == null) ? 0 : courses.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		CoursesGroup other = (CoursesGroup) obj;
		if (courses == null) {
			if (other.courses != null)
				return false;
		} else if (!courses.equals(other.courses))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	public void setGroups(List<Course> courses) {
        this.courses = courses;		
	}
	
	public List<Course> getCourses() {
		if (courses==null) {
			courses = new ArrayList<Course>();
		}
		return courses;
	}
	
}
