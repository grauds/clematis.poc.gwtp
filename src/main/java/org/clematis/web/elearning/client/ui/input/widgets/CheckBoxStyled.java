package org.clematis.web.elearning.client.ui.input.widgets;

import org.clematis.web.elearning.client.ui.input.resources.InputResources;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;

public class CheckBoxStyled extends Composite implements HasClickHandlers {

	private final HTMLPanel panel;
	private final HTMLPanel panelCheck;
	private final CheckBox checkBox;
	private final Label labelError;

	private HandlerRegistration handlerOnClick;

	public CheckBoxStyled() {
		CheckBoxResources.INSTANCE.css().ensureInjected();
		InputResources.INSTANCE.css().ensureInjected();

		panel = new HTMLPanel("");

		panelCheck = new HTMLPanel("");
		checkBox = new CheckBox();
		// Set checkbox style
		checkBox.getElement().getFirstChildElement().setClassName(CheckBoxResources.INSTANCE.css().checkbox());
		// Set label style
		checkBox.getElement().getFirstChildElement().getNextSiblingElement().setClassName(CheckBoxResources.INSTANCE.css().caption());
		panelCheck.add(checkBox);

		labelError = new Label();
		labelError.setStyleName(InputResources.INSTANCE.css().ib_labelerror());
		labelError.setVisible(false);

		panel.add(panelCheck);
		panel.add(labelError);

		initWidget(panel);
	}

	@Override
	protected void onLoad() {
		super.onLoad();
		setHandlers();
	}
	
	@Override
	protected void onDetach() {
		super.onDetach();
		removeHandlers();
	}

	private void setHandlers() {
		if (handlerOnClick == null) {
			handlerOnClick = checkBox.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					isValid();
				}
			});
		}
	}

	private void removeHandlers() {
		if (handlerOnClick != null) {
			handlerOnClick.removeHandler();
			handlerOnClick = null;
		}
	}

	public CheckBox getCheckBox() {
		return checkBox;
	}

	public void setCaption(String caption) {
		checkBox.setText(caption);
	}

	public void setStyleName(String style) {
		panel.addStyleName(style);
	}

	public Boolean getValue() {
		return checkBox.getValue();
	}
	
	public void setValue(Boolean value) {
		checkBox.setValue(value);
	}	

	@Override
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		return checkBox.addClickHandler(handler);
	}

	public boolean isValid() {
		panelCheck.setStyleName(InputResources.INSTANCE.css().error(), false);
		labelError.setVisible(false);
		return true;
	}

	protected void showError(String error) {
		labelError.setVisible(true);
		labelError.setText(error);
		panelCheck.setStyleName(InputResources.INSTANCE.css().error(), true);
	}

	public void setEnabled(boolean enabled) {
		checkBox.setEnabled(enabled);
	}

}
