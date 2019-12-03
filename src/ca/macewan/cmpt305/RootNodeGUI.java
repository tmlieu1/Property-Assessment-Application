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
/**
 * Represents the table node for the controller GUI. 
 */
public class RootNodeGUI {
	
	private TableView<Property> table;
	private FilteredList <Property> filteredData; 
	List <Property> rawData;
	private TextArea statistics;
	private File file;
	
	/**
	 * Initializes the data for the class
	 * @param filteredData
	 * @param rawData
	 * @param file
	 * */
	public RootNodeGUI(FilteredList <Property> filteredData, 
			List <Property> rawData, File file) {
		this.filteredData = filteredData;
		this.rawData = rawData;
		this.file = file;
	}
	
	/**
	 * Constructs the BorderPane and returns it.
	 * @return
	 * */
	public BorderPane Pane() {
		//rootnode, input and table configure
		BorderPane rootNode = new BorderPane();
		InputGUI input = new InputGUI(filteredData, rawData, file);
		table = input.configureTable();
		table.prefHeightProperty().bind(rootNode.heightProperty().multiply(0.96));
		
		//separator and statistics textarea configured
		Separator sep = new Separator();
		statistics = input.configureStats();
		
		//both vboxes are constructed and populated
		VBox vBoxIn = input.configureInput();
		VBox vBoxTable = configureRight();
		vBoxIn.prefWidthProperty().bind(rootNode.widthProperty().multiply(0.18));
		vBoxTable.prefWidthProperty().bind(rootNode.widthProperty().multiply(0.82));
		vBoxIn.getChildren().addAll(sep, statistics);
		
		//aligns the vboxes in the rootnode and returns it
		rootNode.setLeft(vBoxIn);
		rootNode.setCenter(vBoxTable);
		return rootNode;	
	}
		
	/**
	 * Configures the look of the table VBox and returns it.
	 * @return
	 * */
	private VBox configureRight() {
		//creates the label
		final Label label = new Label("Edmonton Property Assessments");
		label.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		
		//creates the label, sets the style, and populates it
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