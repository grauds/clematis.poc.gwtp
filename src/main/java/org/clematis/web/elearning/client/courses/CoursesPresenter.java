package org.clematis.web.elearning.client.courses;

import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.NameToken;

import org.clematis.web.elearning.client.place.NameTokens;
import org.clematis.web.elearning.client.teachers.GetTeachersResult;

import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.google.inject.Inject;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;

import org.clematis.web.elearning.client.courses.GetCoursesGroupAction;
import org.clematis.web.elearning.client.courses.GetCoursesGroupResult;
import org.clematis.web.elearning.client.general.GeneralPagePresenter;
import org.clematis.web.elearning.client.general.ShowErrorMessageEvent;

public class CoursesPresenter extends
		Presenter<CoursesPresenter.MyView, CoursesPresenter.MyProxy> {

	public interface MyView extends View {

		void showCourses(GetCoursesGroupResult result);

		void showLoader(boolean visible);

		void clearCourses();

		void showTeachers(GetTeachersResult result);

		void clearTeachers();

		void showTeachersLoader(boolean visible);
	}

	@ProxyCodeSplit
	@NameToken(NameTokens.COURSES)
	public interface MyProxy extends ProxyPlace<CoursesPresenter> {
	}

	private final DispatchAsync dispatcher;
	
	@Inject
	public CoursesPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy, final DispatchAsync dispatcher) {
		super(eventBus, view, proxy);
		this.dispatcher = dispatcher;		
	}

	@Override
	protected void revealInParent() {
		RevealContentEvent.fire(this, GeneralPagePresenter.TYPE_GeneralContent, this);
		
		getView().clearTeachers();
	}
	

	@Override
	protected void onReveal() {
		super.onReveal();
		getView().clearCourses();
		getView().showLoader(true);
		dispatcher.execute(new GetCoursesGroupAction(), new AsyncCallback<GetCoursesGroupResult>() {

			@Override
			public void onFailure(Throwable caught) {
				getView().showLoader(false);
				getEventBus().fireEvent(new ShowErrorMessageEvent("Ошибка получения списка курсов ", caught));
			}

			@Override
			public void onSuccess(GetCoursesGroupResult result) {
				getView().showLoader(false);
				getView().showCourses(result);
			}
			
		});
	}

	@Override
	protected void onBind() {	
		super.onBind();
		
		registerHandler(getEventBus().addHandler(CourseSelectedEvent.getType(),
				new CourseSelectedEvent.CourseSelectedHandler() {
					
					@Override
					public void onCourseSelected(CourseSelectedEvent event) {
						
					}
				}));
		
		
	}
}
