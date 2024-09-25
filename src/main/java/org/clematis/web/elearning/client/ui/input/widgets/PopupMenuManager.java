package org.clematis.web.elearning.client.ui.input.widgets;

import org.clematis.web.elearning.client.ui.input.resources.InputResources;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.PopupPanel;


public class PopupMenuManager {

	private static PopupPanel popupPanel;
	private MenuBar menu;		
	
	public void showPopupMenu(int x, int y, int width)
	{
		boolean show = true;
		
		if(  getPopupPanel().isAttached() )
		{
			show = ! getPopupPanel().getWidget().equals(getMenu());
			hidePopupMenu();
		}
		
		if ( show )
		{
			getPopupPanel().setPopupPosition(x, y);
			getMenu().setWidth(width + "px");

			getPopupPanel().clear();
			getPopupPanel().setWidget(getMenu());
			
			getPopupPanel().show();
		}
	}
	
	public void hidePopupMenu(){
		getPopupPanel().hide();
	}
	
	public static PopupPanel getPopupPanel(){
		if ( popupPanel == null ){
			InputResources.INSTANCE.css().ensureInjected();
			
			popupPanel = new PopupPanel();
			popupPanel.setStyleName( InputResources.INSTANCE.css().ib_popuppanel() ); 	
			popupPanel.setAutoHideEnabled(true);
			popupPanel.setAutoHideOnHistoryEventsEnabled(true);
		}
		return popupPanel;
	}		
	
	public MenuBar getMenu(){
		if ( menu == null ){
			menu = new MenuBar();
		}
		return menu;
	}
	
	public void addItem(final String name, String key){
		MenuItem item = new MenuItem(name, true, new Command() {
			
			@Override
			public void execute() {
				hidePopupMenu();
				onItemSelected(name);
			}
		});
		item.addStyleName( InputResources.INSTANCE.css().ib_popupitem() );
		getMenu().addItem( item );
	}
	
	public void onItemSelected(String text) {
		
	}
	
	public void addItem(final String name, String key, final ClickHandler clickHandler){
		MenuItem item = new MenuItem(name, true, new Command() {
			
			@Override
			public void execute() {
				clickHandler.onClick(null);				
			}
		});
		item.addStyleName( InputResources.INSTANCE.css().ib_popupitem() );
		getMenu().addItem( item );
	}		
}
