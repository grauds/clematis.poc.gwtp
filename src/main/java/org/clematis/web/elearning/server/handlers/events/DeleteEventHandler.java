package org.clematis.web.elearning.server.handlers.events;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.clematis.web.elearning.client.events.DeleteEventAction;
import org.clematis.web.elearning.client.events.DeleteEventResult;
import org.clematis.web.elearning.server.BasicHandler;

import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

public class DeleteEventHandler extends BasicHandler implements ActionHandler<DeleteEventAction,DeleteEventResult> {

	@Override
	public DeleteEventResult execute(DeleteEventAction action, ExecutionContext context)
			throws ActionException {
		try {
			deleteEvent(action.getEventId());
			return new DeleteEventResult();
		} catch (Exception e) {

			throw new ActionException(e);
		}
	}

	private void deleteEvent(int eventId) throws Exception {
		PreparedStatement cstmt = null;
        Connection connection = null;
        ResultSet rs = null;
        try
        {
            connection = this.getConnection();
            cstmt = connection.prepareStatement("DELETE FROM EVENTS where ID=?;");
            cstmt.setInt(1, eventId);

            cstmt.executeUpdate();

        }
        catch (Exception e)
        {
           // logWithUserInfo(GetConnectionStateHandler.class.getName(), Level.SEVERE, Thread.currentThread().getName(), e);
            throw new Exception(e);
        }
        finally
        {
            close(cstmt, rs, connection);
        }		
	}

	@Override
	public Class<DeleteEventAction> getActionType() {
		return DeleteEventAction.class;
	}

	@Override
	public void undo(DeleteEventAction arg0, DeleteEventResult arg1,
			ExecutionContext arg2) throws ActionException {
		
	}

}

