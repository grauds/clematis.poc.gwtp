package org.clematis.web.elearning.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class PinWidget extends Composite implements HasClickHandlers, ClickHandler {

	private static PinWidgetUiBinder uiBinder = GWT
			.create(PinWidgetUiBinder.class);

	interface PinWidgetUiBinder extends UiBinder<Widget, PinWidget> {
	}
	
	@UiField FocusPanel pin;
	@UiField Image pin_image;
	private boolean selected = false;
	private HandlerRegistration clickHandler;	

	public PinWidget() {
		initWidget(uiBinder.createAndBindUi(this));
		
		setSelected(false);

		clickHandler = pin.addClickHandler(this);
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
		pin_image.setVisible(selected);
	}

    @Override
	protected void onLoad() {
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
		pin_image.setVisible(!pin_image.isVisible());
		setSelected(pin_image.isVisible());
	}

	@Override
    public HandlerRegistration addClickHandler(ClickHandler handler) {
        return pin.addClickHandler(handler);
    }
	
}
