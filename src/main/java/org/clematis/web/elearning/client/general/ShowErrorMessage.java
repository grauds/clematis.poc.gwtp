package org.clematis.web.elearning.client.general;

import com.gwtplatform.dispatch.annotation.GenEvent;
import com.gwtplatform.dispatch.annotation.Order;

@GenEvent
public class ShowErrorMessage {
	@Order(1)
	String errorMessage;
	@Order(2)
	Throwable exceptionDetails;
}
