package org.clematis.web.elearning.client.ui.input.elements;

import org.clematis.web.elearning.client.ui.input.widgets.SuggestBoxEx;

import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Widget;



public class SuggestElement implements InputElementInterface<String> {

	private final SuggestBoxEx suggestBox;
	
	public SuggestElement(SuggestBoxEx suggestBox) {
		this.suggestBox = suggestBox;
	}	
	
	public SuggestBoxEx getSuggestBox() {
		return suggestBox;
	}		
		
	@Override
	public void fireEvent(GwtEvent<?> event) {
		getSuggestBox().fireEvent(event);
	}

	@Override
	public HandlerRegistration addBlurHandler(BlurHandler handler) {
		return getSuggestBox().getTextBox().addBlurHandler(handler);
	}

	@Override
	public HandlerRegistration addFocusHandler(FocusHandler handler) {
		return getSuggestBox().getTextBox().addFocusHandler(handler);
	}

	@Override
	public Widget asWidget() {
		return getSuggestBox().asWidget();
	}

	@Override
	public String getText() {
		return getSuggestBox().getText();
	}

	@Override
	public void setText(String text) {
		getSuggestBox().setText(text);
	}

	@Override
	public int getTabIndex() {
		return getSuggestBox().getTabIndex();
	}

	@Override
	public void setAccessKey(char key) {
		getSuggestBox().setAccessKey(key);
	}

	@Override
	public void setFocus(boolean focused) {
		getSuggestBox().setFocus(focused);
	}

	@Override
	public void setTabIndex(int index) {
		getSuggestBox().setTabIndex(index);
	}

	@Override
	public boolean isEnabled() {
		return getSuggestBox().getTextBox().isEnabled();
	}

	@Override
	public void setEnabled(boolean enabled) {
		getSuggestBox().getTextBox().setEnabled(enabled);
		getSuggestBox().setVisible(enabled);
	}

	public void setReadOnly(boolean readOnly) {
		getSuggestBox().getTextBox().setReadOnly(readOnly);
	}

	@Override
	public HandlerRegistration addKeyPressHandler(KeyPressHandler handler) {
		return getSuggestBox().getTextBox().addKeyPressHandler(handler);
	}
	
	@Override
	public HandlerRegistration addKeyUpHandler(KeyUpHandler handler) {
		return getSuggestBox().getTextBox().addKeyUpHandler(handler);
	}

	@Override
	public String getValue() {
		return getSuggestBox().getValue();
	}

	@Override
	public void setValue(String string) {
		getSuggestBox().setValue(string);
	}

	@Override
	public void setValueString(String value) {
		setValue(value);
	}

	@Override
	public void setInputStyle(String name) {
		getSuggestBox().getSuggestBox().asWidget().setStyleName(name);
	}
	
	@Override
	public void setInputStyle(String name, boolean add) {
		getSuggestBox().getTextBox().setStyleName(name, add);
	}	
}
