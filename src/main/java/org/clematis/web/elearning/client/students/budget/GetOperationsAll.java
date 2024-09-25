package org.clematis.web.elearning.client.students.budget;

import java.util.List;

import org.clematis.web.elearning.shared.domain.Operation;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.Out;
import com.gwtplatform.dispatch.shared.Action;

@GenDispatch(serviceName = Action.DEFAULT_SERVICE_NAME + "GetOperationsAll")
public class GetOperationsAll {
	@Out(1)
    List<Operation> operations;
}
