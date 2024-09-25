package org.clematis.web.elearning.shared.domain;

import java.util.LinkedList;
import java.util.List;

/**
 */
public class UserGroup extends BasicEntity
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 2799748888857312346L;
	private String name;
    private List<User> users = new LinkedList<User>();

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void add(User user)
    {
        if ( user != null )
        {
            users.add( user );
        }
    }

    public List<User> getUsers()
    {
        return users;
    }
    
    public void setUsers(List<User> users) {
		this.users = users;
	}

	@Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        UserGroup userGroup = (UserGroup) o;

        if (name != null ? !name.equals(userGroup.name) : userGroup.name != null) return false;
        if (users != null ? !users.equals(userGroup.users) : userGroup.users != null) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (users != null ? users.hashCode() : 0);
        return result;
    }

    @Override
    public String toString()
    {
        return "UserGroup{" +
                "name='" + name + '\'' +
                ", users=" + users +
                '}';
    }
}
