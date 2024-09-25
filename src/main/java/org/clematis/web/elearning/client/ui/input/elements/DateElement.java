package org.clematis.web.elearning.client.ui.input.elements;

import java.util.Date;

import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;


public class DateElement implements InputElementInterface<Date> {

	private DateBox dateBox;
	
	public DateElement(DateBox dateBox) {
		super();
		this.dateBox = dateBox;
	}
	
	public DateBox getDateBox() {
		return dateBox;
	}

	@Override
	public int getTabIndex() {
		return dateBox.getTabIndex();
	}

	@Override
	public void setAccessKey(char key) {
		dateBox.setAccessKey(key);
	}

	@Override
	public void setFocus(boolean focused) {
		dateBox.setFocus(focused);
	}

	@Override
	public void setTabIndex(int index) {
		dateBox.setTabIndex(index);
	}

	@Override
	public boolean isEnabled() {
		return dateBox.getTextBox().isEnabled();
	}

	@Override
	public void setEnabled(boolean enabled) {
		dateBox.getTextBox().setEnabled(enabled);
	}

	@Override
	public String getText() {
		return dateBox.getTextBox().getText();
	}

	@Override
	public void setText(String text) {
		dateBox.getTextBox().setText(text);
	}

	@Override
	public Widget asWidget() {
		return dateBox.asWidget();
	}

	@Override
	public HandlerRegistration addFocusHandler(FocusHandler handler) {
		return dateBox.getTextBox().addFocusHandler(handler);
	}

	@Override
	public void fireEvent(GwtEvent<?> event) {
        dateBox.fireEvent(event);
	}

	@Override
	public HandlerRegistration addBlurHandler(BlurHandler handler) {
		return dateBox.getTextBox().addBlurHandler(handler);
	}

	@Override
	public HandlerRegistration addKeyPressHandler(KeyPressHandler handler) {
		return dateBox.getTextBox().addKeyPressHandler(handler);
	}

	@Override
	public HandlerRegistration addKeyUpHandler(KeyUpHandler handler) {
		return dateBox.getTextBox().addKeyUpHandler(handler);
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		dateBox.getTextBox().setReadOnly(readOnly);
	}

	@Override
	public Date getValue() {
		return dateBox.getValue();
	}

	@Override
	public void setValue(Date date) {
		dateBox.setValue(date);
	}

	@Override
	public void setValueString(String value) {
		
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
