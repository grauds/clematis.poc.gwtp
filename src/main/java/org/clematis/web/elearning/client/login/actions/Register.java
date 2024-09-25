package org.clematis.web.elearning.client.login.actions;

import org.clematis.web.elearning.shared.domain.User;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;
import com.gwtplatform.dispatch.shared.Action;

@GenDispatch(serviceName = Action.DEFAULT_SERVICE_NAME + "Register")
public class Register {
	@In(1)
	String email;
	@In(2)
	String password;
	@In(3)
	String username;
	@In(4)
	String secondName;
	@In(5)
	String phone;	
	@Out(1)
	User user;
	
}
