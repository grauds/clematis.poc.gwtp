package org.clematis.web.elearning.client.fields;

import java.util.ArrayList;
import java.util.List;

import org.clematis.web.elearning.client.login.AppMenuItem;

public class MenuListField extends Field {
	private static final long serialVersionUID = -7850861316251050353L;

	private List<AppMenuItem> items;

	public MenuListField() {
		type = Type.MENULIST;
	}

	public List<AppMenuItem> getItems() {
		return items;
	}

	public void addItem(AppMenuItem item) {
		if (item != null) {
			if (items == null) {
				items = new ArrayList<AppMenuItem>();
			}
			items.add(item);
		}
	}

}
