package org.clematis.web.elearning.client.classes;

import org.clematis.web.elearning.shared.domain.StudentsClass;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class ClassDescriptionWidget extends Composite {

	private static ClassDescriptionWidgetUiBinder uiBinder = GWT
			.create(ClassDescriptionWidgetUiBinder.class);

	interface ClassDescriptionWidgetUiBinder extends
			UiBinder<Widget, ClassDescriptionWidget> {
	}

	public ClassDescriptionWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@UiField FlexTable info;	

	public void setStudentClass(StudentsClass studentClass) {
		
		if (studentClass!=null) {
			info.setWidget(0, 0, new Label("Название"));
			info.setWidget(0, 1, new Label(studentClass.getName()));
			
			info.setWidget(1, 0, new Label("UID"));
			info.setWidget(1, 1, new Label(studentClass.getUID()));
		}	
	}

}
