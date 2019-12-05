package ca.macewan.cmpt305;

import java.io.File;
import java.text.NumberFormat;
import java.util.ArrayList;
//java utilities
import java.util.List;

//javafx imports
//import javafx.application.Application;
import javafx.application.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.Worker.State;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

//java utilities
import java.util.List;

import java.io.File;
import java.text.NumberFormat;

/**
 * Main controller to display all relevant modules
 */
public class PropertyAssessmentGUI extends Application {
	
	NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
	private List<Property> rawData;
	private FilteredList<Property> filteredData;
	private File file;
	private ApiEdmonton YEG;
	
	/**
	 * Launch arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
	/**
	 * Start function displays the window when instantiated.
	 */
	public void start(Stage primaryStage) throws Exception {
		//preloader
		final Stage preloaderStage = new Stage();
		PreloaderGUI pre = new PreloaderGUI();
		pre.start(preloaderStage);
		
		//start
		TabPane tabPane = new TabPane();
		Scene scene = new Scene(tabPane);
		
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
		engine.getLoadWorker().stateProperty().addListener(
				new ChangeListener<State>() {
		            public void changed(ObservableValue ov, State oldState, State newState) {
		            	if(newState == State.SUCCEEDED) {
		            		engine.executeScript("main()");
//		                    //engine.executeScript("addPoint(0,-26.487000,151.984000)");
//		                	InputGUI input = new InputGUI(filteredData, rawData, file, YEG);
//		            		List<Property> rawerData = input.getRawData();
//		            		double[] lat = new double[rawerData.size()];
//		            		double[] lon = new double[rawerData.size()];
//		            		for(int i = 0; i < rawerData.size(); i++) {
////		            			lat[i] = rawerData.get(i).getLocation().getLatitude();
////		            			lon[i] = rawerData.get(i).getLocation().getLongitude();
//		            			engine.executeScript("setHeatMap(\"" + rawerData.get(i).getLocation().getLatitude() + "\", \"" + rawerData.get(i).getLocation().getLongitude() + "\")");
//		            		}
//		            		
//		            		engine.executeScript("createHeatMap()");
		                }
		            }
		        });
		engine.load(url);
		
		//options
		Image sun = new Image("file:sun.png");
		Image moon = new Image("file:moon.png");
		ImageView option = new ImageView();
		option.setImage(moon);
		
		//on image click, clear and change the css
		option.setOnMouseClicked((MouseEvent e) ->{
			if (option.getImage().equals(moon)) {
				scene.getStylesheets().clear();
				scene.getStylesheets().add(getClass().getResource("stylesheetdark.css").toString());
				option.setImage(sun);
			}
			else if (option.getImage().equals(sun)) {
				scene.getStylesheets().clear();
				scene.getStylesheets().add(getClass().getResource("stylesheetlight.css").toString());
				option.setImage(moon);
			}
		});
		
		//bind the image to a borderpane and set it
		BorderPane optionNode = new BorderPane();
		option.setPreserveRatio(true);
		option.fitWidthProperty().bind(optionNode.widthProperty().multiply(0.75));
		option.fitHeightProperty().bind(optionNode.heightProperty().multiply(0.75));
		optionNode.setCenter(option);
		
		//tabs
		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		Tab tab1 = new Tab("Table", rootNode);
		Tab tab2 = new Tab("Charts", secondNode);
		Tab tab3 = new Tab("Map", map);
		Tab tab4 = new Tab("Options", optionNode);
		tabPane.getTabs().addAll(tab1, tab2, tab3, tab4);
		
		//stage
		scene.getStylesheets().add(getClass().getResource("stylesheetlight.css").toString());
		primaryStage.setTitle("Edmonton Property Assessments");
		primaryStage.getIcons().add(new Image("file:edmonton-logo.png"));
		primaryStage.setMaximized(true);
		primaryStage.setScene(scene);
		primaryStage.show();
		preloaderStage.close();
	}
}