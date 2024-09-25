package org.clematis.web.elearning.shared.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class BasicEntity implements Serializable
{
    private static final long serialVersionUID = 8994261103660953454L;
    private int id = -1;
    private Map<String, Object> properties = new HashMap<String, Object>();

    public String getIdString()
    {
        return getPrefix() + getId();
    }

    public void setIdString(String id)
    {
        if (id != null && !id.trim().equals(""))
        {
            if (id.startsWith(getPrefix()))
            {
                try
                {
                    this.setId(Integer.parseInt(id.substring(getPrefix().length())));
                }
                catch (NumberFormatException ex)
                {
                    throw new IllegalArgumentException("Prefix provided does not match " + getPrefix() + " - " + id);
                }
            } else
            {
                throw new IllegalArgumentException("Prefix provided does not match " + getPrefix() + " - " + id);
            }
        } else
        {
            throw new IllegalArgumentException("Empty id provided instead of one with prefix " + getPrefix());
        }
    }

    public String getPrefix()
    {
        return "entity";
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BasicEntity that = (BasicEntity) o;

        if (id != that.id) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        return id;
    }

    @Override
    public String toString()
    {
        return "BasicEntity{" +
                "id=" + getId() +
                '}';
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }


    public void addProperty(String key, Object property)
    {
        if ( key != null && property != null )
        {
            properties.put( key, property );
        }
    }

    public void addProperties(Map<String, Object> properties)
    {
        if (  properties != null )
        {
            this.properties.putAll( properties );
        }
    }

    public Object getProperty(String key)
    {
        if ( key != null )
        {
            return properties.get( key );
        }
        return null;
    }

    public void removeProperty(String key)
    {
        if ( key != null)
        {
            properties.remove(key);
        }
    }

    public Map<String, Object> getProperties()
    {
        return properties;
    }
    
}