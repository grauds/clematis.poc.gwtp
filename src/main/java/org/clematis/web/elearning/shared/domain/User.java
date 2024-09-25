package org.clematis.web.elearning.shared.domain;

/**
 */
public class User extends BasicEntity
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 8611924010743217241L;
	private String name;
    private String secondName;
    private EmailAddress email;
	private String sessionID;

    public String getName()
    {
       return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getSecondName()
    {
        return secondName;
    }

    public void setSecondName(String secondName)
    {
        this.secondName = secondName;
    }

    public EmailAddress getEmail()
    {
        return email;
    }

    public void setEmail(EmailAddress email)
    {
        this.email = email;
    }


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((secondName == null) ? 0 : secondName.hashCode());
		result = prime * result
				+ ((sessionID == null) ? 0 : sessionID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (secondName == null) {
			if (other.secondName != null)
				return false;
		} else if (!secondName.equals(other.secondName))
			return false;
		if (sessionID == null) {
			if (other.sessionID != null)
				return false;
		} else if (!sessionID.equals(other.sessionID))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", secondName=" + secondName + ", email="
				+ email + ", sessionID=" + sessionID + "]";
	}

	public void setSessionID(String sessionID) 
	{
		this.sessionID = sessionID;
	}

	public String getSessionID()
	{
		return sessionID;
	}
	
	
}
