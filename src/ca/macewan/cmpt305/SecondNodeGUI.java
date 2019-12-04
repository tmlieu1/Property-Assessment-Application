package ca.macewan.cmpt305;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.json.JSONException;

//javafx imports
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.Node;
import javafx.scene.chart.Chart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Represents the chart node for the controller GUI
 * */
public class SecondNodeGUI {
	
	private Chart chart;
	private FilteredList <Property> filteredData;
	private List <Property> rawData;
	private TextArea statistics;
	private VBox chartInput;
	private File file;
	private ApiEdmonton API;
	
	/**
	 * Initializes the data for the class
	 * @param filteredData
	 * @param rawData
	 * @param file
	 * */
	public SecondNodeGUI(FilteredList<Property> filteredData, 
			List<Property> rawData, File file, ApiEdmonton API) {
		this.filteredData = filteredData;
		this.rawData = rawData;
		this.file = file;
		this.API = API;
	}
	
	/**
	 * Constructs the BorderPane and returns it.
	 * @return
	 * @throws JSONException 
	 * @throws IOException 
	 * */
	public BorderPane Pane() {
		//secondnode, input and chart configure
		BorderPane secNode = new BorderPane();
		InputGUI input = new InputGUI(filteredData, rawData, file, API);
		chart = input.configureChart();
		chart.prefHeightProperty().bind(secNode.heightProperty().multiply(0.96));
		
		//separator and statistics textarea configured
		Separator sep = new Separator();
		statistics = input.configureStats();
		
		
		//vboxes are constructed and populated
		VBox vBoxIn = input.configureInput();
		VBox vBoxChartInput = input.configureChartInput();
		VBox vBoxChart = input.getChartBox();
		vBoxChart.setStyle("-fx-padding: 10;" +
				"-fx-border-style: solid inside;" +
				"-fx-border-width: 1;" +
				"-fx-border-insets: 10, 10, 10, 10;" +
				"-fx-border-color: lightgray;");
		vBoxIn.prefWidthProperty().bind(secNode.widthProperty().multiply(0.18));
		vBoxChart.prefWidthProperty().bind(secNode.widthProperty().multiply(0.82));
		vBoxChart.prefHeightProperty().bind(secNode.heightProperty());
		vBoxIn.getChildren().addAll(sep, statistics, vBoxChartInput);
		
		secNode.setLeft(vBoxIn);
		secNode.setCenter(vBoxChart);
		return secNode;
	}
}