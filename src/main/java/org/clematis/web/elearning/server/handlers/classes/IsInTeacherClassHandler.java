package org.clematis.web.elearning.server.handlers.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


import org.clematis.web.elearning.client.students.classes.IsInTeacherClassAction;
import org.clematis.web.elearning.client.students.classes.IsInTeacherClassResult;
import org.clematis.web.elearning.server.BasicHandler;

import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

public class IsInTeacherClassHandler extends BasicHandler implements ActionHandler<IsInTeacherClassAction, IsInTeacherClassResult> {

	@Override
	public IsInTeacherClassResult execute(IsInTeacherClassAction action, ExecutionContext context)
			throws ActionException {
		try {
			return new IsInTeacherClassResult(isInClass(action.getStudentId(), action.getTeacherId()));
		} catch (Exception e) {

			throw new ActionException(e);
		}
	}

	private boolean isInClass(int studentId, int teacherId) throws ActionException
    {
        PreparedStatement cstmt = null;
        Connection connection = null;
        ResultSet rs = null;
        try
        {
            connection = this.getConnection();
            cstmt = connection.prepareStatement("select ID_USER from CLASS_MEMBERS where ID_CLASS IN (select ID from CLASSES where ID_TEACHER=?);");
            cstmt.setInt(1, teacherId);
            rs = cstmt.executeQuery();
            return rs.next();
        }
        catch (Exception e)
        {
            //logWithUserInfo(StudentsServiceImpl.class.getName(), Level.SEVERE, Thread.currentThread().getName(), e);
            throw new ActionException(e);
        }
        finally
        {
            close(cstmt, rs, connection);
        }
    }
	

	@Override
	public Class<IsInTeacherClassAction> getActionType() {
		return IsInTeacherClassAction.class;
	}

	@Override
	public void undo(IsInTeacherClassAction action, IsInTeacherClassResult result, ExecutionContext context) throws ActionException {
		
	}

}
