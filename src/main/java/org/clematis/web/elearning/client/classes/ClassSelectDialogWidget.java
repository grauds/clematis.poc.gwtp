package org.clematis.web.elearning.client.classes;

import java.util.ArrayList;
import java.util.List;

import org.clematis.web.elearning.client.classes.ClassSelectedEvent.ClassSelectedHandler;
import org.clematis.web.elearning.client.general.HintWidget;
import org.clematis.web.elearning.shared.domain.StudentsClass;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ClassSelectDialogWidget extends Composite implements ClassSelectedHandler {

	private static ClassSelectDialogWidgetUiBinder uiBinder = GWT.create(ClassSelectDialogWidgetUiBinder.class);

	interface ClassSelectDialogWidgetUiBinder extends UiBinder<Widget, ClassSelectDialogWidget> {
	}
	
	 @UiField VerticalPanel classSelector;
	 @UiField HintWidget hintWidget;
	 
	 private StudentsClass selectedClass;
	 private List<ClassListWidget> classListWidgets = new ArrayList<ClassListWidget>();

	public ClassSelectDialogWidget(String hint, List<StudentsClass> classes) {
		initWidget(uiBinder.createAndBindUi(this));
		
		for (StudentsClass studentClass:classes) {
			ClassListWidget classListWidget = new ClassListWidget(studentClass);
			classListWidget.addClassSelectedEventHandler(this);
			classListWidgets.add(classListWidget);
			classSelector.add(classListWidget);
		}
		
		hintWidget.setBody(hint);
	}

	@Override
	public void onClassSelected(ClassSelectedEvent event) {
		for (ClassListWidget classListWidget:classListWidgets) {
			if (!classListWidget.getStudentsClass().equals(event.getStudentClass())) {
				classListWidget.select(false);				
			}
		}
	    this.selectedClass = event.getStudentClass();
	}

	public StudentsClass getSelectedClass() {
		return selectedClass;
	}

	
}
