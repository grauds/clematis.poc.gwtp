package org.clematis.web.elearning.client.fields;

public class SubmitField extends Field {
	private static final long serialVersionUID = -8877021649891659092L;

	protected boolean closeOnSuccess;
	protected boolean isDefaultButton;

	/** Serialization requirements */
	protected SubmitField() {
	}

	public boolean closeOnSuccess() {
		return closeOnSuccess;
	}

	public SubmitField setCloseOnSuccess(boolean closeOnSuccess) {
		this.closeOnSuccess = closeOnSuccess;
		return this;
	}

	public boolean isDefaultButton() {
		return isDefaultButton;
	}

	public SubmitField setDefaultButton(boolean isDefault) {
		this.isDefaultButton = isDefault;
		return this;
	}
}
