package org.clematis.web.elearning.server.handlers.budget;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.clematis.web.elearning.client.students.budget.GetOperationsAllAction;
import org.clematis.web.elearning.client.students.budget.GetOperationsAllResult;
import org.clematis.web.elearning.server.BasicHandler;
import org.clematis.web.elearning.shared.domain.Operation;

import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

public class GetOperationsAllHandler extends BasicHandler implements
		ActionHandler<GetOperationsAllAction, GetOperationsAllResult> {

	@Override
	public GetOperationsAllResult execute(GetOperationsAllAction action,
			ExecutionContext context) throws ActionException {
		try {
			return new GetOperationsAllResult(getOperationsAll());
		} catch (Exception e) {

			throw new ActionException(e);
		}
	}

	@Override
	public Class<GetOperationsAllAction> getActionType() {
		return GetOperationsAllAction.class;
	}

	@Override
	public void undo(GetOperationsAllAction arg0, GetOperationsAllResult arg1,
			ExecutionContext arg2) throws ActionException {
		
	}
	
	public List<Operation> getOperationsAll() throws ActionException
    {
        PreparedStatement cstmt = null;
        Connection connection = null;
        ResultSet rs = null;
        try
        {
            connection = this.getConnection();
            cstmt = connection.prepareStatement("select * from OPERATIONS;");
            rs = cstmt.executeQuery();
            
            List<Operation> operations = new ArrayList<Operation>();
            
            while(rs.next()) {
            	Operation operation = new Operation();
            	
            	operation.setDate(rs.getDate("DATE"));
            	operation.setSum(rs.getFloat("OUTCOME"));
            	operation.setId(rs.getInt("ID"));
            	
            	operations.add(operation);
            }

            return operations;            
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
