package org.clematis.web.elearning.client.ui.input.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.Widget;


public class SelectBoxEx extends Composite {

	private static SelectBoxExUiBinder uiBinder = GWT.create(SelectBoxExUiBinder.class);

	@UiField HorizontalPanel holder;	
	@UiField TextBox textBox;
	@UiField ArrowButton button;
	
	private HandlerRegistration clickHandler;
	
	private PopupMenuManager popupMenuManager = new PopupMenuManager()
	{
		@Override
		public void onItemSelected(String text) {
			setText( text );
		}
	};	
	
	interface SelectBoxExUiBinder extends UiBinder<Widget, SelectBoxEx> {
	}

	public SelectBoxEx() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	protected void onLoad() {
		clickHandler = button.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {	
				if ( getTextBox().isEnabled() ) {
					popupMenuManager.showPopupMenu(getAbsoluteLeft(), getAbsoluteTop() + getOffsetHeight(), getOffsetWidth());
				}
			}
		});
		super.onLoad();
	}
	
	public void onClick(ClickEvent event) {
		popupMenuManager.hidePopupMenu();
	}	

	@Override
	protected void onUnload() {
		super.onUnload();
		if (clickHandler != null) {
			clickHandler.removeHandler();
			clickHandler = null;
		}
	}
	
	public TextBoxBase getTextBox() {
		return textBox;
	}

	public String getText() {
		return textBox.getText();
	}
	
	public void setText(String text) {
		textBox.setText(text);
	}
	
	public int getTabIndex() {
		return textBox.getTabIndex();
	}
	
	public void setAccessKey(char key) {
		textBox.setAccessKey(key);
	}
	
	public void setFocus(boolean focused) {
		textBox.setFocus(focused);
	}
	
	public void setTabIndex(int index) {
		textBox.setTabIndex(index);
	}
	
	public String getValue() {
		return textBox.getValue();
	}
	
	public void setValue(String string) {
		textBox.setValue(string);		
	}

	public boolean isEnabled() {
		return textBox.isEnabled();
	}

	public void setEnabled(boolean enabled) {
		textBox.setEnabled(enabled);
		button.setVisible(enabled);
	}

	public HandlerRegistration addKeyUpHandler(KeyUpHandler handler) {
		return textBox.addKeyUpHandler(handler);
	}

	public HandlerRegistration addKeyPressHandler(KeyPressHandler handler) {
		return textBox.addKeyPressHandler(handler);
	}

	public HandlerRegistration addBlurHandler(BlurHandler handler) {
		return textBox.addBlurHandler(handler);
	}

	public HandlerRegistration addFocusHandler(FocusHandler handler) {
		return textBox.addFocusHandler(handler);
	}

	public PopupMenuManager getPopupMenuManager() {
		return popupMenuManager;
	}

}
