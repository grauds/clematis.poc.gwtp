package org.clematis.web.elearning.server.handlers.courses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.clematis.web.elearning.client.courses.GetCoursesAction;
import org.clematis.web.elearning.client.courses.GetCoursesResult;
import org.clematis.web.elearning.server.BasicHandler;
import org.clematis.web.elearning.shared.domain.Course;

import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

public class GetCoursesHandler extends BasicHandler implements ActionHandler<GetCoursesAction,GetCoursesResult>  {

	@Override
	public GetCoursesResult execute(GetCoursesAction action, ExecutionContext context) throws ActionException {
		try {
			List<Course> courses = getCourses();
			return new GetCoursesResult(courses);
		} catch (Exception e) {

			throw new ActionException(e);
		}
	}
	
    public List<Course> getCourses() throws Exception
    {
        PreparedStatement cstmt = null;
        Connection connection = null;
        ResultSet rs = null;
        try
        {
            connection = this.getConnection();
            cstmt = connection.prepareStatement("select * from COURSES;");

            rs = cstmt.executeQuery();

            List<Course> courses = new ArrayList<Course>();
            while ( rs.next() )
            {
            	Course course = new Course();

            	course.setName(rs.getString("NAME"));
            	course.setDescription(rs.getString("DESCRIPTION"));
            	course.setId(rs.getInt("ID"));
                
                courses.add( course );
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
	public void undo(GetCoursesAction action, GetCoursesResult result, ExecutionContext context) throws ActionException {
		
	}

	@Override
	public Class<GetCoursesAction> getActionType() {
		return GetCoursesAction.class;
	}	

}
