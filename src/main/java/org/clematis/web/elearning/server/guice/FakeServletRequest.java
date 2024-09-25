package org.clematis.web.elearning.server.guice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class FakeServletRequest extends HttpServletRequestWrapper {

	public FakeServletRequest(HttpServletRequest request) {
		super(request);
	}

	@Override
	public String getHeader(String name) {
		if (!"If-Modified-Since".equalsIgnoreCase(name)) {
			return super.getHeader(name);
		} else {
			return null;
		}
	}
}
