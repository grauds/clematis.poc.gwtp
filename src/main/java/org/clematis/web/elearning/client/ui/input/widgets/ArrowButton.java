package org.clematis.web.elearning.client.ui.input.widgets;


import org.clematis.web.elearning.client.resources.Resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class ArrowButton extends Composite implements HasText, HasClickHandlers, ClickHandler{

	private static ArrowButtonUiBinder uiBinder = GWT
			.create(ArrowButtonUiBinder.class);

	interface ArrowButtonUiBinder extends UiBinder<Widget, ArrowButton> {
	}

	@UiField FocusPanel panel;	
	@UiField Label text;
	@UiField protected Image image;
	private HandlerRegistration clickHandler;

	public ArrowButton() {
		initWidget(uiBinder.createAndBindUi(this));

		image.setResource(Resources.INSTANCE.downArrow());
	}
	 
    @Override
    public HandlerRegistration addClickHandler(ClickHandler handler) {
        return addHandler(handler, ClickEvent.getType());
    }
    
 
    @Override
	protected void onLoad() {
		clickHandler = panel.addClickHandler(this);
		super.onLoad();
	}

	@Override
	protected void onUnload() {
		super.onUnload();
		if (clickHandler != null) {
			clickHandler.removeHandler();	
			clickHandler = null;
		}
	}

	@Override
    public void onClick(ClickEvent event) {
         this.fireEvent(event);
    }	
	
	public void setText(String string) {
		text.setText(string);
	}

	public String getText() {
		return text.getText();
	}	

}
