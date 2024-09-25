package org.clematis.web.elearning.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.Scheduler;

import org.clematis.web.elearning.client.gin.ApplicationGinjector;
import org.clematis.web.elearning.client.login.actions.GetConnectionState.ConnectionState;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.ui.RootPanel;
import com.gwtplatform.mvp.client.DelayedBindRegistry;


public class ELP implements EntryPoint {

	private final ApplicationGinjector ginjector = GWT.create(ApplicationGinjector.class);
	private static ConnectionState connectionState = new ConnectionState();

	@Override
	public void onModuleLoad() {
		// Defer all application initialization code to onModuleLoadMain() so that the
		// UncaughtExceptionHandler can catch any unexpected exceptions.
//		Log.setUncaughtExceptionHandler();

		Scheduler.get().scheduleDeferred(new ScheduledCommand() {

			@Override
			public void execute() {
				onModuleLoadMain();
			}
		});		

	}

	private void onModuleLoadMain() {
		try {
			DelayedBindRegistry.bind(ginjector);

			ginjector.getPlaceManager().revealCurrentPlace();
			
			// hide loading gif
			RootPanel.get("loading").setVisible(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @return the connectionState
	 */
	public static ConnectionState getConnectionState() {
		return connectionState;
	}
	
	public static int getCurrentUserId() {
		if ( ELP.getConnectionState() != null && ELP.getConnectionState().user!= null && ELP.getConnectionState().user.getUser() != null ) {
			return ELP.getConnectionState().user.getUser().getId();			
		} else {
			return -1;
		}
		
	}

	/**
	 * @param connectionState
	 *            the connectionState to set
	 */
	public static void setConnectionState(ConnectionState connectionState) {
		ELP.connectionState = connectionState;
	}
}
