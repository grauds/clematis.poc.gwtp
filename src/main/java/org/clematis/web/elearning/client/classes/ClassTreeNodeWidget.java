package org.clematis.web.elearning.client.classes;

import org.clematis.web.elearning.client.classes.ClassSelectedEvent.ClassSelectedHandler;
import org.clematis.web.elearning.client.students.StudentListWidget;
import org.clematis.web.elearning.client.ui.PinWidget;
import org.clematis.web.elearning.shared.domain.StudentsClass;
import org.clematis.web.elearning.shared.domain.User;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class ClassTreeNodeWidget extends Composite {

	private static ClassTreeNodeWidgetUiBinder uiBinder = GWT.create(ClassTreeNodeWidgetUiBinder.class);

	interface ClassTreeNodeWidgetUiBinder extends UiBinder<Widget, ClassTreeNodeWidget> {
	}

	public ClassTreeNodeWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@UiField Label name;
	@UiField PinWidget pin;
	
	private HandlerManager handlerManager;
	private StudentsClass studentsClass;
	private HandlerRegistration pinClickRegistration;
	
	@UiField FlowPanel studentsPanel;

	public ClassTreeNodeWidget(final StudentsClass studentsClass) {
		initWidget(uiBinder.createAndBindUi(this));

		handlerManager = new HandlerManager(this);
		
		this.studentsClass = studentsClass;
		
		if ( studentsClass != null ) {
			
			name.setText( studentsClass.getName() );

			for (User user:studentsClass.getUsers()) {
				studentsPanel.add(new StudentListWidget(user, false));	
			}
			
		}
		
		pinClickRegistration = pin.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent e) {
				onPinClick();
			}
		});
	}
	
	public ClassTreeNodeWidget(final StudentsClass studentsClass, boolean b) {
		this(studentsClass);
		pin.setVisible(b);
	}

	public void onPinClick() {
		select(pin.isSelected());
		ClassSelectedEvent.fire(this, studentsClass, pin.isSelected());
	}			

	@Override
    public void fireEvent(GwtEvent<?> event) {
        handlerManager.fireEvent(event);
    }	

	public void select(boolean selected) {
		pin.setSelected(selected);
	}
	
	public boolean isSelected() {
		return pin.isSelected();
	}

    public HandlerRegistration addClassSelectedEventHandler(ClassSelectedHandler handler) {
        return handlerManager.addHandler(ClassSelectedEvent.getType(), handler);
    }
    
	public StudentsClass getStudentsClass() {
		return studentsClass;
	}

	@Override
	protected void onUnload() {
		if( pinClickRegistration != null ) {
			pinClickRegistration.removeHandler();
			pinClickRegistration = null;
		}
		super.onUnload();
	}
}
