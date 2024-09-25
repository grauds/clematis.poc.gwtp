package org.clematis.web.elearning.client.general;

import com.gwtplatform.dispatch.annotation.GenEvent;
import com.gwtplatform.dispatch.annotation.Order;

@GenEvent
public class ShowWarnMessage {
	@Order(1)
	String warnMessage;
}
