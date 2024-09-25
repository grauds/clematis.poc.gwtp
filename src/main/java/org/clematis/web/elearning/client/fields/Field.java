package org.clematis.web.elearning.client.fields;

import java.io.Serializable;

public class Field implements Serializable {
	private static final long serialVersionUID = -671766218436717476L;
	public static final String tagFields = "fields";
	public static enum Type {
		LONG,
		SUBMIT,
		STRING,
		DESCRIPTION,
		DESCRIPTIONINPLACE,
		DATE,		// the value passed as a formatted date 2011-10-07
		DATETIME,	// the value passed as long
		LIST,
		LISTADDABLE,
		SUGGEST,
		SUGGESTADDABLE,	// the field of this type must have SuggestAddableField class
		ATTACHMENT,
		BOOL,
		STATIC,
		BIGDECIMAL,
		MENULIST,	// the field of this type must have MenuListField class
		MENUBLOCK,	// the field of this type must have MenuBlockField class
		SEPARATOR,	// the field of this type must have SeparatorField class
		LINK,		// the field of this type must have LinkField class
		TRIPPLE, 	// Allows True, Fasle, null values.
	}
	public Type type;
	/**
	 * Field caption in Form view.
	 */
	public String captionForm;
	/**
	 * Field caption in Grid view.
	 */
	public String captionGrid;
	/**
	 * Row to place the control in Form view.
	 */
	public int row;
	/**
	 * Column to place the control in Grid view.
	 */
	public int column;
	public int colspan;
	public int rowspan;
	public String id;
	/**
	 * If the field is required then required member
	 * contains error message to show when the field1
	 * is not filled.
	 */
	public String required;
	/**
	 * Flag if not to cleanup the field in the form
	 * after a new element was saved. Default is false:
	 * the field is cleaned up.
	 */
	public boolean dontCleanupAfterNewSaved;
	public boolean readOnly;
	public boolean noFilter;


	public static Field getBigDecimalField(String id, String name, int row, int col, String required) {
		Field field = new Field();
		field.type = Type.BIGDECIMAL;
		field.id = id;
		field.captionForm = name;
		field.captionGrid = name;
		field.row = row;
		field.column = col;
		field.required = required;
		return field;
	}

	public static Field getField(String id, Type type, String name, int row, int col) {
		Field field = new Field();
		field.type = type;
		field.id = id;
		field.captionForm = name;
		field.captionGrid = name;
		field.row = row;
		field.column = col;
		return field;
	}

	public Field setRowSpan(int rowspan) {
		this.rowspan = rowspan;
		return this;
	}

	public Field setRequired(String required) {
		this.required = required;
		return this;
	}

	public Field setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
		return this;
	}

	public boolean isCanFixValue() {
		return dontCleanupAfterNewSaved;
	}

	public Field setCanFixValue(boolean canFixValue) {
		this.dontCleanupAfterNewSaved = canFixValue;
		return this;
	}
}
