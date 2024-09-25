package org.clematis.web.elearning.client.ui.input.elements;

import com.google.gwt.event.dom.client.HasBlurHandlers;
import com.google.gwt.event.dom.client.HasFocusHandlers;
import com.google.gwt.event.dom.client.HasKeyPressHandlers;
import com.google.gwt.event.dom.client.HasKeyUpHandlers;
import com.google.gwt.user.client.ui.Focusable;
import com.google.gwt.user.client.ui.HasEnabled;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.IsWidget;

public interface InputElementInterface<T> extends Focusable, HasEnabled, HasText, IsWidget, HasFocusHandlers, HasBlurHandlers, HasKeyPressHandlers, HasKeyUpHandlers  {

	void setReadOnly(boolean readOnly);

	public abstract T getValue();

	public abstract void setValue(T value);

	void setValueString(String value);
	
	void setInputStyle(String name);

	void setInputStyle(String name, boolean add);

}
