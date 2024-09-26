package org.clematis.web.elearning.server;

import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.clematis.web.elearning.server.log.LoggerFactory;

import com.google.inject.Singleton;

@Singleton
public class BudgetImportServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5430556616606279067L;
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
        String url = "jdbc:mysql://77.91.193.156:3306/elearning_prototype";
        Class.forName ("com.mysql.jdbc.Driver").newInstance ();
        
        Properties properties=new Properties();
        properties.setProperty("user","root");
        properties.setProperty("password","*****");
        properties.setProperty("useUnicode","true");
        properties.setProperty("characterEncoding","UTF-8");
        
        return DriverManager.getConnection (url, properties);   	
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
                    //if (item.isFormField())
                    {
                        
                        String fileName = item.getName();

                        if (fileName != null && !fileName.equals(""))
                        {
                            fileName = FilenameUtils.getName(fileName);

                            Connection con = null;
                            InputStream in = null;
                            PreparedStatement ps = null;

                            try
                            {
                                con = this.getConnection();
                                con.setAutoCommit(false);
                                in = item.getInputStream();
                                
                                HSSFWorkbook wb = new HSSFWorkbook(in);
                                
                                deleteAll(con, ps);
                                Map<String, Integer> currencies = parseCurrencies(wb, con, ps);
                                Map<String, Integer> accounts = parseAccounts(wb, con, ps, currencies);
                                Map<String, Integer> bitems = parseItems(wb, con, ps);
                                Map<String, Integer> agents = parseAgents(wb, con, ps);
                                Map<String, Integer> projects = parseProjects(wb, con, ps);
                                parseOperations(wb, con, ps, accounts, agents, projects, bitems);
                                
                                response.setContentType("text/plain");
                                response.getWriter().write("Import successfull");
                            }
                            catch (Exception e)
                            {
                                if (con != null) 
                                {
                                    try 
                                    {
                                        con.rollback();
                                    } catch (SQLException ex) {
                                        Logger.getLogger(BudgetImportServlet.class.getName()).log(Level.SEVERE, "Cannot rollback the changes. ", ex);
                                    }
                                }
                                LoggerFactory.getLogger(BudgetImportServlet.class.getName()).log(Level.SEVERE, "Cannot upload file: " + e.getMessage());
                            }
                            finally
                            {
                                try
                                {
                                    if (in != null) in.close();

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
                    }
                }  // end of while
            }
            catch (FileUploadException e)
            {
                LoggerFactory.getLogger(BudgetImportServlet.class.getName()).log(Level.SEVERE, "Cannot upload file: " + e.getMessage());
            }

        }
    }
    
    private void deleteAll(Connection con, PreparedStatement ps) throws SQLException {

		ps = con.prepareStatement("DELETE FROM OPERATIONS;");
		ps.executeUpdate();
    	ps = con.prepareStatement("DELETE FROM ACCOUNTS;");
		ps.executeUpdate();
		ps = con.prepareStatement("DELETE FROM CURRENCIES;");
		ps.executeUpdate();
		ps = con.prepareStatement("DELETE FROM ITEMS;");
		ps.executeUpdate();
		ps = con.prepareStatement("DELETE FROM AGENTS;");
		ps.executeUpdate();
		ps = con.prepareStatement("DELETE FROM PROJECTS;");
		ps.executeUpdate();
    }
    
    private Map<String, Integer> parseCurrencies(HSSFWorkbook wb, Connection con, PreparedStatement ps) throws SQLException {
    	Map<String, Integer> currencies = new HashMap<String, Integer>();
		
		Sheet sheet = wb.getSheetAt(7);
        Iterator<Row> it = sheet.iterator();
        int i = 1;
        if (it.hasNext()) {
        	it.next();
        }
        while (it.hasNext()) {
            Row row = it.next();
    		ps = con.prepareStatement("INSERT INTO CURRENCIES(ID,NAME,SIGN) VALUES(?,?,?)");
    		ps.setInt(1, i);
            ps.setString(2, row.getCell(0).getStringCellValue());
            ps.setString(3, row.getCell(1).getStringCellValue());
            ps.executeUpdate();
            currencies.put(row.getCell(1).getStringCellValue(), i);
            i++;
        }
    	return currencies;
    }

	private Map<String, Integer> parseAccounts(HSSFWorkbook wb, Connection con, PreparedStatement ps, Map<String, Integer> currencies) throws SQLException {
		Map<String, Integer> accounts = new HashMap<String, Integer>();
		
		Sheet sheet = wb.getSheetAt(5);
        Iterator<Row> it = sheet.iterator();
        int i = 1;
        if (it.hasNext()) {
        	it.next();
        }
        while (it.hasNext()) {
            Row row = it.next();
    		ps = con.prepareStatement("INSERT INTO ACCOUNTS(ID,NAME,ID_CURRENCY) VALUES(?,?,?)");
    		ps.setInt(1, i);
            ps.setString(2, row.getCell(0).getStringCellValue());
            ps.setInt(3, currencies.get(row.getCell(1).getStringCellValue()));
            ps.executeUpdate();
            accounts.put(row.getCell(0).getStringCellValue(), i);
            i++;
        }
		return accounts;
	}
	
	private Map<String, Integer> parseItems(HSSFWorkbook wb, Connection con, PreparedStatement ps) throws SQLException {
	    	Map<String, Integer> items = new HashMap<String, Integer>();
			
			
		Sheet sheet = wb.getSheetAt(3);
	    Iterator<Row> it = sheet.iterator();
	    int i = 1;
	    if (it.hasNext()) {
	    	it.next();
	    }
	    while (it.hasNext()) {
	        Row row = it.next();
			ps = con.prepareStatement("INSERT INTO ITEMS(ID,NAME) VALUES(?,?)");
			ps.setInt(1, i);
	        ps.setString(2, row.getCell(0).getStringCellValue());
	        ps.executeUpdate();
	        items.put(row.getCell(0).getStringCellValue(), i);
	        i++;
	    }
		return items;
	}	
	
	private Map<String, Integer> parseAgents(HSSFWorkbook wb, Connection con, PreparedStatement ps) throws SQLException {
    	Map<String, Integer> agents = new HashMap<String, Integer>();
		
			
		Sheet sheet = wb.getSheetAt(2);
	    Iterator<Row> it = sheet.iterator();
	    int i = 1;
	    if (it.hasNext()) {
	    	it.next();
	    }
	    while (it.hasNext()) {
	        Row row = it.next();
			ps = con.prepareStatement("INSERT INTO AGENTS(ID,NAME) VALUES(?,?)");
			ps.setInt(1, i);
	        ps.setString(2, row.getCell(0).getStringCellValue());
	        ps.executeUpdate();
	        agents.put(row.getCell(0).getStringCellValue(), i);
	        i++;
	    }
		return agents;
	}
	
	private Map<String, Integer> parseProjects(HSSFWorkbook wb, Connection con, PreparedStatement ps) throws SQLException {
    	Map<String, Integer> projects = new HashMap<String, Integer>();
		
			
		Sheet sheet = wb.getSheetAt(2);
	    Iterator<Row> it = sheet.iterator();
	    int i = 1;
	    if (it.hasNext()) {
	    	it.next();
	    }
	    while (it.hasNext()) {
	        Row row = it.next();
			ps = con.prepareStatement("INSERT INTO PROJECTS(ID,NAME) VALUES(?,?)");
			ps.setInt(1, i);
	        ps.setString(2, row.getCell(0).getStringCellValue());
	        ps.executeUpdate();
	        projects.put(row.getCell(1).getStringCellValue(), i);
	        i++;
	    }
		return projects;
	}
	
	private void parseOperations(HSSFWorkbook wb, Connection con, PreparedStatement ps,
			                     Map<String, Integer> accounts, Map<String, Integer> agents,
			                     Map<String, Integer> projects, Map<String, Integer> items) throws SQLException {
			
		Sheet sheet = wb.getSheetAt(0);
	    Iterator<Row> it = sheet.iterator();
	    int i = 1;
	    if (it.hasNext()) {
	    	it.next();
	    }
	    while (it.hasNext()) {
	        Row row = it.next();
			ps = con.prepareStatement("INSERT INTO OPERATIONS(ID,DATE,ID_ACCOUNT_FROM,OUTCOME,ID_ACCOUNT_TO,INCOME,ID_AGENT,ID_PROJECT,ID_ITEM,COMMENTS) "
	                                    + "VALUES(?,?,?,?,?,?,?,?,?,?);");			
			ps.setInt(1, i);			
	        ps.setTimestamp(2, new java.sql.Timestamp(row.getCell(2).getDateCellValue().getTime()));
	        if (row.getCell(3) != null && accounts.get(row.getCell(3).getStringCellValue()) != null) {
		        ps.setInt(3, accounts.get(row.getCell(3).getStringCellValue()));
	        } else {
	        	ps.setNull(3, Types.INTEGER);
	        }
	        if (row.getCell(5) != null) {
		        ps.setBigDecimal(4, new BigDecimal(row.getCell(5).getNumericCellValue()));
	        } else {
	        	ps.setNull(4, Types.BIGINT);
	        }
	        if (row.getCell(7) != null && accounts.get(row.getCell(7).getStringCellValue()) != null) {
 		        ps.setInt(5, accounts.get(row.getCell(7).getStringCellValue()));
	        } else {
	        	ps.setNull(5, Types.INTEGER);
	        }
	        if (row.getCell(9) != null) {
	        	ps.setBigDecimal(6, new BigDecimal(row.getCell(9).getNumericCellValue()));
	        } else {
	        	ps.setNull(6, Types.BIGINT);
	        }	        
	        if (row.getCell(12) != null && agents.get(row.getCell(12).getStringCellValue()) != null) {
	        	ps.setInt(7, agents.get(row.getCell(12).getStringCellValue()));	        	
	        } else {
	        	ps.setNull(7, Types.INTEGER);
	        }	        
	        if (row.getCell(13) != null && projects.get(row.getCell(13).getStringCellValue()) != null) {
		        ps.setInt(8, projects.get(row.getCell(13).getStringCellValue()));
	        } else {
	        	ps.setNull(8, Types.INTEGER);
	        }
	        if (row.getCell(9) != null && items.get(row.getCell(11).getStringCellValue()) != null) {
	        	ps.setInt(9, items.get(row.getCell(11).getStringCellValue()));
	        } else {
	        	ps.setNull(9, Types.INTEGER);
	        }	
	        if (row.getCell(14) != null) {
		        ps.setString(10, row.getCell(14).getStringCellValue());
	        } else {
	        	ps.setNull(10, Types.VARCHAR);
	        }	
	        ps.executeUpdate();
	        i++;
	    }
	}
}
