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
	private ApiEdmonton API;
	
	/**
	 * Initializes the data for the class
	 * @param filteredData
	 * @param rawData
	 * @param file
	 * */
	public SecondNodeGUI(FilteredList<Property> filteredData, List<Property> rawData, File file, ApiEdmonton API) {
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
	public BorderPane Pane() throws IOException, JSONException {
		//vbox for the charts
		//VBox vBoxCharts = new VBox(createPie("Ward"));
		
		//configures the input vbox
		ChartBuilder chartData= new ChartBuilder(rawData);
		Map<String,Integer> map = chartData.createMapAssClass();
		Map<String,Integer> map1 = chartData.createMapNeigh();
		Map<String,Integer> map2 = chartData.createMapWard();
		System.out.println(map);
		System.out.println(map1);
		System.out.println(map2);
		VBox vBoxIn = chartData.createInputBox();
		VBox vBoxCharts = chartData.getVbox();
		//configures the borderpane 
		BorderPane secNode = new BorderPane();
		vBoxIn.prefWidthProperty().bind(secNode.widthProperty().multiply(0.22));
		vBoxCharts.prefWidthProperty().bind(secNode.maxWidthProperty().multiply(0.78));
		secNode.setLeft(vBoxIn);
		secNode.setCenter(vBoxCharts);
		
		return secNode;
	}
}