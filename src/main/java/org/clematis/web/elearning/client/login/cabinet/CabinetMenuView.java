package org.clematis.web.elearning.client.login.cabinet;

import org.clematis.web.elearning.client.ELP;
import org.clematis.web.elearning.client.fields.BlockInfo;
import org.clematis.web.elearning.client.fields.MenuBlockField;
import org.clematis.web.elearning.client.login.AppMenuItem;
import org.clematis.web.elearning.client.ui.ElementsForm;
import org.clematis.web.elearning.client.ui.ElementsForm.ElementsFormFactory;

import com.gwtplatform.mvp.client.ViewImpl;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class CabinetMenuView extends ViewImpl implements
		CabinetMenuPresenter.MyView {

	private final Widget widget;
	
	@UiField(provided=true) ElementsForm panel;

	private boolean isCreated = false;

	public interface Binder extends UiBinder<Widget, CabinetMenuView> {
	}

	@Inject
	public CabinetMenuView(final Binder binder, ElementsFormFactory elementsFormFactory ) {
		
		panel = elementsFormFactory.create();		
		widget = binder.createAndBindUi(this);
	}

	@Override
	public void createMenu() {
		if ( !isCreated  ) {
			if ( ELP.getConnectionState().isTeacher() ) {
				createTeacherMenu();
			} else if ( ELP.getConnectionState().isStudent() ) {
				createStudentMenu();
			} else if ( ELP.getConnectionState().isAdmin() ) {
				createAdminMenu();
			}
			isCreated = true;
		}
	}
	
	public void setCreated(boolean created) {
		isCreated = created;
	}

	@Override
	public Widget asWidget() {
		return widget;
	}
	
	
	protected void createStudentMenu() {
		
		BlockInfo menu = new BlockInfo();
		
//-------------------------
		
		MenuBlockField video = new MenuBlockField();
		
		AppMenuItem videoClass = new AppMenuItem("Видео-класс", "stvideo");
		AppMenuItem videoArchives = new AppMenuItem("Архивы", "");
		
		
		video.row = 0;
		video.column = 0;		
		
		video.setIconUrl("images/videocam.png");
		video.getItems().add(videoClass);
		video.getItems().add(videoArchives);
		
		menu.getFields().add(video);
		
//-------------------------

		MenuBlockField schedule = new MenuBlockField();
		
		schedule.row = 0;
		schedule.column = 1;
		
		AppMenuItem nearestClasses = new AppMenuItem("Ближайшие занятия", "");
		AppMenuItem classes = new AppMenuItem("Расписание занятий по дням недели", "stschedule");
		
		schedule.setIconUrl("images/schedule.png");
		schedule.getItems().add(nearestClasses);
		schedule.getItems().add(classes);
				
		menu.getFields().add(schedule);
		
//-------------------------

		MenuBlockField payments = new MenuBlockField();
		
		payments.row = 1;
		payments.column = 1;
		
		AppMenuItem payment = new AppMenuItem("Пополнить счет", "");
		AppMenuItem paymentHistory = new AppMenuItem("История платежей", "");
		AppMenuItem budget = new AppMenuItem("Расчет бюджета", "stbudget");
		
		payments.setIconUrl("images/wallet.png");
		payments.getItems().add(payment);
		payments.getItems().add(paymentHistory);
		payments.getItems().add(budget);
				
		menu.getFields().add(payments);		
	
		panel.initBlock(menu, null);
	}
	
	protected void createTeacherMenu() {
		
		BlockInfo menu = new BlockInfo();
		
//-------------------------

		MenuBlockField students = new MenuBlockField();

		AppMenuItem newClass = new AppMenuItem("Создать группу", "newclass");
		AppMenuItem myStudents = new AppMenuItem("Группы", "tstudents");
		
		students.setIconUrl("images/students.png");
		students.getItems().add(newClass);
		students.getItems().add(myStudents);
				
		menu.getFields().add(students);		
		
//-------------------------

		MenuBlockField courses = new MenuBlockField();
		
		courses.row = 0;
		courses.column = 1;
		
		AppMenuItem coursesSelect = new AppMenuItem("Мои курсы", "tcourses");
		
		courses.setIconUrl("images/courses.png");
		courses.getItems().add(coursesSelect);
				
		menu.getFields().add(courses);			

//-------------------------
		
		MenuBlockField video = new MenuBlockField();
		
		AppMenuItem videoClass = new AppMenuItem("Видео-класс", "tvideo");
		AppMenuItem videoArchives = new AppMenuItem("Архивы", "");
		
		
		video.row = 1;
		video.column = 0;		
		
		video.setIconUrl("images/videocam.png");
		video.getItems().add(videoClass);
		video.getItems().add(videoArchives);
		
		menu.getFields().add(video);
		
//-------------------------

		MenuBlockField schedule = new MenuBlockField();
		
		schedule.row = 1;
		schedule.column = 1;
		
		AppMenuItem classes = new AppMenuItem("Расписание занятий по дням недели", "tschedule");
		
		schedule.setIconUrl("images/schedule.png");
		schedule.getItems().add(classes);
				
		menu.getFields().add(schedule);
	
		

	
		panel.initBlock(menu, null);
	}	
	
	protected void createAdminMenu() {
		
		BlockInfo menu = new BlockInfo();
		
//-------------------------

		MenuBlockField users = new MenuBlockField();

		AppMenuItem newStudents = new AppMenuItem("Новый пользователь", "");
		AppMenuItem myClasses = new AppMenuItem("Список пользователей и групп", "");
		
		users.setIconUrl("images/movie.png");
		users.getItems().add(newStudents);
		users.getItems().add(myClasses);
				
		menu.getFields().add(users);		

//-------------------------
		
		MenuBlockField video = new MenuBlockField();
		
		AppMenuItem videoArchives = new AppMenuItem("Архивы", "");
		
		video.row = 1;
		video.column = 0;		
		
		video.setIconUrl("images/movie.png");
		video.getItems().add(videoArchives);
		
		menu.getFields().add(video);
		

		panel.initBlock(menu, null);
	}	
}
