package org.clematis.web.elearning.client.login;


import org.clematis.web.elearning.client.place.NameTokens;
import org.clematis.web.elearning.client.ui.input.BoolInput;
import org.clematis.web.elearning.client.ui.input.Input;
import org.clematis.web.elearning.client.ui.input.PasswordInput;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;


public class LoginWidget extends Composite {

	private static UserMenuWidgetUiBinder uiBinder = GWT.create(UserMenuWidgetUiBinder.class);

	interface UserMenuWidgetUiBinder extends UiBinder<Widget, LoginWidget> {
	
	}
	
	@UiField HTMLPanel signInPanel;
	@UiField HTMLPanel messagePanel;

	@UiField Input inputLogin;
	@UiField PasswordInput inputPassword;
	@UiField BoolInput remember;
	@UiField Button remindPassword;
	@UiField Button submitLogin;
	
	private final PlaceManager placeManager;	

	public LoginWidget(PlaceManager placeManager) {
		initWidget(uiBinder.createAndBindUi(this));
		
		this.placeManager = placeManager;
		messagePanel.setVisible(false);
	}

	@UiHandler("registerButton")
	protected void onRegisterButton(ClickEvent clickEvent) {
		placeManager.revealPlace(new PlaceRequest(NameTokens.REGISTER));		
	}
	
	@UiHandler("submitLogin")
	protected void onLoginButton(ClickEvent clickEvent) {
         if ( inputLogin.isFieldValid() && inputPassword.isFieldValid() ) {
        	 doLogin();
         } else {
        	 inputLogin.validate();
        	 inputPassword.validate();
         }
	}
	
	public void doLogin() {
		
	}
	
	public void enable(boolean enabled) {
		inputLogin.setEnabled(enabled);
		inputPassword.setEnabled(enabled);
		remember.setEnabled(enabled);
		submitLogin.setEnabled(enabled);
		remindPassword.setEnabled(enabled);
		messagePanel.setVisible(!enabled);
	}
	
    public String getUserName() {    	
    	return inputLogin.getText();
    }
	
    public String getUserPassword() {    	
    	return inputPassword.getText();
    }

	public BoolInput getRemember() {
		return remember;
	}
	
    
}
