package org.clematis.web.elearning.client.fields;

/**
 * The button represents on of the choices in business process task form.
 * @author vlakadm
 *
 */
public class SubmitTask extends SubmitField {
	private static final long serialVersionUID = -5544277838562163279L;
	/**
	 * This is a key of the node describing controls of the form
	 * representing the task. 
	 */
	private String taskDefinitionKey;
	/**
	 * This is a key of the node containing process definition.
	 */
	private String processDefinitionKey;

	/** For serialization */
	protected SubmitTask() {
	}

	public SubmitTask(String id, String caption, int row, int col, boolean closeOnSuccess, String taskDefinitionKey, String processDefinitionKey) {
		this.type = Type.SUBMIT;
		this.id = id;
		this.captionForm = caption;
		this.captionGrid = caption;
		this.row = row;
		this.column = col;
		this.closeOnSuccess = closeOnSuccess;
		this.taskDefinitionKey = taskDefinitionKey;
		this.processDefinitionKey = processDefinitionKey;
	}

	public String getTaskDefinitionKey() {
		return taskDefinitionKey;
	}

	public void setTaskDefinitionKey(String taskDefinitionKey) {
		this.taskDefinitionKey = taskDefinitionKey;
	}

	public String getProcessDefinitionKey() {
		return processDefinitionKey;
	}

	public void setProcessDefinitionKey(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
	}

}
