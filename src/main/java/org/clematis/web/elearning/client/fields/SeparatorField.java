package org.clematis.web.elearning.client.fields;

/**
 * This field is shown as empty space of specified sizes.
 * @author vlakadm
 *
 */
public class SeparatorField extends Field {
	private static final long serialVersionUID = 8804313303791833912L;

	public String width;
	public String height;

	/**
	 * Must-have constructor for serialization
	 */
	public SeparatorField() {
	}

	public SeparatorField(int row, int col, String width, String height) {
		this.type = Type.SEPARATOR;
		this.row = row;
		this.column = col;
		this.width = width;
		this.height = height;
	}
}
