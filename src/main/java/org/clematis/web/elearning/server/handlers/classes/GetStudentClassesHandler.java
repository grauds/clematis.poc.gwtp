package org.clematis.web.elearning.server.handlers.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.clematis.web.elearning.client.students.classes.GetStudentClassesAction;
import org.clematis.web.elearning.client.students.classes.GetStudentClassesResult;
import org.clematis.web.elearning.server.BasicHandler;
import org.clematis.web.elearning.shared.domain.StudentsClass;

import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

public class GetStudentClassesHandler extends BasicHandler  implements ActionHandler<GetStudentClassesAction, GetStudentClassesResult> {

	@Override
	public GetStudentClassesResult execute(GetStudentClassesAction action, ExecutionContext context) throws ActionException {
		try {
			if (action.getTeacherId()!=null) {
				return new GetStudentClassesResult(getStudentClasses(action.getStudentId(), action.getTeacherId()));
			}
			else {
				return new GetStudentClassesResult(getStudentClasses(action.getStudentId()));
			}
		} catch (Exception e) {

			throw new ActionException(e);
		}
	}

	private List<StudentsClass> getStudentClasses(int studentId) throws Exception {
		PreparedStatement cstmt = null;
        Connection connection = null;
        ResultSet rs = null;
        try
        {
            connection = this.getConnection();
            
            cstmt = connection.prepareStatement("select * from CLASSES where ID in (select ID_CLASS from CLASS_MEMBERS where ID_USER=?);");
            cstmt.setInt(1, studentId);
            rs = cstmt.executeQuery();
            
            List<StudentsClass> classes = new ArrayList<StudentsClass>();
            
            while(rs.next()) {
            	StudentsClass studentClass = new StudentsClass();
            	
            	studentClass.setName(rs.getString("NAME"));
            	studentClass.setUID(rs.getString("UID"));
            	studentClass.setId(rs.getInt("ID"));
            	
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

	private List<StudentsClass> getStudentClasses(int studentId, int teacherId) throws Exception {
		PreparedStatement cstmt = null;
        Connection connection = null;
        ResultSet rs = null;
        try
        {
            connection = this.getConnection();
            
            cstmt = connection.prepareStatement("select * from CLASSES where ID in (select ID_CLASS from CLASS_MEMBERS where ID_CLASS IN (select ID from CLASSES where ID_TEACHER=?) AND ID_USER=?);");
            cstmt.setInt(1, teacherId);
            cstmt.setInt(2, studentId);
            rs = cstmt.executeQuery();
            
            List<StudentsClass> classes = new ArrayList<StudentsClass>();
            
            while(rs.next()) {
            	StudentsClass studentClass = new StudentsClass();
            	
            	studentClass.setName(rs.getString("NAME"));
            	studentClass.setUID(rs.getString("UID"));
            	studentClass.setId(rs.getInt("ID"));
            	
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

	@Override
	public Class<GetStudentClassesAction> getActionType() {
		return GetStudentClassesAction.class;
	}

	@Override
	public void undo(GetStudentClassesAction action, GetStudentClassesResult result, ExecutionContext context)
			throws ActionException {
		
	}

}
