package org.clematis.web.elearning.client.ui.input.elements;

import org.clematis.web.elearning.client.ui.input.widgets.SelectBoxEx;

import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Widget;


public class SelectElement implements InputElementInterface<String> {

	private final SelectBoxEx selectBox;
	
	public SelectElement(SelectBoxEx selectBox) {
		this.selectBox = selectBox;
	}

	public SelectBoxEx getSelectBox() {
		return selectBox;
	}		
	
	@Override
	public void fireEvent(GwtEvent<?> event) {
		getSelectBox().fireEvent(event);
	}

	@Override
	public HandlerRegistration addBlurHandler(BlurHandler handler) {
		return getSelectBox().addBlurHandler(handler);
	}

	@Override
	public HandlerRegistration addFocusHandler(FocusHandler handler) {
		return getSelectBox().addFocusHandler(handler);
	}

	@Override
	public Widget asWidget() {
		return getSelectBox().asWidget();
	}

	@Override
	public String getText() {
		return getSelectBox().getText();
	}

	@Override
	public void setText(String text) {
		getSelectBox().setText(text);
	}

	@Override
	public int getTabIndex() {
		return getSelectBox().getTabIndex();
	}

	@Override
	public void setAccessKey(char key) {
		getSelectBox().setAccessKey(key);
	}

	@Override
	public void setFocus(boolean focused) {
		getSelectBox().setFocus(focused);
	}

	@Override
	public void setTabIndex(int index) {
		getSelectBox().setTabIndex(index);
	}

	@Override
	public boolean isEnabled() {
		return getSelectBox().isEnabled();
	}

	@Override
	public void setEnabled(boolean enabled) {
		getSelectBox().setEnabled(enabled);
	}

	public void setReadOnly(boolean readOnly) {
		// this control cannot be read only, disable it
		getSelectBox().setEnabled(readOnly);
	}

	@Override
	public HandlerRegistration addKeyPressHandler(KeyPressHandler handler) {
		return getSelectBox().addKeyPressHandler(handler);
	}
	
	@Override
	public HandlerRegistration addKeyUpHandler(KeyUpHandler handler) {
		return getSelectBox().addKeyUpHandler(handler);
	}

	public String getValue() {
		return getSelectBox().getText();
	}

	public void setValue(String string) {
		getSelectBox().setText(string);
	}

	@Override
	public void setValueString(String value) {
		setValue(value);
	}

	@Override
	public void setInputStyle(String name) {
		getSelectBox().getTextBox().asWidget().setStyleName(name);
	}

	@Override
	public void setInputStyle(String name, boolean add) {
		getSelectBox().getTextBox().setStyleName(name, add);
	}	
}
