package org.clematis.web.elearning.client.events;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.shared.Action;

@GenDispatch(serviceName = Action.DEFAULT_SERVICE_NAME + "DeleteEvent")
public class DeleteEvent {
	@In(1)
    int eventId;
}
