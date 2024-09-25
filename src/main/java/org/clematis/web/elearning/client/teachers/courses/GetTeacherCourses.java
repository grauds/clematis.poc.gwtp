package org.clematis.web.elearning.client.teachers.courses;

import java.util.List;

import org.clematis.web.elearning.shared.domain.Course;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;
import com.gwtplatform.dispatch.shared.Action;

@GenDispatch(serviceName = Action.DEFAULT_SERVICE_NAME + "GetTeacherCourses")
public class GetTeacherCourses {
	@In(1)
	Integer teacherId;
	@Out(1)
	List<Course> courses;
}
