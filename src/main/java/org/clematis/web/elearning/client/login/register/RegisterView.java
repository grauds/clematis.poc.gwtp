package org.clematis.web.elearning.client.login.register;

import org.clematis.web.elearning.client.general.ShowErrorMessageEvent;
import org.clematis.web.elearning.client.login.actions.RegisterAction;
import org.clematis.web.elearning.client.login.actions.RegisterResult;
import org.clematis.web.elearning.client.place.NameTokens;
import org.clematis.web.elearning.client.ui.input.Input;
import org.clematis.web.elearning.client.ui.input.MailInput;
import org.clematis.web.elearning.client.ui.input.PasswordInput;

import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.ViewImpl;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class RegisterView extends ViewImpl implements RegisterPresenter.MyView {

	private final Widget widget;
	private final DispatchAsync dispatcher;

	public interface Binder extends UiBinder<Widget, RegisterView> {
	}
	
	@UiField HTMLPanel registerPanel;
	@UiField HTMLPanel messagePanel;
	@UiField HTMLPanel finalPanel;

    @UiField MailInput inputMail;
	@UiField Input inputName;
	@UiField Input inputSecondName;
	@UiField Input inputPhone;
	@UiField PasswordInput inputPassword;
	@UiField PasswordInput confirmPassword;

	private final EventBus eventBus;	
	private final PlaceManager placeManager;

	@Inject
	public RegisterView(final EventBus eventBus, final Binder binder, final DispatchAsync dispatcher, PlaceManager placeManager) {
		widget = binder.createAndBindUi(this);
		
		this.dispatcher = dispatcher;
		this.eventBus = eventBus;
		this.placeManager = placeManager;
		messagePanel.setVisible(false);
		finalPanel.setVisible(false);
	}
	

	@UiHandler("cabinetButton")
	protected void onRegisterButton(ClickEvent clickEvent) {
		placeManager.revealPlace(new PlaceRequest(NameTokens.CABINET));		
	}	

	@Override
	public Widget asWidget() {
		return widget;
	}
	
	@UiHandler("submitRegistration")
	public void onSubmitRegistration(ClickEvent ev) {
		
		if ( inputPhone.isFieldValid() && inputName.isFieldValid() && inputSecondName.isFieldValid() && inputMail.isFieldValid() 
				&& (inputPassword.getText() != null && inputPassword.getText().equals(confirmPassword.getText())) ) {
			
			registerPanel.setVisible(false);
			messagePanel.setVisible(true);
			
			dispatcher.execute(new RegisterAction(inputMail.getText(), inputPassword.getText(), inputName.getText(), inputSecondName.getText(), inputPhone.getText()), 
					new AsyncCallback<RegisterResult>() {

				@Override
				public void onFailure(Throwable caught) {
					
					messagePanel.setVisible(false);
					registerPanel.setVisible(true);
					eventBus.fireEvent(new ShowErrorMessageEvent("Ошибка регистрации ", caught));
					
				}

				@Override
				public void onSuccess(RegisterResult result) {
					
					messagePanel.setVisible(false);
					finalPanel.setVisible(true);				
				}
				
			});			
			
        } else {
        	inputPhone.validate();
            inputName.validate();
        	inputSecondName.validate();
        	inputMail.validate();
        	inputPassword.validate();
        	if ( ! (inputPassword.getText() != null && inputPassword.getText().equals(confirmPassword.getText())) ) {
        		inputPassword.showError("Пароли не совпадают.");
        	}
        }

	}
}
