package org.clematis.web.elearning.client.place;

import com.gwtplatform.mvp.client.proxy.PlaceManagerImpl;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.TokenFormatter;
import com.google.inject.Inject;
import com.google.gwt.event.shared.EventBus;

public class ApplicationPlaceManager extends PlaceManagerImpl {
	
	private final PlaceRequest defaultPlaceRequest;
	private final PlaceRequest errorPlaceRequest;

	@Inject
	public ApplicationPlaceManager(EventBus eventBus, TokenFormatter tokenFormatter, @DefaultPlace String defaultNameToken, @ErrorPlace String errorNameToken) {
		super(eventBus, tokenFormatter);
		this.defaultPlaceRequest = new PlaceRequest(defaultNameToken);
		this.errorPlaceRequest = new PlaceRequest(errorNameToken);
	}

	@Override
	public void revealDefaultPlace() {
		revealPlace(defaultPlaceRequest);
	}

	@Override
	public void revealErrorPlace(String invalidHistoryToken) {
		revealPlace(errorPlaceRequest);
	}
}
