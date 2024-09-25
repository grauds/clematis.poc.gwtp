package org.clematis.web.elearning.client.teachers.courses;

import java.util.ArrayList;
import java.util.List;

import org.clematis.web.elearning.client.ELP;
import org.clematis.web.elearning.client.courses.CourseListWidget;
import org.clematis.web.elearning.client.courses.CoursesGroupListWidget;
import org.clematis.web.elearning.client.general.ShowErrorMessageEvent;
import org.clematis.web.elearning.shared.domain.Course;
import org.clematis.web.elearning.shared.domain.CoursesGroup;

import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.ViewImpl;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class SelectCoursesView extends ViewImpl implements SelectCoursesPresenter.MyView {

	private final Widget widget;

	public interface Binder extends UiBinder<Widget, SelectCoursesView> {
	}
	
	@UiField VerticalPanel coursesGroupsList;
	@UiField HTMLPanel messagePanel;
	@UiField Button buttonSave;
	
	private final DispatchAsync dispatcher;
	private final EventBus eventBus;
	
	private final List<CoursesGroupListWidget> coursesGroupsWidgets = new ArrayList<CoursesGroupListWidget>();

	@Inject
	public SelectCoursesView(final Binder binder, final DispatchAsync dispatcher, final EventBus eventBus) {
		widget = binder.createAndBindUi(this);
		
		this.dispatcher = dispatcher;
		this.eventBus = eventBus;		
	}

	@Override
	public Widget asWidget() {
		return widget;
	}

	@Override
	public void showCourses(List<CoursesGroup> coursesGroups, List<Course> courses) {
        coursesGroupsList.clear();
        coursesGroupsWidgets.clear();
        /**
         * Show new tree
         */
        for ( CoursesGroup coursesGroup : coursesGroups ) {
        	/**
        	 * Create widget for group
        	 */
        	CoursesGroupListWidget coursesGroupListWidget = new CoursesGroupListWidget(coursesGroup, courses);
        	/**
        	 * Add to list of widgets
        	 */
        	coursesGroupsWidgets.add(coursesGroupListWidget);
        	/**
        	 * Add to layout
        	 */
        	coursesGroupsList.add(coursesGroupListWidget);
        }
	}
	
	@Override
	public void showLoader(boolean visible) {
		messagePanel.setVisible(visible);	
	}

	@Override
	public void clearCourses() {
        coursesGroupsList.clear();
	}	
	
	@UiHandler("buttonSave")
	protected void saveButton(ClickEvent e) {
		buttonSave.setEnabled(false);
		dispatcher.execute(new SelectCoursesAction(ELP.getConnectionState().user.getUser().getId(), getSelectedCourses()), new AsyncCallback<SelectCoursesResult>() {
			
			@Override
			public void onFailure(Throwable caught) {
				buttonSave.setEnabled(true);
				eventBus.fireEvent(new ShowErrorMessageEvent("Ошибка сохранения набора курсов ", caught));
			}

			@Override
			public void onSuccess(SelectCoursesResult result) {
				buttonSave.setEnabled(true); 
			}
		});	
	}	

	private List<Course> getSelectedCourses() {
		List<Course> courses = new ArrayList<Course>();
		
		for (CoursesGroupListWidget coursesGroupListWidget : coursesGroupsWidgets) {
			for (CourseListWidget courseListWidget : coursesGroupListWidget.getCourseWidgets()) {
				if (courseListWidget.isSelected()) {
					courses.add(courseListWidget.getCourse());
				}
			}
		}
		
		return courses;
	}

}
