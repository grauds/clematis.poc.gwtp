package org.clematis.web.elearning.client.general;

import org.clematis.web.elearning.client.ELP;
import org.clematis.web.elearning.client.general.GeneralPagePresenter.GeneralPageViewInterface;
import org.clematis.web.elearning.client.login.LoginEvent;
import org.clematis.web.elearning.client.login.LoginPopupWidget;
import org.clematis.web.elearning.client.login.LogoutEvent;
import org.clematis.web.elearning.client.login.actions.GetConnectionStateAction;
import org.clematis.web.elearning.client.login.actions.GetConnectionStateResult;
import org.clematis.web.elearning.client.login.navigation.SingnedInGatekeeper;
import org.clematis.web.elearning.client.place.NameTokens;
import org.clematis.web.elearning.shared.domain.NamePasswordAuthObject;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.http.client.UrlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.ViewImpl;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;


public class GeneralPageView extends ViewImpl implements GeneralPageViewInterface {
	public interface Binder extends UiBinder<Widget, GeneralPageView> {
	}
	private final Widget widget;

	@UiField HorizontalPanel panel;
	
	@UiField Button loginButton;
	@UiField Button cabinetButton;	
	@UiField Button settingsButton;
	
	private PopupPanel loginPopupPanel;
	private LoginPopupWidget loginPopupWidget;
	
	private PopupPanel userMenuPopupPanel;
	private UserMenuWidget userMenu;	
	
	private final EventBus eventBus;		
	private final PlaceManager placeManager;	

	@UiField Anchor aEnglish;
	@UiField Anchor aRussian;
	
	@UiField VerticalPanel errorbannerholder;
	@UiField Label errorbanner;
	@UiField Button closeLink;	
	
	@UiField VerticalPanel warnbannerholder;
	@UiField Label warnbanner;
	@UiField Button closeWarnLink;		

	@Inject
	protected GeneralPageView(final EventBus eventBus, Binder binder, final DispatchAsync dispatcher, final PlaceManager placeManager) {
		widget = binder.createAndBindUi(this);
		
		this.eventBus = eventBus;		
		this.placeManager = placeManager;	
		
		this.errorbannerholder.setVisible(false);
		this.warnbannerholder.setVisible(false);
		
		loginPopupWidget = new LoginPopupWidget(eventBus, dispatcher, placeManager)
		{
			@Override
			public void doLogin() {
				
				loginPopupPanel.hide();
				sendAuth(dispatcher, placeManager);
			}			
			
			@Override
			public void doRegister() {
				loginPopupPanel.hide();
			}
			
		};
		
		loginPopupPanel = new PopupPanel(true);
		loginPopupPanel.setWidget(loginPopupWidget);	
		
		userMenuPopupPanel = new PopupPanel(true);
		userMenu = new UserMenuWidget()
		{
			@Override
			public void onLogout(ClickEvent e) {
				userMenuPopupPanel.hide();
				eventBus.fireEvent(new LogoutEvent());
				// TODO reveal the same place
				placeManager.revealPlace(new PlaceRequest(NameTokens.MAIN));
			}
			
		};
		userMenuPopupPanel.setWidget(userMenu);	
		
	}
	
	@UiHandler("loginButton")
	public void onLoginButton(ClickEvent clickEvent) {
		loginPopupPanel.setPopupPosition((int) (loginButton.getAbsoluteLeft() + loginButton.getOffsetWidth()/2 - 290), loginButton.getAbsoluteTop() + loginButton.getOffsetHeight() + 15);
		loginPopupPanel.show();
	}
	
	@UiHandler("mainButton")
	public void onMainButton(ClickEvent clickEvent) {
		placeManager.revealPlace(new PlaceRequest(NameTokens.MAIN));		
	}
	
	@UiHandler("settingsButton")
	public void onSettingsButton(ClickEvent clickEvent) {
		userMenuPopupPanel.setPopupPosition((int) (settingsButton.getAbsoluteLeft() + settingsButton.getOffsetWidth()/2 - 290), settingsButton.getAbsoluteTop() + settingsButton.getOffsetHeight() + 15);
		userMenuPopupPanel.show();		
	}		
	
