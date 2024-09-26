package org.clematis.web.elearning.server.handlers.courses;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.clematis.web.elearning.client.teachers.courses.SelectCoursesAction;
import org.clematis.web.elearning.client.teachers.courses.SelectCoursesResult;
import org.clematis.web.elearning.server.BasicHandler;
import org.clematis.web.elearning.shared.domain.Course;

import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

public class SelectCoursesHandler extends BasicHandler implements ActionHandler<SelectCoursesAction,SelectCoursesResult>  {

	@Override
	public SelectCoursesResult execute(SelectCoursesAction action, ExecutionContext context) throws ActionException {
		try {
			selectCourses(action.getCourses(), action.getTeacherId());
			return new SelectCoursesResult();
		} catch (Exception e) {

			throw new ActionException(e);
		}
	}

	@Override
	public Class<SelectCoursesAction> getActionType() {
		return SelectCoursesAction.class;
	}

	@Override
	public void undo(SelectCoursesAction action, SelectCoursesResult result, ExecutionContext context) throws ActionException {
		
	}

    public void selectCourses(List<Course> courses, int teacherId) throws Exception
    {
        PreparedStatement cstmt = null;
        Connection connection = null;
        ResultSet rs = null;
        try
        {
            connection = this.getConnection();
            cstmt = connection.prepareStatement("delete from TEACHER_COURSES where ID_TEACHER=?;");
            cstmt.setInt(1, teacherId);
            cstmt.executeUpdate();
            
            // TODO batch update
            for(Course course:courses )
            {
	            cstmt = connection.prepareStatement("insert into TEACHER_COURSES(ID_TEACHER,ID_COURSE) values (?,?);");
	            cstmt.setInt(1, teacherId);
	            cstmt.setInt(2, course.getId());
	            cstmt.executeUpdate();
            }

        }
        catch (Exception e)
        {
            //logWithUserInfo(GetConnectionStateHandler.class.getName(), Level.SEVERE, Thread.currentThread().getName(), e);
            throw new Exception(e);
        }
        finally
        {
            close(cstmt, rs, connection);
        }
    }	
    
  
}
