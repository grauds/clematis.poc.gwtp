package org.clematis.web.elearning.server.handlers.teachers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.clematis.web.elearning.client.teachers.classes.GetTeacherClassesAction;
import org.clematis.web.elearning.client.teachers.classes.GetTeacherClassesResult;
import org.clematis.web.elearning.server.BasicHandler;
import org.clematis.web.elearning.shared.domain.EmailAddress;
import org.clematis.web.elearning.shared.domain.StudentsClass;
import org.clematis.web.elearning.shared.domain.User;

import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

public class GetTeacherClassesHandler extends BasicHandler implements ActionHandler<GetTeacherClassesAction, GetTeacherClassesResult>  {
	

	@Override
	public GetTeacherClassesResult execute(GetTeacherClassesAction action, ExecutionContext context) throws ActionException {
		try {
			List<StudentsClass> classes = getTeacherClasses(action.getTeacherId());
			return new GetTeacherClassesResult(classes);
		} catch (Exception e) {
			throw new ActionException(e);
		}
	}

	protected List<StudentsClass> getTeacherClasses(Integer teacherId) throws Exception {
        PreparedStatement cstmt = null;
        Connection connection = null;
        ResultSet rs = null;
        try
        {
            connection = this.getConnection();
            
            cstmt = connection.prepareStatement("select * from CLASSES where ID_TEACHER=?;");
            cstmt.setInt(1, teacherId);
            rs = cstmt.executeQuery();
            
            List<StudentsClass> classes = new ArrayList<StudentsClass>();
            
            while(rs.next()) {
            	StudentsClass studentClass = new StudentsClass();
            	
            	studentClass.setName(rs.getString("NAME"));
            	studentClass.setUID(rs.getString("UID"));
            	studentClass.setId(rs.getInt("ID"));
            	
            	studentClass.setUsers(getClassUsers(studentClass.getId()));
            	
            	classes.add(studentClass);
            	
            }

            return classes;
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
	
	protected List<User> getClassUsers(int classId) throws Exception {
		PreparedStatement cstmt = null;
        Connection connection = null;
        ResultSet rs = null;
        try
        {
            connection = this.getConnection();
            
	    	cstmt = connection.prepareStatement("select * from USERS where ID in (select ID_USER from CLASS_MEMBERS where ID_CLASS=?);");
	        cstmt.setInt(1, classId);
	        rs = cstmt.executeQuery();
	        
	        List<User> users = new ArrayList<User>();
	        
	        while(rs.next()) {
	        	User user = new User();
	        	
	        	 user.setName(rs.getString("NAME"));
	             user.setSecondName(rs.getString("SECOND_NAME"));
	             user.setEmail(new EmailAddress(rs.getString("EMAIL")));
	             user.setId(rs.getInt("ID"));
	        	
	        	users.add(user);
	        }
	        return users;
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
	public Class<GetTeacherClassesAction> getActionType() {
		return GetTeacherClassesAction.class;
	}

	@Override
	public void undo(GetTeacherClassesAction action, GetTeacherClassesResult result, ExecutionContext context)
			throws ActionException {
		
	}	  
}
