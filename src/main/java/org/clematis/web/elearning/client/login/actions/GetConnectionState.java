package org.clematis.web.elearning.client.login.actions;



import org.clematis.web.elearning.shared.domain.AuthObject;
import org.clematis.web.elearning.shared.domain.UserInRole;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;
import com.gwtplatform.dispatch.shared.Action;


@GenDispatch(serviceName = Action.DEFAULT_SERVICE_NAME + "ConnectionState")
public class GetConnectionState {

	public static class ConnectionState implements IsSerializable {
		public UserInRole user;
		public boolean isLoggedIn;
		public boolean isRemembered;
		public boolean isAdmin() {
			return user != null && user.getUserRole() != null &&user.getUserRole().getId() == 1;
		}
		public boolean isTeacher() {
			return user != null && user.getUserRole() != null &&user.getUserRole().getId() == 2;
		}
		public boolean isStudent() {
			return user != null && user.getUserRole() != null &&user.getUserRole().getId() == 3;
		}
	}

	@In(1)
	String urlToContinue;
	@In(2)
	AuthObject authObject;
	@In(3)
	String nextPage;
	@Out(1)
	ConnectionState connectionState;
	@Out(2)
	String savedNextPage;
}
