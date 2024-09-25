package org.clematis.web.elearning.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.google.inject.Singleton;

@Singleton
public class MediaConfigurationServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8907631447595754600L;
	/**
     * Process Request for file downloading the server.
     *
     * @param request  - HttpServletRequest param
     * @param response - HttpServletResponse param
     */
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
    	response.setContentType( "text/html" );
        
        // Always set the Content Type before data is printed
        PrintWriter out = response.getWriter();
        out.print( "server=77.91.193.156:1935" );
        out.close();        
    }
}
