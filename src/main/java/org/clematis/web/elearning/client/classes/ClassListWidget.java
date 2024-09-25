package org.clematis.web.elearning.client.classes;

import org.clematis.web.elearning.client.classes.ClassSelectedEvent.ClassSelectedHandler;
import org.clematis.web.elearning.client.ui.PinWidget;
import org.clematis.web.elearning.shared.domain.StudentsClass;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class ClassListWidget extends Composite {

	private static ClassLightListWidgetUiBinder uiBinder = GWT
			.create(ClassLightListWidgetUiBinder.class);

	interface ClassLightListWidgetUiBinder extends
			UiBinder<Widget, ClassListWidget> {
	}
	
	interface MyStyle extends CssResource {
		String selected();

		String unselected();
	}	

	@UiField MyStyle style;		
	
	@UiField Label name;
	@UiField PinWidget pin;
	@UiField HorizontalPanel holder;
	
	private HandlerManager handlerManager;
	private StudentsClass studentsClass;
	private HandlerRegistration pinClickRegistration;
	
	public ClassListWidget(StudentsClass studentsClass) {
		initWidget(uiBinder.createAndBindUi(this));

		handlerManager = new HandlerManager(this);
		
		this.studentsClass = studentsClass;
		
        if ( studentsClass != null ) {
			
			name.setText( studentsClass.getName() );
			
		}	
        pinClickRegistration = pin.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent e) {
				onPinClick();
			}
		});
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
		holder.setStyleName(selected ? style.selected() : style.unselected());
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
