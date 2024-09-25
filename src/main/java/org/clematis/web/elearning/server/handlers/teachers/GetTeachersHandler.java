package org.clematis.web.elearning.server.handlers.teachers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.clematis.web.elearning.client.teachers.GetTeachersAction;
import org.clematis.web.elearning.client.teachers.GetTeachersResult;
import org.clematis.web.elearning.server.BasicHandler;
import org.clematis.web.elearning.shared.domain.Course;
import org.clematis.web.elearning.shared.domain.CoursesGroup;
import org.clematis.web.elearning.shared.domain.EmailAddress;
import org.clematis.web.elearning.shared.domain.Teacher;

import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

public class GetTeachersHandler extends BasicHandler implements ActionHandler<GetTeachersAction, GetTeachersResult> {

	@Override
	public GetTeachersResult execute(GetTeachersAction action, ExecutionContext context) throws ActionException {
		try {
			List<Teacher> teachers = getTeachersAll(action.getCourses());
			return new GetTeachersResult(teachers);
		} catch (Exception e) {
			throw new ActionException(e);
		}
	}

	@Override
	public Class<GetTeachersAction> getActionType() {
		return GetTeachersAction.class;
	}

	@Override
	public void undo(GetTeachersAction action, GetTeachersResult result, ExecutionContext context) throws ActionException {
		
	}

	protected String createClause(CoursesGroup coursesGroup) {
		if ( coursesGroup != null ) {
			
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < coursesGroup.getCourses().size(); i++) {
				sb.append(" AND ID_COURSE=?");
			}
		}
		return null;
	}
	
    public List<Teacher> getTeachersAll(List<Course> courses) throws Exception
    {
        PreparedStatement cstmt = null;
        Connection connection = null;
        ResultSet rs = null;
        try
        {
            connection = this.getConnection();
            
            String sql = "select DISTINCT USERS.* from USER_IN_ROLE,USERS,TEACHER_COURSES where USER_IN_ROLE.ID_USER=USERS.ID AND USER_IN_ROLE.ID_ROLE=2 AND TEACHER_COURSES.ID_TEACHER=USERS.ID";
            /**
             * Add courses
             */

            List<Teacher> teachers = new ArrayList<Teacher>();
            
            if ( courses != null && courses.size() > 0 ) {
            	sql = sql + " AND (";
            	int counter = 0;
            	for (@SuppressWarnings("unused") Course course:courses) {
            		sql = sql + "TEACHER_COURSES.ID_COURSE=?";
            		counter++;
            		if(counter < courses.size()) {
            			sql = sql + " OR ";
            		}
            	}
            	sql = sql + ")";
            }
            else return teachers;
            	
            
            sql = sql + ";";
;            
            cstmt = connection.prepareStatement(sql);
            for (int i=0; i<courses.size();i++) {
            	cstmt.setInt(i+1, courses.get(i).getId());
            }
            rs = cstmt.executeQuery();

            while ( rs.next() )
            {
            	Teacher user = new Teacher();

                user.setName(rs.getString("NAME"));
                user.setSecondName(rs.getString("SECOND_NAME"));
                user.setEmail(new EmailAddress(rs.getString("EMAIL")));
                user.setId(rs.getInt("ID"));

                teachers.add(user);
            }

            return teachers;

        }
        catch (Exception e)
        {
            //logWithUserInfo(StudentsServiceImpl.class.getName(), Level.SEVERE, Thread.currentThread().getName(), e);
        	throw new Exception(e);
        }
        finally
        {
            close(cstmt, rs, connection);
        }
    }	
	
}
