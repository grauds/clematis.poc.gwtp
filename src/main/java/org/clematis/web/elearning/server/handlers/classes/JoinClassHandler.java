package org.clematis.web.elearning.server.handlers.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.clematis.web.elearning.client.students.classes.JoinClassAction;
import org.clematis.web.elearning.client.students.classes.JoinClassResult;
import org.clematis.web.elearning.server.BasicHandler;

import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

public class JoinClassHandler extends BasicHandler implements ActionHandler<JoinClassAction, JoinClassResult> {

	@Override
	public JoinClassResult execute(JoinClassAction action, ExecutionContext context)
			throws ActionException {
		try {
			joinClass(action.getStudentId(), action.getClassId());
			return new JoinClassResult();
		} catch (Exception e) {

			throw new ActionException(e);
		}
	}

	@Override
	public Class<JoinClassAction> getActionType() {
		return JoinClassAction.class;
	}

	@Override
	public void undo(JoinClassAction action, JoinClassResult result, ExecutionContext context) throws ActionException {
		
	}
	
    public void joinClass(int studentId, int classId) throws ActionException
    {
        PreparedStatement cstmt = null;
        Connection connection = null;
        ResultSet rs = null;
        try
        {
            connection = this.getConnection();
            cstmt = connection.prepareStatement("insert into CLASS_MEMBERS(ID_CLASS, ID_USER) values(?,?);");
            cstmt.setInt(1, classId);
            cstmt.setInt(2, studentId);
            cstmt.executeUpdate();
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
}
