package org.clematis.web.elearning.client.ui.input.widgets;


public class SelectAddableBoxEx extends SelectBoxEx {

	private SuggestAddButton selectAddButton;	
	
	public SelectAddableBoxEx() {
		super();
		selectAddButton = new SuggestAddButton();
        holder.add(selectAddButton);
	}
	
	public SuggestAddButton getSelectAddButton() {
		return selectAddButton;
	}
}
