package org.clematis.web.elearning.server.guice;

import org.clematis.web.elearning.server.BudgetImportServlet;
import org.clematis.web.elearning.server.ImageDownloadServlet;
import org.clematis.web.elearning.server.ImageUploadServlet;
import org.clematis.web.elearning.server.FlashEventsServlet;
import org.clematis.web.elearning.server.MediaConfigurationServlet;

import com.google.inject.servlet.ServletModule;
import com.gwtplatform.dispatch.shared.ActionImpl;
import com.gwtplatform.dispatch.shared.SecurityCookie;
import com.gwtplatform.dispatch.server.guice.DispatchServiceImpl;
import com.gwtplatform.dispatch.server.guice.HttpSessionSecurityCookieFilter;

public class DispatchServletModule extends ServletModule {
	static {
		System.setProperty("javax.xml.transform.TransformerFactory",
				"com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl");
	}

	@Override
	public void configureServlets() {
		bindConstant().annotatedWith(SecurityCookie.class).to("SECURE_COOKIE");
	
		serve("/" + ActionImpl.DEFAULT_SERVICE_NAME + "*").with(DispatchServiceImpl.class);
		serve("/video*").with(MediaConfigurationServlet.class);
		serve("/events*").with(FlashEventsServlet.class);
		serve("/upload*").with(ImageUploadServlet.class);
		serve("/download*").with(ImageDownloadServlet.class);
		serve("/budget_import*").with(BudgetImportServlet.class);
		
		filter("*.nocache.js").through(HttpSessionSecurityCookieFilter.class);
		filter("*.nocache.js").through(NocacheFilter.class);
		

		super.configureServlets();		
	}
}
