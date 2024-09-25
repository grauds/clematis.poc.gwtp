package org.clematis.web.elearning.client.fields;

public class SubmitAnalyticsField extends SubmitField {
	private static final long serialVersionUID = 6524632843005464726L;

	private String analyticsBase;

	/**
	 * Constructor needed for serialization
	 */
	protected SubmitAnalyticsField() {
		this.analyticsBase = null;
		this.closeOnSuccess = false;
	}

	public SubmitAnalyticsField(String id, String analyticsBase, String caption, int row, int col, boolean closeOnSuccess) {
		this.type = Type.SUBMIT;
		this.id = id;
		this.analyticsBase = analyticsBase;
		this.captionForm = caption;
		this.captionGrid = caption;
		this.row = row;
		this.column = col;
		this.closeOnSuccess = closeOnSuccess;
	}

	public String getAnalyticsBase() {
		return analyticsBase;
	}

}
