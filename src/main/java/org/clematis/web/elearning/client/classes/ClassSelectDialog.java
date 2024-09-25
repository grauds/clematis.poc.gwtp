package org.clematis.web.elearning.client.classes;

import java.util.List;

import org.clematis.web.elearning.shared.domain.StudentsClass;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ClassSelectDialog extends DialogBox {

	private ClassSelectDialogWidget classSelectDialogWidget;

	public ClassSelectDialog(String hint, List<StudentsClass> classes) {
		super();
		
		setGlassEnabled(true);
		setText("Диалог выбора класса");
		
		VerticalPanel dialogContents = new VerticalPanel();
	    dialogContents.setSpacing(4);
	    setWidget(dialogContents);
	    
	    classSelectDialogWidget = new ClassSelectDialogWidget(hint, classes);
	    classSelectDialogWidget.setHeight("400px");
	    dialogContents.add(classSelectDialogWidget);

	    // Add a close button at the bottom of the dialog
	    HorizontalPanel buttons = new HorizontalPanel();
	    buttons.setSpacing(5);
	    buttons.setHeight("30px");
	    
	    Button chooseButton = new Button("Выбрать класс", new ClickHandler() {
	          public void onClick(ClickEvent event) {
	             onChooseClass();
	          }
	        });
	    chooseButton.setStyleName("bluebutton");
	    buttons.add(chooseButton);
	    
	    Button closeButton = new Button("Закрыть", new ClickHandler() {
	          public void onClick(ClickEvent event) {
	        	  hide();
	          }
	        });
	    closeButton.setStyleName("greybutton");
	    buttons.add(closeButton);	    
	    
	    dialogContents.add(buttons);
	    dialogContents.setCellHorizontalAlignment(closeButton, HasHorizontalAlignment.ALIGN_RIGHT);

	}

	public void onChooseClass() {
		hide();
	}
	
	public StudentsClass getSelectedClass() {
		if ( classSelectDialogWidget != null ) {
			return classSelectDialogWidget.getSelectedClass();
		}
		return null;
	}
}
