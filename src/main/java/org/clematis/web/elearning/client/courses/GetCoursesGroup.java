package org.clematis.web.elearning.client.courses;

import java.util.List;


import org.clematis.web.elearning.shared.domain.CoursesGroup;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.Out;
import com.gwtplatform.dispatch.shared.Action;

@GenDispatch(serviceName = Action.DEFAULT_SERVICE_NAME + "CoursesGroups")
public class GetCoursesGroup {
	@Out(1)
	List<CoursesGroup> coursesGroups;
}
