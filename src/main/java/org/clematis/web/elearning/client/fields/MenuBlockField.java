package org.clematis.web.elearning.client.fields;

import java.util.ArrayList;
import java.util.List;

import org.clematis.web.elearning.client.login.AppMenuItem;


public class MenuBlockField extends Field {
	private static final long serialVersionUID = 4040428266833774246L;

	private String iconUrl;
	private List<AppMenuItem> items;

	public MenuBlockField() {
		type = Type.MENUBLOCK;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public List<AppMenuItem> getItems() {
		if ( items == null ) {
			items = new ArrayList<AppMenuItem>();
		}
		return items;
	}

	public void setItems(List<AppMenuItem> items) {
		this.items = items;
	}
}
