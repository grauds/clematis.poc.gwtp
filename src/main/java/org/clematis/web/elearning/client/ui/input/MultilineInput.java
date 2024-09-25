package org.clematis.web.elearning.client.ui.input;

import org.clematis.web.elearning.client.ui.input.elements.InputElement;
import org.clematis.web.elearning.client.ui.input.elements.InputElementInterface;
import org.clematis.web.elearning.client.ui.input.resources.InputResources;

import com.google.gwt.user.client.ui.TextArea;


public class MultilineInput extends InputBase<String, InputElementInterface<String>> {
	
	public MultilineInput(){
		super(new InputElement(new TextArea()));
	}

	@Override
	protected String getInputStyleName() {
		return InputResources.INSTANCE.css().textarea();
	}

	@Override
	protected String getInputErrorStyleName() {
		return InputResources.INSTANCE.css().textarea() + " " + InputResources.INSTANCE.css().error();
	}

	public void setReadOnly(boolean readOnly) {
		input.setReadOnly(readOnly);
	}
}
