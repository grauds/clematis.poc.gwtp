package org.clematis.web.elearning.client.ui.input;

import org.clematis.web.elearning.client.ui.input.resources.InputMessages;
import org.clematis.web.elearning.client.ui.input.resources.InputResources;

public class LongInput extends Input {

	public LongInput() {
		// Add right alignment
		input.asWidget().addStyleName(InputResources.INSTANCE.css().ib_longinputformat());
	}

	/**
	 * The field is valid in case it's empty or if it's not empty
	 * then it's value must be convertible to Long and the
	 * Long's string representation must be equal to the original string.
	 */
	@Override
	public boolean isFieldValid() {
		String text = input.getText();
		if (text != null && !text.trim().isEmpty()) {
			try {
				Long l = Long.parseLong(text);
				if (text.equals(l.toString())) {
					return true;
				}
			} catch (Exception e) {
			}
			showError(InputMessages.INSTANCE.getLongFieldMessage());
			return false;
		}
		return super.isFieldValid();
	}

	public Long getValueLong() {
		String text = input.getText();
		if (text != null && !text.trim().isEmpty()) {
			try {
				Long l = Long.parseLong(text);
				return l;
			} catch (Exception e) {
			}
		}
		return null;
	}
}
