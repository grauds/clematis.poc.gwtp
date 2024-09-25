package org.clematis.web.elearning.shared.domain;

/**
 */
public class UserInRole extends BasicEntity
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 6367985428809942196L;
	private User user;
    private UserRole userRole;

    public UserInRole(User user, UserRole userRole)
    {
        this.user = user;
        this.userRole = userRole;
    }

    public UserInRole()
    {
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public UserRole getUserRole()
    {
        return userRole;
    }

    public void setUserRole(UserRole userRole)
    {
        this.userRole = userRole;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        UserInRole that = (UserInRole) o;

        if (user != null ? !user.equals(that.user) : that.user != null) return false;
        if (userRole != null ? !userRole.equals(that.userRole) : that.userRole != null) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = super.hashCode();
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (userRole != null ? userRole.hashCode() : 0);
        return result;
    }

    @Override
    public String toString()
    {
        return "UserInRole{" +
                "user=" + user +
                ", userRole=" + userRole +
                '}';
    }
}
