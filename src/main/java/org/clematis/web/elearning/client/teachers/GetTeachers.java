package org.clematis.web.elearning.client.teachers;

import java.util.List;

import org.clematis.web.elearning.shared.domain.Course;
import org.clematis.web.elearning.shared.domain.Teacher;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Optional;
import com.gwtplatform.dispatch.annotation.Out;
import com.gwtplatform.dispatch.shared.Action;

@GenDispatch(serviceName = Action.DEFAULT_SERVICE_NAME + "Teachers")
public class GetTeachers {
	@In(1)
	@Optional	
	List<Course> courses;	
	@Out(1)
	List<Teacher> teachers;
}
