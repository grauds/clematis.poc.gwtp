package org.clematis.web.elearning.client.login.signin;

import org.clematis.web.elearning.client.ELP;
import org.clematis.web.elearning.client.general.ShowErrorMessageEvent;
import org.clematis.web.elearning.client.login.LoginEvent;
import org.clematis.web.elearning.client.login.LoginWidget;
import org.clematis.web.elearning.client.login.actions.GetConnectionStateAction;
import org.clematis.web.elearning.client.login.actions.GetConnectionStateResult;
import org.clematis.web.elearning.client.login.navigation.SingnedInGatekeeper;
import org.clematis.web.elearning.client.place.NameTokens;
import org.clematis.web.elearning.shared.domain.NamePasswordAuthObject;

import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.ViewImpl;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class SignInView extends ViewImpl implements SignInPresenter.MyView {

	private final Widget widget;

	public interface Binder extends UiBinder<Widget, SignInView> {
	}
	
	@UiField(provided=true) LoginWidget loginWidget;
	
	private final EventBus eventBus;	
	private final DispatchAsync dispatcher;
	private final PlaceManager placeManager;

	@Inject
	public SignInView(final EventBus eventBus, final Binder binder, final PlaceManager placeManager, final DispatchAsync dispatcher) {
		
		final SignInView instance = this;
		
		this.eventBus = eventBus;		
		this.dispatcher = dispatcher;
		this.placeManager = placeManager;
		
		loginWidget = new LoginWidget(placeManager)
		{

			@Override
			public void doLogin() {
				instance.doLogin();
			}
			
		};
		
		widget = binder.createAndBindUi(this);
	}

	@Override
	public Widget asWidget() {
		return widget;
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

	@Override
	public void showLoginWidget(boolean show) {
		loginWidget.setVisible(show);
	}	
}
