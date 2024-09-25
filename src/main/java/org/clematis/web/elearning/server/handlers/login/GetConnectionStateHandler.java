package org.clematis.web.elearning.server.handlers.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.clematis.web.elearning.client.login.actions.GetConnectionState.ConnectionState;
import org.clematis.web.elearning.client.login.actions.GetConnectionStateAction;
import org.clematis.web.elearning.client.login.actions.GetConnectionStateResult;
import org.clematis.web.elearning.server.BasicHandler;
import org.clematis.web.elearning.server.util.BCrypt;
import org.clematis.web.elearning.shared.UUID;
import org.clematis.web.elearning.shared.domain.EmailAddress;
import org.clematis.web.elearning.shared.domain.IdentAuthObject;
import org.clematis.web.elearning.shared.domain.NamePasswordAuthObject;
import org.clematis.web.elearning.shared.domain.User;
import org.clematis.web.elearning.shared.domain.UserInRole;
import org.clematis.web.elearning.shared.domain.UserRole;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;


/**
 * 
 */
public class GetConnectionStateHandler extends BasicHandler implements ActionHandler<GetConnectionStateAction, GetConnectionStateResult> {
	
	private Provider<HttpSession> sessionProvider;
	private final static String savedNextPageParameters = "NEXTPAGE";

	@Inject
	public GetConnectionStateHandler(Provider<HttpSession> sessionProvider, Provider<HttpServletRequest> requestProvider) {
		this.sessionProvider = sessionProvider;
	}


	/**
	 * This is the main user authorization point to the application.
	 */
	@Override
	public GetConnectionStateResult execute(GetConnectionStateAction action, ExecutionContext context) throws ActionException {

		HttpSession session = sessionProvider.get();
		ConnectionState connectionState = new ConnectionState();
		connectionState.isLoggedIn = false;

		if (action == null || action.getUrlToContinue() == null) {
			throw new ActionException("Authorization data is empty or incomplete.");
		}

		// Get saved next page parameters
		String nextPage = (String) session.getAttribute(savedNextPageParameters);
		// Store next page for future use
		session.setAttribute(savedNextPageParameters, action.getNextPage());
		
		if ( action.getAuthObject() instanceof NamePasswordAuthObject ) {
			
			try {
				User user =  login(((NamePasswordAuthObject)action.getAuthObject()).getName(),
						((NamePasswordAuthObject)action.getAuthObject()).getPassword());
				if ( user != null ) {
					List<UserInRole> roles = getUserRoles(user.getId());
					if ( roles != null && roles.size() > 0 ) {
						
						connectionState.isLoggedIn = true;
						connectionState.user = roles.get(0);
						
				    	return new GetConnectionStateResult(connectionState, nextPage);			
					}
				}
			} catch (Exception e) {
				throw new ActionException(e);
			}
			
		} else if ( action.getAuthObject() instanceof IdentAuthObject ) {
			
			try {
				User user =  loginByID(((IdentAuthObject)action.getAuthObject()).getId());
				if ( user != null ) {
					List<UserInRole> roles = getUserRoles(user.getId());
					if ( roles != null && roles.size() > 0 ) {
						
						connectionState.isLoggedIn = true;
						connectionState.user = roles.get(0);
						
				    	return new GetConnectionStateResult(connectionState, nextPage);			
					}
				}
			} catch (Exception e) {
				throw new ActionException(e);
			}
			
		}
		
		throw new ActionException("Cannot login, incorrect username or password.");
	}

	@Override
	public Class<GetConnectionStateAction> getActionType() {
		return GetConnectionStateAction.class;
	}

	@Override
	public void undo(GetConnectionStateAction action, GetConnectionStateResult result, ExecutionContext context) throws ActionException {
	}