	@UiHandler("cabinetButton")
	public void onCabinetButton(ClickEvent clickEvent) {
		placeManager.revealPlace(new PlaceRequest(NameTokens.CABINET));	
	}	
	
	@UiHandler("coursesButton")
	public void onCoursesButton(ClickEvent clickEvent) {
		placeManager.revealPlace(new PlaceRequest(NameTokens.COURSES));		
	}
	
	@UiHandler("closeLink")
	public void onCloseErrorLink(ClickEvent clickEvent) {
		hideErrorMessage();		
	}
	
	@UiHandler("closeWarnLink")
	public void onCloseWarnLink(ClickEvent clickEvent) {
		hideWarnMessage();		
	}

	@Override
	public void setLanguageHrefs() {
		UrlBuilder builder = Window.Location.createUrlBuilder();
		aEnglish.setHref(builder.setParameter("locale", "en").buildString());
		aRussian.setHref(builder.setParameter("locale", "ru-RU").buildString());
	}	
	
	@Override
	public Widget asWidget() {
		return widget;
	}

	@Override
	public void setInSlot(Object slot, IsWidget content) {
		if (slot == GeneralPagePresenter.TYPE_GeneralContent) {
			panel.clear();
			panel.add(content);
		} else {
			super.setInSlot(slot, content);
		}
	}
	
	@Override
	public Button getCloseLink() {
		return closeLink;
	}	

	@Override
	public void updateLoggedState() {
 		loginButton.setVisible(!ELP.getConnectionState().isLoggedIn);		
		cabinetButton.setVisible(ELP.getConnectionState().isLoggedIn);
		settingsButton.setVisible(ELP.getConnectionState().isLoggedIn);
		userMenu.updateMenu();
	}
	
	@Override
	public void showErrorMessage(ShowErrorMessageEvent event) {
		if ( event != null ) {
			errorbanner.setText(event.getExceptionDetails().getMessage());
		} else {
			errorbanner.setText("Неизвестная ошибка");
		}
		errorbannerholder.setVisible(true);
	}
	
	@Override
	public void hideErrorMessage() {
		errorbanner.setText("");
		errorbannerholder.setVisible(false);
	}	
	
	@Override
	public void showWarnMessage(String text) {
		if ( text != null ) {
			warnbanner.setText(text);
			warnbannerholder.setVisible(true);
		} 		
	}
	
	@Override
	public void hideWarnMessage() {
		warnbanner.setText("");
		warnbannerholder.setVisible(false);
	}		

	private void sendAuth(final DispatchAsync dispatcher, final PlaceManager placeManager) {
		
		loginPopupWidget.enable(false);
		
    	dispatcher.execute(new GetConnectionStateAction(Window.Location.getHref(), new NamePasswordAuthObject(loginPopupWidget.getUserName(),
    														                                            loginPopupWidget.getUserPassword()), ""), 
				new AsyncCallback<GetConnectionStateResult>() {

			@Override
			public void onFailure(Throwable caught) {

				eventBus.fireEvent(new ShowErrorMessageEvent("Ошибка авторизации ", caught));
				loginPopupWidget.enable(true);
				
			}

			@Override
			public void onSuccess(GetConnectionStateResult result) {

				loginPopupWidget.enable(true);
				// Initialize ConnectionState
				ELP.setConnectionState(result.getConnectionState());
				ELP.getConnectionState().isRemembered = loginPopupWidget.getRemember().getValue();
				// Check if we are inside the registration process:
				PlaceRequest placeToFollow = SingnedInGatekeeper.getRestoredOrDecodedUrl(result.getSavedNextPage(), placeManager.getCurrentPlaceRequest());
				if (ELP.getConnectionState().user != null && NameTokens.REGISTER.equals(placeToFollow.getNameToken())
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
}
