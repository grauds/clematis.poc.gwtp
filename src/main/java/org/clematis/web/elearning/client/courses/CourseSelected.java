package org.clematis.web.elearning.client.courses;

import org.clematis.web.elearning.shared.domain.Course;

import com.gwtplatform.dispatch.annotation.GenEvent;
import com.gwtplatform.dispatch.annotation.Order;

@GenEvent
public class CourseSelected {
	@Order(1)
	Course course;
	@Order(2)
	Boolean selected;
}
