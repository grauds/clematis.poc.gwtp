package org.clematis.web.elearning.client.general;

import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.NameToken;
import org.clematis.web.elearning.client.place.NameTokens;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.google.inject.Inject;
import com.google.gwt.event.shared.EventBus;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import org.clematis.web.elearning.client.general.GeneralPagePresenter;

public class PageNotFoundPresenter extends
		Presenter<PageNotFoundPresenter.MyView, PageNotFoundPresenter.MyProxy> {

	public interface MyView extends View {
	}

	@ProxyCodeSplit
	@NameToken(NameTokens.PAGE_404)
	public interface MyProxy extends ProxyPlace<PageNotFoundPresenter> {
	}

	@Inject
	public PageNotFoundPresenter(final EventBus eventBus, final MyView view,
			final MyProxy proxy) {
		super(eventBus, view, proxy);
	}

	@Override
	protected void revealInParent() {
		RevealContentEvent.fire(this, GeneralPagePresenter.TYPE_GeneralContent,
				this);
	}

	@Override
	protected void onBind() {
		super.onBind();
	}
}
