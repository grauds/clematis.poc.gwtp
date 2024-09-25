package org.clematis.web.elearning.shared.domain;

/**
 */
public class UserRole extends BasicEntity
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -148486271267704745L;
	private String name;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        UserRole userRole = (UserRole) o;

        if (name != null ? !name.equals(userRole.name) : userRole.name != null) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString()
    {
        return "UserRole{" +
                "name='" + name + '\'' +
                '}';
    }
}