    /**
     * User login procedure
     *
     * @param password for username
     * @return user records
     */
    public User login(String login, String password) throws Exception
    {
        PreparedStatement cstmt = null;
        Connection connection = null;
        ResultSet rs = null;
        try
        {
            connection = this.getConnection();
            cstmt = connection.prepareStatement("select * from USERS where EMAIL=?;");
            cstmt.setString(1, login);

            rs = cstmt.executeQuery();
            if ( rs.next() )
            {
                User user = new User();

                user.setName(rs.getString("NAME"));
                user.setSecondName(rs.getString("SECOND_NAME"));
                user.setEmail(new EmailAddress(rs.getString("EMAIL")));
                user.setId(rs.getInt("ID"));
               
                boolean valid = false;
                
                if (rs.getString("PASSWORD") != null && !"".equals(rs.getString("PASSWORD"))) {
                    valid = BCrypt.checkpw(password, rs.getString("PASSWORD"));
                } else if ( (password == null || "".equals(password.trim())) && ( rs.getString("PASSWORD") == null || "".equals(rs.getString("PASSWORD"))) ) {
                	valid = true;
                } else {
                	valid = false;
                }
                
                /**
                 * 
                 */
                if ( valid ) {
                	user.setSessionID( generateSessionID() );
                }
                else {
                	throw new Exception("Incorrect login or password");
                }

                return user;
            }
            return null;

        }
        catch (Exception e)
        {
            //logWithUserInfo(GetConnectionStateHandler.class.getName(), Level.SEVERE, Thread.currentThread().getName(), e);
            throw new Exception(e);
        }
        finally
        {
            close(cstmt, rs, connection);
        }
    }

     private String generateSessionID()
     {
		return UUID.uuid();
	 }

	/**
     * User login procedure
     *
     * @param id of user
     * @return user records
     */
    public User loginByID(int id) throws Exception
    {
        PreparedStatement cstmt = null;
        Connection connection = null;
        ResultSet rs = null;
        try
        {
            connection = this.getConnection();
            cstmt = connection.prepareStatement("select * from USERS where ID=?;");
            cstmt.setInt(1, id);

            rs = cstmt.executeQuery();
            if ( rs.next() )
            {
                User user = new User();

                user.setName(rs.getString("NAME"));
                user.setSecondName(rs.getString("SECOND_NAME"));
                user.setEmail(new EmailAddress(rs.getString("EMAIL")));
                user.setId(rs.getInt("ID"));

                return user;
            }
            return null;

        }
        catch (Exception e)
        {
            //logWithUserInfo(GetConnectionStateHandler.class.getName(), Level.SEVERE, Thread.currentThread().getName(), e);
            throw new Exception(e);
        }
        finally
        {
            close(cstmt, rs, connection);
        }
    }

    /**
    * Retrieve user roles in system
    *
    * @param id of user
    * @return user roles records
    */
   public List<UserInRole> getUserRoles(int id) throws Exception
   {
       PreparedStatement cstmt = null;
       Connection connection = null;
       ResultSet rs = null;
       try
       {
           connection = this.getConnection();
           cstmt = connection.prepareStatement("select * from USERS where ID=?;");
           cstmt.setInt(1, id);

           rs = cstmt.executeQuery();

           User user = new User();
           if ( rs.next() )
           {
               user.setName(rs.getString("NAME"));
               user.setSecondName(rs.getString("SECOND_NAME"));
               user.setEmail(new EmailAddress(rs.getString("EMAIL")));
               user.setId(rs.getInt("ID"));
           }
           else
           {
               throw new Exception("No user found with ID=" + id);
           }

           connection = this.getConnection();
           cstmt = connection.prepareStatement("select USER_IN_ROLE.ID_ROLE,ROLES.NAME from USER_IN_ROLE,ROLES where USER_IN_ROLE.ID_USER=? AND USER_IN_ROLE.ID_ROLE=ROLES.ID;");
           cstmt.setInt(1, id);

           rs = cstmt.executeQuery();

           List<UserInRole> userInRoles = new ArrayList<UserInRole>();
           while ( rs.next() )
           {
               UserRole role = new UserRole();

               role.setName(rs.getString("NAME"));
               role.setId(rs.getInt("ID_ROLE"));

               userInRoles.add( new UserInRole(user,  role) );
           }

           return userInRoles;

       }
       catch (Exception e)
       {
           //logWithUserInfo(GetConnectionStateHandler.class.getName(), Level.SEVERE, Thread.currentThread().getName(), e);
           throw new Exception(e);
       }
       finally
       {
           close(cstmt, rs, connection);
       }
   }


    /**
     * User update procedure
     *
     * @param userId of user
     * @param login nickname
     * @param username of user
     * @param secondName of user
     * @param email of user
     * @param roleId id of new role of user
     * @throws com.google.gwt.user.client.rpc.Exception
     */
    public void updateUser(int userId, String username, String secondName, String email, int roleId) throws Exception
    {
        PreparedStatement cstmt = null;
        Connection connection = null;
        ResultSet rs = null;
        try
        {
            connection = this.getConnection();
            cstmt = connection.prepareStatement("update USERS set NAME=?,SECOND_NAME=?,EMAIL=? where ID=?;");

            cstmt.setString(1, username);
            cstmt.setString(2, secondName);
            cstmt.setString(3, email);
            cstmt.setInt(4, userId);

            cstmt.executeUpdate();

            cstmt = connection.prepareStatement("update USER_IN_ROLE set ID_ROLE=? where ID_USER=?;");
            cstmt.setInt(1, roleId);
            cstmt.setInt(2, userId);

            cstmt.executeUpdate();
        }
        catch (Exception e)
        {
            //logWithUserInfo(GetConnectionStateHandler.class.getName(), Level.SEVERE, Thread.currentThread().getName(), e);
            throw new Exception(e);
        }
        finally
        {
            close(cstmt, rs, connection);
        }
    }

