package org.clematis.web.elearning.client.fields;

import java.util.List;

public class LinkField extends Field {
	private static final long serialVersionUID = 6704137776476264844L;

	/**
	 * Stores template for the link. To get the link each item
	 * in the parameters list should be placed instead of corresponding
	 * {X} entry. Thus, for the first parameter {0} should be replaced etc. 
	 */
	private String linkTemplate;
	private List<String> parameters;

	/** Required for serialization */
	protected LinkField() {
	}

	public LinkField(String name, String linkTemplate, List<String> params) {
		this.type = Type.LINK;
		this.captionForm = this.captionGrid = name;
		this.linkTemplate = linkTemplate;
		this.parameters = params;
	}

	public String getLinkTemplate() {
		return linkTemplate;
	}

	public List<String> getParameters() {
		return parameters;
	}
}
