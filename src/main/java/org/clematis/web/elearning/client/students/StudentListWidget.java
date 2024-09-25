package org.clematis.web.elearning.client.students;

import org.clematis.web.elearning.client.ui.PinWidget;
import org.clematis.web.elearning.shared.domain.User;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class StudentListWidget extends Composite {

	private static StudentListWidgetUiBinder uiBinder = GWT
			.create(StudentListWidgetUiBinder.class);

	interface StudentListWidgetUiBinder extends
			UiBinder<Widget, StudentListWidget> {
	}
	
	@UiField Label userName;
	@UiField Label userEmail;
	

	@UiField PinWidget pin;
	
	public StudentListWidget(User user) {
		initWidget(uiBinder.createAndBindUi(this));
		
		if ( user != null ) {
			userName.setText(user.getName() + " " +user.getSecondName());
			
			if ( user.getEmail() != null ) {
				userEmail.setText(user.getEmail().getText());
			} else {
				userEmail.setText("Неизвестный адрес почты");
			}
		} else {
			userName.setText("Неизвестные имя и фамилия");
			userEmail.setText("Неизвестный адрес почты");
		}
	}

	public StudentListWidget(User user, boolean b) {
		this(user);
		
		pin.setVisible(b);
	}

}
