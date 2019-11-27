package ca.macewan.cmpt305;

//javafx imports
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;


/* Add constructor for the chart gui
 * 
 * */
public class SecondNodeGUI {
	
	private ComboBox<String> chartComboBox;
	
	public BorderPane Pane(FilteredList<Property> data) {
		
		
		
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
		VBox vBoxIn = new VBox(10);
		vBoxIn.setStyle("-fx-padding: 10;" +
				"-fx-border-style: solid inside;" +
				"-fx-border-width: 1;" +
				"-fx-border-insets: 10, 10, 10, 10;" +
				"-fx-border-color: lightgray;");
		VBox vBoxCharts = new VBox(10);
		vBoxCharts.setStyle("-fx-padding: 10;" +
				"-fx-border-style: solid inside;" +
				"-fx-border-width: 1;" +
				"-fx-border-insets: 10, 10, 10, 10;" +
				"-fx-border-color: lightgray;");
		vBoxIn.getChildren().addAll(labelChoice, chartComboBox, button);
		vBoxCharts.getChildren().add(labelChart);
		
		//borderpane
		BorderPane secNode = new BorderPane();
		vBoxIn.prefWidthProperty().bind(secNode.widthProperty().multiply(0.22));
		vBoxCharts.prefWidthProperty().bind(secNode.maxWidthProperty().multiply(0.78));
		secNode.setLeft(vBoxIn);
		secNode.setCenter(vBoxCharts);
		
		return secNode;
	}
}
