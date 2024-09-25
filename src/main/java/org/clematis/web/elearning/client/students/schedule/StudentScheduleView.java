package org.clematis.web.elearning.client.students.schedule;

import java.util.List;

import org.clematis.web.elearning.client.ELP;
import org.clematis.web.elearning.client.events.ScheduleWidget;
import org.clematis.web.elearning.shared.domain.Event;

import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.ViewImpl;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class StudentScheduleView extends ViewImpl implements
		StudentSchedulePresenter.MyView {

	private final Widget widget;

	public interface Binder extends UiBinder<Widget, StudentScheduleView> {
	}
	
	@UiField(provided=true) ScheduleWidget scheduleWidget;
	@UiField HTMLPanel messagePanel;

	@Inject
	public StudentScheduleView(final Binder binder, final DispatchAsync dispatcher, final EventBus eventBus) {
		scheduleWidget = new ScheduleWidget(ELP.getCurrentUserId(), dispatcher, eventBus);
		widget = binder.createAndBindUi(this);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}

	@Override
	public void showLoader(boolean visible) {
		messagePanel.setVisible(visible);
		scheduleWidget.setVisible(!visible);
	}

	@Override
	public void clearEvents() {
		scheduleWidget.clearEvents();
	}	

	@Override
	public void showEvents(List<Event> events) {
		scheduleWidget.showEvents(events);
	}
}
