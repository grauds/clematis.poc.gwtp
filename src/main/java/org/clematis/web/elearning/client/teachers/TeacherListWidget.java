package org.clematis.web.elearning.client.teachers;

import java.util.List;

import org.clematis.web.elearning.client.courses.CourseListWidget;
import org.clematis.web.elearning.client.events.ScheduleWidget;
import org.clematis.web.elearning.client.general.ShowErrorMessageEvent;
import org.clematis.web.elearning.client.teachers.courses.GetTeacherCoursesAction;
import org.clematis.web.elearning.client.teachers.courses.GetTeacherCoursesResult;
import org.clematis.web.elearning.client.teachers.schedule.GetTeacherEventsAction;
import org.clematis.web.elearning.client.teachers.schedule.GetTeacherEventsResult;
import org.clematis.web.elearning.shared.domain.Course;
import org.clematis.web.elearning.shared.domain.Teacher;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.dispatch.shared.DispatchAsync;

public class TeacherListWidget extends Composite {

	private static TeacherListWidgetUiBinder uiBinder = GWT.create(TeacherListWidgetUiBinder.class);

	interface TeacherListWidgetUiBinder extends UiBinder<Widget, TeacherListWidget> {
	}
	
	@UiField FlexTable info;
	@UiField(provided=true) ScheduleWidget scheduleWidget;
	@UiField VerticalPanel teacherCourses;
	
	private DispatchAsync dispatcher;
	private EventBus eventBus;
	private Teacher teacher;
	
	

	public TeacherListWidget() {
		scheduleWidget = new ScheduleWidget();
		initWidget(uiBinder.createAndBindUi(this));
	}

	public TeacherListWidget(Teacher teacher, final DispatchAsync dispatcher, final EventBus eventBus) {
		
		scheduleWidget = new ScheduleWidget(teacher.getId(), dispatcher, eventBus);
		initWidget(uiBinder.createAndBindUi(this));
		
		this.dispatcher = dispatcher;
		this.eventBus = eventBus;
		
		this.teacher = teacher;		
		
		if (teacher!=null) {
			info.setWidget(0, 0, new Label("Имя"));
			info.setWidget(0, 1, new Label(teacher.getName()));
			
			info.setWidget(1, 0, new Label("Фамилия"));
			info.setWidget(1, 1, new Label(teacher.getSecondName()));
			
			info.setWidget(2, 0, new Label("E-Mail"));
			info.setWidget(2, 1, new Label(teacher.getEmail().getText()));
		};
		
	}

	@Override
	protected void onLoad() {
		dispatcher.execute(new GetTeacherCoursesAction(teacher.getId()), new AsyncCallback<GetTeacherCoursesResult>() {

			@Override
			public void onFailure(Throwable caught) {
				eventBus.fireEvent(new ShowErrorMessageEvent("Ошибка получения списка курсов преподавателей ", caught));
			}

			@Override
			public void onSuccess(GetTeacherCoursesResult coursesResult) {
				List<Course> courses = coursesResult.getCourses();	
				 if (courses!= null) {
					for ( Course course:courses) {
						teacherCourses.add(new CourseListWidget(course,false));
					}
				 }
			}
		});
		dispatcher.execute(new GetTeacherEventsAction(teacher.getId()), new AsyncCallback<GetTeacherEventsResult>() {

			@Override
			public void onFailure(Throwable caught) {
				eventBus.fireEvent(new ShowErrorMessageEvent("Ошибка получения расписания ", caught));
			}

			@Override
			public void onSuccess(final GetTeacherEventsResult events) {
				
				scheduleWidget.showEvents(events.getEvents());						
			}
			
		});
	}

}
