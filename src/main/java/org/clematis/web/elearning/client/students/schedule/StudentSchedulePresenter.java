package org.clematis.web.elearning.client.students.schedule;

import java.util.List;

import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.NameToken;

import org.clematis.web.elearning.client.ELP;
import org.clematis.web.elearning.client.place.NameTokens;

import com.gwtplatform.mvp.client.annotations.UseGatekeeper;

import org.clematis.web.elearning.client.login.navigation.StudentGatekeeper;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.google.inject.Inject;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import org.clematis.web.elearning.client.general.GeneralPagePresenter;
import org.clematis.web.elearning.client.general.ShowErrorMessageEvent;
import org.clematis.web.elearning.shared.domain.Event;

public class StudentSchedulePresenter
		extends
		Presenter<StudentSchedulePresenter.MyView, StudentSchedulePresenter.MyProxy> {

	public interface MyView extends View {

		void showLoader(boolean b);

		void showEvents(List<Event> events);

		void clearEvents();
	}
	
	private final DispatchAsync dispatcher;	
	
	@ProxyCodeSplit
	@NameToken(NameTokens.STUDENT_SCHEDULE)
	@UseGatekeeper(StudentGatekeeper.class)
	public interface MyProxy extends ProxyPlace<StudentSchedulePresenter> {
	}

	@Inject
	public StudentSchedulePresenter(final EventBus eventBus, final MyView view,
			final MyProxy proxy, final DispatchAsync dispatcher) {
		super(eventBus, view, proxy);

		this.dispatcher = dispatcher;	
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
	
	@Override
	protected void onReveal() {
		super.onReveal();
		getView().clearEvents();
		getView().showLoader(true);
		dispatcher.execute(new GetStudentEventsAction(ELP.getCurrentUserId()), new AsyncCallback<GetStudentEventsResult>() {

			@Override
			public void onFailure(Throwable caught) {
				getView().showLoader(false);
				getEventBus().fireEvent(new ShowErrorMessageEvent("Ошибка получения расписания ", caught));
			}

			@Override
			public void onSuccess(final GetStudentEventsResult events) {
				
				getView().showLoader(false);
				getView().showEvents(events.getEvents());						
			}
			
		});
	}		
}
