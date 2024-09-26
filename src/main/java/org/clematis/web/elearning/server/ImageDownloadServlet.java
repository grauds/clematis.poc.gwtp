/******************************************************************************

 File: FileDownloadServlet.java

 © Copyright 2009-2010, Marinvent Corporation. All rights reserved.

 Project............ Common project
 Module............. Server code
 Module Version.....
 Author............. <Anton Troshin>
 Created............ 20-Oct-2010
 Description........
 Data Files......... none
 Required Libraries. GWT libraries
 Notes..............

 Change History (most recent first)
 //----------------------------------------------------------------------------
 //$Source: FileDownloadServlet.java $
 //$Log: FileDownloadServlet.java  $
 //----------------------------------------------------------------------------
 ******************************************************************************/
package org.clematis.web.elearning.server;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.clematis.web.elearning.server.log.LoggerFactory;

import com.google.inject.Singleton;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;

/**
 *
 */
@Singleton
public class ImageDownloadServlet extends HttpServlet
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1430581402195740015L;

	public synchronized Connection getConnection() throws Exception
    {
        Connection c = null;

        try
        {
            c = _getConnection();
            if (c != null)
            {
                return c;
            }
        }
        catch (Exception ex)
        {
            //LoggerFactory.getLogger(BasicService.class.getName()).log(Level.SEVERE, Thread.currentThread().getName(), ex);
        }

        if (c == null)
        {
            throw new Exception("Cannot establish connection to database. Contact system administrator.");
        }
        return c;
    }

    private Connection _getConnection() throws Exception
    {
//      Context initCtx = new InitialContext();
//      Context envCtx = (Context) initCtx.lookup("java:comp/env");
//      DataSource ds = (DataSource) envCtx.lookup("jdbc/tds");
//
//      if (ds != null)
//      {
//          return ds.getConnection();
//      } else
//          return null;
  	  String userName = "root";
      String password = "****";
      String url = "jdbc:mysql://77.91.193.156:3306/elearning_prototype";
      Class.forName ("com.mysql.jdbc.Driver").newInstance ();
      return DriverManager.getConnection (url, userName, password);    
    }

    /**
     * Process Request for file downloading the server.
     *
     * @param request  - HttpServletRequest param
     * @param response - HttpServletResponse param
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        Connection con = null;
        PreparedStatement ps = null;

        ResultSet rs = null;

        InputStream is = null;
        OutputStream os = null;

        try
        {
            con = this.getConnection();

           // String p = request.getParameter("p");
            String id = request.getParameter("id");
            
            /* 
            select the image from the picture table    .
            */
            ps = con.prepareStatement("select image from PICTURES where id=?");
            ps.setInt(1, Integer.parseInt(id));
            
            ResultSet rs1 = ps.executeQuery();
            String imgLen="";
            if(rs1.next()){
                imgLen = rs1.getString(1);
            }
            
            rs1 = ps.executeQuery();
            if(rs1.next()){
                int len = imgLen.length();
                byte [] rb = new byte[len];

                /* retrieving image in binary format*/

                InputStream readImg = rs1.getBinaryStream(1);
                readImg.read(rb, 0, len); 

                response.reset();
                response.setContentType("image/jpg");
                response.getOutputStream().write(rb,0,len);
                response.getOutputStream().flush();
            }           
        }
        catch (Exception e)
        {
            LoggerFactory.getLogger(ImageDownloadServlet.class.getName()).log(Level.SEVERE, "Cannot download file: " + e.getMessage());
        }
        finally
        {
            try
            {
                if (is != null) is.close();
                if (os != null) os.close();

                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.commit();
                if (con != null) con.close();
            }
            catch (Exception e)
            {
                // do nothing
            }
        }
    }

    /**
     * Get the info for downloading of the file
     *
     * @param request  - HttpServletRequest param
     * @param response - HttpServletResponse param
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        processRequest(request, response);
    }

    /**
     * Post the info for downloading of the file
     *
     * @param request  - HttpServletRequest param
     * @param response - HttpServletResponse param
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        processRequest(request, response);
    }

}
