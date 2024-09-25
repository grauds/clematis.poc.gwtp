package org.clematis.web.elearning.client.ui.input;

import org.clematis.web.elearning.client.ui.input.elements.InputElementInterface;
import org.clematis.web.elearning.client.ui.input.resources.InputMessages;
import org.clematis.web.elearning.client.ui.input.resources.InputResources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;


public abstract class InputBase<K, T extends InputElementInterface<K>> extends Composite {

	private static InputBaseUiBinder uiBinder = GWT.create(InputBaseUiBinder.class);

	@SuppressWarnings("rawtypes")
	interface InputBaseUiBinder extends UiBinder<Widget, InputBase> {
	}
	
	protected abstract String getInputStyleName();
	protected abstract String getInputErrorStyleName();

	@UiField Label caption;
	@UiField SimplePanel captionHolder;
	
	@UiField Label label;
	@UiField Label labelError;
	//@UiField Label labelRequired;
	
	@UiField SimplePanel pin;
	
	@UiField HTMLPanel inputHolder;
	protected T input;
	
	protected HandlerRegistration handlerFocusInput;
	protected HandlerRegistration handlerFocusBlur;
	protected HandlerRegistration handlerLabelClick;

	protected boolean isInputFloatRight;
	private boolean clearedOnSubmit;
	private boolean required;

	public InputBase(T input) {
		
		InputResources.INSTANCE.css().ensureInjected();			
		initWidget(uiBinder.createAndBindUi(this));

		setCaptionOnLeft(false);
		pin.setStyleName(InputResources.INSTANCE.css().ib_nopinnable());

		this.input = input;		
		inputHolder.add(input);
		this.input.setInputStyle(getInputStyleName());
		
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
		if (handlerFocusInput == null) {
			handlerFocusInput = input.addFocusHandler(new FocusHandler() {
				@Override
				public void onFocus(FocusEvent event) {
					label.setVisible(false);
					// Set ordinary style for the input (in might be set to error before)
					input.setInputStyle(getInputErrorStyleName(), false);
				}
			});
		}
		if (handlerFocusBlur == null) {
			handlerFocusBlur = input.addBlurHandler(new BlurHandler() {
				@Override
				public void onBlur(BlurEvent event) {
					validate();
				}
	
			});
		}
		if (handlerLabelClick == null) {
			// This handler is needed to pass click event from the grey label which is shown by the center
			// of the input control down to the input control so the last could take the focus.
			// Otherwise the focus is taken by the label and the users click is lost.
			handlerLabelClick = label.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					input.setFocus(true);
				}
			});
		}
	}
	
	public void validate() {
		if (input.getText() == null || input.getText().isEmpty()) {
			// In case the input is empty show the grey label inside it
			label.setVisible(true);
		}
		if (isFieldValid()) {
			labelError.setVisible(false);
			input.setInputStyle(getInputErrorStyleName(), false);
		} else {
			input.setInputStyle(getInputErrorStyleName(), true);
		}
	}		

	private void removeHandlers() {
		if (handlerFocusInput != null) {
			handlerFocusInput.removeHandler();
			handlerFocusInput = null;
		}
		if (handlerFocusBlur != null) {
			handlerFocusBlur.removeHandler();
			handlerFocusBlur = null;
		}
		if (handlerLabelClick != null) {
			handlerLabelClick.removeHandler();
			handlerLabelClick = null;
		}
	}

	public void setCaption(String text) {
		caption.setText(text);
	}

	public void setLabel(String text) {
		label.setText(text);
		if (text == null || text.trim().isEmpty()) {
			removeHandlers();
		} else {
			setHandlers();
		}
	}

	public void setCaptionOnLeft(boolean isLeft) {
		captionHolder.setStyleName(isLeft ? InputResources.INSTANCE.css().ib_captionleft() : InputResources.INSTANCE.css().ib_captionontop());
	}

	@Override
	public void setStyleName(String style) {
		this.addStyleName(style);
	}

	@Override
	public void setWidth(String width) {
		inputHolder.asWidget().setWidth(width);
	}

	public void setInputHeight(String height) {
		inputHolder.asWidget().setHeight(height);
		input.asWidget().setHeight(height);
	}

	public void setFocus(boolean focused) {
		input.setFocus(focused);
	}

	/**
	 * Override this method to validate a field when it's loosing the focus.
	 * @return
	 */
	public boolean isFieldValid() {
		if (isRequired()) {
			if (input.getText() == null || input.getText().trim().isEmpty()) {
				showDefaultError();
				return false;
			}
			return true;
		}
		return true;
	}

	/**
	 * This method should be called to show error message under the control.
	 * @param error
	 */
	public void showError(String error) {
		labelError.setVisible(true);
		labelError.setText(error);
		input.setInputStyle(getInputErrorStyleName(), true);
	}

	protected void showDefaultError() {
		showError(InputMessages.INSTANCE.getDefaultErrorMessage());
	}

	public void setEnabled(boolean enabled) {
		input.setEnabled(enabled);
	}

	public String getText() {
		return input.getText();
	}

	public void setText(String text) {
		input.setText(text);
		label.setVisible(text == null || text.trim().isEmpty());
	}
	
	public K getValue() {
		return input.getValue();
	}

	public void setValue(K value) {
		input.setValue(value);
		label.setVisible(value == null);
		if ( value instanceof String ) {
			label.setVisible( ((String)value).trim().isEmpty() );
		}
	}	
	
	public void setValueString(String text) {
		input.setText(text);
		label.setVisible(text == null || text.trim().isEmpty() );
	}
	
	public T getInput() {
		return input;
	}
	
	public boolean isClearedOnSubmit() {
		return clearedOnSubmit;
	}
	
	public void setClearedOnSubmit(boolean clearedOnSubmit) {
		this.clearedOnSubmit = clearedOnSubmit;
		pin.setStyleName(clearedOnSubmit ? InputResources.INSTANCE.css().ib_nopin() : InputResources.INSTANCE.css().ib_pin());
	}
	
	public boolean isRequired() {
		return required;
	}

	public void setRequired(String requiredMessage) {
		setRequired(requiredMessage != null);
	//	labelRequired.setText(requiredMessage != null ? requiredMessage : "");
	}
	
	public void setRequired(boolean required) {
		this.required = required;
		//labelRequired.setVisible(required);
	}
	
}
