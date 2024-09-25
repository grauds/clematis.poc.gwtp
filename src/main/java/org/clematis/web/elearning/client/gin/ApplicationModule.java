package org.clematis.web.elearning.client.gin;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.inject.Singleton;
import com.gwtplatform.dispatch.shared.SecurityCookie;
import com.gwtplatform.mvp.client.RootPresenter;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.proxy.ParameterTokenFormatter;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.TokenFormatter;

import org.clematis.web.elearning.client.courses.CoursesPresenter;
import org.clematis.web.elearning.client.courses.CoursesView;
import org.clematis.web.elearning.client.general.GeneralPagePresenter;
import org.clematis.web.elearning.client.general.GeneralPageView;
import org.clematis.web.elearning.client.place.ApplicationPlaceManager;
import org.clematis.web.elearning.client.ui.ElementsFactory.ElementsFactoryFactory;
import org.clematis.web.elearning.client.ui.ElementsForm.ElementsFormFactory;
import org.clematis.web.elearning.client.ui.MenuBlockItemWidget.MenuBlockItemWidgetFactory;
import org.clematis.web.elearning.client.ui.MenuListItemWidget.MenuListItemWidgetFactory;
import org.clematis.web.elearning.client.place.DefaultPlace;
import org.clematis.web.elearning.client.place.ErrorPlace;
import org.clematis.web.elearning.client.place.NameTokens;
import org.clematis.web.elearning.client.login.cabinet.CabinetMenuPresenter;
import org.clematis.web.elearning.client.login.cabinet.CabinetMenuView;
import org.clematis.web.elearning.client.login.register.RegisterPresenter;
import org.clematis.web.elearning.client.login.register.RegisterView;
import org.clematis.web.elearning.client.general.PageNotFoundPresenter;
import org.clematis.web.elearning.client.general.PageNotFoundView;
import org.clematis.web.elearning.client.general.MainPagePresenter;
import org.clematis.web.elearning.client.general.MainPageView;
import org.clematis.web.elearning.client.login.signin.SignInPresenter;
import org.clematis.web.elearning.client.login.signin.SignInView;
import org.clematis.web.elearning.client.students.schedule.StudentSchedulePresenter;
import org.clematis.web.elearning.client.students.schedule.StudentScheduleView;
import org.clematis.web.elearning.client.students.video.StudentVideoRoomPresenter;
import org.clematis.web.elearning.client.students.video.StudentVideoRoomView;
import org.clematis.web.elearning.client.teachers.video.TeacherVideoRoomPresenter;
import org.clematis.web.elearning.client.teachers.video.TeacherVideoRoomView;
import org.clematis.web.elearning.client.teachers.courses.SelectCoursesPresenter;
import org.clematis.web.elearning.client.teachers.courses.SelectCoursesView;
import org.clematis.web.elearning.client.teachers.classes.TeacherClassesPresenter;
import org.clematis.web.elearning.client.teachers.classes.TeacherClassesView;
import org.clematis.web.elearning.client.teachers.schedule.SchedulePresenter;
import org.clematis.web.elearning.client.teachers.schedule.ScheduleView;
import org.clematis.web.elearning.client.students.budget.BudgetLinePresenter;
import org.clematis.web.elearning.client.students.budget.BudgetLineView;

public class ApplicationModule extends AbstractPresenterModule {

	@Override
	protected void configure() {
		
		bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);
		bind(PlaceManager.class).to(ApplicationPlaceManager.class).in(Singleton.class);
		bind(TokenFormatter.class).to(ParameterTokenFormatter.class).in(Singleton.class);
		bind(RootPresenter.class).asEagerSingleton();
		
		bindConstant().annotatedWith(SecurityCookie.class).to("SECURE_COOKIE");
		
		bindConstant().annotatedWith(DefaultPlace.class).to(NameTokens.MAIN);
		bindConstant().annotatedWith(ErrorPlace.class).to(NameTokens.PAGE_404);
		
		install(new GinFactoryModuleBuilder().build(MenuBlockItemWidgetFactory.class));		
		install(new GinFactoryModuleBuilder().build(MenuListItemWidgetFactory.class));
		install(new GinFactoryModuleBuilder().build(ElementsFactoryFactory.class));		
		install(new GinFactoryModuleBuilder().build(ElementsFormFactory.class));		
		
		bindPresenter(GeneralPagePresenter.class, GeneralPagePresenter.GeneralPageViewInterface.class, GeneralPageView.class, GeneralPagePresenter.GeneralPageProxy.class);		
		bindPresenter(RegisterPresenter.class, RegisterPresenter.MyView.class, RegisterView.class, RegisterPresenter.MyProxy.class);

		bindPresenter(CabinetMenuPresenter.class, CabinetMenuPresenter.MyView.class, CabinetMenuView.class, CabinetMenuPresenter.MyProxy.class);
		
		bindPresenter(PageNotFoundPresenter.class, PageNotFoundPresenter.MyView.class, PageNotFoundView.class, PageNotFoundPresenter.MyProxy.class);
		bindPresenter(MainPagePresenter.class, MainPagePresenter.MyView.class, MainPageView.class, MainPagePresenter.MyProxy.class);

		bindPresenter(SignInPresenter.class, SignInPresenter.MyView.class, SignInView.class, SignInPresenter.MyProxy.class);

		bindPresenter(StudentVideoRoomPresenter.class, StudentVideoRoomPresenter.MyView.class, StudentVideoRoomView.class, StudentVideoRoomPresenter.MyProxy.class);
		bindPresenter(TeacherVideoRoomPresenter.class, TeacherVideoRoomPresenter.MyView.class, TeacherVideoRoomView.class, TeacherVideoRoomPresenter.MyProxy.class);

		bindPresenter(CoursesPresenter.class, CoursesPresenter.MyView.class, CoursesView.class, CoursesPresenter.MyProxy.class);

		bindPresenter(SelectCoursesPresenter.class, SelectCoursesPresenter.MyView.class, SelectCoursesView.class, SelectCoursesPresenter.MyProxy.class);
		bindPresenter(TeacherClassesPresenter.class, TeacherClassesPresenter.MyView.class, TeacherClassesView.class, TeacherClassesPresenter.MyProxy.class);
		bindPresenter(SchedulePresenter.class, SchedulePresenter.MyView.class, ScheduleView.class, SchedulePresenter.MyProxy.class);
		bindPresenter(StudentSchedulePresenter.class, StudentSchedulePresenter.MyView.class, StudentScheduleView.class, StudentSchedulePresenter.MyProxy.class);

		bindPresenter(BudgetLinePresenter.class,BudgetLinePresenter.MyView.class, BudgetLineView.class, BudgetLinePresenter.MyProxy.class);
	}
}
