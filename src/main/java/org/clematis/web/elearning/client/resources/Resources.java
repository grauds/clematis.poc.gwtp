package org.clematis.web.elearning.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.TextResource;

public interface Resources extends ClientBundle {
	
	public static final Resources INSTANCE =  GWT.create(Resources.class);

	@Source("photo.png")
	public ImageResource user();	

	@Source("prototype_logo.png")
	public ImageResource prototype_logo();
	
	@Source("pin_blue.png")	
	public ImageResource pin_blue();
	
	@Source("bulb.png")
	public ImageResource bulb();
	
	@Source("settings.png")
	public ImageResource settings();	
	
//	@Source("back.png")
//	public ImageResource back();
//	
//	@Source("forward.png")
//	public ImageResource forward();	
	
	@Source("logo.png")
	public ImageResource logo();
	
	@Source("em.png")
	public ImageResource mail();

	@Source("page-403.png")
	public ImageResource page403();		
	
	@Source("page-404.png")
	public ImageResource page404();	

	@Source("archives.png")
	public ImageResource archives();	

	@Source("arrow_down.png")
	public ImageResource downArrow();
	
	@Source("skype_logo.png")
	public ImageResource skypeLogo();
		
	@Source("plus.png")
	public ImageResource plus();
	
	@Source("headset.png")
	public ImageResource headset();		

	@Source("refresh_16x16.png")
	public ImageResource refresh();

	@Source("loader.gif")
	public ImageResource loader();

	@Source("multitude_wh_16.png")
	public ImageResource copyToClipboard();

	@Source("ajax-loader.gif")
	public ImageResource ajaxLoader();

	@Source("delete16x16.png")
	public ImageResource delete16x16();

	@Source("excel_16x16.gif")
	public ImageResource excel16x16();

	@Source("license.xml")
	public TextResource getLicenseText();
}
