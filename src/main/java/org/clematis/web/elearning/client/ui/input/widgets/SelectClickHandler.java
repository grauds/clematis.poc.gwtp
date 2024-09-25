package org.clematis.web.elearning.client.ui.input.widgets;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;


public class SelectClickHandler implements ClickHandler {

	private SelectBoxEx selectBox;
	
	public SelectClickHandler() {
		super();
	}

	public SelectClickHandler(SelectBoxEx selectBox) {
		super();
		setSelectBox(selectBox);
	}

	@Override
	public void onClick(ClickEvent event) {
		if ( selectBox != null ){
			selectBox.onClick( event );
		}
	}

	public void setSelectBox(SelectBoxEx selectBox) {
		this.selectBox = selectBox;		
	}
}
