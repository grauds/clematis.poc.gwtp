package org.clematis.web.elearning.client.teachers.courses;

import java.util.List;

import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.NameToken;

import org.clematis.web.elearning.client.ELP;
import org.clematis.web.elearning.client.place.NameTokens;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import org.clematis.web.elearning.client.login.navigation.TeacherGatekeeper;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.google.inject.Inject;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;

import org.clematis.web.elearning.client.courses.CourseSelectedEvent;
import org.clematis.web.elearning.client.courses.GetCoursesGroupAction;
import org.clematis.web.elearning.client.courses.GetCoursesGroupResult;
import org.clematis.web.elearning.client.general.GeneralPagePresenter;
import org.clematis.web.elearning.client.general.ShowErrorMessageEvent;
import org.clematis.web.elearning.shared.domain.Course;
import org.clematis.web.elearning.shared.domain.CoursesGroup;

public class SelectCoursesPresenter
		extends
		Presenter<SelectCoursesPresenter.MyView, SelectCoursesPresenter.MyProxy> {

	public interface MyView extends View {

		void clearCourses();

		void showLoader(boolean b);

		void showCourses(List<CoursesGroup> coursesGroups, List<Course> courses);
	}

	@ProxyCodeSplit
	@NameToken(NameTokens.TEACHER_SELECT_COURSES)
	@UseGatekeeper(TeacherGatekeeper.class)
	public interface MyProxy extends ProxyPlace<SelectCoursesPresenter> {
	}
	
	private final DispatchAsync dispatcher;	

	@Inject
	public SelectCoursesPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy, final DispatchAsync dispatcher) {
		super(eventBus, view, proxy);

		this.dispatcher = dispatcher;			
	}

	@Override
	protected void revealInParent() {
		RevealContentEvent.fire(this, GeneralPagePresenter.TYPE_GeneralContent, this);
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
			public void onSuccess(final GetCoursesGroupResult courses) {
				dispatcher.execute(new GetTeacherCoursesAction(ELP.getConnectionState().user.getUser().getId()), new AsyncCallback<GetTeacherCoursesResult>() {

					@Override
					public void onFailure(Throwable caught) {
						
					}

					@Override
					public void onSuccess(GetTeacherCoursesResult teacherCourses) {
						getView().showLoader(false);
						getView().showCourses(courses.getCoursesGroups(),teacherCourses.getCourses());						
					}
				});
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
