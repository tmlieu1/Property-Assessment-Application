package ca.macewan.cmpt305;

import java.io.File;
import java.util.List;
import java.util.Map;

//javafx imports
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Represents the chart node for the controller GUI
 * */
public class SecondNodeGUI {
	
	private FilteredList <Property> filteredData;
	private List <Property> rawData;
	private File file;
	
	/**
	 * Initializes the data for the class
	 * @param filteredData
	 * @param rawData
	 * @param file
	 * */
	public SecondNodeGUI(FilteredList<Property> filteredData, List<Property> rawData, File file) {
		this.filteredData = filteredData;
		this.rawData = rawData;
		this.file = file;
	}
	
	/**
	 * Constructs the BorderPane and returns it.
	 * @return
	 * */
	public BorderPane Pane() {
		//labels
		final Label labelChoice = new Label("Chart Selection");
		labelChoice.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		final Label labelChart = new Label("Charts");
		labelChart.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		
		//comboBox and button
		ObservableList<String> options = FXCollections.observableArrayList(
				"Chart 1",
				"Chart 2",
				"Chart 3",
				"Chart 4"
				);
		ComboBox<String>chartComboBox = new ComboBox<String>(options);
		chartComboBox.setValue("");
		Button button = new Button("Confirm");
		
		//vbox for the charts
		VBox vBoxCharts = new VBox(10);
		vBoxCharts.setStyle("-fx-padding: 10;" +
				"-fx-border-style: solid inside;" +
				"-fx-border-width: 1;" +
				"-fx-border-insets: 10, 10, 10, 10;" +
				"-fx-border-color: lightgray;");
		vBoxCharts.getChildren().add(labelChart);
		
		//configures the input vbox
		InputGUI input = new InputGUI(filteredData, rawData, file);
		VBox vBoxIn = input.configureInput();
		vBoxIn.getChildren().addAll(labelChoice, chartComboBox, button);
		ChartBuilder chartData= new ChartBuilder(input.getFiltered());
		Map<String,Integer> map = chartData.createMapAssClass();
		Map<String,Integer> map1 = chartData.createMapNeigh();
		Map<String,Integer> mape2 = chartData.createMapWard();
		System.out.println(map);
		System.out.println(map1);
		System.out.println(mape2);
		
		//configures the borderpane
		BorderPane secNode = new BorderPane();
		vBoxIn.prefWidthProperty().bind(secNode.widthProperty().multiply(0.22));
		vBoxCharts.prefWidthProperty().bind(secNode.maxWidthProperty().multiply(0.78));
		secNode.setLeft(vBoxIn);
		secNode.setCenter(vBoxCharts);
		
		return secNode;
	}
}