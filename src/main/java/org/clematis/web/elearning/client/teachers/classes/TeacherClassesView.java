package org.clematis.web.elearning.client.teachers.classes;

import java.util.ArrayList;
import java.util.List;

import org.clematis.web.elearning.client.ELP;
import org.clematis.web.elearning.client.classes.ClassTreeNodeWidget;
import org.clematis.web.elearning.client.general.ShowErrorMessageEvent;
import org.clematis.web.elearning.shared.domain.StudentsClass;

import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.ViewImpl;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class TeacherClassesView extends ViewImpl implements
		TeacherClassesPresenter.MyView {

	private final Widget widget;

	public interface Binder extends UiBinder<Widget, TeacherClassesView> {
	}
	
	@UiField VerticalPanel classesList;
	@UiField HTMLPanel messagePanel;
	
	private final List<ClassTreeNodeWidget> classesWidgets = new ArrayList<ClassTreeNodeWidget>();
	private final EventBus eventBus;
	private final DispatchAsync dispatcher;	
	
	@Inject
	public TeacherClassesView(final Binder binder, final EventBus eventBus, final DispatchAsync dispatcher) {
		widget = binder.createAndBindUi(this);
		
		this.eventBus = eventBus;
		this.dispatcher = dispatcher;
	}

	@Override
	public Widget asWidget() {
		return widget;
	}

	@Override
	public void clearClasses() {
		classesList.clear();
	}

	@Override
	public void showLoader(boolean visible) {
		messagePanel.setVisible(visible);
	}
	
	@UiHandler("buttonRefresh")
	public void onRefresh(ClickEvent e) {
		requestClasses();	
	}

	@Override
	public void showClasses(List<StudentsClass> classes) {
		classesList.clear();
        classesWidgets.clear();
        /**
         * Show new tree
         */
        for ( StudentsClass studentsClass : classes ) {
        	/**
        	 * Create widget for group
        	 */
        	ClassTreeNodeWidget classListWidget = new ClassTreeNodeWidget(studentsClass, false);
        	/**
        	 * Add to list of widgets
        	 */
        	classesWidgets.add(classListWidget);
        	/**
        	 * Add to list of handlers
        	 */
        	classesList.add(classListWidget);
        }
		
	}

	@Override
	public void requestClasses() {
		clearClasses();
		showLoader(true);
		dispatcher.execute(new GetTeacherClassesAction(ELP.getConnectionState().user.getUser().getId()), new AsyncCallback<GetTeacherClassesResult>() {

			@Override
			public void onFailure(Throwable caught) {
				showLoader(false);
				eventBus.fireEvent(new ShowErrorMessageEvent("Ошибка получения списка классов ", caught));
			}

			@Override
			public void onSuccess(final GetTeacherClassesResult classes) {
				showLoader(false);
				showClasses(classes.getClasses());	
			}
			
		});
	}
}
