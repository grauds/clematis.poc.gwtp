package org.clematis.web.elearning.client.teachers.classes;

import java.util.List;

import org.clematis.web.elearning.shared.domain.StudentsClass;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;
import com.gwtplatform.dispatch.shared.Action;

@GenDispatch(serviceName = Action.DEFAULT_SERVICE_NAME + "GetTeacherClasses")
public class GetTeacherClasses {
	@In(1)
	Integer teacherId;
	@Out(1)
	List<StudentsClass> classes;
}
