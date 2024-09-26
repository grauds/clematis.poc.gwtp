package org.clematis.web.elearning.client.events;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.clematis.web.elearning.client.ELP;
import org.clematis.web.elearning.client.general.ShowErrorMessageEvent;
import org.clematis.web.elearning.shared.domain.Event;
import org.clematis.web.elearning.shared.domain.StudentsClass;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.dispatch.shared.DispatchAsync;

public class ScheduleWidget extends Composite {

	private static final ScheduleWidgetUiBinder uiBinder = GWT
			.create(ScheduleWidgetUiBinder.class);

	interface ScheduleWidgetUiBinder extends UiBinder<Widget, ScheduleWidget> {
	}
	
	interface ScheduleWidgetStyle extends CssResource {
         String cell();
         String cell_editable();
         String cell_selected();
         String cell_not_selected();
         String header();
		String start_cell();
		String today_cell();
		String today_cell_note();
	}	
	
	@UiField ScheduleWidgetStyle style;	
	@UiField FlexTable header;
	@UiField FlexTable panel;
	@UiField ScrollPanel scrollPanel;
	@UiField VerticalPanel specialSymbols;	
	@UiField VerticalPanel classesColors;
	
	private List<HandlerRegistration> daysClickHandlers = new ArrayList<HandlerRegistration>();
	
	private Map<StudentsClass, String> classColor = new HashMap<StudentsClass, String>();
	
	private PopupPanel eventPopupPanel;
	private EventWidget eventWidget;
	private int observerUserId;
	private Widget todayWidget;
	private HandlerRegistration todayHandlerRegistration;	
	
	class EventCell extends FocusPanel {
		
		private boolean selected = false;
		private Event event = null;
		private int column;
		private int row;

		public boolean isSelected() {
			return selected;
		}

		public void setSelected(boolean selected) {
			this.selected = selected;
		}

		public Event getEvent() {
			return event;
		}

		public void setEvent(Event event) {
			this.event = event;
		}

		public void setRow(int j) {
			this.row = j;
		}

		public void setColumn(int i) {
			this.column = i;
		}

		public int getColumn() {
			return column;
		}

		public int getRow() {
			return row;
		}

		public void setColor(String hexColor) {
			DOM.setStyleAttribute(getElement(), "backgroundColor", hexColor);
		}
		
	}
	
	public ScheduleWidget() {
		initWidget(uiBinder.createAndBindUi(this));
		
	}

