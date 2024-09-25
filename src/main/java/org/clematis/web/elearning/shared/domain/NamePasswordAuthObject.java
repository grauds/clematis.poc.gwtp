package org.clematis.web.elearning.shared.domain;

public class NamePasswordAuthObject extends AuthObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6539604921888486680L;

	private String name;
	private String password;
	
	public NamePasswordAuthObject(String userName, String userPassword) {
	     setName(userName);
	     setPassword(userPassword);
	}
	
	
	
	public NamePasswordAuthObject() {
		super();
	}



	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NamePasswordAuthObject other = (NamePasswordAuthObject) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "NamePasswordAuthObject [name=" + name + ", password="
				+ password + "]";
	}
	
	
}
