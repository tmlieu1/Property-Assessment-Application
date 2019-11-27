package ca.macewan.cmpt305;

import java.io.File;
import java.util.List;

//javafx imports
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/* Add constructor for the chart gui
 * 
 * */
public class SecondNodeGUI {
	
	private FilteredList <Property> filteredData;
	private List <Property> rawData;
	private File file;
	private InputGUI input;
	
	public SecondNodeGUI(FilteredList<Property> filteredData, List<Property> rawData, 
			File file, InputGUI input) {
		this.filteredData = filteredData;
		this.rawData = rawData;
		this.file = file;
	}
	
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
		
		//vbox
		VBox vBoxCharts = new VBox(10);
		vBoxCharts.setStyle("-fx-padding: 10;" +
				"-fx-border-style: solid inside;" +
				"-fx-border-width: 1;" +
				"-fx-border-insets: 10, 10, 10, 10;" +
				"-fx-border-color: lightgray;");
		vBoxCharts.getChildren().add(labelChart);
		
		//BorderPane secondNode
		VBox vBoxIn = input.configureInput();
		vBoxIn.getChildren().addAll(labelChoice, chartComboBox, button);
		
		//borderpane
		BorderPane secNode = new BorderPane();
		vBoxIn.prefWidthProperty().bind(secNode.widthProperty().multiply(0.22));
		vBoxCharts.prefWidthProperty().bind(secNode.maxWidthProperty().multiply(0.78));
		secNode.setLeft(vBoxIn);
		secNode.setCenter(vBoxCharts);
		
		return secNode;
	}
}
