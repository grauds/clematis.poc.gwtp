package org.clematis.web.elearning.client.login.cabinet;


import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;

import org.clematis.web.elearning.client.general.GeneralPagePresenter;
import org.clematis.web.elearning.client.login.LogoutEvent;
import org.clematis.web.elearning.client.login.navigation.SingnedInGatekeeper;
import org.clematis.web.elearning.client.place.NameTokens;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.google.inject.Inject;
import com.google.gwt.event.shared.EventBus;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;

public class CabinetMenuPresenter extends Presenter<CabinetMenuPresenter.MyView, CabinetMenuPresenter.MyProxy> {

	public interface MyView extends View {

		void createMenu();

		void setCreated(boolean created);
	}

	@ProxyCodeSplit
	@NameToken(NameTokens.CABINET)
	@UseGatekeeper(SingnedInGatekeeper.class)
	public interface MyProxy extends ProxyPlace<CabinetMenuPresenter> {
	}

	@Inject
	public CabinetMenuPresenter(final EventBus eventBus, final MyView view,
			final MyProxy proxy) {
		super(eventBus, view, proxy);
	}

	@Override
	protected void revealInParent() {
		RevealContentEvent.fire(this, GeneralPagePresenter.TYPE_GeneralContent, this);
	}

	@Override
	protected void onReveal() {
		super.onReveal();
		// This is the place where we can be sure the user was logged in.
		getView().createMenu();
	}
	
	@Override
	protected void onBind() {
		super.onBind();
		
		registerHandler(getEventBus().addHandler(LogoutEvent.getType(),
				new LogoutEvent.LogoutHandler() {
					
					@Override
					public void onLogout(LogoutEvent event) {
						
						getView().setCreated(false);				
            		};
		}));
	}
		
}
