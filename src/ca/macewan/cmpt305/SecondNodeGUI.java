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
	 * @throws JSONException 
	 * @throws IOException 
	 * */
	public BorderPane Pane() throws IOException, JSONException {
		InputGUI input = new InputGUI(filteredData, rawData, file);
		FilteredList<Property> data = input.getFiltered();
		//configures the input vbox
		VBox vBoxCharts = new VBox(10);
		vBoxCharts.setStyle("-fx-padding: 50;" +
				"-fx-border-style: solid inside;" +
				"-fx-border-width: 1;" +
				"-fx-border-insets: 10, 10, 10, 10;" +
				"-fx-border-color: lightgray;");
		ChartBuilder chartData = new ChartBuilder(vBoxCharts, data);
<<<<<<< HEAD
		VBox vBoxIn = input.configureInput();
		vBoxIn.getChildren().add(chartData.createInputBox());
=======
		VBox vBoxIn = chartData.createInputBox();
		
>>>>>>> 47d89ad59d9562263e02bd368a3b6bd3a18cb0e8
		//vbox for the charts
		//configures the borderpane 
		BorderPane secNode = new BorderPane();
		vBoxIn.prefWidthProperty().bind(secNode.widthProperty().multiply(0.22));
		vBoxCharts.prefWidthProperty().bind(secNode.maxWidthProperty().multiply(0.78));
		secNode.setLeft(vBoxIn);
		secNode.setCenter(vBoxCharts);
		
		return secNode;
	}
}