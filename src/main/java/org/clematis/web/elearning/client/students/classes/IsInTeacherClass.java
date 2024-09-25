package org.clematis.web.elearning.client.students.classes;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;
import com.gwtplatform.dispatch.shared.Action;

@GenDispatch(serviceName = Action.DEFAULT_SERVICE_NAME + "IsInCLass")
public class IsInTeacherClass {
   @In(1) 
   int studentId;
   @In(2) 
   int teacherId;
   @Out(1)
   boolean result;
}
