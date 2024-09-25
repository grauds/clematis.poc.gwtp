package org.clematis.web.elearning.client.students.classes;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.shared.Action;

@GenDispatch(serviceName = Action.DEFAULT_SERVICE_NAME + "JoinClass")
public class JoinClass {
	   @In(1) 
	   int studentId;
	   @In(2) 
	   int classId;
}
