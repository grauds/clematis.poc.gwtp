package org.clematis.web.elearning.client.login;

import org.clematis.web.elearning.client.ELP;
import org.clematis.web.elearning.client.general.ShowErrorMessageEvent;
import org.clematis.web.elearning.client.login.actions.GetConnectionStateAction;
import org.clematis.web.elearning.client.login.actions.GetConnectionStateResult;
import org.clematis.web.elearning.client.login.navigation.SingnedInGatekeeper;
import org.clematis.web.elearning.client.place.NameTokens;
import org.clematis.web.elearning.client.ui.input.BoolInput;
import org.clematis.web.elearning.shared.domain.NamePasswordAuthObject;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

public class LoginPopupWidget extends Composite {

	private static LoginPopupWidgetUiBinder uiBinder = GWT.create(LoginPopupWidgetUiBinder.class);

	interface LoginPopupWidgetUiBinder extends
			UiBinder<Widget, LoginPopupWidget> {
	}
	
	@UiField(provided=true) LoginWidget loginWidget;
	
	private final EventBus eventBus;
	private final PlaceManager placeManager;	
	private final DispatchAsync dispatcher;	

	public LoginPopupWidget(final EventBus eventBus, final DispatchAsync dispatcher, final PlaceManager placeManager) {
		
		final LoginPopupWidget instance = this;
		
		this.eventBus = eventBus;
		this.dispatcher = dispatcher;
		this.placeManager = placeManager;
		
		loginWidget = new LoginWidget(placeManager)
		{

			@Override
			public void doLogin() {
				instance.doLogin();
			}

			@Override
			protected void onRegisterButton(ClickEvent clickEvent) {
				instance.doRegister();
				super.onRegisterButton(clickEvent);
			}
			
		};
		
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void doRegister() {
				
	}	

	public void doLogin() {
	
		loginWidget.enable(false);
		
		dispatcher.execute(new GetConnectionStateAction(Window.Location.getHref(), new NamePasswordAuthObject(loginWidget.getUserName(),
				                                                                                  loginWidget.getUserPassword()), ""), 
				new AsyncCallback<GetConnectionStateResult>() {

			@Override
			public void onFailure(Throwable caught) {
				eventBus.fireEvent(new ShowErrorMessageEvent("Ошибка авторизации ", caught));
				loginWidget.enable(true);
			}

			@Override
			public void onSuccess(GetConnectionStateResult result) {

				loginWidget.enable(true);
				// Initialize ConnectionState
				ELP.setConnectionState(result.getConnectionState());
				ELP.getConnectionState().isRemembered = loginWidget.getRemember().getValue();
				// Check if we are inside the registration process:
				PlaceRequest placeToFollow = SingnedInGatekeeper.getRestoredOrDecodedUrl(result.getSavedNextPage(), placeManager.getCurrentPlaceRequest());
				if (ELP.getConnectionState().user != null && placeToFollow != null && 
											(  NameTokens.REGISTER.equals(placeToFollow.getNameToken()) )
											) {
					// Yes, we are inside the registration process. Follow requested address
					placeManager.revealPlace(placeToFollow);
					return;
				}
				if (ELP.getConnectionState().isLoggedIn) {
					placeManager.revealPlace(SingnedInGatekeeper.getDecodedUrl(placeManager.getCurrentPlaceRequest()));
				} else {
					// User is not authorized, is not logged in. Just give him an interface to choose sign in method
					placeManager.revealPlace(new PlaceRequest(NameTokens.SIGNIN));
				}		
				
				eventBus.fireEvent(new LoginEvent());
			}
		});
	}	

	public String getUserName() {
		return loginWidget.getUserName();
	}

	public String getUserPassword() {
		return loginWidget.getUserPassword();
	}

	public void enable(boolean enabled) {
		loginWidget.enable(enabled);
	}

	public BoolInput getRemember() {
		return loginWidget.getRemember();
	}
	
	

}
