package org.clematis.web.elearning.client.students.schedule;

import java.util.List;

import org.clematis.web.elearning.shared.domain.Event;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;
import com.gwtplatform.dispatch.shared.Action;

@GenDispatch(serviceName = Action.DEFAULT_SERVICE_NAME + "GetStudentEvents")
public class GetStudentEvents {
    @In(1)
    int studentId;
    @Out(1)
    List<Event> events;
}
