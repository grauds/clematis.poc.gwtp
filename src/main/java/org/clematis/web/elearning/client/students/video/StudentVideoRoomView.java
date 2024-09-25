package org.clematis.web.elearning.client.students.video;

import com.gwtplatform.mvp.client.ViewImpl;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class StudentVideoRoomView extends ViewImpl implements
		StudentVideoRoomPresenter.MyView {

	private final Widget widget;
	
	@UiField SimplePanel flashHolder;
	
	public interface Binder extends UiBinder<Widget, StudentVideoRoomView> {
	}

	@Inject
	public StudentVideoRoomView(final Binder binder) {
		widget = binder.createAndBindUi(this);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}
	
	@Override
	public void setFlash(HTML html) {
		clearFlash();
		flashHolder.add(html);		
	}

	@Override
	public void clearFlash() {
		flashHolder.clear();
	}	
}
