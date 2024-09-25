package org.clematis.web.elearning.server.guice;

import org.clematis.web.elearning.client.courses.GetCoursesAction;
import org.clematis.web.elearning.client.courses.GetCoursesGroupAction;
import org.clematis.web.elearning.client.events.AddEventAction;
import org.clematis.web.elearning.client.events.DeleteEventAction;
import org.clematis.web.elearning.client.events.EditEventAction;
import org.clematis.web.elearning.client.login.actions.GetConnectionStateAction;
import org.clematis.web.elearning.client.login.actions.RegisterAction;
import org.clematis.web.elearning.client.students.budget.GetOperationsAllAction;
import org.clematis.web.elearning.client.students.classes.GetAvailableClassesAction;
import org.clematis.web.elearning.client.students.classes.GetStudentClassesAction;
import org.clematis.web.elearning.client.students.classes.IsInTeacherClassAction;
import org.clematis.web.elearning.client.students.classes.JoinClassAction;
import org.clematis.web.elearning.client.students.classes.LeaveClassAction;
import org.clematis.web.elearning.client.students.schedule.GetStudentEventsAction;
import org.clematis.web.elearning.client.teachers.GetTeachersAction;
import org.clematis.web.elearning.client.teachers.classes.GetTeacherClassesAction;
import org.clematis.web.elearning.client.teachers.courses.GetTeacherCoursesAction;
import org.clematis.web.elearning.client.teachers.courses.SelectCoursesAction;
import org.clematis.web.elearning.client.teachers.schedule.GetTeacherEventsAction;
import org.clematis.web.elearning.server.handlers.budget.GetOperationsAllHandler;
import org.clematis.web.elearning.server.handlers.classes.GetAvailableClassesHandler;
import org.clematis.web.elearning.server.handlers.classes.GetStudentClassesHandler;
import org.clematis.web.elearning.server.handlers.classes.IsInTeacherClassHandler;
import org.clematis.web.elearning.server.handlers.classes.JoinClassHandler;
import org.clematis.web.elearning.server.handlers.classes.LeaveClassHandler;
import org.clematis.web.elearning.server.handlers.courses.GetCoursesGroupsHandler;
import org.clematis.web.elearning.server.handlers.courses.GetCoursesHandler;
import org.clematis.web.elearning.server.handlers.courses.SelectCoursesHandler;
import org.clematis.web.elearning.server.handlers.events.AddEventHandler;
import org.clematis.web.elearning.server.handlers.events.DeleteEventHandler;
import org.clematis.web.elearning.server.handlers.events.EditEventHandler;
import org.clematis.web.elearning.server.handlers.events.GetStudentEventsHandler;
import org.clematis.web.elearning.server.handlers.events.GetTeacherEventsHandler;
import org.clematis.web.elearning.server.handlers.login.GetConnectionStateHandler;
import org.clematis.web.elearning.server.handlers.login.RegisterHandler;
import org.clematis.web.elearning.server.handlers.teachers.GetTeacherClassesHandler;
import org.clematis.web.elearning.server.handlers.teachers.GetTeacherCoursesHandler;
import org.clematis.web.elearning.server.handlers.teachers.GetTeachersHandler;

import com.gwtplatform.dispatch.rpc.server.guice.HandlerModule;

public class ServerModule extends HandlerModule {

	@Override
	protected void configureHandlers() {

		bindHandler(RegisterAction.class, RegisterHandler.class);
		bindHandler(GetConnectionStateAction.class, GetConnectionStateHandler.class);	

		bindHandler(GetCoursesGroupAction.class, GetCoursesGroupsHandler.class);
		bindHandler(GetCoursesAction.class, GetCoursesHandler.class);
		
		bindHandler(GetTeachersAction.class, GetTeachersHandler.class);
		
		bindHandler(SelectCoursesAction.class, SelectCoursesHandler.class);
		bindHandler(GetTeacherCoursesAction.class, GetTeacherCoursesHandler.class);
		
		bindHandler(IsInTeacherClassAction.class, IsInTeacherClassHandler.class);
		bindHandler(JoinClassAction.class, JoinClassHandler.class);
		bindHandler(LeaveClassAction.class, LeaveClassHandler.class);
		
		bindHandler(GetTeacherClassesAction.class, GetTeacherClassesHandler.class);
		bindHandler(GetStudentClassesAction.class, GetStudentClassesHandler.class);
		bindHandler(GetAvailableClassesAction.class, GetAvailableClassesHandler.class);
		
		bindHandler(AddEventAction.class, AddEventHandler.class);
		bindHandler(EditEventAction.class, EditEventHandler.class);
		bindHandler(DeleteEventAction.class, DeleteEventHandler.class);
		bindHandler(GetStudentEventsAction.class, GetStudentEventsHandler.class);
		bindHandler(GetTeacherEventsAction.class, GetTeacherEventsHandler.class);
		
		bindHandler(GetOperationsAllAction.class, GetOperationsAllHandler.class);
		
	}

}
