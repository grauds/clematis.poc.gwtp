package org.clematis.web.elearning.client.students.video;

import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.NameToken;

import org.clematis.web.elearning.client.ELP;
import org.clematis.web.elearning.client.place.NameTokens;
import org.clematis.web.elearning.client.students.classes.GetStudentClassesAction;
import org.clematis.web.elearning.client.students.classes.GetStudentClassesResult;

import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import org.clematis.web.elearning.client.login.navigation.StudentGatekeeper;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.google.inject.Inject;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;

import org.clematis.web.elearning.client.classes.ClassSelectDialog;
import org.clematis.web.elearning.client.general.GeneralPagePresenter;
import org.clematis.web.elearning.client.general.ShowErrorMessageEvent;
import org.clematis.web.elearning.client.general.ShowWarnMessageEvent;

public class StudentVideoRoomPresenter
		extends
		Presenter<StudentVideoRoomPresenter.MyView, StudentVideoRoomPresenter.MyProxy> {

	public interface MyView extends View {

		void setFlash(HTML html);

		void clearFlash();
	}

	@ProxyCodeSplit
	@NameToken(NameTokens.STUDENT_VIDEO)
	@UseGatekeeper(StudentGatekeeper.class)
	public interface MyProxy extends ProxyPlace<StudentVideoRoomPresenter> {
	}

	private final DispatchAsync dispatcher;

	@Inject
	public StudentVideoRoomPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy, final DispatchAsync dispatcher) {
		super(eventBus, view, proxy);
		
		this.dispatcher = dispatcher;
	}

	@Override
	protected void revealInParent() {
		RevealContentEvent.fire(this, GeneralPagePresenter.TYPE_GeneralContent, this);
	}

	@Override
	protected void onBind() {
		super.onBind();
	}
	
	@Override
	protected void onReveal() {
		getView().clearFlash();
		dispatcher.execute(new GetStudentClassesAction(ELP.getConnectionState().user.getUser().getId()), new AsyncCallback<GetStudentClassesResult>() {

			@Override
			public void onFailure(Throwable caught) {
				getEventBus().fireEvent(new ShowErrorMessageEvent("Ошибка при получении классов преподавателей ", caught));
			}

			@Override
			public void onSuccess(GetStudentClassesResult result) {
				
				final ClassSelectDialog classSelectDialog = new ClassSelectDialog("Выберите класс который вы хотите посетить",result.getClasses())
				{

					@Override
					public void onChooseClass() {

						if (getSelectedClass() != null){
							getEventBus().fireEvent(new ShowWarnMessageEvent("Не покидайте страницу во время трансляции видео"));
							
							HTML html = new HTML("<object width='820' height='625'>"+
						             "<param name='movie' value='flash/student.swf?classId="+ getSelectedClass().getId() +"&amp;userId=" + ELP.getConnectionState().user.getUser().getEmail()
						             +"&amp;nick=" + ELP.getConnectionState().user.getUser().getName() + " " + ELP.getConnectionState().user.getUser().getSecondName()
						             +"&amp;photo=http://domain.com/photo.jpg&amp;context_path=el-prototype'></param>"+
						             "<param name='allowFullScreen' value='true'></param>"+
						             "<param name='allowscriptaccess' value='always'></param>"+
						             "<param name='wmode' value='opaque'></param>"+
						             "<embed src='flash/student.swf?classId="+ getSelectedClass().getId() +"&amp;userId=" + ELP.getConnectionState().user.getUser().getEmail()
						             +"&amp;nick=" + ELP.getConnectionState().user.getUser().getName() + " " + ELP.getConnectionState().user.getUser().getSecondName()
						             +"&amp;photo=http://domain.com/photo.jpg&amp;context_path=el-prototype' wmode='opaque' type='application/x-shockwave-flash' width='820' height='625' allowscriptaccess='always' allowfullscreen='true'>"+
						             "</embed>"+
						          "</object>");
							
							getView().setFlash(html);	
						}
						super.onChooseClass();
					}					
				};
				
                classSelectDialog.setGlassEnabled(true);
                classSelectDialog.center();
                classSelectDialog.show();                
				
			}
		});		
		super.onReveal();
	}	
}
