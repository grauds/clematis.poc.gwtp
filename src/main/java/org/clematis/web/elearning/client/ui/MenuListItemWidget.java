package org.clematis.web.elearning.client.ui;

import java.util.Set;

import org.clematis.web.elearning.client.fields.MenuListField;
import org.clematis.web.elearning.client.login.AppMenuItem;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.gwtplatform.mvp.client.proxy.ParameterTokenFormatter;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

public class MenuListItemWidget extends Composite {

	private static MenuListItemWidgetUiBinder uiBinder = GWT
			.create(MenuListItemWidgetUiBinder.class);

	interface MenuListItemWidgetUiBinder extends
			UiBinder<Widget, MenuListItemWidget> {
	}
	
	interface MenuListItemStyle extends CssResource {
		String link();
	}	

	@UiField MenuListItemStyle style;			
	@UiField Label caption;
	@UiField VerticalPanel links;
	
	private final ParameterTokenFormatter parameterTokenFormatter;
	
	public interface MenuListItemWidgetFactory {
		public MenuListItemWidget create( MenuListField field );
	}		
	
	@Inject
	public MenuListItemWidget(@Assisted MenuListField field, final ParameterTokenFormatter parameterTokenFormatter) {
		initWidget(uiBinder.createAndBindUi(this));
		
        this.parameterTokenFormatter = parameterTokenFormatter;
		
		if ( field != null )
		{
			caption.setText( field.captionForm );
			
			for ( AppMenuItem item : field.getItems() )
			{
				links.add(createHyperlink( item ));
			}
		}
	}
	
	protected Hyperlink createHyperlink(AppMenuItem item)
	{
		if ( item != null  )
		{
	        Hyperlink hyperlink = new Hyperlink();	        
	        hyperlink.setText( item.name );
	        hyperlink.setStyleName(style.link());
	        PlaceRequest placeRequest = new PlaceRequest(item.form);
			if ( item.params != null )
			{
				Set<String> keys = item.params.keySet();
				for (String key : keys )
				{
					placeRequest = placeRequest.with(key, item.params.get(key));
				}
			}	        
	        hyperlink.setTargetHistoryToken( parameterTokenFormatter.toPlaceToken(placeRequest) );	        
			return hyperlink;
		}
		return null;
	}

}
