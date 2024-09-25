package org.clematis.web.elearning.shared.domain;

/**
 */
public class StudentsClass extends UserGroup
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 4612475048121877591L;
	private User teacher;
	private String UID;

    public User getTeacher()
    {
        return teacher;
    }

    public void setTeacher(User teacher)
    {
        this.teacher = teacher;
    }

	public String getUID() {
		return UID;
	}

	public void setUID(String uID) {
		UID = uID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((UID == null) ? 0 : UID.hashCode());
		result = prime * result + ((teacher == null) ? 0 : teacher.hashCode());
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
		StudentsClass other = (StudentsClass) obj;
		if (UID == null) {
			if (other.UID != null)
				return false;
		} else if (!UID.equals(other.UID))
			return false;
		if (teacher == null) {
			if (other.teacher != null)
				return false;
		} else if (!teacher.equals(other.teacher))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "StudentsClass [teacher=" + teacher + ", UID=" + UID + "]";
	}

	
}
