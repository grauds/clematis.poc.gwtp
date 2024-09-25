package org.clematis.web.elearning.client.students.budget;

import java.util.List;

import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.NameToken;

import org.clematis.web.elearning.client.place.NameTokens;

import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.google.inject.Inject;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import org.clematis.web.elearning.client.general.GeneralPagePresenter;
import org.clematis.web.elearning.client.general.ShowErrorMessageEvent;
import org.clematis.web.elearning.shared.domain.Operation;

public class BudgetLinePresenter extends
		Presenter<BudgetLinePresenter.MyView, BudgetLinePresenter.MyProxy> {

	public interface MyView extends View {

		void showLoader(boolean b);

		void showData(List<Operation> operations);

		void clearData();
	}

	private final DispatchAsync dispatcher;	

	@ProxyCodeSplit
	@NameToken(NameTokens.STUDENT_BUDGET)
	//@UseGatekeeper(StudentGatekeeper.class)
	public interface MyProxy extends ProxyPlace<BudgetLinePresenter> {
	}

	@Inject
	public BudgetLinePresenter(final EventBus eventBus, final MyView view,
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
		super.onReveal();
		getView().clearData();
		getView().showLoader(true);
		dispatcher.execute(new GetOperationsAllAction(), new AsyncCallback<GetOperationsAllResult>() {

			@Override
			public void onFailure(Throwable caught) {
				getView().showLoader(false);
				getEventBus().fireEvent(new ShowErrorMessageEvent("Ошибка получения данных ", caught));
			}

			@Override
			public void onSuccess(final GetOperationsAllResult operations) {
				
				getView().showLoader(false);
				getView().showData(operations.operations);						
			}
			
		});
	}			
}
