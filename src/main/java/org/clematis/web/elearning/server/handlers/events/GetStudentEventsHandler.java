package org.clematis.web.elearning.server.handlers.events;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.clematis.web.elearning.client.students.schedule.GetStudentEventsAction;
import org.clematis.web.elearning.client.students.schedule.GetStudentEventsResult;
import org.clematis.web.elearning.server.BasicHandler;
import org.clematis.web.elearning.shared.domain.Event;
import org.clematis.web.elearning.shared.domain.StudentsClass;

import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

public class GetStudentEventsHandler extends BasicHandler implements ActionHandler<GetStudentEventsAction,GetStudentEventsResult> {

	@Override
	public GetStudentEventsResult execute(GetStudentEventsAction action, ExecutionContext context)
			throws ActionException {
		try {
			List<Event> events = getStudentEvents(action.getStudentId());
			return new GetStudentEventsResult(events);
		} catch (Exception e) {

			throw new ActionException(e);
		}
	}

	private List<Event> getStudentEvents(int studentId) throws Exception {
		PreparedStatement cstmt = null;
        Connection connection = null;
        ResultSet rs = null;
        try
        {
            connection = this.getConnection();
            cstmt = connection.prepareStatement("select * from EVENTS where ID_CLASS in (select ID_CLASS from CLASS_MEMBERS where ID_USER=?);");
            cstmt.setInt(1, studentId);
            rs = cstmt.executeQuery();

            List<Event> events = new ArrayList<Event>();
            while ( rs.next() )
            {
            	Event event = new Event();

            	event.setName( rs.getString("NAME"));
            	event.setDescription( rs.getString("DESCRIPTION"));
            	event.setTeacherId( rs.getInt("ID_TEACHER"));
            	event.setStudentsClass(getStudentClass(rs.getInt("ID_CLASS")));
            	event.setTimeInterval( rs.getInt("TIME"));
            	event.setDay( rs.getInt("DAY"));
            	event.setId( rs.getInt("ID"));
                
            	events.add( event );
            }

            return events;

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
	public Class<GetStudentEventsAction> getActionType() {
		return GetStudentEventsAction.class;
	}

	@Override
	public void undo(GetStudentEventsAction arg0, GetStudentEventsResult arg1,
			ExecutionContext arg2) throws ActionException {
		
	}

}