    public void deleteUser(int userId) throws Exception
    {
        PreparedStatement cstmt = null;
        Connection connection = null;
        ResultSet rs = null;
        try
        {
            connection = this.getConnection();
            cstmt = connection.prepareStatement("delete from USERS where ID=?;");

            cstmt.setInt(1, userId);
            cstmt.executeUpdate();
        }
        catch (Exception e)
        {
            //logWithUserInfo(GetConnectionStateHandler.class.getName(), Level.SEVERE, Thread.currentThread().getName(), e);
            throw new Exception(e);
        }
        finally
        {
            close(cstmt, rs, connection);
        }
    }

    public List<User> getUsers() throws Exception
    {
        PreparedStatement cstmt = null;
        Connection connection = null;
        ResultSet rs = null;
        try
        {
            connection = this.getConnection();
            cstmt = connection.prepareStatement("select * from USERS;");

            rs = cstmt.executeQuery();

            List<User> users = new ArrayList<User>();
            while ( rs.next() )
            {
                User user = new User();

                user.setName(rs.getString("NAME"));
                user.setSecondName(rs.getString("SECOND_NAME"));
                user.setEmail(new EmailAddress(rs.getString("EMAIL")));
                user.setId(rs.getInt("ID"));
                
                users.add( user );
            }

            return users;

        }
        catch (Exception e)
        {
            //logWithUserInfo(GetConnectionStateHandler.class.getName(), Level.SEVERE, Thread.currentThread().getName(), e);
            throw new Exception(e);
        }
        finally
        {
            close(cstmt, rs, connection);
        }
    }

    public List<UserRole> getRoles() throws Exception
    {
        PreparedStatement cstmt = null;
        Connection connection = null;
        ResultSet rs = null;
        try
        {
            connection = this.getConnection();
            cstmt = connection.prepareStatement("select * from ROLES;");

            rs = cstmt.executeQuery();

            List<UserRole> roles = new ArrayList<UserRole>();
            while ( rs.next() )
            {
                UserRole role = new UserRole();

                role.setName(rs.getString("NAME"));
                role.setId(rs.getInt("ID"));

                roles.add(role);
            }

            return roles;

        }
        catch (Exception e)
        {
            //logWithUserInfo(GetConnectionStateHandler.class.getName(), Level.SEVERE, Thread.currentThread().getName(), e);
            throw new Exception(e);
        }
        finally
        {
            close(cstmt, rs, connection);
        }
    }

    public List<UserInRole> getUsersRoles() throws Exception 
    {
        PreparedStatement cstmt = null;
        Connection connection = null;
        ResultSet rs = null;
        try
        {
            connection = this.getConnection();
            cstmt = connection.prepareStatement("select USERS.*,USER_IN_ROLE.ID_ROLE,ROLES.NAME AS ROLE_NAME from USER_IN_ROLE,ROLES,USERS where USER_IN_ROLE.ID_USER=USERS.ID AND USER_IN_ROLE.ID_ROLE=ROLES.ID;");

            rs = cstmt.executeQuery();

            List<UserInRole> usersInRoles = new ArrayList<UserInRole>();
            while ( rs.next() )
            {
                User user = new User();

                user.setName(rs.getString("NAME"));
                user.setSecondName(rs.getString("SECOND_NAME"));
                user.setEmail(new EmailAddress(rs.getString("EMAIL")));
                user.setId(rs.getInt("ID"));


                UserRole role = new UserRole();

                role.setName(rs.getString("ROLE_NAME"));
                role.setId(rs.getInt("ID_ROLE"));

                usersInRoles.add(new UserInRole(user, role));
            }

            return usersInRoles;

        }
        catch (Exception e)
        {
            //logWithUserInfo(GetConnectionStateHandler.class.getName(), Level.SEVERE, Thread.currentThread().getName(), e);
            throw new Exception(e);
        }
        finally
        {
            close(cstmt, rs, connection);
        }
    }	

}
