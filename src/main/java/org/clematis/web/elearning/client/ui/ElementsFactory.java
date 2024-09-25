package org.clematis.web.elearning.client.ui;

import org.clematis.web.elearning.client.fields.Field;
import org.clematis.web.elearning.client.fields.MenuBlockField;
import org.clematis.web.elearning.client.fields.MenuListField;
import org.clematis.web.elearning.client.ui.MenuBlockItemWidget.MenuBlockItemWidgetFactory;
import org.clematis.web.elearning.client.ui.MenuListItemWidget.MenuListItemWidgetFactory;
import org.clematis.web.elearning.client.ui.input.BigDecimalInput;
import org.clematis.web.elearning.client.ui.input.BoolInput;
import org.clematis.web.elearning.client.ui.input.DateInput;
import org.clematis.web.elearning.client.ui.input.Input;
import org.clematis.web.elearning.client.ui.input.InputBase;
import org.clematis.web.elearning.client.ui.input.LongInput;
import org.clematis.web.elearning.client.ui.input.MultilineInput;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.inject.Inject;


public class ElementsFactory {

	public interface ElementsFactoryFactory {
		public ElementsFactory create();
	}

	/**
	 * Menu block factory
	 */
	private MenuBlockItemWidgetFactory menuBlockItemWidgetFactory;
	/**
	 * Menu list factory
	 */
	private MenuListItemWidgetFactory menuListItemWidgetFactory;

	@Inject
	protected ElementsFactory(MenuBlockItemWidgetFactory menuBlockItemWidgetFactory,
			MenuListItemWidgetFactory menuListItemWidgetFactory) {

		this.menuBlockItemWidgetFactory = menuBlockItemWidgetFactory;
		this.menuListItemWidgetFactory = menuListItemWidgetFactory;

	}

	public Widget createInputControl(Field field) {
		Widget widget = null;

		switch (field.type) {

		case STRING:
			widget = createStringControl(field);
			break;			
		case BOOL:
			widget = createBoolControl(field);
			break;			
		case STATIC:
			widget = createStaticControl(field);
			break;
		case SEPARATOR:
			widget = createSeparator(field);
			break;
		case LONG:
			widget = createLongControl(field);
			break;
		case BIGDECIMAL:
			widget = createBigDecimalControl(field);
			break;
		case MENUBLOCK:
			widget = menuBlockItemWidgetFactory.create((MenuBlockField) field);
			break;
		case MENULIST:
			widget = menuListItemWidgetFactory.create((MenuListField) field);
			break;
		case DATE:
			widget = createDateControl(field);
			break;
		case DATETIME:
			widget = createDateTimeControl(field);
			break;

		}
		if (widget instanceof InputBase<?,?>) {
			((InputBase<?,?>)widget).setEnabled(!field.readOnly);			
			((InputBase<?,?>)widget).setRequired(field.required != null);
		}
		return widget;
	}

	protected InputBase<?,?> createSeparator(Field field) {
		return null;
	}

	protected InputBase<?,?> createStaticControl(Field field) {
		return null;
	}

	protected InputBase<?,?> createBoolControl(Field field) {
		BoolInput l = new BoolInput();
		l.setCaptionOnLeft(true);
		l.setCaption(field.captionForm);
		l.setClearedOnSubmit(!field.dontCleanupAfterNewSaved);
		return l;
	}

	protected InputBase<?,?> createStringControl(Field field) {
		Input l = new Input();
		l.setCaptionOnLeft(true);
		l.setCaption(field.captionForm);
		l.setClearedOnSubmit(!field.dontCleanupAfterNewSaved);
		return l;
	}

	protected InputBase<?,?> createBigDecimalControl(Field field) {
		BigDecimalInput l = new BigDecimalInput();
		l.setCaptionOnLeft(true);
		l.setCaption(field.captionForm);
		l.setClearedOnSubmit(!field.dontCleanupAfterNewSaved);
		return l;
	}

	protected InputBase<?,?> createLongControl(Field field) {
		LongInput l = new LongInput();
		l.setCaptionOnLeft(true);
		l.setCaption(field.captionForm);
		l.setClearedOnSubmit(!field.dontCleanupAfterNewSaved);
		return l;
	}

	protected InputBase<?,?> createDescriptionInPlaceControl(Field field) {
		MultilineInput l = new MultilineInput();
		l.setCaptionOnLeft(true);
		l.setCaption(field.captionForm);
		l.setInputHeight("300px");
		l.setClearedOnSubmit(!field.dontCleanupAfterNewSaved);
		return l;
	}
	
	protected InputBase<?,?> createDateControl(Field field) {
		DateInput di = new DateInput();
		di.setCaptionOnLeft(true);
		di.setCaption(field.captionForm);
		
		DateBox datebox = di.getInput().getDateBox();
		datebox.setFormat(new DateBox.DefaultFormat(DateTimeFormat
				.getFormat(PredefinedFormat.DATE_SHORT)));
		// if (value != null) {
		// try {
		// Date dt = ClientUtils.stringToDate(value);
		// datebox.setValue(dt);
		// } catch (Exception e) {
		// datebox.getTextBox().setText(e.getMessage());
		// }
		// }
		di.setEnabled(!field.readOnly);
		di.setClearedOnSubmit(!field.dontCleanupAfterNewSaved);
		
		return di;
	}

	protected InputBase<?,?> createDateTimeControl(Field field) {
		DateInput di = new DateInput();
		di.setCaptionOnLeft(true);
		di.setCaption(field.captionForm);
		
		DateBox datebox = di.getInput().getDateBox();
		datebox.setFormat(new DateBox.DefaultFormat(DateTimeFormat
				.getFormat(PredefinedFormat.DATE_TIME_SHORT)));
		// if (value != null) {
		// try {
		// Date dt = new Date(Long.parseLong(value));
		// datebox.setValue(dt);
		// } catch (Exception e) {
		// datebox.getTextBox().setText(e.getMessage());
		// }
		// }
		di.setEnabled(!field.readOnly);
		di.setClearedOnSubmit(!field.dontCleanupAfterNewSaved);
		
		return di;
	}

}
