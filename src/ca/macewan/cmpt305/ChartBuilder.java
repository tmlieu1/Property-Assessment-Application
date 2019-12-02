package ca.macewan.cmpt305;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONException;

import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

public class ChartBuilder {
	private List<Property> APIdata;
	private String chartType = "";
	private String dataType = "";
	private VBox graph;
	
	public ChartBuilder(List<Property> APIdata) throws IOException, JSONException {
		this.APIdata = APIdata;
		this.graph = createChartBox();
	}

	public Map<String, Integer> createMapAssClass() {
		Map<String, Integer> map = new HashMap<String,Integer>();
		String name;
		for (int i = 0; i < this.APIdata.size(); i++) {
			if (this.APIdata.get(i).getAssessedClass() == null) {
				continue;
			}
			try {
				name = this.APIdata.get(i).getAssessedClass();
				int val = map.get(this.APIdata.get(i).getAssessedClass());
				map.replace(name,++val);
				
			} catch (Exception e) {
				name = this.APIdata.get(i).getAssessedClass();
				map.put(name, 1);
			}
		}
		return map;
	}
	
	public Map<String, Integer> createMapWard() {
		Map<String, Integer> map = new HashMap<String,Integer>();
		String name;
		for (int i = 0; i < this.APIdata.size(); i++) {
			if (this.APIdata.get(i).getNeighbourhood().getNBHWard().contentEquals("")) {
				continue;
			}
			try {
				name = this.APIdata.get(i).getNeighbourhood().getNBHWard();
				int val = map.get(this.APIdata.get(i).getNeighbourhood().getNBHWard());
				map.replace(name,++val);
				
			} catch (Exception e) {
				name = this.APIdata.get(i).getNeighbourhood().getNBHWard();
				map.put(name, 1);
			}
		}
		return map;
	}
	
	public Map<String, Integer> createMapNeigh() {
		Map<String, Integer> map = new HashMap<String,Integer>();
		String name;
		for (int i = 0; i < this.APIdata.size(); i++) {
			if (this.APIdata.get(i).getNBHName().contentEquals("")) {
				continue;
			}
			try {
				name = this.APIdata.get(i).getNBHName();
				int val = map.get(this.APIdata.get(i).getNBHName());
				map.replace(name,++val);
				
			} catch (Exception e) {
				name = this.APIdata.get(i).getNBHName();
				map.put(name, 1);
			}
		}
		return map;
	}

	@SuppressWarnings("null")
	public Chart createChart(){
		if (this.dataType.contentEquals("") || this.chartType.contentEquals("")) {
			PieChart null_chart = new PieChart();
			return null_chart;
		}
		Map<String, Integer> chartData = new HashMap<String, Integer>();	
		if (this.dataType == "Neighbourhood") {
			chartData = createMapNeigh();
		}
		else if (this.dataType == "Assessment Class") {
			chartData = createMapAssClass();
		}
		else {
			chartData = createMapWard();
		}
		if (this.chartType.contentEquals("Pie")) {
			PieChart pieChart = new PieChart();
			Set<String> keys = chartData.keySet();
			for (String key: keys){
				pieChart.getData().add(new PieChart.Data(key, chartData.get(key)));
			}
			return pieChart;
		}
		return null;
	}

	public VBox getVbox() {
		return this.graph;
	}
	
	public VBox createChartBox() {
		VBox chartBox = new VBox(10);
		final Label labelCharts = new Label("Chart");
		labelCharts.setFont(Font.font("Arial",FontWeight.BOLD, 16));
		chartBox.setStyle("-fx-padding: 10;" +
				"-fx-border-style: solid inside;" +
				"-fx-border-width: 1;" +
				"-fx-border-insets: 10, 10, 10, 10;" +
				"-fx-border-color: lightgray;");
		Chart chart = createChart();
		chartBox.getChildren().addAll(chart);
		return chartBox;
	}
	
	public VBox createInputBox() {
		//labels
		final Label labelChoice = new Label("Chart Selection");
		labelChoice.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		final Label labelChart = new Label("Charts");
		labelChart.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		final Label labelTypeData = new Label("Data Type Selection");
		labelTypeData.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		final Label labelData = new Label("Data Type");
		labelData.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		
		//comboBox and button
		ObservableList<String> options = FXCollections.observableArrayList(
				"Pie",
				"Bar"
				);
		ComboBox<String>chartComboBox = new ComboBox<String>(options);
		chartComboBox.setValue("");
		ObservableList<String> optionData = FXCollections.observableArrayList(
				"Neighbourhood",
				"Assessment Class",
				"Ward");
		ComboBox<String> dataComboBox = new ComboBox<String>(optionData);
		dataComboBox.setValue("");
		HBox hBoxBtn = new HBox(10);
		Button confirmBtn = new Button("Confirm");
		confirmBtn.setOnAction(event -> {
			// gets data that was entered by the user
			this.chartType = chartComboBox.valueProperty().getValue();
			this.dataType = dataComboBox.valueProperty().getValue();
			this.graph = createChartBox();
		});
		hBoxBtn.getChildren().add(confirmBtn);
		//vbox for the charts
		VBox vBoxCharts = new VBox(10);
		vBoxCharts.setStyle("-fx-padding: 10;" +
				"-fx-border-style: solid inside;" +
				"-fx-border-width: 1;" +
				"-fx-border-insets: 10, 10, 10, 10;" +
				"-fx-border-color: lightgray;");
		//vBoxCharts.getChildren().add(labelChart);
		vBoxCharts.getChildren().addAll(labelChoice, chartComboBox);
		vBoxCharts.getChildren().addAll(labelTypeData, dataComboBox, hBoxBtn);
		return vBoxCharts;
	}
}