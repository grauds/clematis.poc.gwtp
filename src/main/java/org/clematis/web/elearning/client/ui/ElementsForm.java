package org.clematis.web.elearning.client.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.clematis.web.elearning.client.fields.BlockInfo;
import org.clematis.web.elearning.client.fields.Field;
import org.clematis.web.elearning.client.ui.ElementsFactory.ElementsFactoryFactory;
import org.clematis.web.elearning.client.ui.input.InputBase;
import org.clematis.web.elearning.client.ui.input.resources.InputResources;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;


public class ElementsForm extends FlexTable {
	
	/**
     * Elements factory
     */
	private final ElementsFactory elementsFactory;

	/**
	 * All form controls
	 */
    private final Map<Field, InputBase<?,?>> controls = new HashMap<Field, InputBase<?,?>>();
    /**
     * Form controls
     */
	private List<InputBase<?,?>> widgets = new LinkedList<InputBase<?,?>>();    
	/**
	 * Handlers
	 */
	private List<HandlerRegistration> handlers = new ArrayList<HandlerRegistration>();

	public interface ElementsFormFactory {
		public ElementsForm create();
	}
	
	@Inject
	protected ElementsForm(ElementsFactoryFactory elementsFactoryFactory) {

		InputResources.INSTANCE.css().ensureInjected();	

		this.elementsFactory = elementsFactoryFactory.create();
	}	
	
	public void initBlock(BlockInfo info, String error) {
		/**
		 * Clear panels, buttons and list of input controls
		 */
		clear();		 
//		buttons.clear();
		controls.clear();
		/**
		 * Clear button enter key handlers
		 */
		for (HandlerRegistration handlerRegistration : handlers ) {
			handlerRegistration.removeHandler();
		}
		handlers.clear();
		
		if (info == null) {
			// Error while getting blocks of elements data
//			eventBus.fireEvent(new ShowErrorMessageEvent("Error creating form" + (error == null ? "" : (": " +error)), null));
		} else {
			
			if (info.getFields() != null) {
//				List<Widget> buttons = new ArrayList<Widget>();
	            for (Field field : info.getFields()) {
	            	Widget widget = null; 
	            	
	            	switch (field.type) {
//						case SUBMIT:
//							buttons.add(createSubmit(field));
//							break;	
						default:
							widget = elementsFactory.createInputControl(field);
							break;
						
	            	}
	            	if (widget != null) {
		            	/**
		            	 * Settle widget in right position in carrier FlexTable
		            	 */
	            		setSpans(field);	          	
	            		if (field.row >= 0 && field.column >= 0) {
							if (widget instanceof InputBase<?,?>) {
//								((InputBase<?,?>)widget).getInput().addKeyUpHandler(enterHandler);
								controls.put(field, (InputBase<?,?>)widget);
		            			widgets.add((InputBase<?,?>)widget);
							}	            			
	            			addWidget(field.row, field.column, widget);
	            		}
//	            		else if (field.row < 0) {	            			
//	            			widget.setVisible(false);
//	            			if (widget instanceof InputBase<?,?>) {
////	            				((InputBase<?,?>)widget).getInput().addKeyUpHandler(enterHandler);
//	            				controls.put(field, (InputBase<?,?>)widget);
//	            			}
//	            			addWidget((-1)*field.row, field.column > 0 ? field.column : (-1) *field.column, widget);	            			
//	            			
//	            			if ( button.isVisible() == false ) {
//	            				// TODO i18n
//	            				button.setText( "Другие поля" );
//	            				handlers.add(button.addClickHandler(new ClickHandler() {
//	            					@Override
//	            					public void onClick(ClickEvent event) {
//	            						for ( Widget w : hiddenWidgets ) {
//	            							w.setVisible(!w.isVisible());
//	            						}
//	            					}
//	            				}));	            				
//	            				button.setVisible(true);
//	            			}
//	            		}
	            	}
	            }
	            /**
	             * Add buttons
	             */
//	            for ( Widget button : buttons ){
//	            	buttonsPanel.add( button );
//	            }
			}
		}
		/**
		 * Notify listeners that this block has loaded its data
		 */
//		eventBus.fireEvent(new ElementsBlockUpdatedEvent(getTitle()));
	}
	
	protected void addWidget(int x, int y, Widget widget) {
		
		try {
			
    		Widget cell = getWidget(x, y);
			if ( cell == null ) {
				
				cell = new FlowPanel();
				((FlowPanel)cell).add(widget);				
				setWidget(x, y, cell);
			}
			else if ( cell instanceof FlowPanel ) {
				
				((FlowPanel)cell).add(widget);
			}
		}
		catch (IndexOutOfBoundsException ex ) {
			/* just add */
			FlowPanel cell = new FlowPanel();
			cell.add(widget);			
			setWidget(x, y, cell);			
		}
	}

	private void setSpans(Field field) {
		
		if ( field.row >= 0  && field.column >= 0 ){
			if (field.rowspan > 1) {
				getFlexCellFormatter().setRowSpan(field.row, field.column, field.rowspan);
			}
			if (field.colspan > 1) {
				getFlexCellFormatter().setColSpan(field.row, field.column, field.colspan);
			}
		}
		else{
			if (field.rowspan > 1) {
				getFlexCellFormatter().setRowSpan((-1) * field.row, field.column > 0 ? field.column : (-1) *field.column, field.rowspan);
			}
			if (field.colspan > 1) {
				getFlexCellFormatter().setColSpan((-1) * field.row, field.column > 0 ? field.column : (-1) *field.column, field.colspan);
			}
		}	
		
	}

//	private Widget createSubmit(final Field field) {
//		final Button button = new Button();
//		button.setText( field.captionForm );
//		handlers.add(button.addClickHandler(new ClickHandler() {
//			
//			@Override
//			public void onClick(ClickEvent event) {
//				doSubmit(field);
//			}
//		}));
//		
//		if ( defaultButtonId == null ) {
//			if ( field.id.contains(Constants.buttonDefaultSubstring) ){
//                defaultButtonId = field.id; 
//                button.setStyleName(InputResources.INSTANCE.css().bluebutton());
//			}	
//			else{
//			    button.setStyleName(InputResources.INSTANCE.css().greybutton());
//			}
//		}
//		else{
//            if ( field.id.equals(defaultButtonId)) {
//                button.setStyleName(InputResources.INSTANCE.css().bluebutton());
//            } 	
//			else{
//			    button.setStyleName(InputResources.INSTANCE.css().greybutton());
//			}           
//		}	
//			
//    	buttons.put( field.id, button );
//    	
//		button.addStyleName(InputResources.INSTANCE.css().colorbuttonspacing());
//		handlers.add(button.addKeyUpHandler(enterHandler));
//		
//		return button;
//	}	
	
	public void setEnabled(boolean enabled) {
		for (Entry<Field, InputBase<?,?>> item : controls.entrySet()) {
			// Enable control only if enabled == true and read only == false
			item.getValue().setEnabled(enabled ? !item.getKey().readOnly : false);
		}
	}		
	
}
