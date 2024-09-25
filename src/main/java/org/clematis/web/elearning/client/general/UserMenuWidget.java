package org.clematis.web.elearning.client.general;


import org.clematis.web.elearning.client.ELP;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;

import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class UserMenuWidget extends Composite {

	private static UserMenuWidgetUiBinder uiBinder = GWT.create(UserMenuWidgetUiBinder.class);

	interface UserMenuWidgetUiBinder extends UiBinder<Widget, UserMenuWidget> {
	}

	private static final UrlTemplate URL_TEMPLATE = GWT
			.create(UrlTemplate.class);

	public interface UrlTemplate extends SafeHtmlTemplates {
		@Template("<a href=\"{0}\">{1}</a>")
		SafeHtml messageWithLink(String url, String text);
	}

	@UiField HTMLPanel menu;
	@UiField Label userName;
	@UiField Label userEmail;
	
	@UiField HTML info;
	@UiField HTML settings;	
	@UiField HTML logout;

	public UserMenuWidget() {
		
		initWidget(uiBinder.createAndBindUi(this));

		updateMenu();
		
		info.setHTML("Анкета");
		settings.setHTML("Установки");	
		logout.setHTML("Выйти");
	}

	public void updateMenu() {
		
		if ( ELP.getConnectionState().user != null && ELP.getConnectionState().user.getUser() != null ) {
			userName.setText(ELP.getConnectionState().user.getUser().getName() + " " + ELP.getConnectionState().user.getUser().getSecondName());
			
			if ( ELP.getConnectionState().user.getUser().getEmail() != null ) {
				userEmail.setText(ELP.getConnectionState().user.getUser().getEmail().getText());
			} else {
				userEmail.setText("Неизвестный адрес почты");
			}
		} else {
			userName.setText("Неизвестные имя и фамилия");
			userEmail.setText("Неизвестный адрес почты");
		}
		
	}

	public static SafeHtml buildAnchorItem(String place, String name) {
		SafeHtmlBuilder builder = new SafeHtmlBuilder();
		builder.append(URL_TEMPLATE.messageWithLink(place, name));
		return builder.toSafeHtml();
	}
	
	@UiHandler("logout")
    public void onLogout(ClickEvent e) {
	}

}
