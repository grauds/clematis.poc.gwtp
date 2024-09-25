package org.clematis.web.elearning.shared.domain;

public class IdentAuthObject extends AuthObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1934720485881421765L;

	private int id;

	public IdentAuthObject(int id) {
		setId(id);
	}
	
	public IdentAuthObject() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		IdentAuthObject other = (IdentAuthObject) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "IdentAuthObject [id=" + id + "]";
	}
	
}
