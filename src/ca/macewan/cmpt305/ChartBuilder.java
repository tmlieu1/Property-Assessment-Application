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
public class ChartBuilder {
	private List<Property> APIdata;
	private String chartType = "";
	private String dataType = "";
	private Chart graph;
	private VBox chartVBox;
	
	public ChartBuilder(VBox chartVBox) throws IOException, JSONException {
		ApiEdmonton API = new ApiEdmonton();
		API.getUrl();
		this.chartVBox = chartVBox;
		this.APIdata = API.getExtractedAPIData(API.getbr());
		this.graph = new PieChart();
	}

	public Map<String, Integer> createMapAssClass() {
		Map<String, Integer> map = new HashMap<String,Integer>();
		String name;
		for (int i = 0; i < this.APIdata.size(); i++) {
			if (this.APIdata.get(i).getAssessedClass().contentEquals("")) {
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
		for (int i = 0; i < this.APIdata.size()-1; i++) {
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

	@SuppressWarnings({ "null", "unchecked" })
	public Chart createChart(){
		PieChart null_chart = new PieChart();
		if (this.dataType.contentEquals("") || this.chartType.contentEquals("")) {
			return null_chart;
		}
		System.out.println(dataType);
		System.out.println(chartType);
		Map<String, Integer> chartData = new HashMap<String, Integer>();	
		if (this.dataType == "Neighbourhood") {
			chartData = createMapNeigh();
			System.out.println(chartData);
		}
		else if (this.dataType == "Assessment Class") {
			chartData = createMapAssClass();
		}
		else {
			chartData = createMapWard();
		}
		
		if (this.chartType.contentEquals("Pie")) {
			PieChart pieChart = new PieChart();
			System.out.println("Im baking pie");
			Set<String> keys = chartData.keySet();
			for (String key: keys){
				pieChart.getData().add(new PieChart.Data(key, chartData.get(key)));
			}
			return pieChart;
		}
		else if (this.chartType.contentEquals("Bar")) {
			final CategoryAxis xAxis = new CategoryAxis();
			final NumberAxis yAxis = new NumberAxis();
			BarChart<String, Number> barChart = new BarChart<String, Number>(xAxis,yAxis);
			barChart.setTitle("Bar Graph");
			xAxis.setLabel(this.dataType);
			yAxis.setLabel("Value");
			System.out.println("im in the milky way");
			XYChart.Series<String, Number> bar = new XYChart.Series<String, Number>();
			System.out.println("found the 3 musketters");
			Set<String> keys = chartData.keySet();
			for (String key: keys) {
				bar.getData().add(new XYChart.Data<String, Number>(key, chartData.get(key)));
			}
			barChart.getData().addAll(bar);
			System.out.println("Thats a candy bar");
			return barChart;
		}
		else {
			System.out.println("OMG");
			return (null_chart);
		}
}

	public Chart getChart() {
		return this.graph;
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
			System.out.println("Help");
			this.chartType = chartComboBox.valueProperty().getValue();
			this.dataType = dataComboBox.valueProperty().getValue();
			this.graph = createChart();
			this.chartVBox.getChildren().clear();
			this.chartVBox.getChildren().add(this.graph);
			
		});
		hBoxBtn.getChildren().add(confirmBtn);
		//vbox for the charts
		VBox vBoxCharts = new VBox(10);
		vBoxCharts.setStyle("-fx-padding: 10;" +
				"-fx-border-style: solid inside;" +
				"-fx-border-width: 1;" +
				"-fx-border-insets: 10, 10, 10, 10;" +
				"-fx-border-color: lightgray;");
		vBoxCharts.getChildren().addAll(labelChoice, chartComboBox);
		vBoxCharts.getChildren().addAll(labelTypeData, dataComboBox, hBoxBtn);
		return vBoxCharts;
	}
	
	
}
