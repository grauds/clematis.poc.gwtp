package org.clematis.web.elearning.client.ui.input;

import org.clematis.web.elearning.client.ui.input.elements.InputElement;
import org.clematis.web.elearning.client.ui.input.elements.InputElementInterface;
import org.clematis.web.elearning.client.ui.input.resources.InputResources;

import com.google.gwt.user.client.ui.PasswordTextBox;

public class PasswordInput extends InputBase<String, InputElementInterface<String>> {

	public PasswordInput(){
		super(new InputElement(new PasswordTextBox()));
	}

	@Override
	protected String getInputStyleName() {
		return InputResources.INSTANCE.css().input();
	}

	@Override
	protected String getInputErrorStyleName() {
		return InputResources.INSTANCE.css().input() + " " + InputResources.INSTANCE.css().error();
	}

}
