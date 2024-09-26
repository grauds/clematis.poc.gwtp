package org.clematis.web.elearning.server.handlers.events;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.clematis.web.elearning.client.events.EditEventAction;
import org.clematis.web.elearning.client.events.EditEventResult;
import org.clematis.web.elearning.server.BasicHandler;
import org.clematis.web.elearning.shared.domain.Event;
import org.clematis.web.elearning.shared.domain.StudentsClass;

import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

public class EditEventHandler extends BasicHandler implements ActionHandler<EditEventAction,EditEventResult> {

	@Override
	public EditEventResult execute(EditEventAction action, ExecutionContext context)
			throws ActionException {
		try {
			return new EditEventResult(editEvent(action.getEventId(), action.getName(), action.getDescription(), action.getTeacherId(),
					action.getClassId(), action.getTimeInterval(), action.getDay()));
		} catch (Exception e) {

			throw new ActionException(e);
		}
	}

	private Event editEvent(int eventId, String name, String description, int teacherId, int classId, int timeInterval, int day) throws Exception {
		PreparedStatement cstmt = null;
        Connection connection = null;
        ResultSet rs = null;
        try
        {
            connection = this.getConnection();
            cstmt = connection.prepareStatement("UPDATE EVENTS SET NAME=?,DESCRIPTION=?,ID_TEACHER=?,ID_CLASS=?,TIME=?,DAY=? where ID=?;");

            cstmt.setString(1, name);
            cstmt.setString(2, description);
            cstmt.setInt(3, teacherId);
            cstmt.setInt(4, classId);
            cstmt.setInt(5, timeInterval);
            cstmt.setInt(6, day);
            cstmt.setInt(7, eventId);

            cstmt.executeUpdate();
            
            cstmt = connection.prepareStatement("select ID from EVENTS where ID=?;");
            cstmt.setInt(1, eventId);

            rs = cstmt.executeQuery();
            if ( rs.next() )
            {
            	Event event = new Event();

            	event.setName(name);
            	event.setDescription(description);
            	event.setTeacherId(teacherId);
            	event.setStudentsClass(getStudentClass(classId));
            	event.setTimeInterval(timeInterval);
            	event.setDay(day);
            	event.setId( rs.getInt("ID"));

                return event;
            }
            return null;

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
	
	public StudentsClass getStudentClass(int classId) throws Exception {
		PreparedStatement cstmt = null;
        Connection connection = null;
        ResultSet rs = null;
        try
        {
            connection = this.getConnection();
            
            cstmt = connection.prepareStatement("select * from CLASSES where ID=?;");
            cstmt.setInt(1, classId);
            rs = cstmt.executeQuery();
            
            if(rs.next()) {
            	StudentsClass studentClass = new StudentsClass();
            	
            	studentClass.setName(rs.getString("NAME"));
            	studentClass.setUID(rs.getString("UID"));
            	studentClass.setId(rs.getInt("ID"));
            	
            	return studentClass;
            }
            return null;
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
	public Class<EditEventAction> getActionType() {
		return EditEventAction.class;
	}

	@Override
	public void undo(EditEventAction arg0, EditEventResult arg1,
			ExecutionContext arg2) throws ActionException {

		
	}

}
