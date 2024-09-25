package org.clematis.web.elearning.server.handlers.teachers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.clematis.web.elearning.client.teachers.courses.GetTeacherCoursesAction;
import org.clematis.web.elearning.client.teachers.courses.GetTeacherCoursesResult;
import org.clematis.web.elearning.server.BasicHandler;
import org.clematis.web.elearning.shared.domain.Course;

import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

public class GetTeacherCoursesHandler extends BasicHandler implements ActionHandler<GetTeacherCoursesAction, GetTeacherCoursesResult>  {
	
	
	
    public List<Course> getSelectedCourses(int teacherId) throws Exception
    {
        PreparedStatement cstmt = null;
        Connection connection = null;
        ResultSet rs = null;
        try
        {
            connection = this.getConnection();
            
            cstmt = connection.prepareStatement("select COURSES.* from TEACHER_COURSES,COURSES where TEACHER_COURSES.ID_TEACHER=? AND COURSES.ID=TEACHER_COURSES.ID_COURSE;");
            cstmt.setInt(1, teacherId);
            rs = cstmt.executeQuery();
            
            List<Course> courses = new ArrayList<Course>();
            
            while(rs.next()) {
            	Course course = new Course();
            	
            	course.setName(rs.getString("NAME"));
            	course.setDescription(rs.getString("DESCRIPTION"));
            	course.setId(rs.getInt("ID"));
            	
            	courses.add(course);
            }

            return courses;
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

	@Override
	public GetTeacherCoursesResult execute(GetTeacherCoursesAction action, ExecutionContext context) throws ActionException {
		try {
			List<Course> courses = getSelectedCourses(action.getTeacherId());
			return new GetTeacherCoursesResult(courses);
		} catch (Exception e) {
			throw new ActionException(e);
		}
	}

	@Override
	public Class<GetTeacherCoursesAction> getActionType() {
		return GetTeacherCoursesAction.class;
	}

	@Override
	public void undo(GetTeacherCoursesAction action, GetTeacherCoursesResult result, ExecutionContext context)
			throws ActionException {
		
	}	  
}
