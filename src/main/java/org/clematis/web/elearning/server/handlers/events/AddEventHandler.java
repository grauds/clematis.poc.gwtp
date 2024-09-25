package org.clematis.web.elearning.server.handlers.events;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.clematis.web.elearning.client.events.AddEventAction;
import org.clematis.web.elearning.client.events.AddEventResult;
import org.clematis.web.elearning.server.BasicHandler;
import org.clematis.web.elearning.shared.domain.Event;
import org.clematis.web.elearning.shared.domain.StudentsClass;

import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

public class AddEventHandler extends BasicHandler implements ActionHandler<AddEventAction,AddEventResult> {

	@Override
	public AddEventResult execute(AddEventAction action, ExecutionContext context)
			throws ActionException {
		try {
			return new AddEventResult(addEvent(action.getName(), action.getDescription(), action.getTeacherId(), action.getClassId(), action.getTimeInterval(), action.getDay()));
		} catch (Exception e) {

			throw new ActionException(e);
		}
	}

	private Event addEvent(String name, String description, int teacherId, int classId, int timeInterval, int day) throws Exception {
		PreparedStatement cstmt = null;
        Connection connection = null;
        ResultSet rs = null;
        try
        {
            connection = this.getConnection();
            cstmt = connection.prepareStatement("INSERT INTO EVENTS (NAME,DESCRIPTION,ID_TEACHER,ID_CLASS,TIME,DAY) VALUES (?,?,?,?,?,?);");

            cstmt.setString(1, name);
            cstmt.setString(2, description);
            cstmt.setInt(3, teacherId);
            cstmt.setInt(4, classId);
            cstmt.setInt(5, timeInterval);
            cstmt.setInt(6, day);

            cstmt.executeUpdate();
            
            cstmt = connection.prepareStatement("select ID from EVENTS where TIME=? AND DAY=? AND ID_CLASS=?;");
            cstmt.setInt(1, timeInterval);
            cstmt.setInt(2, day);
            cstmt.setInt(3, classId);            

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
	public Class<AddEventAction> getActionType() {
		return AddEventAction.class;
	}

	@Override
	public void undo(AddEventAction arg0, AddEventResult arg1,
			ExecutionContext arg2) throws ActionException {
		
	}

}
