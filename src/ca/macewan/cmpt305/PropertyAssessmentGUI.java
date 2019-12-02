
package ca.macewan.cmpt305;

//javafx imports
import javafx.application.Application;
import javafx.collections.transformation.FilteredList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

//implemented in Milestone 3
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.image.Image;

//java utilities
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
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
		SecondNodeGUI chartNode = new SecondNodeGUI(filteredData, rawData, file, YEG);
		BorderPane secondNode = chartNode.Pane();
		
		//BorderPane thirdNode
		BorderPane thirdNode = new BorderPane();
		
		//webview
		WebView map = new WebView();
		WebEngine engine = map.getEngine();
		String googlemaps = this.getClass().getResource("/ca/macewan/cmpt305/website.html").toExternalForm();
		engine.load(googlemaps);
		
		//API
//		URL url =  new URL("https://data.edmonton.ca/resource/q7d6-ambg.json");
//		URLConnection con = url.openConnection();
//		InputStream is = con.getInputStream();
		br = getPropertyAPI("https://data.edmonton.ca/resource/q7d6-ambg.json");
		List<Property> vals = getExtractedAPIData(br);
		
		//tabs
		TabPane tabPane = new TabPane();
		Tab tab1 = new Tab("Table", rootNode);
		tab1.setClosable(false);
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