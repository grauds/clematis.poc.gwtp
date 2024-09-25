package org.clematis.web.elearning.server;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Singleton;

@Singleton
public class FlashEventsServlet extends HttpServlet  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7485526694948281063L;
	/**
     * Process Request for file downloading the server.
     *
     * @param request  - HttpServletRequest param
     * @param response - HttpServletResponse param
     */
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
    	       
    }
}
