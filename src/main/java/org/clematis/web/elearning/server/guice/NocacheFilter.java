package org.clematis.web.elearning.server.guice;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Singleton;

/**
 * Filter prevents file from beeing cached and force the server to always retrieve
 * current version of the file.
 * 
 * @author vladimirkiva@finbudget.com
 * 
 */
@Singleton
public class NocacheFilter implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		// This is used to strengthen approach to avoid 304 response from the server.
		FakeServletRequest fakeRequest = new FakeServletRequest((HttpServletRequest)request);

		httpResponse.setHeader("Cache-Control", "no-cache, must-revalidate");
		httpResponse.setHeader("Pragma", "no-cache");
		httpResponse.setDateHeader("Expires", 0);
		httpResponse.setHeader("ETag", ""); // Cleanup ETag.
		chain.doFilter(fakeRequest, response);
		httpResponse.setHeader("Last-Modified", "Tue, 22 Mar 2011 00:50:24 GMT"); // Set date in the past
		httpResponse.setHeader("Cache-Control", "no-cache, must-revalidate");
	}

}
