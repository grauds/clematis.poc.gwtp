package org.clematis.web.elearning.client.courses;

import java.util.ArrayList;
import java.util.List;

import org.clematis.web.elearning.client.ELP;
import org.clematis.web.elearning.client.courses.CourseSelectedEvent.CourseSelectedHandler;
import org.clematis.web.elearning.client.courses.CoursesSelectedEvent.CoursesSelectedHandler;
import org.clematis.web.elearning.client.courses.GetCoursesGroupResult;
import org.clematis.web.elearning.client.general.ShowErrorMessageEvent;
import org.clematis.web.elearning.client.students.StudentTeacherListWidget;
import org.clematis.web.elearning.client.teachers.GetTeachersAction;
import org.clematis.web.elearning.client.teachers.GetTeachersResult;
import org.clematis.web.elearning.client.teachers.TeacherListWidget;
import org.clematis.web.elearning.shared.domain.Course;
import org.clematis.web.elearning.shared.domain.CoursesGroup;
import org.clematis.web.elearning.shared.domain.Teacher;

import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.ViewImpl;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class CoursesView extends ViewImpl implements CoursesPresenter.MyView, CourseSelectedHandler, CoursesSelectedHandler {

	private final Widget widget;
	
	@UiField VerticalPanel coursesGroupsList;
	@UiField HTMLPanel messagePanel;
	@UiField VerticalPanel teachersSearchResults;
	@UiField HTMLPanel searchMessagePanel;

	private final DispatchAsync dispatcher;
	private final EventBus eventBus;
	
	private final List<CoursesGroupListWidget> coursesGroupsWidgets = new ArrayList<CoursesGroupListWidget>();
	private final List<HandlerRegistration> coursesHandlers = new ArrayList<HandlerRegistration>(); 
	
	public interface Binder extends UiBinder<Widget, CoursesView> {
	}

	@Inject
	public CoursesView(final Binder binder, final DispatchAsync dispatcher, final EventBus eventBus) {
		widget = binder.createAndBindUi(this);

		messagePanel.setVisible(false);
		searchMessagePanel.setVisible(false);
		
		this.dispatcher = dispatcher;
		this.eventBus = eventBus;
	}

	@Override
	public Widget asWidget() {
		return widget;
	}

	@Override
	public void showCourses(GetCoursesGroupResult result) {
        coursesGroupsList.clear();
        coursesGroupsWidgets.clear();
        /**
         * Unregister old listeners
         */
        for (HandlerRegistration registration : coursesHandlers){
        	registration.removeHandler();
        }
        coursesHandlers.clear();
        /**
         * Show new tree
         */
        if ( result != null ) {
            for ( CoursesGroup coursesGroup : result.getCoursesGroups() ) {
            	CoursesGroupListWidget coursesGroupListWidget = new CoursesGroupListWidget(coursesGroup);
            	/**
            	 * Register listeners
            	 */
            	coursesHandlers.add(coursesGroupListWidget.addCoursesSelectedEventHandler(this));
            	coursesHandlers.addAll(coursesGroupListWidget.addCourseSelectedEventHandler(this));
            	/**
            	 * Add to list of widgets
            	 */
            	coursesGroupsWidgets.add(coursesGroupListWidget);
            	/**
            	 * Add to list of handlers
            	 */
            	coursesGroupsList.add(coursesGroupListWidget);
            }
        }        
	}
	
	
	@Override
	public void showTeachers(GetTeachersResult result) {
		teachersSearchResults.clear();
        if ( result != null ) {
            for ( Teacher teacher : result.getTeachers() ) {		
        		if ( ELP.getConnectionState().isLoggedIn && ELP.getConnectionState().isStudent() ) {
        			teachersSearchResults.add(new StudentTeacherListWidget(teacher, dispatcher, eventBus));
        		} else {
        			teachersSearchResults.add(new TeacherListWidget(teacher, dispatcher, eventBus));	
        		}
            }
        }        
	}	

	@Override
	public void showLoader(boolean visible) {
		messagePanel.setVisible(visible);	
	}

	@Override
	public void clearCourses() {
        coursesGroupsList.clear();
	}

	@Override
	public void clearTeachers() {
		teachersSearchResults.clear();
	}

	@Override
	public void showTeachersLoader(boolean visible) {
		searchMessagePanel.setVisible(visible);	
	}

	@Override
	public void onCoursesSelected(CoursesSelectedEvent event) {
		clearTeachers();
		showTeachersLoader(true);
		dispatcher.execute(new GetTeachersAction(getSelectedCourses()), new AsyncCallback<GetTeachersResult>() {

			@Override
			public void onFailure(Throwable caught) {
				showTeachersLoader(false);
				eventBus.fireEvent(new ShowErrorMessageEvent("Ошибка получения списка преподавателей ", caught));
			}

			@Override
			public void onSuccess(GetTeachersResult result) {
				showTeachersLoader(false);
				showTeachers(result);
			}
		});		
	}	

	@Override
	public void onCourseSelected(CourseSelectedEvent event) {
		clearTeachers();
		showTeachersLoader(true);
		dispatcher.execute(new GetTeachersAction(getSelectedCourses()), new AsyncCallback<GetTeachersResult>() {

			@Override
			public void onFailure(Throwable caught) {
				showTeachersLoader(false);
				eventBus.fireEvent(new ShowErrorMessageEvent("Ошибка получения списка преподавателей ", caught));
			}

			@Override
			public void onSuccess(GetTeachersResult result) {
				showTeachersLoader(false);
				showTeachers(result);
			}
		});
	}

	private List<Course> getSelectedCourses() {
		List<Course> courses = new ArrayList<Course>();
		
		for (CoursesGroupListWidget coursesGroupListWidget : coursesGroupsWidgets) {
			for (CourseListWidget courseListWidget : coursesGroupListWidget.getCourseWidgets()) {
				if (courseListWidget.isSelected()) {
					courses.add(courseListWidget.getCourse());
				}
			}
		}
		
		return courses;
	}

}
