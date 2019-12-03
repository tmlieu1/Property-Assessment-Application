package ca.macewan.cmpt305;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	// initializing variables
	private List<Property> APIdata;
	private String chartType = "";
	private String dataType = "";
	private Chart graph;
	private VBox chartVBox;
	
	/*
	 * Consturctor for ChartBuilder Class
	 * Will use the parameters VBox, List<Property>
	 */
	public ChartBuilder(VBox chartVBox, List<Property> data) {
		this.chartVBox = chartVBox;
		this.APIdata = data;
		this.graph = new PieChart();
	}
	
	/*
	 * Purpose: Create a map, from factoring the Assessment class
	 * Parameters: None
	 * Returns: Map<String, Integer>
	 *  
	 */
	public Map<String, Integer> createMapAssClass() {
		Map<String, Integer> map = new HashMap<String,Integer>();
		String name;
		// if the data is empty go to the next line of data
		for (int i = 0; i < this.APIdata.size(); i++) {
			if (this.APIdata.get(i).getAssessedClass().contentEquals("")) {
				continue;
			}
			// try and catch for adding or appending new keys to the map
			try {
				// if the name exists inside the map
				name = this.APIdata.get(i).getAssessedClass();
				int val = map.get(this.APIdata.get(i).getAssessedClass());
				// replace value with an incremented one
				map.replace(name,++val);
				
			} catch (Exception e) {
				// adds new key to map
				name = this.APIdata.get(i).getAssessedClass();
				map.put(name, 1);
			}
		}
		return map;
	}
	
	
	/*
	 * Purpose: Create a map, from factoring the Ward area
	 * Parameters: None
	 * Return: Map<String, Integer>
	 * 
	 */
	public Map<String, Integer> createMapWard() {
		Map<String, Integer> map = new HashMap<String,Integer>();
		String name;
		// similar to the assessment class one
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
	
	/*
	 * Purpose: Create a map from factoring the Neighbourhoods
	 * Parameter: None
	 * Return: Map<String, Integer>
	 */
	public Map<String, Integer> createMapNeigh() {
		Map<String, Integer> map = new HashMap<String,Integer>();
		String name;
		// similar to the ones above
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

	/*
	 * Purpose: This function will create a chart depending on the type of chart and data
	 * 			and returns the chart
	 * Parameter: None
	 * Return: Chart Class
	 * 
	 */
	@SuppressWarnings({ "unchecked" })
	public Chart createChart(){
		System.out.println("create chart");
		System.out.println(this.chartType);
		System.out.println(this.chartType.contentEquals("Bar"));
		// initializes null chart as an empty chart
		PieChart null_chart = new PieChart();
		if (this.dataType.contentEquals("") || this.chartType.contentEquals("")) {
			// if the data does not exist then it cannot produce a graph
			return null_chart;
		}
		// create a buffer map
		Map<String, Integer> chartData = new HashMap<String, Integer>();
		// find which data type is chosen
		if (this.dataType == "Neighbourhood") {
			chartData = createMapNeigh();
		}
		else if (this.dataType == "Assessment Class") {
			chartData = createMapAssClass();
		}
		else {
			chartData = createMapWard();
		}
		// if the chart type is pie
		if (this.chartType.contentEquals("Pie")) {
			PieChart pieChart = new PieChart();
			System.out.println("Im baking pie");
			// gets all the keys in the map
			Set<String> keys = chartData.keySet();
			// for loop to add all the data to the pie chart
			for (String key: keys){
				pieChart.getData().add(new PieChart.Data(key, chartData.get(key)));
			}
			return pieChart;
		}
		// if chart type is Bar
		else if (this.chartType.contentEquals("Bar")) {
			System.out.println("Bar is here");
			final CategoryAxis xAxis = new CategoryAxis();
			final NumberAxis yAxis = new NumberAxis();
			// buffer barChart
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
			if (this.graph == null) {
				throw new NullPointerException("Error Null");
			}
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
