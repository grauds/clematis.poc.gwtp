package org.clematis.web.elearning.client.general;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class HintWidget extends Composite {

	private static HintWidgetUiBinder uiBinder = GWT.create(HintWidgetUiBinder.class);

	interface HintWidgetUiBinder extends UiBinder<Widget, HintWidget> {
	}
	
	@UiField Label title;
	@UiField Label body;

	public HintWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setTitle(String title) {
		this.title.setText(title);
	}
	
	public void setBody(String body) {
		this.body.setText(body);
	}
}
