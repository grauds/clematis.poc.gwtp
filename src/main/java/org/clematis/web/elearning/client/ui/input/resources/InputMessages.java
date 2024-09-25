package org.clematis.web.elearning.client.ui.input.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Constants;

public interface InputMessages extends Constants {
	public static final InputMessages INSTANCE = GWT.create(InputMessages.class);

	@DefaultStringValue("Это поле должно быть заполнено")
	String getDefaultErrorMessage();

	@DefaultStringValue("Неверный адрес электронной почты")
	String getEmailInvalidMessage();

	@DefaultStringValue("В поле должно быть введено целое число")
	String getLongFieldMessage();
	
	@DefaultStringValue("В поле должна быть введена денежная сумма")
	String getBigDecimalFieldMessage();	

	@DefaultStringValue("Число должно быть больше нуля")
	String getMustBeGreaterThenZero();

	@DefaultStringValue("Текст не содержится в списке возможных вариантов")
	String getSuggestFieldMessage();
}
