package org.clematis.web.elearning.client.events;

import org.clematis.web.elearning.shared.domain.Event;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;
import com.gwtplatform.dispatch.shared.Action;

@GenDispatch(serviceName = Action.DEFAULT_SERVICE_NAME + "EditEvent")
public class EditEvent {
	@In(1)
    int eventId;
	@In(2)
    String name;
    @In(3)
    int classId;
    @In(4)
    int teacherId;
    @In(5)
    String description;
    @In(6)
    int timeInterval;
    @In(7)
    int day;
    @Out(1)
    Event event;
}
