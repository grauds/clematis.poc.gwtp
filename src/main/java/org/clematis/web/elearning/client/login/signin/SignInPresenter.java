package org.clematis.web.elearning.client.login.signin;

import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.NameToken;

import org.clematis.web.elearning.client.ELP;
import org.clematis.web.elearning.client.place.NameTokens;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.google.inject.Inject;
import com.google.gwt.event.shared.EventBus;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import org.clematis.web.elearning.client.general.GeneralPagePresenter;

public class SignInPresenter extends
		Presenter<SignInPresenter.MyView, SignInPresenter.MyProxy> {

	public interface MyView extends View {

		void showLoginWidget(boolean show);
	}

	@ProxyCodeSplit
	@NameToken(NameTokens.SIGNIN)
	public interface MyProxy extends ProxyPlace<SignInPresenter> {
	}

	@Inject
	public SignInPresenter(final EventBus eventBus, final MyView view,
			final MyProxy proxy) {
		super(eventBus, view, proxy);
	}

	@Override
	protected void revealInParent() {
		RevealContentEvent.fire(this, GeneralPagePresenter.TYPE_GeneralContent,
				this);
	}

	@Override
	protected void onReset() {
		getView().showLoginWidget(!ELP.getConnectionState().isLoggedIn);
		super.onReset();
	}

	@Override
	protected void onReveal() {
		getView().showLoginWidget(!ELP.getConnectionState().isLoggedIn);
		super.onReveal();
	}

	@Override
	protected void onBind() {
		super.onBind();
	}
}
