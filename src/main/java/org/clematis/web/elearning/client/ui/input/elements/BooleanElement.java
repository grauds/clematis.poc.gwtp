package org.clematis.web.elearning.client.ui.input.elements;

import org.clematis.web.elearning.client.ui.input.widgets.CheckBoxStyled;

import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Widget;


public class BooleanElement implements InputElementInterface<Boolean> {

    private CheckBoxStyled checkBox;
	
	public BooleanElement(CheckBoxStyled checkBox) {
		super();
		this.checkBox = checkBox;
	}
	
	public CheckBoxStyled getCheckBox() {
		return checkBox;
	}
	
	@Override
	public int getTabIndex() {
		return checkBox.getCheckBox().getTabIndex();
	}

	@Override
	public void setAccessKey(char key) {
		checkBox.getCheckBox().setAccessKey(key);
	}

	@Override
	public void setFocus(boolean focused) {
		checkBox.getCheckBox().setFocus(focused);
	}

	@Override
	public void setTabIndex(int index) {
		checkBox.getCheckBox().setTabIndex(index);
	}

	@Override
	public boolean isEnabled() {
		return checkBox.getCheckBox().isEnabled();
	}

	@Override
	public void setEnabled(boolean enabled) {
		checkBox.getCheckBox().setEnabled(enabled);
	}

	@Override
	public String getText() {
		return checkBox.getValue().toString();
	}

	@Override
	public void setText(String text) {
		// TODO handle true or false strings?
	}

	@Override
	public Widget asWidget() {
		return checkBox.asWidget();
	}

	@Override
	public HandlerRegistration addFocusHandler(FocusHandler handler) {
		return checkBox.getCheckBox().addFocusHandler(handler);
	}

	@Override
	public void fireEvent(GwtEvent<?> event) {
		checkBox.getCheckBox().fireEvent(event);
	}

	@Override
	public HandlerRegistration addBlurHandler(BlurHandler handler) {
		return checkBox.getCheckBox().addBlurHandler(handler);
	}

	@Override
	public HandlerRegistration addKeyPressHandler(KeyPressHandler handler) {
		return checkBox.getCheckBox().addKeyPressHandler(handler);
	}

	@Override
	public HandlerRegistration addKeyUpHandler(KeyUpHandler handler) {
		return checkBox.getCheckBox().addKeyUpHandler(handler);
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		checkBox.setEnabled(readOnly);
	}

	@Override
	public Boolean getValue() {
		return checkBox.getValue();
	}

	@Override
	public void setValue(Boolean value) {
		checkBox.setValue(value);
	}

	@Override
	public void setValueString(String value) {
  	    setValue(Boolean.valueOf(value));
	}

	@Override
	public void setInputStyle(String name) {
		asWidget().setStyleName(name);
	}
	
	@Override
	public void setInputStyle(String name, boolean add) {
		asWidget().setStyleName(name, add);
	}	
	
}
