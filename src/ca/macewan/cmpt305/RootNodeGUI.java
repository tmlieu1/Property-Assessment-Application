package ca.macewan.cmpt305;

import java.io.File;
import java.util.List;

import javafx.collections.transformation.FilteredList;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
/* Add constructor for the rootnode (table, input, etc)
 * 
 * */
public class RootNodeGUI {
	
	private TableView<Property> table;
	private FilteredList <Property> filteredData; 
	List <Property> rawData;
	private TextArea statistics;
	private File file;
	
	public RootNodeGUI(FilteredList <Property> filteredData, 
			List <Property> rawData, File file) {
		this.filteredData = filteredData;
		this.rawData = rawData;
		this.file = file;
	}
	
	public BorderPane Pane() {
		BorderPane rootNode = new BorderPane();
		InputGUI input = new InputGUI(filteredData, rawData, file);
		table = input.configureTable();
		table.prefHeightProperty().bind(rootNode.heightProperty().multiply(0.90));
		
		Separator sep = new Separator();
		statistics = input.configureStats();
		
		VBox vBoxIn = input.configureInput();
		VBox vBoxTable = configureRight();
		vBoxIn.prefWidthProperty().bind(rootNode.widthProperty().multiply(0.22));
		vBoxTable.prefWidthProperty().bind(rootNode.widthProperty().multiply(0.78));
		vBoxIn.getChildren().addAll(sep, statistics);
		
		rootNode.setLeft(vBoxIn);
		rootNode.setCenter(vBoxTable);
		return rootNode;
		
	}
		
	//configures the look of the table vbox
	private VBox configureRight() {
		
		//vBox
		final Label label = new Label("Edmonton Property Assessments");
		label.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		VBox vBox = new VBox(10);
		vBox.setStyle("-fx-padding: 10;" +
				"-fx-border-style: solid inside;" +
				"-fx-border-width: 1;" +
				"-fx-border-insets: 10, 10, 10, 10;" +
				"-fx-border-color: lightgray;");
		vBox.getChildren().addAll(label, table);
		
		return vBox;
	}

}
