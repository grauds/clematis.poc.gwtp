package org.clematis.web.elearning.client.fields;

public class SubmitTransactionField extends SubmitField {
	private static final long serialVersionUID = -8505514997721360978L;

	/**
	 * For actual transactions the System must place actual
	 * scenario in the process of registering new transaction.
	 */
	private boolean isActualTransaction;

	/** Required for serialization */
	protected SubmitTransactionField() {
	}

	public SubmitTransactionField(String id, String name, int row, int col) {
		this.type = Type.SUBMIT;
		this.id = id;
		this.captionForm = name;
		this.captionGrid = name;
		this.row = row;
		this.column = col;
	}

	public boolean isActualTransaction() {
		return isActualTransaction;
	}

	public SubmitTransactionField setActualTransaction(boolean isActualTransaction) {
		this.isActualTransaction = isActualTransaction;
		return this;
	}

}
