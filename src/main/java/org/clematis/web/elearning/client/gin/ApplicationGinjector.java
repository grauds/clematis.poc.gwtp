package org.clematis.web.elearning.client.gin;

import com.google.gwt.inject.client.GinModules;
import com.gwtplatform.dispatch.client.gin.DispatchAsyncModule;

import org.clematis.web.elearning.client.courses.CoursesPresenter;
import org.clematis.web.elearning.client.general.GeneralPagePresenter;
import org.clematis.web.elearning.client.gin.ApplicationModule;

import com.google.gwt.inject.client.Ginjector;
import com.google.gwt.event.shared.EventBus;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.google.gwt.inject.client.AsyncProvider;
import com.google.inject.Provider;


import org.clematis.web.elearning.client.login.cabinet.CabinetMenuPresenter;
import org.clematis.web.elearning.client.login.navigation.AdminGatekeeper;
import org.clematis.web.elearning.client.login.navigation.SingnedInGatekeeper;
import org.clematis.web.elearning.client.login.navigation.StudentGatekeeper;
import org.clematis.web.elearning.client.login.navigation.TeacherGatekeeper;
import org.clematis.web.elearning.client.login.register.RegisterPresenter;
import org.clematis.web.elearning.client.general.PageNotFoundPresenter;
import org.clematis.web.elearning.client.general.MainPagePresenter;
import org.clematis.web.elearning.client.login.signin.SignInPresenter;
import org.clematis.web.elearning.client.students.video.StudentVideoRoomPresenter;
import org.clematis.web.elearning.client.teachers.video.TeacherVideoRoomPresenter;
import org.clematis.web.elearning.client.teachers.courses.SelectCoursesPresenter;
import org.clematis.web.elearning.client.teachers.classes.TeacherClassesPresenter;
import org.clematis.web.elearning.client.teachers.schedule.SchedulePresenter;
import org.clematis.web.elearning.client.students.schedule.StudentSchedulePresenter;
import org.clematis.web.elearning.client.students.budget.BudgetLinePresenter;

@GinModules({ DispatchAsyncModule.class, ApplicationModule.class })
public interface ApplicationGinjector extends Ginjector {

	EventBus getEventBus();

	PlaceManager getPlaceManager();
	
	AsyncProvider<PageNotFoundPresenter> getPageNotFoundPresenter();
	
	// Sign up/ Sing in group
	Provider<GeneralPagePresenter> getGeneralPagePresenter();
	AsyncProvider<RegisterPresenter> getRegisterPresenter();

	// Main group
	SingnedInGatekeeper getSignedInGatekeeper();
	StudentGatekeeper getStudentGatekeeper();
	TeacherGatekeeper getTeacherGatekeeper();
	AdminGatekeeper getAdminGatekeeper();

	AsyncProvider<CabinetMenuPresenter> getCabinetMenuPresenter();
	AsyncProvider<MainPagePresenter> getMainPagePresenter();
	AsyncProvider<SignInPresenter> getSignInPresenter();

	AsyncProvider<StudentVideoRoomPresenter> getStudentVideoRoomPresenter();
	AsyncProvider<TeacherVideoRoomPresenter> getTeacherVideoRoomPresenter();
	AsyncProvider<CoursesPresenter> getCoursesPresenter();
	AsyncProvider<SelectCoursesPresenter> getSelectCoursesPresenter();
	AsyncProvider<TeacherClassesPresenter> getTeacherClassesPresenter();
	AsyncProvider<SchedulePresenter> getSchedulePresenter();
	AsyncProvider<StudentSchedulePresenter> getStudentSchedulePresenter();

	AsyncProvider<BudgetLinePresenter> getBudgetLinePresenter();
	
}
