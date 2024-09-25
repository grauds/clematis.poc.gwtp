package org.clematis.web.elearning.client.fields;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;


/**
 * This class is a container for data describing a single block of elements.
 * It is also used to describe a table-form.
 */
public class BlockInfo implements IsSerializable {
	private String title;
	private List<Field> fields;
	
	public List<Field> getFields() {
		if (fields == null) {
			fields = new ArrayList<Field>();
		}
		return fields;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
}