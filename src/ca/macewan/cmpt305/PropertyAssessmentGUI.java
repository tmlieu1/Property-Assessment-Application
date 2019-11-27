package ca.macewan.cmpt305;

//javafx imports
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;

//java utilities
import java.util.List;
import java.io.File;
import java.text.NumberFormat;

public class PropertyAssessmentGUI extends Application {
	
	NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
	private List<Property> rawData;
	private ObservableList<Property> data;
	private FilteredList<Property> filteredData;
	private SortedList<Property> sortedData;
	private File file;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void start(Stage primaryStage) throws Exception {
		//populate data
		file = new File("Property_Assessment_Data_2019.csv");
		populateData(file.getName());
		
		//Input node
		InputGUI input = new InputGUI(filteredData, rawData, file);
		
		//BorderPane rootNode
		RootNodeGUI tableNode = new RootNodeGUI(filteredData, rawData, sortedData, file, input);
		BorderPane rootNode = tableNode.Pane();
		
		//BorderPane secondNode
		SecondNodeGUI chartNode = new SecondNodeGUI(filteredData, rawData, file, input);
		BorderPane secondNode = chartNode.Pane();
		
		//BorderPane thirdNode
		BorderPane thirdNode = new BorderPane();
		
		//webview
		WebView map = new WebView();
		WebEngine engine = map.getEngine();
		String url = this.getClass().getResource("/ca/macewan/cmpt305/website.html").toExternalForm();
		engine.load(url);
		
		//tabs
		TabPane tabPane = new TabPane();
		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		Tab tab1 = new Tab("Table", rootNode);
		Tab tab2 = new Tab("Map", map);
		Tab tab3 = new Tab("Charts", secondNode);
		Tab tab4 = new Tab("Comparison", thirdNode);
		tabPane.getTabs().addAll(tab1, tab2, tab3, tab4);
		
		//scene
		primaryStage.setTitle("Edmonton Property Assessments");
		Scene scene = new Scene(tabPane);
		primaryStage.setMaximized(true);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	//populates the data and wraps it.
	public void populateData(String filename) {
		rawData = FileReader.getTableData(filename);
		data = FXCollections.observableArrayList(rawData);
		filteredData = new FilteredList<Property>(data);
		sortedData = new SortedList<Property>(filteredData);
	}
}