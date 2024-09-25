package org.clematis.web.elearning.client.ui.input;

import java.util.Date;

import org.clematis.web.elearning.client.ui.input.elements.DateElement;
import org.clematis.web.elearning.client.ui.input.resources.InputResources;

import com.google.gwt.user.datepicker.client.DateBox;


public class DateInput extends InputBase<Date, DateElement> {

	public DateInput() {
		super(new DateElement(new DateBox()));
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
