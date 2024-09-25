package org.clematis.web.elearning.server.handlers.courses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.clematis.web.elearning.client.courses.GetCoursesGroupAction;
import org.clematis.web.elearning.client.courses.GetCoursesGroupResult;
import org.clematis.web.elearning.server.BasicHandler;
import org.clematis.web.elearning.shared.domain.Course;
import org.clematis.web.elearning.shared.domain.CoursesGroup;

import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

public class GetCoursesGroupsHandler extends BasicHandler implements ActionHandler<GetCoursesGroupAction, GetCoursesGroupResult>  {

	@Override
	public GetCoursesGroupResult execute(GetCoursesGroupAction action, ExecutionContext context)
			throws ActionException {
		try {
			List<CoursesGroup> coursesGroups = getCoursesGroups();
			return new GetCoursesGroupResult(coursesGroups);
		} catch (Exception e) {

			throw new ActionException(e);
		}
	}

	@Override
	public Class<GetCoursesGroupAction> getActionType() {
		return GetCoursesGroupAction.class;
	}

	@Override
	public void undo(GetCoursesGroupAction action, GetCoursesGroupResult result, ExecutionContext context) throws ActionException {
		
	}
	
    public List<Course> getCourses(int groupId) throws Exception
    {
        PreparedStatement cstmt = null;
        Connection connection = null;
        ResultSet rs = null;
        try
        {
            connection = this.getConnection();
            cstmt = connection.prepareStatement("select * from COURSES where ID_GROUP=?;");
            cstmt.setInt(1, groupId);
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
	
    public List<CoursesGroup> getCoursesGroups() throws Exception
    {
        PreparedStatement cstmt = null;
        Connection connection = null;
        ResultSet rs = null;
        try
        {
            connection = this.getConnection();
            cstmt = connection.prepareStatement("select * from COURSES_GROUPS;");

            rs = cstmt.executeQuery();

            List<CoursesGroup> coursesGroups = new ArrayList<CoursesGroup>();
            while ( rs.next() )
            {
            	CoursesGroup coursesGroup = new CoursesGroup();

            	coursesGroup.setName(rs.getString("NAME"));
            	coursesGroup.setDescription(rs.getString("DESCRIPTION"));
            	coursesGroup.setId(rs.getInt("ID"));
            	
            	coursesGroup.setGroups(getCourses(coursesGroup.getId()));
            	
            	coursesGroups.add(coursesGroup);
            }

            return coursesGroups;

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
