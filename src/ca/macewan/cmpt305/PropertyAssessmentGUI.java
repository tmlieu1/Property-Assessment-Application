package ca.macewan.cmpt305;

//javafx imports
//import javafx.application.Application;
import javafx.application.*;
import javafx.collections.transformation.FilteredList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.image.Image;

//java utilities
import java.util.List;

import java.io.File;
import java.text.NumberFormat;

public class PropertyAssessmentGUI extends Application {
	
	NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
	private List<Property> rawData;
	private FilteredList<Property> filteredData;
	private File file;
	private ApiEdmonton YEG;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void start(Stage primaryStage) throws Exception {
		//preloader
		final Stage preloaderStage = new Stage();
		PreloaderGUI pre = new PreloaderGUI();
		pre.start(preloaderStage);
		
		//fetch API data
		YEG = new ApiEdmonton();
		
		//populate data
		file = new File("Property_Assessment_Data_2019.csv");
		
		//BorderPane rootNode
		RootNodeGUI tableNode = new RootNodeGUI(filteredData, rawData, file, YEG);
		BorderPane rootNode = tableNode.Pane();
		
		//BorderPane secondNode
		SecondNodeGUI chartNode = new SecondNodeGUI(filteredData, rawData, file, YEG);
		BorderPane secondNode = chartNode.Pane();
	
		//webview
		WebView map = new WebView();
		WebEngine engine = map.getEngine();
		String url = this.getClass().getResource("/ca/macewan/cmpt305/website.html").toExternalForm();
		engine.load(url);
		
		//tabs
		TabPane tabPane = new TabPane();
		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		Tab tab1 = new Tab("Table", rootNode);
		Tab tab2 = new Tab("Charts", secondNode);
		Tab tab3 = new Tab("Map", map);
		tabPane.getTabs().addAll(tab1, tab2, tab3);
		
		//scene
		Scene scene = new Scene(tabPane);
		scene.getStylesheets().add("path/stylesheet.css");
		primaryStage.setTitle("Edmonton Property Assessments");
		primaryStage.getIcons().add(new Image("file:edmonton-logo.png"));
		primaryStage.setMaximized(true);
		primaryStage.setScene(scene);
		primaryStage.show();
		preloaderStage.close();
	}
}