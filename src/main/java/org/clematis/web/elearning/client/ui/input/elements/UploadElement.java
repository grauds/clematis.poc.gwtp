package org.clematis.web.elearning.client.ui.input.elements;

import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.Widget;

public class UploadElement implements InputElementInterface<String>  {

	private final FileUpload fileUpload;
	
	public UploadElement(FileUpload fileUpload) {
		this.fileUpload = fileUpload;
	}	
	
	public FileUpload getUploadBox() {
		return fileUpload;
	}		
	
	@Override
	public int getTabIndex() {
		return getUploadBox().getElement().getTabIndex();
	}

	@Override
	public void setAccessKey(char key) {
				
	}

	@Override
	public void setFocus(boolean focused) {
				
	}

	@Override
	public void setTabIndex(int index) {
		getUploadBox().getElement().setTabIndex(index);
	}

	@Override
	public boolean isEnabled() {
		return getUploadBox().isEnabled();
	}

	@Override
	public void setEnabled(boolean enabled) {
		getUploadBox().setEnabled(enabled);
	}

	@Override
	public String getText() {
		return getUploadBox().getFilename();
	}

	@Override
	public void setText(String text) {
		
	}

	@Override
	public Widget asWidget() {
		return getUploadBox().asWidget();
	}

	@Override
	public HandlerRegistration addFocusHandler(FocusHandler handler) {
		return null;
	}

	@Override
	public void fireEvent(GwtEvent<?> event) {
		getUploadBox().fireEvent(event);
	}

	@Override
	public HandlerRegistration addBlurHandler(BlurHandler handler) {
		return null;
	}

	@Override
	public HandlerRegistration addKeyPressHandler(KeyPressHandler handler) {
		return null;
	}

	@Override
	public HandlerRegistration addKeyUpHandler(KeyUpHandler handler) {
		return null;
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		getUploadBox().setEnabled(!readOnly);
	}

	@Override
	public String getValue() {
		return getUploadBox().getFilename();
	}

	@Override
	public void setValue(String value) {
		
	}

	@Override
	public void setValueString(String value) {
		
	}

	@Override
	public void setInputStyle(String name) {
		getUploadBox().setStyleName(name);
	}

	@Override
	public void setInputStyle(String name, boolean add) {
		getUploadBox().setStyleName(name, add);
	}

}
