package org.clematis.web.elearning.client.fields;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * This class represents a record shown in
 * a filtered table. The record might be a transaction from
 * TransLedger or a analytics from analytics etc.
 * @author vlakadm
 *
 */
public class Record implements IsSerializable  {
	/**
	 * String representation of Key<?> of the current record.
	 */
	public String key;
	/**
	 * Container for properties.
	 */
	private HashMap<String, Serializable> values;
	/**
	 * This flag is used in client grids to mark
	 * records which were changed by a user.
	 */
	public boolean isChanged;
	/**
	 * This flag is used on the client to show
	 * the record should be deleted
	 */
	public boolean toDelete;
	/**
	 * Status of the object. It's shown in the
	 * corresponding field in the grid.
	 */
	public String status;

	protected Record() {
	}
	
	public Record(String key) {
		this.key = key == null ? null : new String(key);
		values = new HashMap<String, Serializable>();
		isChanged = false;
		toDelete = false;
	}

	public String getKey() {
		return key;
	}

	public void setValue(String key, String value) {
		if (values == null) {
			values = new HashMap<String, Serializable>();
		}
		values.put(key, value);
	}

	public String getValueString(String key) {
		String value = null;
		if (values != null && key != null) {
			Serializable result = values.get(key);
			if (result != null && result instanceof String) {
				value = (String)result;
			}
		}
		/*
		 * To support attachemnt's link to nodes we check if predefined
		 * field Attachment.keyNodeId is found then return it otherwise
		 * in case Attachment.keyNodeId is requested and it's not found
		 * assume node's key is stored in this.key field.
		 */
//		if (value == null && Attachment.keyNodeId.equals(key)) {
//			value = this.key;
//		}
		return value;
	}

	/**
	 * Creates a clone of the object.
	 * @return
	 */
	public Record makeClone() {
		Record dest = new Record(key);
		copy(this, dest);
		return dest;
	}

	protected static final void copy(Record source, Record dest) {
		for (Entry<String, Serializable> item : source.values.entrySet()) {
			Serializable value = item.getValue();
			if (value == null) {
				dest.values.put(item.getKey(), null);
			} else if (value instanceof String) {
				String newValue = new String((String) value);
				dest.values.put(item.getKey(), newValue);
			} else {
				// In other cases just copy the reference.... 
				// TODO: another types are not supported in this version!!!!!!!!!!!!!!!!!!!!!!!!!!
				// The clue might be in using ClientSerializationStreamReader
				// http://code.google.com/p/google-web-toolkit/source/browse/trunk/user/src/com/google/gwt/user/client/rpc/impl/ClientSerializationStreamReader.java deserialize
				// or readObject from AbstractSerializationStreamReader
				dest.values.put(item.getKey(), value);
			}
		}
		dest.isChanged = source.isChanged;
		dest.toDelete = source.toDelete;
		dest.status = source.status == null ? null : new String(source.status);
	}

	public Set<Entry<String, Serializable>> getValuesSet() {
		return values.entrySet();
	}

	public HashMap<String, Serializable> getValues() {
		return values;
	}

	public void setValues(HashMap<String, Serializable> map) {
		values = map;
	}
}
