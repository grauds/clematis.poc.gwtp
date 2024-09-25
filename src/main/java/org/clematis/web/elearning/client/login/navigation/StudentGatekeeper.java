package org.clematis.web.elearning.client.login.navigation;

import org.clematis.web.elearning.client.ELP;
import org.clematis.web.elearning.client.place.NameTokens;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.gwtplatform.mvp.client.proxy.Gatekeeper;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

@Singleton
public class StudentGatekeeper implements Gatekeeper {

	protected boolean isLoggedIn() {
		return ELP.getConnectionState().isLoggedIn && ELP.getConnectionState().isStudent();
	}
	
	private final PlaceManager placeManager;
	public final static String savedUrlToken = "x";

	@Inject
	public StudentGatekeeper(PlaceManager placeManager) {
		this.placeManager = placeManager;
	}
	
	public PlaceManager getPlaceManager() {
		return placeManager;
	}

	@Override
	public boolean canReveal() {
		if (isLoggedIn()) {
			String path = placeManager.getCurrentPlaceRequest().getParameter(savedUrlToken, "");
			if (path != null && path.trim().isEmpty() == false) {
				// Restore request after log on process.
				placeManager.revealPlace(getDecodedUrl(placeManager.getCurrentPlaceRequest()));
			}
			return true;
		} else {
			placeManager.revealPlace(getEncodedLoginUrl(placeManager.getCurrentPlaceRequest()));
			return true;
		}
	}


	public static PlaceRequest getEncodedLoginUrl(PlaceRequest requested) {
		String path = requested.getNameToken();
		if (path == null) {
			path = "";
		}
		for (String param : requested.getParameterNames()) {
			path += ";" + param + "=" + requested.getParameter(param, "");
		}
		PlaceRequest pr = new PlaceRequest(NameTokens.SIGNIN);
		return pr.with(savedUrlToken, path);
	}

	public static PlaceRequest getRestoredOrDecodedUrl(String savedUrl, PlaceRequest requested) {
		// Give priority to request
		String path = requested.getParameter(SingnedInGatekeeper.savedUrlToken, "");
		if (path == null || path.trim().isEmpty() == true) {
			path = savedUrl;
		}
		return getUrlFromParamsString(path);
	}

	public static PlaceRequest getDecodedUrl(PlaceRequest requested) {
		return getUrlFromParamsString(requested.getParameter(SingnedInGatekeeper.savedUrlToken, ""));
	}

	private static PlaceRequest getUrlFromParamsString(String path) {
		PlaceRequest result = null;
		
		if (path!= null && path.trim().isEmpty() == false) {
			String[] parts = path.split(";");
			if (parts.length > 0) {
				// First part is token
				result = new PlaceRequest(parts[0]);
				for (int i = 1; i < parts.length; i++) {
					String[] params = parts[i].split("=");
					if (params.length == 2) {
						result = result.with(params[0], params[1]);
					}
				}
			}
		}
		return result == null ? new PlaceRequest(NameTokens.CABINET) : result; 
	}	

}
