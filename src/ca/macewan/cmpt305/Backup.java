/* Student Name: Lou Lieu
 * Property Assessment Data 2019
 * Lab Section: CMPT 305-X02L
 * CMPT 305, Fall 2019
 */

/*
package ca.macewan.cmpt305;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;

public class Property{
	//main method
	public static void main (String[] args) {
		String data = getInput("Property_Assessment_Data_2019.csv", "CSV filename [press enter for default data]: ");
		try {
			PropertyAssessment [] propertyValues = readFile(data);
			displayStats(propertyValues);
			findByAcc(propertyValues);
			PropertyAssessment [] nbhValues = getNBHData(propertyValues);
			displayStats(nbhValues);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//gathers the data from methods given a PropertyAssessment array and displays the output
	public static void displayStats(PropertyAssessment [] propertyValues) {
		int n = getNum(propertyValues);
		int min = getMin(propertyValues);
		int max = getMax(propertyValues);
		long range = getRange(max, min);
		long mean = getMean(propertyValues);
		int sd = getSD(propertyValues, mean);
		int median = getMedian(propertyValues);
		System.out.println("n: " + n +
				"\nmin: " + min + 
				"\nmax: " + max +
				"\nrange: " + range +
				"\nmean: " + mean +
				"\nstandard deviation: " + sd +
				"\nmedian: " + median);
	}
	
	//finds a property by account number in the array and displays information about it
	public static void findByAcc(PropertyAssessment [] values) {
		int n = 0; 
		int accNum = Integer.parseInt(getInput("10202374", "\nFind a property assessment by account number: "));
		while (n < values.length) {
			if (accNum == values[n].getAccountNum()) {
				System.out.println("Account number = " + values[n].getAccountNum() + 
						"\nAddress = " + values[n].getAddress() +
						"\nAssessed Value = $" + values[n].getAssessedVal() +
						"\nAssessment Class = " + values[n].getAssessedClass() +
						"\nNeighbourhood = " + values[n].getNeighbourhood() +
						"\nLocation = " + values[n].getLocation());
				return;
			}
			n++;
		}
	}
	
	//gets the number of records of a neighbourhood
	public static int getNBHNum(PropertyAssessment[] values, String nbh) {
		int n = 0;
		for(int i = 0; i < values.length; i++) {
			if (values[i].getNeighbourhood().getNBHName().equals(nbh)) {
				n++;
			}
		}
		return n;
	}
	
	public static int getACNum(PropertyAssessment[] values, String assessmentClass) {
		int n = 0 ;
		for (int i = 0; i < values.length; i++) {
			if (values[i].getAssessedClass().equals(assessmentClass)) {
				n++;
			}
		}
		return n;
	}
	
	//gets the data of a neighbourhood, returning it as an array of PropertyAssessment
	public static PropertyAssessment [] getNBHData(PropertyAssessment[] values){
		String nbh = getInput("WEBBER GREENS", "\nNeighbourhood: "); 
		int numRecords = getNBHNum(values, nbh);
		int n = 0;
		PropertyAssessment [] pa = new PropertyAssessment[numRecords];
		for(int i = 0; i < values.length; i++) {
			if (values[i].getNeighbourhood().getNBHName().equals(nbh)) {
				pa[n] = new PropertyAssessment(values[i].getAccountNum(), values[i].getAddress(), values[i].getAssessedVal(),
						values[i].getAssessedClass(), values[i].getNeighbourhood(), values[i].getLocation());
				n++;
			}
		}
		Arrays.sort(pa);
		return pa;
	}
	
	//combine all input methods to come from the same method.
	public static String getInput(String defaultCase, String inputMessage) {
		Scanner s = new Scanner(System.in);
		System.out.print(inputMessage);
		String input = s.nextLine();
		if (input != "" && input.length() != 0) {
			defaultCase = input;
		}
		return defaultCase;
	}
	
	//gets number of records in the csv file
	public static int getNumRecords(String filename) throws IOException{
		Scanner file = new Scanner(Paths.get(filename));
		if (file.hasNextLine()) {
			file.nextLine(); //ignore the header
		}
		int n = 0;
		while (file.hasNextLine()) {
			file.nextLine();
			n++;
		}
		file.close();
		return n;
	}
	
	//reads through the file and returns the data as a PropertyAssessment array
	public static PropertyAssessment [] readFile(String filename) throws IOException {
		int numRecords = getNumRecords(filename);
		PropertyAssessment [] pa = new PropertyAssessment[numRecords];
		int n = 0;
		Scanner file = new Scanner(Paths.get(filename));
		if (file.hasNextLine()) {
			file.nextLine();
		}
		while (file.hasNextLine()) {
			String currentLine = file.nextLine();
			Scanner tokenizer = new Scanner(currentLine).useDelimiter(",");
			String [] temp = new String[12];
			for (int i = 0; i < 12 && tokenizer.hasNext(); i++) {
				String test = tokenizer.next();
				temp[i] = test;
			}
			Address addr = new Address(temp[1], temp[2], temp[3]); 
			Location loc = new Location(Double.parseDouble(temp[10]), Double.parseDouble(temp[11]));
			Neighbourhood nbh = new Neighbourhood(temp[6], temp[7], temp[8]);
			pa[n] = new PropertyAssessment(Integer.parseInt(temp[0]), addr, Integer.parseInt(temp[4]), temp[5], nbh, loc);
			n++;	
		}
		Arrays.sort(pa);
		return pa;
	}
	
	//returns the number of entries in the array
	public static int getNum(PropertyAssessment [] values) {
		return values.length;
	}
	
	//returns the smallest value in the array
	public static int getMin(PropertyAssessment [] values) {
		int min = 2147483647;
		for(int i = 0; i < values.length; i++) {
			if (values[i].getAssessedVal() < min)
				min = values[i].getAssessedVal();
		}
		return min;
	}
	
	//returns the largest value in the array
	public static int getMax(PropertyAssessment [] values) {
		int max = 0;
		for(int i = 0; i < values.length; i++) {
			if (values[i].getAssessedVal() > max)
				max = values[i].getAssessedVal();
		}
		return max;
	}
	
	//returns the range of the array
	public static long getRange(int max, int min) {
		return max - min;
	}
	
	//returns the mean of the array
	public static long getMean(PropertyAssessment [] values) {
		long sum = 0;
		for(int i = 0; i < values.length; i++) {
			sum += values[i].getAssessedVal();
		}
		long mean = (sum/values.length);
		return mean;
	}
	
	//returns the standard deviation of the array
	public static int getSD(PropertyAssessment [] values, long mean) {
		int standardDev = 0;
		long sum = 0;
		for(int i = 0; i < values.length; i++) {
			sum += Math.pow(Math.abs(values[i].getAssessedVal() - mean), 2);
		}
		standardDev = (int) Math.round((Math.sqrt(sum/values.length)));
		return standardDev;
	}
	
	//returns the median of the array
	public static int getMedian(PropertyAssessment [] values) {
		int median = values[(values.length/2)].getAssessedVal();
		return median;
	}
}

*/




