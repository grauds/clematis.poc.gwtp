package org.clematis.web.elearning.server.handlers.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.clematis.web.elearning.client.login.actions.RegisterAction;
import org.clematis.web.elearning.client.login.actions.RegisterResult;
import org.clematis.web.elearning.server.BasicHandler;
import org.clematis.web.elearning.server.MailSender;
import org.clematis.web.elearning.server.util.BCrypt;
import org.clematis.web.elearning.shared.domain.EmailAddress;
import org.clematis.web.elearning.shared.domain.User;

import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

public class RegisterHandler extends BasicHandler implements ActionHandler<RegisterAction, RegisterResult>  {


	@Override
	public RegisterResult execute(RegisterAction action, ExecutionContext context) throws ActionException {
		
		try {
			User user = register(action.getPassword(), action.getUsername(), action.getSecondName(), action.getPhone(), action.getEmail());
			if ( user != null ) {
				MailSender.sendMail(user.getEmail(), "ELP: Registration successful", "Congratulations! You have registered to ELP Prototype. Your password is: " + action.getPassword());
			}
			return new RegisterResult(user);
		} catch (Exception e) {

			throw new ActionException(e);
		}
	}

	@Override
	public Class<RegisterAction> getActionType() {
		return RegisterAction.class;
	}

	@Override
	public void undo(RegisterAction action, RegisterResult result, ExecutionContext context) throws ActionException {
		
	}

    /**
     * User register procedure
     *
     * @param username of user
     * @param secondName of user
     * @param email of user
     * @return user record
     * @throws com.google.gwt.user.client.rpc.Exception
     */
    public User register(String password, String username, String secondName, String email, String phone, int roleId) throws Exception
    {
        PreparedStatement cstmt = null;
        Connection connection = null;
        ResultSet rs = null;
        try
        {
            connection = this.getConnection();
            cstmt = connection.prepareStatement("insert into USERS(NAME,SECOND_NAME,EMAIL,PASSWORD,PHONE) values(?,?,?,?,?);");

            cstmt.setString(1, username);
            cstmt.setString(2, secondName);
            cstmt.setString(3, email);
            cstmt.setString(4, BCrypt.hashpw(password, BCrypt.gensalt()));
            cstmt.setString(5, phone);

            cstmt.executeUpdate();

            cstmt = connection.prepareStatement("insert into USER_IN_ROLE(ID_USER, ID_ROLE) select ID, ? from USERS where EMAIL=?;");
            cstmt.setInt(1, roleId);
            cstmt.setString(2, email);

            cstmt.executeUpdate();

            cstmt = connection.prepareStatement("select ID from USERS where EMAIL=?;");
            cstmt.setString(1, email);

            rs = cstmt.executeQuery();
            if ( rs.next() )
            {
                User user = new User();

                user.setName(username);
                user.setSecondName(secondName);
                user.setEmail(new EmailAddress(email));
                user.setId( rs.getInt("ID"));

                return user;
            }
            return null;

        }
        catch (Exception e)
        {
           // logWithUserInfo(GetConnectionStateHandler.class.getName(), Level.SEVERE, Thread.currentThread().getName(), e);
            throw new Exception(e);
        }
        finally
        {
            close(cstmt, rs, connection);
        }
    }
    
    /**
     * User register procedure
     *
     * @param username of user
     * @param secondName of user
     * @param email of user
     * @return user record
     * @throws com.google.gwt.user.client.rpc.Exception
     */
    public User register(String password, String username, String secondName, String phone, String email) throws Exception
    {
        PreparedStatement cstmt = null;
        Connection connection = null;
        ResultSet rs = null;
        try
        {
            connection = this.getConnection();
            cstmt = connection.prepareStatement("insert into USERS(NAME,SECOND_NAME,EMAIL,PASSWORD,PHONE) values(?,?,?,?,?);");

            cstmt.setString(1, username);
            cstmt.setString(2, secondName);
            cstmt.setString(3, email);
            cstmt.setString(4, BCrypt.hashpw(password, BCrypt.gensalt()));
            cstmt.setString(5, phone);

            cstmt.executeUpdate();

            cstmt = connection.prepareStatement("insert into USER_IN_ROLE(ID_USER, ID_ROLE) select ID, 3 from USERS where EMAIL=?;");
            cstmt.setString(1, email);

            cstmt.executeUpdate();

            cstmt = connection.prepareStatement("select ID from USERS where EMAIL=?;");
            cstmt.setString(1, email);

            rs = cstmt.executeQuery();
            if ( rs.next() )
            {
                User user = new User();

                user.setName(username);
                user.setSecondName(secondName);
                user.setEmail(new EmailAddress(email));
                user.setId( rs.getInt("ID") );

                return user;
            }
            return null;

        }
        catch (Exception e)
        {
         //   logWithUserInfo(GetConnectionStateHandler.class.getName(), Level.SEVERE, Thread.currentThread().getName(), e);
            throw new Exception(e);
        }
        finally
        {
            close(cstmt, rs, connection);
        }
    }

}
