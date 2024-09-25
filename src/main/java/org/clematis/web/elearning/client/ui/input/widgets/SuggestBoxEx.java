package org.clematis.web.elearning.client.ui.input.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.Widget;


public class SuggestBoxEx extends Composite {

	private static SuggestBoxExUiBinder uiBinder = GWT.create(SuggestBoxExUiBinder.class);

	interface SuggestBoxExUiBinder extends UiBinder<Widget, SuggestBoxEx> {
	}

	@UiField HorizontalPanel holder;
	@UiField(provided=true) SuggestBox suggestBox;
	@UiField ArrowButton button;
	
	public SuggestBoxEx(MultiWordSuggestOracle oracle) {
		suggestBox = new SuggestBox(oracle);
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("button")
	void onClick(ClickEvent e) {
		/*
		 * TODO
		 * extend DefaultSuggestionDisplay and pass to suggest box constructor.
		 * Reason: change css-styles on the fly to add scrollbar functionality
		 * when displaying hundreds of suggestions when the left button is clicked.
		 */
//		if ( suggestBox.getTextBox().isEnabled() ) {
//			((MultiWordSuggestOracle)suggestBox.getSuggestOracle()).setItemsAsDefaultSuggestions();
//			suggestBox.showSuggestionList();
//		}
	}

	public TextBoxBase getTextBox() {
		return suggestBox.getTextBox();
	}
	
	public SuggestBox getSuggestBox() {
		return suggestBox;
	}
	
	public HorizontalPanel getHolder() {
		return holder;
	}

	public ArrowButton getButton() {
		return button;
	}

	public String getText() {
		return suggestBox.getText();
	}

	public void setText(String text) {
		suggestBox.setText(text);
	}

	public int getTabIndex() {
		return suggestBox.getTabIndex();
	}

	public void setAccessKey(char key) {
		suggestBox.setAccessKey(key);
	}

	public void setFocus(boolean focused) {
		suggestBox.setFocus(focused);
	}

	public void setTabIndex(int index) {
		suggestBox.setTabIndex(index);
	}

	public String getValue() {
		return suggestBox.getValue();
	}

	public void setValue(String string) {
		suggestBox.setValue(string);		
	}

	public void showSuggestionList() {
		suggestBox.showSuggestionList();
	}

	public MultiWordSuggestOracle getSuggestOracle() {
		return (MultiWordSuggestOracle)suggestBox.getSuggestOracle();
	}

}



