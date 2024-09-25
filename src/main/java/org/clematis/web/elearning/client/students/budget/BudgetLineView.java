package org.clematis.web.elearning.client.students.budget;

import java.util.List;

import org.clematis.web.elearning.shared.domain.Operation;

import com.gwtplatform.mvp.client.ViewImpl;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.Selection;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.events.SelectHandler;
import com.google.gwt.visualization.client.visualizations.corechart.AxisOptions;
import com.google.gwt.visualization.client.visualizations.corechart.LineChart;
import com.google.gwt.visualization.client.visualizations.corechart.Options;

public class BudgetLineView extends ViewImpl implements
		BudgetLinePresenter.MyView {

	private final Widget widget;
	
	@UiField Button buttonAddOp;
	@UiField FormPanel formPanel;
	@UiField FileUpload dataFileUploadField;
	@UiField HTMLPanel pieHolder;
	@UiField HTMLPanel messagePanel;
	
	private LineChart pie;

	public interface Binder extends UiBinder<Widget, BudgetLineView> {
	}

	@Inject
	public BudgetLineView(final Binder binder) {
		widget = binder.createAndBindUi(this);
		
		formPanel.setEncoding(FormPanel.ENCODING_MULTIPART);
	    formPanel.setMethod(FormPanel.METHOD_POST);
	    addSubmitHandlers();
	    
		
	    // Load the visualization api, passing the onLoadCallback to be called
	    // when loading is done.
	    VisualizationUtils.loadVisualizationApi(onLoadCallback, LineChart.PACKAGE);	
	}

	@UiHandler("buttonAddOp")
	protected void onBudgetImport(ClickEvent clickEvent) {
         formPanel.submit();
	}	

	private void addSubmitHandlers() {    
	    formPanel.addSubmitCompleteHandler(new SubmitCompleteHandler() {
		    @Override
		    public void onSubmitComplete(SubmitCompleteEvent event) {
		        
		    }
	  });
    }	
	
	@Override
	public Widget asWidget() {
		return widget;
	}
	
	// Create a callback to be called when the visualization API
    // has been loaded.
    Runnable onLoadCallback = new Runnable() {
      public void run() { 
        // Create a pie chart visualization.
    	pie = new LineChart(createTable(null), createOptions());
        pie.addSelectHandler(createSelectHandler(pie));
        pieHolder.add(pie);        
      }
    };

	private Options createOptions() {
		Options options = Options.create();
	    options.setHeight(540);
	    options.setTitle("All operations");
	    options.setWidth(1000);
	    options.setInterpolateNulls(true);
	    
	    AxisOptions vAxisOptions = AxisOptions.create();
	    vAxisOptions.setMinValue(0);
	    vAxisOptions.setMaxValue(2000);
	    vAxisOptions.setTitle("Остаток (руб)");
	    options.setVAxisOptions(vAxisOptions);
	    
	    AxisOptions hAxisOptions = AxisOptions.create();
	    hAxisOptions.setMinValue(2002);
	    hAxisOptions.setMaxValue(2020);
	    hAxisOptions.setTitle("Даты");
	    options.setHAxisOptions(hAxisOptions);

		return options;
	}

	private SelectHandler createSelectHandler(final LineChart chart) {
		return new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				String message = "";

				// May be multiple selections.
				JsArray<Selection> selections = chart.getSelections();

				for (int i = 0; i < selections.length(); i++) {
					// add a new line for each selection
					message += i == 0 ? "" : "\n";

					Selection selection = selections.get(i);

					if (selection.isCell()) {
						// isCell() returns true if a cell has been selected.

						// getRow() returns the row number of the selected cell.
						int row = selection.getRow();
						// getColumn() returns the column number of the selected
						// cell.
						int column = selection.getColumn();
						message += "cell " + row + ":" + column + " selected";
					} else if (selection.isRow()) {
						// isRow() returns true if an entire row has been
						// selected.

						// getRow() returns the row number of the selected row.
						int row = selection.getRow();
						message += "row " + row + " selected";
					} else {
						// unreachable
						message += "Pie chart selections should be either row selections or cell selections.";
						message += "  Other visualizations support column selections as well.";
					}
				}

				Window.alert(message);
			}
		};
	}

	private AbstractDataTable createTable(List<Operation> operations) {
		DataTable data = DataTable.create();
	    data.addColumn(ColumnType.DATETIME, "Year");
	    data.addColumn(ColumnType.NUMBER, "Остатки");
	    int counter = 0;
	    if (operations != null) {
			data.addRows(operations.size());
			for (Operation op : operations) {
				data.setValue(counter, 0, op.getDate());
				data.setValue(counter, 1, op.getSum());
				counter++;
			}
		}	    
	    return data;

	}

	@Override
	public void showLoader(boolean visible) {
		messagePanel.setVisible(visible);
		pieHolder.setVisible(!visible);
	}

	@Override
	public void showData(List<Operation> operations) {
		pie.draw(createTable(operations));
	}

	@Override
	public void clearData() {
		//pie.draw(createTable(null));
	}
}