	public ScheduleWidget(int observerUserId, final DispatchAsync dispatcher, final EventBus eventBus) {
		initWidget(uiBinder.createAndBindUi(this));
		
		this.observerUserId = observerUserId;
				
		eventWidget = new EventWidget(dispatcher, eventBus)
		{

			@Override
			public void editTime() {
				if (getCell().getEvent() != null && getSelectedClass()!=null){
					dispatcher.execute(new EditEventAction(getCell().getEvent().getId(), inputName.getText(),getSelectedClass().getId(), ELP.getCurrentUserId(), 
							inputDescription.getText(), getCell().getRow(), getCell().getColumn()),
	                                               new AsyncCallback<EditEventResult>() {
	
						@Override
						public void onFailure(Throwable caught) {
							eventBus.fireEvent(new ShowErrorMessageEvent("Ошибка редактирования занятия ", caught));
							eventPopupPanel.hide();
						}
	
						@Override
						public void onSuccess(EditEventResult result) {
							getCell().setEvent(result.getEvent());
                            String hexColor = classColor.get(getCell().getEvent().getStudentsClass());
							
							if (hexColor == null) {
								hexColor = generateColor();
								classColor.put(getCell().getEvent().getStudentsClass(), hexColor);
								
								HorizontalPanel oneColorLine = new HorizontalPanel();
								oneColorLine.setSpacing(10);
								
				                EventCell cell = new EventCell();
				  			    cell.setStyleName(style.cell());
				  			    cell.setColor(hexColor);
				  			    
				  			    oneColorLine.add(cell);
				  			    oneColorLine.add(new Label(getCell().getEvent().getStudentsClass().getName()));
								
								classesColors.add(oneColorLine);									
							}
							
							getCell().setColor(hexColor);
							eventPopupPanel.hide();						
						}
					});
				}
			}

			@Override
			public void dismissTime() {
				if (getCell().getEvent() != null ) {
					dispatcher.execute(new DeleteEventAction(getCell().getEvent().getId()), new AsyncCallback<DeleteEventResult>() {
						
						@Override
						public void onFailure(Throwable caught) {
							eventBus.fireEvent(new ShowErrorMessageEvent("Ошибка удаления занятия ", caught));
							eventPopupPanel.hide();
						}
						
						@Override
						public void onSuccess(DeleteEventResult result) {

							getCell().setEvent(null);
							getCell().setSelected(!getCell().isSelected());
							getCell().setStyleName(style.cell_selected(), getCell().isSelected());
							getCell().setColor("#ffffff");
							eventPopupPanel.hide();					
						}
					});	
				}
			}

			@Override
			public void setTime() {
				inputClass.validate();
				if (getSelectedClass()!=null){
					dispatcher.execute(new AddEventAction(inputName.getText(),getSelectedClass().getId(), ELP.getCurrentUserId(), inputDescription.getText(), getCell().getRow(), getCell().getColumn()),
	                                               new AsyncCallback<AddEventResult>() {
	
						@Override
						public void onFailure(Throwable caught) {
							eventBus.fireEvent(new ShowErrorMessageEvent("Ошибка добавления занятия ", caught));
							eventPopupPanel.hide();
						}
	
						@Override
						public void onSuccess(AddEventResult result) {
							
							getCell().setEvent(result.getEvent());
							getCell().setSelected(!getCell().isSelected());
							getCell().setStyleName(style.cell_selected(), getCell().isSelected());
							String hexColor = classColor.get(getCell().getEvent().getStudentsClass());
							
							if (hexColor == null) {
								hexColor = generateColor();
								classColor.put(getCell().getEvent().getStudentsClass(), hexColor);
								
								HorizontalPanel oneColorLine = new HorizontalPanel();
								oneColorLine.setSpacing(10);
								
				                EventCell cell = new EventCell();
				  			    cell.setStyleName(style.cell());
				  			    cell.setColor(hexColor);
				  			    
				  			    oneColorLine.add(cell);
				  			    oneColorLine.add(new Label(getCell().getEvent().getStudentsClass().getName()));
								
								classesColors.add(oneColorLine);									
							}
							
							getCell().setColor(hexColor);
							eventPopupPanel.hide();
							
						}
					});
				}
			}

			
		};
		eventPopupPanel = new PopupPanel(true);
		eventPopupPanel.setWidget(eventWidget);		
		
		
		Label label = new Label("");
		label.setStyleName(style.start_cell());
		
		header.setWidget(0, 0, label);
		header.setWidget(0, 1, getDayHeader("ПН"));
		header.setWidget(0, 2, getDayHeader("ВТ"));
		header.setWidget(0, 3, getDayHeader("СР"));
		header.setWidget(0, 4, getDayHeader("ЧТ"));
		header.setWidget(0, 5, getDayHeader("ПТ"));
		header.setWidget(0, 6, getDayHeader("СБ"));
		header.setWidget(0, 7, getDayHeader("ВСК"));
		
		HorizontalPanel specialSymbol = new HorizontalPanel();
		specialSymbol.setSpacing(10);
		
        EventCell cell = new EventCell();
		cell.setStyleName(style.today_cell_note());
		todayHandlerRegistration = cell.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if ( todayWidget != null ) {
					scrollPanel.ensureVisible(todayWidget);	
				}
				
			}
		});
		    
		specialSymbol.add(cell);
		specialSymbol.add(new Label("Текущее время"));
		
		specialSymbols.add(specialSymbol);			
	}
	
	@Override
	protected void onLoad() {
		super.onLoad();	
		
		
		if ( observerUserId == -1 ) {
			observerUserId = ELP.getCurrentUserId();
		}
		createWeek();
	}
	
	protected Label getDayHeader(String day) {
		
		Label label = new Label(day);		
		label.setStyleName(style.header());		
		return label;
		
	}

	@Override
	protected void onUnload() {
		super.onUnload();
		
		for (HandlerRegistration registration:daysClickHandlers) {
			registration.removeHandler();
		}		
		daysClickHandlers.clear();
		
		if ( todayHandlerRegistration != null ) {
			todayHandlerRegistration.removeHandler();
		}
		todayHandlerRegistration = null;
	}
	
	@UiHandler("buttonScrollDay")
	public void buttonScrollDay(ClickEvent e) {

		
		scrollPanel.setVerticalScrollPosition(560);
	}

	protected void createWeek() {

		
		for ( int i = 1; i <= 24; i++ ) {
			int hour = (i-1);
			panel.setWidget(2*i-1, 0, new Label(hour +":00"));
			panel.setWidget(2*i, 0, new Label(hour +":30"));
		} 		
		
		for ( int i = 1; i < 8; i++ ) {
			for ( int j = 1; j < 49; j++ ) {
				final EventCell cell = new EventCell();
				
				if(observerUserId == ELP.getCurrentUserId() && ELP.getConnectionState().isTeacher()) {
					
					cell.setStyleName(style.cell());
					cell.setStyleName(style.cell_editable(), true);
					
					daysClickHandlers.add(cell.addClickHandler(new ClickHandler()
					{

						@Override
						public void onClick(ClickEvent event) {
							
							eventWidget.setCell(cell);
							eventPopupPanel.setPopupPosition((int) (cell.getAbsoluteLeft() + cell.getOffsetWidth()/2 - 320), cell.getAbsoluteTop() + cell.getOffsetHeight() + 15);
							eventPopupPanel.show();		
							
						}
						
					}));
				} else {
					cell.setStyleName(style.cell());
				}
				
				cell.setRow(j);
				cell.setColumn(i);
				panel.setWidget(j, i, cell);
			}
		}
		
		
		
	}
	
	public void clearEvents() {
		panel.clear();
		
		for (HandlerRegistration registration:daysClickHandlers) {
			registration.removeHandler();
		}
		daysClickHandlers.clear();
	}

	public void showEvents(List<Event> events) {
		clearEvents();
		createWeek();
		for (Event event:events) {
			
			Widget w = panel.getWidget(event.getTimeInterval(), event.getDay());
			if (w instanceof EventCell) {
			
				((EventCell)w).setEvent(event);
				((EventCell)w).setSelected(true);
				w.setStyleName(style.cell_selected(), true);
				/**
				 * Color the event, according to class
				 */
				String hexColor = classColor.get(((EventCell)w).getEvent().getStudentsClass());
				if (hexColor == null) {
					hexColor = generateColor();
					classColor.put(((EventCell)w).getEvent().getStudentsClass(), hexColor);
					
					HorizontalPanel oneColorLine = new HorizontalPanel();
					oneColorLine.setSpacing(10);
					
	                EventCell cell = new EventCell();
	  			    cell.setStyleName(style.cell());
	  			    cell.setColor(hexColor);
	  			    
	  			    oneColorLine.add(cell);
	  			    oneColorLine.add(new Label(((EventCell)w).getEvent().getStudentsClass().getName()));
					
					classesColors.add(oneColorLine);					
				}
				((EventCell)w).setColor(hexColor);
			}
			
		}
		
		scrollPanel.setVerticalScrollPosition(560);
		markTodayCell();
		Timer timer = new Timer() {

			@Override
			public void run() {
				
				markTodayCell();		
			}
			
		};
		timer.scheduleRepeating(60000);
	}
	
	public void markTodayCell() {
		
		Date today = new Date();
		int column = today.getDay();
		int row = 2 * today.getHours() + ( today.getMinutes()<30 ? 0:1 ) + 1 ;
		
		if ( todayWidget != null ) {
			todayWidget.setStyleName(style.today_cell(), false);
		}
		
		todayWidget = panel.getWidget(row, column);
		todayWidget.setStyleName(style.today_cell(), true);
		
	}

	private String generateColor() {
		
	    int red = Random.nextInt(256);
	    int green = Random.nextInt(256);
	    int blue = Random.nextInt(256);

	    // mix the color
        red = (red + 256) / 2;
        green = (green + 256) / 2;
        blue = (blue + 256) / 2;

	    return rgbToHex(red, green, blue);
	}
	
	private String rgbToHex(int R, int G, int B) { return "#"+toHex(R)+toHex(G)+toHex(B); }
	
	private String toHex(int n) {
		 if (n==0) return "00";
		 n = Math.max(0,Math.min(n,255));
		 return String.valueOf("0123456789ABCDEF".charAt((n-n%16)/16)) + String.valueOf("0123456789ABCDEF".charAt(n%16));
	}	

}
