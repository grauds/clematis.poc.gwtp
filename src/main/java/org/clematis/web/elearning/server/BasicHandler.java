package org.clematis.web.elearning.server;
//
//import javax.naming.Context;
//import javax.naming.InitialContext;
//import javax.sql.DataSource;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.Reader;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;

import org.clematis.web.elearning.server.log.LoggerFactory;

public class BasicHandler {
	
    protected void close(CallableStatement cstmt, Connection connection)
    {
        if (cstmt != null)
        {
            try
            {
                cstmt.close();
            }
            catch (SQLException ex)
            {
                LoggerFactory.getLogger(BasicHandler.class.getName()).log(Level.SEVERE, Thread.currentThread().getName(), ex);
            }
        }
        if (connection != null)
        {
            try
            {
                connection.close();
            }
            catch (SQLException ex)
            {
                LoggerFactory.getLogger(BasicHandler.class.getName()).log(Level.SEVERE, Thread.currentThread().getName(), ex);
            }
        }
    }

    protected void close(PreparedStatement cstmt, Connection connection)
    {
        if (cstmt != null)
        {
            try
            {
                cstmt.close();
            }
            catch (SQLException ex)
            {
                LoggerFactory.getLogger(BasicHandler.class.getName()).log(Level.SEVERE, Thread.currentThread().getName(), ex);
            }
        }
        if (connection != null)
        {
            try
            {
                connection.close();
            }
            catch (SQLException ex)
            {
                LoggerFactory.getLogger(BasicHandler.class.getName()).log(Level.SEVERE, Thread.currentThread().getName(), ex);
            }
        }
    }

    protected void logWithUserInfo(String loggerName, Level level, String message)
    {
        LoggerFactory.getLogger(loggerName).log(level, message);
    }

    protected void logWithUserInfo(String loggerName, Level level, String message, Exception e)
    {
        LoggerFactory.getLogger(loggerName).log(level, message, e);
    }


    protected void logWithUserInfo(String loggerName, Level level, String pattern, String message)
    {
        LoggerFactory.getLogger(loggerName).log(level, pattern, message);
    }

    protected void close(ResultSet rs)
    {
        if (rs != null)
        {
            try
            {
                rs.close();
            }
            catch (SQLException ex)
            {
                LoggerFactory.getLogger(BasicHandler.class.getName()).log(Level.SEVERE, Thread.currentThread().getName(), ex);
            }
        }
    }

    protected void close(CallableStatement cstmt, ResultSet rs, Connection connection)
    {
        close(rs);
        close(cstmt, connection);
    }

    protected void close(PreparedStatement cstmt, ResultSet rs, Connection connection)
    {
        close(rs);
        close(cstmt, connection);
    }

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
            LoggerFactory.getLogger(BasicHandler.class.getName()).log(Level.SEVERE, Thread.currentThread().getName(), ex);
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
//        DataSource ds = (DataSource) envCtx.lookup("jdbc/ds");
//
//        if (ds != null)
//        {
//            return ds.getConnection();
//        } else
//            return null;
    	
//    	String userName = "root";
//        String password = "****";
        String url = "jdbc:mysql://77.91.193.156:3306/elearning_prototype";
        Class.forName ("com.mysql.jdbc.Driver").newInstance ();
        
        Properties properties=new Properties();
        properties.setProperty("user","root");
        properties.setProperty("password","****");
        properties.setProperty("useUnicode","true");
        properties.setProperty("characterEncoding","UTF-8");
        
       // return DriverManager.getConnection (url, userName, password);
        return DriverManager.getConnection (url, properties);
    }

    public static String blob2string(Blob blob) throws SQLException, IOException
    {
        try
        {
            if (blob != null)
            {
                byte[] bdata = blob.getBytes(1, (int) blob.length());
                return new String(bdata, "UTF8");
            } else
            {
                return "";
            }
        }
        catch (Exception ex)
        {
            throw new IllegalArgumentException(ex);
        }
    }

    public static String clob2string(Clob clob) throws SQLException, IOException
    {
        try
        {
            if (clob != null)
            {
                Reader reader = clob.getCharacterStream();
                CharArrayWriter writer = new CharArrayWriter();
                char[] buffer = new char[(int) clob.length()];
                int len;
                while ((len = reader.read(buffer)) > 0)
                {
                    writer.write(buffer, 0, len);
                }
                return writer.toString();
            } else
            {
                return "";
            }
        }
        catch (Exception ex)
        {
            throw new IllegalArgumentException(ex.toString());
        }
    }

}
