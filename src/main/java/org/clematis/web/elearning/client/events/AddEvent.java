package org.clematis.web.elearning.client.events;

import org.clematis.web.elearning.shared.domain.Event;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;
import com.gwtplatform.dispatch.shared.Action;

@GenDispatch(serviceName = Action.DEFAULT_SERVICE_NAME + "AddEvent")
public class AddEvent {
    @In(1)
    String name;
    @In(2)
    int classId;
    @In(3)
    int teacherId;
    @In(4)
    String description;
    @In(5)
    int timeInterval;
    @In(6)
    int day;
    @Out(1)
    Event event;
}
