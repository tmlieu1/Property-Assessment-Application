package ca.macewan.cmpt305;

import java.io.File;
import java.text.NumberFormat;
import java.util.ArrayList;
//java utilities
import java.util.List;

//javafx imports
//import javafx.application.Application;
import javafx.application.*;
import javafx.collections.transformation.FilteredList;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
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
		//fetch API data
		
		YEG = new ApiEdmonton();
		
		//populate data
		file = new File("Property_Assessment_Data_2019.csv");
		
		//BorderPane rootNode
		RootNodeGUI tableNode = new RootNodeGUI(filteredData, rawData, file, YEG);
		BorderPane rootNode = tableNode.Pane();
		
		//BorderPane secondNode
		RootNodeGUI chartNode = new RootNodeGUI(filteredData, rawData, file, YEG);
		BorderPane secondNode = chartNode.Pane();
		
		//BorderPane thirdNode
		BorderPane thirdNode = new BorderPane();
		
		//webview
		WebView map = new WebView();
		WebEngine engine = map.getEngine();
		String url = getClass().getResource("/ca/macewan/cmpt305/website.html").toString();
		engine.load(url);
		InputGUI input = new InputGUI(filteredData, rawData, file, YEG);
		List<Property> rawerData = input.getRawData();
		double[] lat = new double[rawerData.size()];
		double[] lon = new double[rawerData.size()];
		for(int i = 0; i < rawerData.size(); i++) {
//			lat[i] = rawerData.get(i).getLocation().getLatitude();
//			lon[i] = rawerData.get(i).getLocation().getLongitude();
			engine.executeScript("document.setHeatMap(\"" + rawerData.get(i).getLocation().getLatitude() + "\", \"" + rawerData.get(i).getLocation().getLongitude() + "\")");
		}
		
		
		
//		MapController theMap = new MapController();
		
		
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
		primaryStage.getIcons().add(new Image("file:edmonton-logo.png"));
		Scene scene = new Scene(tabPane, Color.SLATEGRAY);
		primaryStage.setMaximized(true);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}