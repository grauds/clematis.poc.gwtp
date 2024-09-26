package org.clematis.web.elearning.client.events;

import org.clematis.web.elearning.client.ELP;
import org.clematis.web.elearning.client.classes.ClassSelectDialog;
import org.clematis.web.elearning.client.events.ScheduleWidget.EventCell;
import org.clematis.web.elearning.client.general.ShowErrorMessageEvent;
import org.clematis.web.elearning.client.teachers.classes.GetTeacherClassesAction;
import org.clematis.web.elearning.client.teachers.classes.GetTeacherClassesResult;
import org.clematis.web.elearning.client.ui.input.Input;
import org.clematis.web.elearning.client.ui.input.MultilineInput;
import org.clematis.web.elearning.shared.domain.StudentsClass;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.dispatch.shared.DispatchAsync;

public class EventWidget extends Composite {

	private static final EventWidgetUiBinder uiBinder = GWT
			.create(EventWidgetUiBinder.class);

	interface EventWidgetUiBinder extends UiBinder<Widget, EventWidget> {
	}

	private EventCell cell;
	
	@UiField Button setTime;
	@UiField Button editTime;
	@UiField Button dismissTime;
	
	@UiField Input inputName;
	@UiField Input inputClass;
	@UiField MultilineInput inputDescription;
	
	private DispatchAsync dispatcher;
	private EventBus eventBus;	
	
	private StudentsClass selectedClass;

	public EventWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public EventWidget(final DispatchAsync dispatcher, final EventBus eventBus) {
		initWidget(uiBinder.createAndBindUi(this));
		
		this.dispatcher = dispatcher;
		this.eventBus = eventBus;
		
		setTime.setVisible(false);
		editTime.setVisible(false);
		dismissTime.setVisible(false);
		
		inputClass.getInput().setReadOnly(true);
	}

	public void setCell(EventCell cell) {
		this.cell = cell;
		
		inputName.setText(cell.getEvent() != null ? cell.getEvent().getName() : "");
		inputClass.setText( (cell.getEvent() != null && cell.getEvent().getStudentsClass() !=null) ? cell.getEvent().getStudentsClass().getName() : "");
		inputDescription.setText(cell.getEvent() != null ? cell.getEvent().getDescription() : "");
		selectedClass = cell.getEvent() != null ? cell.getEvent().getStudentsClass() : null;
		
		if (cell.isSelected()) {
			
			setTime.setVisible(false);
			editTime.setVisible(true);
			dismissTime.setVisible(true);
			
		} else {
			
			setTime.setVisible(true);
			editTime.setVisible(false);
			dismissTime.setVisible(false);
			
		}
			
	}

	public EventCell getCell() {
		return cell;
	}

	@UiHandler("setTime")
	public void onSetTime(ClickEvent ev) {
		setTime();
	}
	
	@UiHandler("editTime")
	public void onEditTime(ClickEvent ev) {
		editTime();
	}	

	@UiHandler("chooseClass")
	public void onChooseClass(ClickEvent ev) {
		dispatcher.execute(new GetTeacherClassesAction(ELP.getConnectionState().user.getUser().getId()), new AsyncCallback<GetTeacherClassesResult>() {

			@Override
			public void onFailure(Throwable caught) {
				eventBus.fireEvent(new ShowErrorMessageEvent("Ошибка получения списка классов ", caught));
			}

			@Override
			public void onSuccess(final GetTeacherClassesResult classes) {

				final ClassSelectDialog classSelectDialog = new ClassSelectDialog("Выберите класс, занятие которого вы ухотите установить", classes.getClasses())
				{
					@Override
					public void onChooseClass() {

						setSelectedClass(getSelectedClass());
						
						if ( getSelectedClass() != null ) {
							inputClass.setText( getSelectedClass().getName() );							
					    } else {
					    	inputClass.setText( "" );
					    }
						inputClass.validate();
						
						super.onChooseClass();
					}
					
				};
				
		        classSelectDialog.setGlassEnabled(true);
		        classSelectDialog.center();
		        classSelectDialog.show();   
			}
			
		});   
	}
	
	@UiHandler("dismissTime")
	public void onDismissTime(ClickEvent ev) {
		dismissTime();
	}

	public void dismissTime() {
		
	}

	public void setTime() {
		
	}
	
	public void editTime() {
		
	}

	public StudentsClass getSelectedClass() {
		return selectedClass;
	}

	public void setSelectedClass(StudentsClass selectedClass) {
		this.selectedClass = selectedClass;
	}
	
}
