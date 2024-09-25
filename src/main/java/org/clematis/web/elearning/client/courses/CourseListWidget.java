package org.clematis.web.elearning.client.courses;

import org.clematis.web.elearning.client.courses.CourseSelectedEvent.CourseSelectedHandler;
import org.clematis.web.elearning.client.ui.PinWidget;
import org.clematis.web.elearning.shared.domain.Course;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class CourseListWidget extends Composite implements HasHandlers  {

	private static CourseListWidgetUiBinder uiBinder = GWT
			.create(CourseListWidgetUiBinder.class);

	interface CourseListWidgetUiBinder extends UiBinder<Widget, CourseListWidget> {
	}
	
	@UiField Label name;
	@UiField Label description;
	@UiField PinWidget pin;
	
	private HandlerManager handlerManager;
	private Course course;
	private HandlerRegistration pinClickRegistration;	

	public CourseListWidget(final Course course) {
		initWidget(uiBinder.createAndBindUi(this));

		handlerManager = new HandlerManager(this);
		
		this.course = course;
		
		if ( course != null ) {
			
			name.setText( course.getName() );
			description.setText( course.getDescription() );
			
		}
		
		pinClickRegistration = pin.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent e) {
				onPinClick();
			}
		});
	}
	
	public CourseListWidget(Course course, boolean b) {
		this(course);
		pin.setVisible(b);
	}

	public void onPinClick() {
		select(pin.isSelected());
		CourseSelectedEvent.fire(this, course, pin.isSelected());
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

    public HandlerRegistration addCourseSelectedEventHandler(CourseSelectedHandler handler) {
        return handlerManager.addHandler(CourseSelectedEvent.getType(), handler);
    }

	public Course getCourse() {
		return course;
	}

	@Override
	protected void onLoad() {
		
		super.onLoad();
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
