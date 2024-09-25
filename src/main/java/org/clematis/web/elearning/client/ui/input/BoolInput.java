package org.clematis.web.elearning.client.ui.input;

import org.clematis.web.elearning.client.ui.input.elements.BooleanElement;
import org.clematis.web.elearning.client.ui.input.resources.InputResources;
import org.clematis.web.elearning.client.ui.input.widgets.CheckBoxStyled;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;


public class BoolInput extends InputBase<Boolean, BooleanElement>  {

	public BoolInput() {
		super(new BooleanElement(new CheckBoxStyled()));
	}

	@Override
	protected String getInputStyleName() {
		return InputResources.INSTANCE.css().checkbox();
	}

	@Override
	protected String getInputErrorStyleName() {
		return InputResources.INSTANCE.css().checkbox() + " " + InputResources.INSTANCE.css().error();
	}	
	
	@Override	
	public void setCaption(String text) {
		getInput().getCheckBox().setCaption(text);
	}

	public HandlerRegistration addClickHandler(ClickHandler handler) {
		return getInput().getCheckBox().addClickHandler(handler);
	}	
}
