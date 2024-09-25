package org.clematis.web.elearning.client.teachers.video;

import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.NameToken;

import org.clematis.web.elearning.client.ELP;
import org.clematis.web.elearning.client.place.NameTokens;
import org.clematis.web.elearning.client.teachers.classes.GetTeacherClassesAction;
import org.clematis.web.elearning.client.teachers.classes.GetTeacherClassesResult;

import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import org.clematis.web.elearning.client.login.navigation.TeacherGatekeeper;
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

public class TeacherVideoRoomPresenter
		extends
		Presenter<TeacherVideoRoomPresenter.MyView, TeacherVideoRoomPresenter.MyProxy> {

	public interface MyView extends View {

		void setFlash(HTML html);

		void clearFlash();
	}

	@ProxyCodeSplit
	@NameToken(NameTokens.TEACHER_VIDEO)
	@UseGatekeeper(TeacherGatekeeper.class)
	public interface MyProxy extends ProxyPlace<TeacherVideoRoomPresenter> {
	}

	private final DispatchAsync dispatcher;

	@Inject
	public TeacherVideoRoomPresenter(final EventBus eventBus, final MyView view,
			final MyProxy proxy, final DispatchAsync dispatcher) {
		super(eventBus, view, proxy);
		
		this.dispatcher = dispatcher;
	}

	@Override
	protected void revealInParent() {
		RevealContentEvent.fire(this, GeneralPagePresenter.TYPE_GeneralContent,
				this);
	}

	@Override
	protected void onBind() {
		super.onBind();
	}

	@Override
	protected void onReveal() {
		getView().clearFlash();
		dispatcher.execute(new GetTeacherClassesAction(ELP.getConnectionState().user.getUser().getId()), new AsyncCallback<GetTeacherClassesResult>() {

			@Override
			public void onFailure(Throwable caught) {
				getEventBus().fireEvent(new ShowErrorMessageEvent("Ошибка при получении классов преподавателя ", caught));
			}

			@Override
			public void onSuccess(GetTeacherClassesResult result) {
				
				final ClassSelectDialog classSelectDialog = new ClassSelectDialog("Выберите класс, в котором вы хотите сейчас преподавать",result.getClasses())
				{

					@Override
					public void onChooseClass() {

						if (getSelectedClass() != null){
							getEventBus().fireEvent(new ShowWarnMessageEvent("Не покидайте страницу во время трансляции видео"));
							
							
							HTML html = new HTML("<object width='850' height='790'>"+
						             "<param name='movie' value='flash/lector.swf?classId="+getSelectedClass().getId()+"&amp;userId=" + ELP.getConnectionState().user.getUser().getEmail()
						             +"&amp;nick=" + ELP.getConnectionState().user.getUser().getName() + " " + ELP.getConnectionState().user.getUser().getSecondName()
						             +"&amp;photo=http://domain.com/photo.jpg&amp;context_path=el-prototype'></param>"+
						             "<param name='allowFullScreen' value='true'></param>"+
						             "<param name='allowscriptaccess' value='always'></param>"+
						             "<param name='wmode' value='opaque'></param>"+
						             "<param name='context_path' value='el-prototype'></param>"+
						             "<embed src='flash/lector.swf?classId="+getSelectedClass().getId()+"&amp;userId=" + ELP.getConnectionState().user.getUser().getEmail()
						             +"&amp;nick=" + ELP.getConnectionState().user.getUser().getName() + " " + ELP.getConnectionState().user.getUser().getSecondName()
						             +"&amp;photo=http://domain.com/photo.jpg&amp;context_path=el-prototype' wmode='opaque' type='application/x-shockwave-flash' width='850' height='790' allowscriptaccess='always' allowfullscreen='true'>"+
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
