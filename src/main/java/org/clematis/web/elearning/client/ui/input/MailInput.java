package org.clematis.web.elearning.client.ui.input;

import org.clematis.web.elearning.client.ui.input.resources.InputResources;

public class MailInput extends Input {

	public MailInput() {
		// Add right alignment
		input.asWidget().addStyleName(InputResources.INSTANCE.css().input());
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
			if (isValidEmail(text) ) {
				return true;
			} else {
				showError("Строка не похожа на e-mail адрес");
				return false;
			}
			
		}
		return super.isFieldValid();
	}
	
	private native boolean isValidEmail(String email) /*-{
	    var reg1 = /(@.*@)|(\.\.)|(@\.)|(\.@)|(^\.)/; // not valid
	    var reg2 = /^.+\@(\[?)[a-zA-Z0-9\-\.]+\.([a-zA-Z]{2,3}|[0-9]{1,3})(\]?)$/; // valid
    	return !reg1.test(email) && reg2.test(email);
    }-*/;	

}
