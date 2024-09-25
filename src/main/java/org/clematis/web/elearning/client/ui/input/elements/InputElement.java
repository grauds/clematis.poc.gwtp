package org.clematis.web.elearning.client.ui.input.elements;

import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.Widget;


public class InputElement implements InputElementInterface<String> {

	private final TextBoxBase textBoxBase;
	
	public InputElement(TextBoxBase textBoxBase) {
		this.textBoxBase = textBoxBase;
	}

	@Override
	public void fireEvent(GwtEvent<?> event) {
		textBoxBase.fireEvent(event);
	}

	@Override
	public HandlerRegistration addBlurHandler(BlurHandler handler) {
		return textBoxBase.addBlurHandler(handler);
	}

	@Override
	public HandlerRegistration addFocusHandler(FocusHandler handler) {
		return textBoxBase.addFocusHandler(handler);
	}

	@Override
	public Widget asWidget() {
		return  textBoxBase.asWidget();
	}

	@Override
	public String getText() {
		return textBoxBase.getText();
	}

	@Override
	public void setText(String text) {
		textBoxBase.setText(text);
	}

	@Override
	public int getTabIndex() {
		return textBoxBase.getTabIndex();
	}

	@Override
	public void setAccessKey(char key) {
		textBoxBase.setAccessKey(key);
	}

	@Override
	public void setFocus(boolean focused) {
		textBoxBase.setFocus(focused);
	}

	@Override
	public void setTabIndex(int index) {
		textBoxBase.setTabIndex(index);
	}

	@Override
	public boolean isEnabled() {
		return textBoxBase.isEnabled();
	}

	@Override
	public void setEnabled(boolean enabled) {
		textBoxBase.setEnabled(enabled);
	}

	public void setReadOnly(boolean readOnly) {
		textBoxBase.setReadOnly(readOnly);
	}

	@Override
	public HandlerRegistration addKeyPressHandler(KeyPressHandler handler) {
		return textBoxBase.addKeyPressHandler(handler);
	}

	@Override
	public HandlerRegistration addKeyUpHandler(KeyUpHandler handler) {
		return textBoxBase.addKeyUpHandler(handler);
	}

	public String getValue() {
		return textBoxBase.getValue();
	}

	public void setValue(String string) {
		textBoxBase.setValue(string);
	}

	@Override
	public void setValueString(String value) {
		setValue(value);
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
