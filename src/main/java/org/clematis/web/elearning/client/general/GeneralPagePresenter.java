package org.clematis.web.elearning.client.general;

import java.util.Date;

import org.clematis.web.elearning.client.ELP;
import org.clematis.web.elearning.client.login.LoginEvent;
import org.clematis.web.elearning.client.login.LogoutEvent;
import org.clematis.web.elearning.client.login.actions.GetConnectionStateAction;
import org.clematis.web.elearning.client.login.actions.GetConnectionStateResult;
import org.clematis.web.elearning.client.place.NameTokens;
import org.clematis.web.elearning.shared.domain.IdentAuthObject;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;


public class GeneralPagePresenter extends Presenter<GeneralPagePresenter.GeneralPageViewInterface, GeneralPagePresenter.GeneralPageProxy> {

	public interface GeneralPageViewInterface extends View {

		void setLanguageHrefs();

		void updateLoggedState();		
		
		void showErrorMessage(ShowErrorMessageEvent event);

		Button getCloseLink();

		void hideErrorMessage();

		void showWarnMessage(String text);

		void hideWarnMessage();		
	}
	
	@ProxyStandard
	@NameToken(NameTokens.GENERAL)
	public interface GeneralPageProxy extends ProxyPlace<GeneralPagePresenter> {
	}
	@ContentSlot
	public static final Type<RevealContentHandler<?>> TYPE_GeneralContent = new Type<RevealContentHandler<?>>();
	
	private final DispatchAsync dispatcher;	
	public static final String USER_COOKIE = "ELP_COOKIE";

	@Inject
	protected GeneralPagePresenter(final DispatchAsync dispatcher, final EventBus eventBus, GeneralPageViewInterface view, GeneralPageProxy proxy) {
		super(eventBus, view, proxy);
		
		this.dispatcher = dispatcher;
	}
	
	@Override
	protected void onReveal() {
        try
        {
            String uID = Cookies.getCookie(GeneralPagePresenter.USER_COOKIE);
            if (uID != null && !uID.equals("") && !uID.equals("undefined"))
            {
            	dispatcher.execute(new GetConnectionStateAction(Window.Location.getHref(), new IdentAuthObject(Integer.parseInt(uID)), ""), new AsyncCallback<GetConnectionStateResult>() {

					@Override
					public void onFailure(Throwable caught) {
						getView().updateLoggedState();
					}

					@Override
					public void onSuccess(GetConnectionStateResult result) {
						
						ELP.setConnectionState(result.getConnectionState());
						getView().updateLoggedState();
						
					}
            	});
            }
        }
        catch (Exception e)
        {
            Cookies.removeCookieNative(GeneralPagePresenter.USER_COOKIE, "/");
        }
        getView().updateLoggedState();
		super.onReveal();
	}

	@Override
	protected void revealInParent() {
		RevealRootContentEvent.fire(this, this);
		getView().setLanguageHrefs();		
	}

	@Override
	protected void onBind() {
		super.onBind();
		registerHandler(getEventBus().addHandler(LoginEvent.getType(),
				new LoginEvent.LoginHandler() {
					
					@Override
					public void onLogin(LoginEvent event) {
						
						if (ELP.getConnectionState().isLoggedIn && ELP.getConnectionState().isRemembered) {
							Cookies.setCookie(USER_COOKIE, Integer.toString(ELP.getCurrentUserId()), new Date(0), null, "/", false);
						}
						
                        getView().updateLoggedState();											 
					};
		}));
		
		registerHandler(getEventBus().addHandler(LogoutEvent.getType(),
				new LogoutEvent.LogoutHandler() {
					
					@Override
					public void onLogout(LogoutEvent event) {
						
						ELP.getConnectionState().isLoggedIn = false;
						ELP.getConnectionState().user = null;
						
						Cookies.removeCookieNative(GeneralPagePresenter.USER_COOKIE, "/");
						
                        getView().updateLoggedState();				
            		};
		}));
		
		registerHandler(getEventBus().addHandler(ShowErrorMessageEvent.getType(),
				new ShowErrorMessageEvent.ShowErrorMessageHandler() {

					@Override
					public void onShowErrorMessage(ShowErrorMessageEvent event) {
						getView().showErrorMessage(event);
					}
		}));
		
		registerHandler(getEventBus().addHandler(ShowWarnMessageEvent.getType(),
				new ShowWarnMessageEvent.ShowWarnMessageHandler() {

					@Override
					public void onShowWarnMessage(ShowWarnMessageEvent event) {
						getView().showWarnMessage(event.getWarnMessage());
					}
		}));
	}
	
	
}
