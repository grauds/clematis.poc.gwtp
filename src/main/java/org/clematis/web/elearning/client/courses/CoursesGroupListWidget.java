package org.clematis.web.elearning.client.courses;


import java.util.ArrayList;
import java.util.List;

import org.clematis.web.elearning.client.courses.CourseSelectedEvent.CourseSelectedHandler;
import org.clematis.web.elearning.client.courses.CoursesSelectedEvent.CoursesSelectedHandler;
import org.clematis.web.elearning.client.ui.PinWidget;
import org.clematis.web.elearning.shared.domain.Course;
import org.clematis.web.elearning.shared.domain.CoursesGroup;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class CoursesGroupListWidget extends Composite implements HasHandlers {

	private static CoursesGroupListWidgetUiBinder uiBinder = GWT
			.create(CoursesGroupListWidgetUiBinder.class);

	interface CoursesGroupListWidgetUiBinder extends
			UiBinder<Widget, CoursesGroupListWidget> {
	}
	
	@UiField Label name;
	@UiField Label description;
	@UiField VerticalPanel coursesPanel;
	@UiField PinWidget pin;	
	
	private HandlerManager handlerManager;
	private CoursesGroup coursesGroup;
	private List<CourseListWidget> courseWidgets = new ArrayList<CourseListWidget>();

	public CoursesGroupListWidget(CoursesGroup coursesGroup, List<Course> courses) {
		initWidget(uiBinder.createAndBindUi(this));
		
		this.coursesGroup = coursesGroup;
		this.handlerManager = new HandlerManager(this);
		
        if ( coursesGroup != null ) {
			
			name.setText( coursesGroup.getName() );
			description.setText( coursesGroup.getDescription() );
			
			for ( Course course : coursesGroup.getCourses() ) {
				CourseListWidget courseListWidget = new CourseListWidget(course);
				courseWidgets.add(courseListWidget);
				coursesPanel.add(courseListWidget);
				if (courses!=null && courses.contains(course)) {
					courseListWidget.select(true);
					select(true);
				}
			}
			
		}
	}
	
	public CoursesGroupListWidget(CoursesGroup coursesGroup) {
		this(coursesGroup, null);
	}

	public void select(boolean selected) {
		pin.setSelected(selected);
	}
	
	public boolean isSelected() {
		return pin.isSelected();
	}
	
	@UiHandler("pin")
	protected void pinClicked(ClickEvent e) {
		select(pin.isSelected());
		
		for ( int i =0; i < coursesPanel.getWidgetCount(); i++) {
			if ( coursesPanel.getWidget(i) instanceof CourseListWidget ) {
				((CourseListWidget) coursesPanel.getWidget(i)).select(pin.isSelected());
			}
		}

		CoursesSelectedEvent.fire(this, coursesGroup, pin.isSelected());
	}
	
	@Override
    public void fireEvent(GwtEvent<?> event) {
        handlerManager.fireEvent(event);
    }
	
	public List<HandlerRegistration> addCourseSelectedEventHandler(CourseSelectedHandler handler) {
        List<HandlerRegistration> registrations = new ArrayList<HandlerRegistration>();
        
        for (CourseListWidget courseListWidget:courseWidgets) {
        	registrations.add(courseListWidget.addCourseSelectedEventHandler(handler));
        }
        
        return registrations;
    }
	
    public HandlerRegistration addCoursesSelectedEventHandler(CoursesSelectedHandler handler) {
        return handlerManager.addHandler(CoursesSelectedEvent.getType(), handler);
    }

	public CoursesGroup getCoursesGroup() {
		return coursesGroup;
	}

	public List<CourseListWidget> getCourseWidgets() {
		return courseWidgets;
	}
  
}
