package org.clematis.web.elearning.client.fields;

import org.clematis.web.elearning.client.login.AppMenuItem;


public class SuggestAddableField extends Field {
	private static final long serialVersionUID = -8929868511371370344L;

	/**
	 * Icon's title to be shown when mouse cursor is over the icon.
	 */
	public String iconTitle;
	/**
	 * The link to the form to create new element.
	 */
	public AppMenuItem link;

	/** Serialization stub */
	protected SuggestAddableField() {
	}

	public SuggestAddableField(String id, String name, int row, int col, String required, String iconTitle, AppMenuItem link) {
		this.type = Type.SUGGESTADDABLE;
		this.id = id;
		this.captionForm = name;
		this.captionGrid = name;
		this.row = row;
		this.column = col;
		this.required = required;
		this.iconTitle = iconTitle;
		this.link = link;
	}

}
