package org.clematis.web.elearning.shared.domain;


/**
 */
public class Event extends BasicEntity
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7682263486367070306L;
	private String name;
    private String description;
    private StudentsClass studentsClass;
    private int teacherId;
    private int day;
    private int timeInterval;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public StudentsClass getStudentsClass() 
    {
		return studentsClass;
	}

	public void setStudentsClass(StudentsClass studentsClass) 
	{
		this.studentsClass = studentsClass;
	}

	public int getTeacherId()
    {
        return teacherId;
    }

    public void setTeacherId(int teacherId)
    {
        this.teacherId = teacherId;
    }

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getTimeInterval() {
		return timeInterval;
	}

	public void setTimeInterval(int timeInterval) {
		this.timeInterval = timeInterval;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + day;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((studentsClass == null) ? 0 : studentsClass.hashCode());
		result = prime * result + teacherId;
		result = prime * result + timeInterval;
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
		Event other = (Event) obj;
		if (day != other.day)
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (studentsClass == null) {
			if (other.studentsClass != null)
				return false;
		} else if (!studentsClass.equals(other.studentsClass))
			return false;
		if (teacherId != other.teacherId)
			return false;
		if (timeInterval != other.timeInterval)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Event [name=" + name + ", description=" + description
				+ ", studentsClass=" + studentsClass + ", teacherId="
				+ teacherId + ", day=" + day + ", timeInterval=" + timeInterval
				+ "]";
	}

}
