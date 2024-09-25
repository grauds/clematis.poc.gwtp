package org.clematis.web.elearning.client.students;

import java.util.List;

import org.clematis.web.elearning.client.ELP;
import org.clematis.web.elearning.client.classes.ClassSelectDialog;
import org.clematis.web.elearning.client.courses.CourseListWidget;
import org.clematis.web.elearning.client.events.ScheduleWidget;
import org.clematis.web.elearning.client.general.ShowErrorMessageEvent;
import org.clematis.web.elearning.client.students.classes.GetAvailableClassesAction;
import org.clematis.web.elearning.client.students.classes.GetAvailableClassesResult;
import org.clematis.web.elearning.client.students.classes.GetStudentClassesAction;
import org.clematis.web.elearning.client.students.classes.GetStudentClassesResult;
import org.clematis.web.elearning.client.students.classes.IsInTeacherClassAction;
import org.clematis.web.elearning.client.students.classes.IsInTeacherClassResult;
import org.clematis.web.elearning.client.students.classes.JoinClassAction;
import org.clematis.web.elearning.client.students.classes.JoinClassResult;
import org.clematis.web.elearning.client.students.classes.LeaveClassAction;
import org.clematis.web.elearning.client.students.classes.LeaveClassResult;
import org.clematis.web.elearning.client.teachers.courses.GetTeacherCoursesAction;
import org.clematis.web.elearning.client.teachers.courses.GetTeacherCoursesResult;
import org.clematis.web.elearning.client.teachers.schedule.GetTeacherEventsAction;
import org.clematis.web.elearning.client.teachers.schedule.GetTeacherEventsResult;
import org.clematis.web.elearning.client.ui.SendMailWidget;
import org.clematis.web.elearning.shared.domain.Course;
import org.clematis.web.elearning.shared.domain.Teacher;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;

public class StudentTeacherListWidget extends Composite {

	private static final StudentTeacherListWidgetUiBinder uiBinder = GWT
			.create(StudentTeacherListWidgetUiBinder.class);

	interface StudentTeacherListWidgetUiBinder extends
			UiBinder<Widget, StudentTeacherListWidget> {
	}
	
	private final PopupPanel popupPanel;
	private final SendMailWidget sendMailWidget;
	
	@UiField FlexTable info;
	@UiField VerticalPanel teacherCourses;	
	@UiField(provided=true) ScheduleWidget scheduleWidget;
	
	private final DispatchAsync dispatcher;
	private final EventBus eventBus;
	
	@UiField Image sendMail;
	
	@UiField Button joinClass;	
	@UiField Button leaveClass;	

	private final Teacher teacher;	
	
	public StudentTeacherListWidget(Teacher teacher, final DispatchAsync dispatcher, final EventBus eventBus) {
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
		}				
		
		sendMailWidget = new SendMailWidget();
		popupPanel = new PopupPanel(true);
		popupPanel.setWidget(sendMailWidget);	
	}
	
	@UiHandler("joinClass")
	public void onJoinClass(ClickEvent e) {
		dispatcher.execute(
			new GetAvailableClassesAction.Builder(
				ELP.getConnectionState().user.getUser().getId()
			).teacherId(teacher.getId()).build(),
			new AsyncCallback<>() {

			@Override
			public void onFailure(Throwable caught) {
				eventBus.fireEvent(new ShowErrorMessageEvent("Ошибка при получении свободных классов преподавателя ", caught));
			}

			@Override
			public void onSuccess(GetAvailableClassesResult result) {
				
				final ClassSelectDialog classSelectDialog = new ClassSelectDialog("Выберите класс, к которому вы хотите присоединиться", result.getClasses())
				{

					@Override
					public void onChooseClass() {

						if ( getSelectedClass() != null ) {
							dispatcher.execute(new JoinClassAction(ELP.getConnectionState().user.getUser().getId(), getSelectedClass().getId()), new AsyncCallback<JoinClassResult>() {

								@Override
								public void onFailure(Throwable caught) {
									eventBus.fireEvent(new ShowErrorMessageEvent("Ошибка присоединения к классу ", caught));
								}

								@Override
								public void onSuccess(JoinClassResult coursesResult) {
									refreshEnrollmentButtons();
								}
							});
						}
						super.onChooseClass();
					}
					
				};
				
                classSelectDialog.setGlassEnabled(true);
                classSelectDialog.center();
                classSelectDialog.show();                
				
			}
		});
	}
	
	@UiHandler("leaveClass")
	public void onLeaveClass(ClickEvent e) {
		dispatcher.execute(new GetStudentClassesAction.Builder(
				ELP.getConnectionState().user.getUser().getId()
			).teacherId(teacher.getId()).build(),
			new AsyncCallback<>() {

			@Override
			public void onFailure(Throwable caught) {
				eventBus.fireEvent(new ShowErrorMessageEvent("Ошибка при получении классов преподавателя ", caught));
			}

			@Override
			public void onSuccess(GetStudentClassesResult result) {
				
				final ClassSelectDialog classSelectDialog = new ClassSelectDialog("Выберите класс, который вы хотите покинуть",result.getClasses())
				{

					@Override
					public void onChooseClass() {

						if (getSelectedClass() != null){
							dispatcher.execute(new LeaveClassAction(ELP.getConnectionState().user.getUser().getId(), getSelectedClass().getId()), new AsyncCallback<LeaveClassResult>() {

								@Override
								public void onFailure(Throwable caught) {
									eventBus.fireEvent(new ShowErrorMessageEvent("Ошибка выхода из класса ", caught));
								}

								@Override
								public void onSuccess(LeaveClassResult coursesResult) {
									refreshEnrollmentButtons();
								}
							});
						}
						super.onChooseClass();
					}
					
				};
				
                classSelectDialog.setGlassEnabled(true);
                classSelectDialog.center();
                classSelectDialog.show();                
				
			}
		});	
	}
	
    @UiHandler("sendMail")
    public void sendMail(ClickEvent e) {
		popupPanel.setPopupPosition(sendMail.getAbsoluteLeft() - 280, sendMail.getAbsoluteTop() + sendMail.getOffsetHeight() + 15);
		popupPanel.show();    	
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
						teacherCourses.add(new CourseListWidget(course, false));
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
		
		refreshEnrollmentButtons();
	}
	
	protected void refreshEnrollmentButtons() {
		
		dispatcher.execute(new GetAvailableClassesAction.Builder(
				ELP.getConnectionState().user.getUser().getId()
			).teacherId(teacher.getId()).build(),
			new AsyncCallback<>() {

			@Override
			public void onFailure(Throwable caught) {
				eventBus.fireEvent(new ShowErrorMessageEvent("Ошибка выхода из класса ", caught));
			}

			@Override
			public void onSuccess(GetAvailableClassesResult result) {
                joinClass.setVisible(!result.getClasses().isEmpty());
			}
		});	
		
		dispatcher.execute(new IsInTeacherClassAction(ELP.getConnectionState().user.getUser().getId(), teacher.getId()), new AsyncCallback<IsInTeacherClassResult>() {

			@Override
			public void onFailure(Throwable caught) {
				eventBus.fireEvent(new ShowErrorMessageEvent("Ошибка выхода из класса ", caught));
			}

			@Override
			public void onSuccess(IsInTeacherClassResult result) {
				if (result.isResult()) {

	  			    leaveClass.setVisible(true); 
					
				} else {
					 
					leaveClass.setVisible(false); 
					
				}
			}
		});		
		
	}
}
