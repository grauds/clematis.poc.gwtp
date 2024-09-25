package org.clematis.web.elearning.client.students.classes;

import java.util.List;

import org.clematis.web.elearning.shared.domain.StudentsClass;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Optional;
import com.gwtplatform.dispatch.annotation.Out;
import com.gwtplatform.dispatch.shared.Action;

@GenDispatch(serviceName = Action.DEFAULT_SERVICE_NAME + "GetStudentClasses")
public class GetStudentClasses {
   @In(1)
   Integer studentId;
   @In(2)
   @Optional
   Integer teacherId;
   @Out(1)
   List<StudentsClass> classes;
}
