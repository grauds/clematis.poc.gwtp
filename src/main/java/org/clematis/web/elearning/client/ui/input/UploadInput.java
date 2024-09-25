package org.clematis.web.elearning.client.ui.input;

import org.clematis.web.elearning.client.ui.input.elements.InputElementInterface;
import org.clematis.web.elearning.client.ui.input.elements.UploadElement;

import com.google.gwt.user.client.ui.FileUpload;

public class UploadInput extends InputBase<String, InputElementInterface<String>> {

	public UploadInput() {
		super(new UploadElement(new FileUpload()));

	}

	@Override
	protected String getInputStyleName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getInputErrorStyleName() {
		// TODO Auto-generated method stub
		return null;
	}

}
