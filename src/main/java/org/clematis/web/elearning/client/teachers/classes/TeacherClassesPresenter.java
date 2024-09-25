package org.clematis.web.elearning.client.teachers.classes;

import java.util.List;

import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.NameToken;

import org.clematis.web.elearning.client.place.NameTokens;

import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import org.clematis.web.elearning.client.login.navigation.TeacherGatekeeper;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.google.inject.Inject;
import com.google.gwt.event.shared.EventBus;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;

import org.clematis.web.elearning.client.general.GeneralPagePresenter;
import org.clematis.web.elearning.shared.domain.StudentsClass;

public class TeacherClassesPresenter extends Presenter<TeacherClassesPresenter.MyView, TeacherClassesPresenter.MyProxy> {

	public interface MyView extends View {

		void clearClasses();

		void showLoader(boolean b);

		void showClasses(List<StudentsClass> classes);

		void requestClasses();
	}

	@ProxyCodeSplit
	@NameToken(NameTokens.TEACHER_STUDENTS)
	@UseGatekeeper(TeacherGatekeeper.class)
	public interface MyProxy extends ProxyPlace<TeacherClassesPresenter> {
	}
	
	@Inject
	public TeacherClassesPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy) {
		super(eventBus, view, proxy);		
	}

	@Override
	protected void revealInParent() {
		RevealContentEvent.fire(this, GeneralPagePresenter.TYPE_GeneralContent, this);
	}
	
	@Override
	protected void onReveal() {
		super.onReveal();
		getView().requestClasses();
	}	

	@Override
	protected void onBind() {
		super.onBind();
	}
}
