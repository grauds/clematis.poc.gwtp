package org.clematis.web.elearning.client.fields;

import org.clematis.web.elearning.client.login.AppMenuItem;


public class ListAddableField extends SuggestAddableField {
	private static final long serialVersionUID = -3217548581473063528L;

	/** Serialization stub */
	protected ListAddableField() {
	}

	public ListAddableField(String id, String name, int row, int col, String required, String iconTitle, AppMenuItem link) {
		super(id, name, row, col, required, iconTitle, link);
		this.type = Type.LISTADDABLE;
	}
}
