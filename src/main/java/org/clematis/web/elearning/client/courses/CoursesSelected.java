package org.clematis.web.elearning.client.courses;

import org.clematis.web.elearning.shared.domain.CoursesGroup;

import com.gwtplatform.dispatch.annotation.GenEvent;
import com.gwtplatform.dispatch.annotation.Order;

@GenEvent
public class CoursesSelected {
	@Order(1)
	CoursesGroup coursesGroup;
	@Order(2)
	Boolean selected;
}
