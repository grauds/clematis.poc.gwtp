package org.clematis.web.elearning.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class SendMailWidget extends Composite {

	private static SendMailWidgetUiBinder uiBinder = GWT
			.create(SendMailWidgetUiBinder.class);

	interface SendMailWidgetUiBinder extends UiBinder<Widget, SendMailWidget> {
	}

	public SendMailWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
