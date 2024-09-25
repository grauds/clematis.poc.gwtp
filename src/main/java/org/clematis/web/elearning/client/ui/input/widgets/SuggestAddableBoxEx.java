package org.clematis.web.elearning.client.ui.input.widgets;

import com.google.gwt.user.client.ui.MultiWordSuggestOracle;

public class SuggestAddableBoxEx extends SuggestBoxEx {

	private SuggestAddButton suggestAddButton;
	
	public SuggestAddableBoxEx(MultiWordSuggestOracle oracle) {
		super(oracle);
		suggestAddButton = new SuggestAddButton();
        holder.add(suggestAddButton);
	}
	
	public SuggestAddButton getSuggestAddButton() {
		return suggestAddButton;
	}	
	
}