//PROPERTY ASSESSMENT GUI
/*
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

//implemented in Milestone 3
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;

//java utilities
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;

public class PropertyAssessmentGUI extends Application {
	
	NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
	private List<Property> rawData;
	private TableView<Property> table = new TableView<Property>();;
	private ObservableList<Property> data;
	private FilteredList<Property> filteredData;
	private SortedList<Property> sortedData;
	private TextArea statistics;
	private TextField accNumField;
	private TextField addrField;
	private TextField nbhField;
	private ComboBox<String> classComboBox;
	private VBox vBoxIn;
	private VBox vBox;
	
	//added in milestone 3
	private TextField lowerValField;
	private TextField upperValField;
	private Button button;
	private File file;
	private Label labelCurr;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void start(Stage primaryStage) throws Exception {
		//populate data
		file = new File("Property_Assessment_Data_2019.csv");
		populateData(file.getName());
		
		//table
		configureTable();
		table.prefHeightProperty().bind(primaryStage.heightProperty().multiply(0.90));
		
		//input vBox
		InputGUI input = new InputGUI(filteredData, rawData, file);
		VBox vBoxIn = input.configureInput();
		Separator sep2 = new Separator();
		vBoxIn.getChildren().addAll(sep2);
		vBoxIn.prefWidthProperty().bind(primaryStage.widthProperty().multiply(0.22));
		vBoxIn.prefHeightProperty().bind(primaryStage.heightProperty().multiply(0.90));
		
		//right vBox
		configureRight();
		vBox.prefWidthProperty().bind(primaryStage.widthProperty().multiply(0.78));
		
		//BorderPane rootNode
		BorderPane rootNode = new BorderPane();
		rootNode.setLeft(vBoxIn);
		rootNode.setCenter(vBox);
		
		//BorderPane secondNode
		SecondNodeGUI secNode = new SecondNodeGUI();
		BorderPane secondNode = secNode.Pane(filteredData, rawData, file);
		
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
	
	//Creates the text to be shown in the statistics field
	public String getStatistics(FilteredList<Property> data) {
		if (data.size() == 0) {
			return "";
		}
		int min = Statistics.getMin(data);
		int max = Statistics.getMax(data);
		long mean = Statistics.getMean(data);
		return "Statistics of Assessed Values\n" + 
				"\nNumber of Properties: " + Statistics.getNum(data) + "\nMin: " + format(min) + 
				"\nMax: " + format(max) + "\nRange: " + format(Statistics.getRange(max, min)) +
				"\nMean: " + format(mean) + "\nMedian: " + format(Statistics.getMedian(data)) + 
				"\nStandard Deviation: " + format(Statistics.getSD(data, mean));
	}
	
	//configures the look of the table vbox
	private void configureRight() {
		//vBox
		final Label label = new Label("Edmonton Property Assessments");
		label.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		vBox = new VBox(10);
		vBox.setStyle("-fx-padding: 10;" +
				"-fx-border-style: solid inside;" +
				"-fx-border-width: 1;" +
				"-fx-border-insets: 10, 10, 10, 10;" +
				"-fx-border-color: lightgray;");
		vBox.getChildren().addAll(label, table);
	}
	
	@SuppressWarnings("unchecked")
	private void configureTable() {
		TableColumn <Property, Integer> accNumCol = new TableColumn<Property, Integer>("Account");
		accNumCol.prefWidthProperty().bind(table.widthProperty().multiply(0.07));
		accNumCol.setCellValueFactory(new Callback<CellDataFeatures<Property, Integer>, ObservableValue<Integer>>() {
	        @Override
	        public ObservableValue<Integer> call(CellDataFeatures<Property, Integer> p) {
	            return new SimpleIntegerProperty(p.getValue().getAccountNum()).asObject();                
	        }
		});
		
		TableColumn <Property, String> addressCol = new TableColumn<Property, String>("Address");
		addressCol.prefWidthProperty().bind(table.widthProperty().multiply(0.25));
		addressCol.setCellValueFactory(new Callback<CellDataFeatures<Property, String>, ObservableValue<String>>() {
	        @Override
	        public ObservableValue<String> call(CellDataFeatures<Property, String> p) {
	            return new SimpleStringProperty(p.getValue().getAddress().toString());                
	        }
		}); 
		
		TableColumn <Property, String> valCol = new TableColumn<Property, String>("Assessed Value");
		valCol.prefWidthProperty().bind(table.widthProperty().multiply(0.11));
		valCol.setCellValueFactory(new Callback<CellDataFeatures<Property, String>, ObservableValue<String>>() {
	        @Override
	        public ObservableValue<String> call(CellDataFeatures<Property, String> p) {
	            return new SimpleStringProperty(format(p.getValue().getAssessedVal()));                
	        }
		});
		
		TableColumn <Property, String > classCol = new TableColumn<Property, String>("Assessed Class");
		classCol.prefWidthProperty().bind(table.widthProperty().multiply(0.11));
		classCol.setCellValueFactory(new Callback<CellDataFeatures<Property, String>, ObservableValue<String>>() {
	        @Override
	        public ObservableValue<String> call(CellDataFeatures<Property, String> p) {
	            return new SimpleStringProperty(p.getValue().getAssessedClass());                
	        }
		});
		
		TableColumn <Property, String> nbhCol = new TableColumn<Property, String>("Neighbourhood");
		nbhCol.prefWidthProperty().bind(table.widthProperty().multiply(0.18));
		nbhCol.setCellValueFactory(new Callback<CellDataFeatures<Property, String>, ObservableValue<String>>() {
	        @Override
	        public ObservableValue<String> call(CellDataFeatures<Property, String> p) {
	            return new SimpleStringProperty(p.getValue().getNBHName());                
	        }
		});
		
		TableColumn <Property, Double> latCol = new TableColumn<Property, Double>("Latitude");
		latCol.prefWidthProperty().bind(table.widthProperty().multiply(0.13));
		latCol.setCellValueFactory(new Callback<CellDataFeatures<Property, Double>, ObservableValue<Double>>() {
	        @Override
	        public ObservableValue<Double> call(CellDataFeatures<Property, Double> p) {
	            return new SimpleDoubleProperty(p.getValue().getLocation().getLatitude()).asObject();                
	        }
		}); 
		
		TableColumn <Property, Double> longCol = new TableColumn<Property, Double>("Longitude");
		longCol.prefWidthProperty().bind(table.widthProperty().multiply(0.14));
		longCol.setCellValueFactory(new Callback<CellDataFeatures<Property, Double>, ObservableValue<Double>>() {
	        @Override
	        public ObservableValue<Double> call(CellDataFeatures<Property, Double> p) {
	            return new SimpleDoubleProperty(p.getValue().getLocation().getLongitude()).asObject();                
	        }
		}); 
		
		table.setItems(sortedData);
		sortedData.comparatorProperty().bind(table.comparatorProperty());
		table.getColumns().setAll(accNumCol, addressCol, valCol, classCol, nbhCol, latCol, longCol);
	}
	
	//Converts to currencyFormatter, stripping cent values.
	public String format(long num) {
		currencyFormatter.setMaximumFractionDigits(0);
		String cf = currencyFormatter.format(num);
		return cf;
	}
	
	//search
	private void search() {
		String accNum = accNumField.getText().strip();
		String addr = addrField.getText().strip();
		String nbh = nbhField.getText().strip().toUpperCase();
		String res = classComboBox.getValue();
		int lower = lowerValField.getText() == "" ? 0 : Integer.parseInt(lowerValField.getText());
		int upper = upperValField.getText() == "" ? 0 : Integer.parseInt(upperValField.getText());
		int min = Statistics.getMin(rawData);
		
		//assigns predicate properties to the filtered data based on fields and comboboxes
		filteredData.predicateProperty().bind(Bindings.createObjectBinding(() ->
	    p -> Integer.toString(
	    		  p.getAccountNum()).contains(accNum)
	           && p.getAddress().toString().contains(addr) 
	           && p.getNBHName().contains(nbh)
	           && (lower == 0 ? p.getAssessedVal() >= min : p.getAssessedVal() >= lower)
	           && (upper == 0 ? p.getAssessedVal() >= min : p.getAssessedVal() <= upper)
	           && (res == "" ? p.getAssessedClass().contains(res) : p.getAssessedClass().equals(res)),

	    accNumField.textProperty(),
	    addrField.textProperty(),
	    nbhField.textProperty(),
	    lowerValField.textProperty(),
	    upperValField.textProperty(),
	    classComboBox.valueProperty()
	));

	statistics.clear();
	statistics.setText(getStatistics(filteredData));
	}
	
	//Search button handling
	private class SearchButtonListener implements EventHandler <MouseEvent>{
		@Override
		public void handle(MouseEvent event) {
			search();
		}
	}
	
	//Reset
	private void reset() {
		classComboBox.valueProperty().set("");
		accNumField.clear();
		addrField.clear();
		nbhField.clear();
		lowerValField.setText("0");
		upperValField.setText("0");
	}
	
	//Reset Button handling
	private class ResetButtonListener implements EventHandler <MouseEvent>{
		@Override
		public void handle(MouseEvent event) {
			reset();
			search();
		}
	}
	
	//configures the look of the input vbox
		private void configureInput() {
			//vBox input labels
			final Label labelIn = new Label("Find Property Assessment");
			labelIn.setFont(Font.font("Arial", FontWeight.BOLD, 16));
			final Label labelAcc = new Label("Account Number:");
			labelAcc.setFont(new Font("Arial", 12));
			final Label labelAddr = new Label("Address (#suite #house street):");
			labelAddr.setFont(new Font("Arial", 12));
			final Label labelNBH = new Label("Neighbourhood:");
			labelNBH.setFont(new Font("Arial", 12));
			final Label labelClass = new Label("Assessment Class:");
			labelNBH.setFont(new Font("Arial", 12));
			final Label labelVal = new Label("Assessment Value");
			labelVal.setFont(new Font("Arial", 12));
			
			//button and current file label
			final Label labelFile = new Label("Current File");
			labelFile.setFont(Font.font("Arial", FontWeight.BOLD, 16));
			labelCurr = new Label(file.getName());
			labelCurr.setFont(new Font("Arial", 12));
			button = new Button("Select File");
			
			//integer filter
			UnaryOperator<Change> intFilter = change -> {
				String newText = change.getControlNewText();
				if (newText.matches("^[0-9]*+$|^$")) {
					return change;
				}
				return null;
			};
			
			//currency
			lowerValField = new TextField();
			upperValField = new TextField();
			lowerValField.setTextFormatter(new TextFormatter<Integer> (new IntegerStringConverter(), 0, intFilter));
			upperValField.setTextFormatter(new TextFormatter<Integer> (new IntegerStringConverter(), 0, intFilter));
			HBox hBoxCur = new HBox(10);
			hBoxCur.getChildren().addAll(lowerValField, upperValField);
			
			//vBox input comboBox
			ObservableList<String> options = FXCollections.observableArrayList(
					"Farmland",
					"Residential",
					"Non Residential",
					"Other Residential"
					);
			classComboBox = new ComboBox<String>(options);
			classComboBox.setValue("");
			
			//textfields
			accNumField = new TextField();
			accNumField.setTextFormatter(new TextFormatter<Integer> (new IntegerStringConverter(), null, intFilter));
			addrField = new TextField();
			nbhField = new TextField();
			
			//hBox btn
			HBox hBoxBtn = new HBox(10);
			Button searchBtn = new Button("Search");
			Button resetBtn = new Button("Reset");
			searchBtn.setOnMouseClicked(new SearchButtonListener());
			resetBtn.setOnMouseClicked(new ResetButtonListener());
			hBoxBtn.getChildren().addAll(searchBtn, resetBtn);
			
			//separator and textarea
			Separator sep1 = new Separator();
			Separator sep2 = new Separator();
			statistics = new TextArea();
			statistics.setEditable(false);
			statistics.setText(getStatistics(filteredData));
			
			//vBoxIn
			vBoxIn = new VBox(10);
			vBoxIn.setStyle("-fx-padding: 10;" +
					"-fx-border-style: solid inside;" +
					"-fx-border-width: 1;" +
					"-fx-border-insets: 10, 10, 10, 10;" +
					"-fx-border-color: lightgray;");
			vBoxIn.getChildren().addAll(labelFile, labelCurr, button, sep1, labelIn, labelAcc, 
					accNumField, labelAddr, addrField, labelNBH, nbhField, labelVal, hBoxCur,
					labelClass, classComboBox, hBoxBtn, sep2, statistics);
		}
	
	//populates the data and wraps it.
	public void populateData(String filename) {
		rawData = FileReader.getTableData(filename);
		data = FXCollections.observableArrayList(rawData);
		filteredData = new FilteredList<Property>(data);
		sortedData = new SortedList<Property>(filteredData);
	}
}
*/