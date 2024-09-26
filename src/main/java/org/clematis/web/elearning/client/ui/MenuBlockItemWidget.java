package org.clematis.web.elearning.client.ui;

import java.util.Set;

import org.clematis.web.elearning.client.fields.MenuBlockField;
import org.clematis.web.elearning.client.login.AppMenuItem;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;

import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.gwtplatform.mvp.client.proxy.ParameterTokenFormatter;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

public class MenuBlockItemWidget extends Composite{

	private static MenuListItemWidgetUiBinder uiBinder = GWT
			.create(MenuListItemWidgetUiBinder.class);

	interface MenuListItemWidgetUiBinder extends
			UiBinder<Widget, MenuBlockItemWidget> {
	}
	
	interface MenuBlockItemStyle extends CssResource {
		String link();
	}	

	@UiField MenuBlockItemStyle style;		
	@UiField Image image;
	@UiField VerticalPanel panel;
	
	private final ParameterTokenFormatter parameterTokenFormatter;
	
	public interface MenuBlockItemWidgetFactory {
		public MenuBlockItemWidget create( MenuBlockField field );
	}	

	@Inject
	public MenuBlockItemWidget(@Assisted MenuBlockField field, final ParameterTokenFormatter parameterTokenFormatter) {
		initWidget(uiBinder.createAndBindUi(this));

		this.parameterTokenFormatter = parameterTokenFormatter;
		
		if ( field != null ) {
			
			if (field.getIconUrl() != null) {
				image.setUrl(field.getIconUrl());
			}
			
			image.setWidth("64px");
			
			for ( AppMenuItem item : field.getItems() ){
				panel.add(createHyperlink( item ));
			}
		}
	}
	
	//<!-- g:Hyperlink targetHistoryToken='token' styleName="{style.link}" text="..."-->	
	protected Hyperlink createHyperlink(AppMenuItem item)
	{
		if ( item != null  ){
	        Hyperlink hyperlink = new Hyperlink();	        
	        hyperlink.setText( item.name );
	        hyperlink.setStyleName(style.link());
	        PlaceRequest placeRequest = new PlaceRequest(item.form);
			if ( item.params != null ){
				Set<String> keys = item.params.keySet();
				for (String key : keys ){
					placeRequest = placeRequest.with(key, item.params.get(key));
				}
			}	        
	        hyperlink.setTargetHistoryToken( parameterTokenFormatter.toPlaceToken(placeRequest) );	        
			return hyperlink;
		}
		return null;
	}
}
