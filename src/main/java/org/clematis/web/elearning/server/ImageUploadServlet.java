/******************************************************************************

 File: FileUploadServlet.java

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
 //$Source: FileUploadServlet.java $
 //$Log: FileUploadServlet.java  $
 //----------------------------------------------------------------------------
 ******************************************************************************/
package org.clematis.web.elearning.server;

import java.sql.SQLException;
import java.util.logging.Logger;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.clematis.web.elearning.server.log.LoggerFactory;

import com.google.inject.Singleton;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

/**
 *
 */
@Singleton
public class ImageUploadServlet extends HttpServlet
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -557250038404135240L;

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
//            LoggerFactory.getLogger(BasicService.class.getName()).log(Level.SEVERE, Thread.currentThread().getName(), ex);
        }

        if (c == null)
        {
            throw new Exception("Cannot establish connection to database. Contact system administrator.");
        }
        return c;
    }

    private Connection _getConnection() throws Exception
    {
//        Context initCtx = new InitialContext();
//        Context envCtx = (Context) initCtx.lookup("java:comp/env");
//        DataSource ds = (DataSource) envCtx.lookup("jdbc/tds");
//
//        if (ds != null)
//        {
//            return ds.getConnection();
//        } else
//            return null;
    	String userName = "root";
        String password = "*****";
        String url = "jdbc:mysql://77.91.193.156:3306/elearning_prototype";
        Class.forName ("com.mysql.jdbc.Driver").newInstance ();
        return DriverManager.getConnection (url, userName, password);    	
    }

    //@Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
    {
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);


        if (isMultipart)
        {
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);

            try
            {
                List<?> items = upload.parseRequest(request);
                Iterator<?> iter = items.iterator();

                while (iter.hasNext())
                {
                    FileItem item = (FileItem) iter.next();
                    if (item.isFormField())
                    {
                        
                        String fileName = item.getName();

                        if (fileName != null && !fileName.equals(""))
                        {
                            fileName = FilenameUtils.getName(fileName);
                            String fileExt = FilenameUtils.getExtension(fileName);

                            Connection con = null;
                            InputStream istream = null;
                            PreparedStatement ps = null;

                            try
                            {
                                con = this.getConnection();
                                con.setAutoCommit(false);
                                istream = item.getInputStream();
                                /* generate a file ID */
                                String fid = getFileID();
                                
                                ps = con.prepareStatement("INSERT INTO IMAGES(image) VALUES(?)");
                                ps.setBinaryStream(1, istream);
                                
                                if (ps.executeUpdate() == 0) {
                                	throw new Exception("Cannot load image");
                                }

                                response.setContentType("text/plain");
                                if (fileExt.toLowerCase().equals("gif")
                                        || fileExt.toLowerCase().equals("jpg")
                                        || fileExt.toLowerCase().equals("jpeg")
                                        || fileExt.toLowerCase().equals("tif")
                                        || fileExt.toLowerCase().equals("bmp")
                                        || fileExt.toLowerCase().equals("png"))
                                {
                                    response.getWriter().write(fid + "|0|" + fileName);
                                }
                                else
                                {
                                    response.getWriter().write(fid + "|1|" + fileName);
                                }

                            }
                            catch (Exception e)
                            {
                                if (con != null) 
                                {
                                    try 
                                    {
                                        con.rollback();
                                    } catch (SQLException ex) {
                                        Logger.getLogger(ImageUploadServlet.class.getName()).log(Level.SEVERE, "Cannot rollback the changes. ", ex);
                                    }
                                }
                                LoggerFactory.getLogger(ImageUploadServlet.class.getName()).log(Level.SEVERE, "Cannot upload file: " + e.getMessage());
                            }
                            finally
                            {
                                try
                                {
                                    if (istream != null) istream.close();

                                    if (ps != null) ps.close();
                                    if (con != null) con.commit();
                                    if (con != null) con.close();
                                }
                                catch (Exception e)
                                {
                                    // do nothing
                                }
                            }
                        } // end of filename check
                    }
                }  // end of while
            }
            catch (FileUploadException e)
            {
                LoggerFactory.getLogger(ImageUploadServlet.class.getName()).log(Level.SEVERE, "Cannot upload file: " + e.getMessage());
            }

        }
    }

    /**
     * Generate a file ID using Random() operation.
     *
     * @return hash value in String as file ID
     */
    protected String getFileID()
    {

        Random random = new Random();
        long r1 = random.nextLong();
        long r2 = random.nextLong();

        String hash1 = Long.toHexString(r1);
        String hash2 = Long.toHexString(r2);
        String hash = hash1 + hash2;

        return hash;
    }
}
