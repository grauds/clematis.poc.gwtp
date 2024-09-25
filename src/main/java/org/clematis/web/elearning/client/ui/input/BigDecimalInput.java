package org.clematis.web.elearning.client.ui.input;

import java.math.BigDecimal;

import org.clematis.web.elearning.client.ClientUtils;
import org.clematis.web.elearning.client.ui.input.resources.InputMessages;
import org.clematis.web.elearning.client.ui.input.resources.InputResources;

import com.google.gwt.i18n.client.NumberFormat;

public class BigDecimalInput extends Input {
	
	public static NumberFormat format = NumberFormat.getFormat("#,##0.00");
	
	public BigDecimalInput() {
		// Add right alignment
		input.asWidget().addStyleName(InputResources.INSTANCE.css().ib_longinputformat());
	}
	
	@Override
	public boolean isFieldValid() {
		String text = input.getText();
		if (text != null && !text.trim().isEmpty()) {
			
	        try {
	            convertEditedValueToValue(text);
	            return true;
	        } catch (NumberFormatException ex) {
	 
	        }

			showError(InputMessages.INSTANCE.getBigDecimalFieldMessage());
			return false;
		}
		return super.isFieldValid();
	}
	
    protected BigDecimal convertEditedValueToValue(String editedValue) {
        if (editedValue == null || editedValue.isEmpty()) {
            return null;
        } else {
            return new BigDecimal(ClientUtils.removeSeparatorsFromBigDecimalString(editedValue));
        }
    }	

	@Override
	public void validate() {
		BigDecimal bd = convertEditedValueToValue(input.getText());
		if (bd != null) {
			input.setText(BigDecimalInput.format.format(bd));
			super.validate();
		}
	}

	@Override
	public String getText() {
		return ClientUtils.removeSeparatorsFromBigDecimalString(super.getText());
	}

	@Override
	public String getValue() {
		return ClientUtils.removeSeparatorsFromBigDecimalString(super.getValue());
	}

}
