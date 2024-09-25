package org.clematis.web.elearning.client.login;

import java.io.Serializable;
import java.util.HashMap;
import com.google.gwt.user.client.rpc.IsSerializable;

public class AppMenuItem implements IsSerializable, Serializable {
	private static final long serialVersionUID = -7417148603528657747L;
	/**
	 * This is the name of the node's property that stores information about AppMenuItem.
	 */
	public static final String fieldAppMenuItem = "appmenuitem";

	/** 
	 * The name of the menu item
	 */
	public String name;
	/**
	 * The form id to be called. It is #-part of the URL
	 */
	public String form;
	/**
	 * Optional list of parameters to be passed to the form
	 * 
	 * @see SignedInGatekeeper
	 */
	public HashMap<String, String> params;

	public AppMenuItem() {
		name = null;
		form = null;
		params = null;
	}
	
	public AppMenuItem(String name, String form) {
		this.name = name;
		this.form = form;
	}	

	public AppMenuItem(String name, String form, String propertyName, String propertyValue) {
		this.name = name;
		this.form = form;
		if (propertyName != null) {
			params = new HashMap<String, String>(1);
			params.put(propertyName, propertyValue);
		}
	}

	public AppMenuItem putProperty(String name, String value) {
		if (name != null && value != null) {
			if (params == null) {
				params = new HashMap<String, String>(1);
			}
			params.put(name, value);
		}
		return this;
	}
}
