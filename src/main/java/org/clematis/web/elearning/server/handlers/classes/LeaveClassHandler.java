package org.clematis.web.elearning.server.handlers.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.clematis.web.elearning.client.students.classes.LeaveClassAction;
import org.clematis.web.elearning.client.students.classes.LeaveClassResult;
import org.clematis.web.elearning.server.BasicHandler;

import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

public class LeaveClassHandler  extends BasicHandler implements ActionHandler<LeaveClassAction, LeaveClassResult> {

	@Override
	public LeaveClassResult execute(LeaveClassAction action, ExecutionContext context)
			throws ActionException {
		try {
			leaveClass(action.getStudentId(), action.getClassId());
			return new LeaveClassResult();
		} catch (Exception e) {

			throw new ActionException(e);
		}
	}

	@Override
	public Class<LeaveClassAction> getActionType() {
		return LeaveClassAction.class;
	}

	@Override
	public void undo(LeaveClassAction action, LeaveClassResult result, ExecutionContext context) throws ActionException {
		
		
	}
	
    public void leaveClass(int studentId, int classId) throws ActionException
    {
        PreparedStatement cstmt = null;
        Connection connection = null;
        ResultSet rs = null;
        try
        {
            connection = this.getConnection();
            cstmt = connection.prepareStatement("delete from CLASS_MEMBERS where ID_CLASS=? and ID_USER=?;");
            cstmt.setInt(1, classId);
            cstmt.setInt(2, studentId);
            cstmt.executeUpdate();
        }
        catch (Exception e)
        {
           // logWithUserInfo(LeaveClassHandler.class.getName(), Level.SEVERE, Thread.currentThread().getName(), e);
        	throw new ActionException(e);
        }
        finally
        {
            close(cstmt, rs, connection);
        }
    }
}