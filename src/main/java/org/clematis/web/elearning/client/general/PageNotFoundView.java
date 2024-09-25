package org.clematis.web.elearning.client.general;

import com.gwtplatform.mvp.client.ViewImpl;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class PageNotFoundView extends ViewImpl implements
		PageNotFoundPresenter.MyView {

	private final Widget widget;

	public interface Binder extends UiBinder<Widget, PageNotFoundView> {
	}

	@Inject
	public PageNotFoundView(final Binder binder) {
		widget = binder.createAndBindUi(this);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}
}
