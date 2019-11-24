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
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;
//implemented in Milestone 3
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

//java utilities
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.ArrayList;
import java.util.Collections;
import java.io.IOException;
import java.text.NumberFormat;

public class PropertyAssessmentGUI extends Application {
	
	NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
	private List<Property> rawData = getTableData();
	private TableView<Property> table = new TableView<Property>();
	private ObservableList<Property> data = FXCollections.observableArrayList(rawData);
	private FilteredList<Property> filteredData = new FilteredList<Property>(data);
	private SortedList<Property> sortedData = new SortedList<Property>(filteredData);
	private TextArea statistics;
	private TextField accNumField;
	private TextField addrField;
	private TextField nbhField;
	private TextField lowerValField;
	private TextField upperValField;
	private ComboBox<String> classComboBox;
	private VBox vBoxIn;
	private VBox vBox;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void start(Stage primaryStage) throws Exception {
		//table
		configureTable();
		table.prefHeightProperty().bind(primaryStage.heightProperty().multiply(0.90));

		//input vBox
		configureInput();
		vBoxIn.prefWidthProperty().bind(primaryStage.widthProperty().multiply(0.22));
		vBoxIn.prefHeightProperty().bind(primaryStage.heightProperty().multiply(0.90));
		
		//right vBox
		configureRight();
		vBox.prefWidthProperty().bind(primaryStage.widthProperty().multiply(0.78));
		
		//border pane
		BorderPane rootNode = new BorderPane();
		rootNode.setLeft(vBoxIn);
		rootNode.setCenter(vBox);
		
		//webview
		WebView map = new WebView();
		WebEngine engine = map.getEngine();
		String url = this.getClass().getResource("/ca/macewan/cmpt305/website.html").toExternalForm();
		engine.load(url);
		
		//tabs
		TabPane tabPane = new TabPane();
		Tab tab1 = new Tab("Table", rootNode);
		tab1.setClosable(false);
		Tab tab2 = new Tab("Map", map);
		tab2.setClosable(false);
		tabPane.getTabs().addAll(tab1, tab2);
		
		//scene
		primaryStage.setTitle("Edmonton Property Assessments");
		Scene scene = new Scene(tabPane);
		primaryStage.setMaximized(true);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	//configures the look of the table vbox
	private void configureRight() {
		//vBox
		final Label label = new Label("Edmonton Property Assessments");
		label.setStyle("-fx-font-weight: bold");
		label.setFont(new Font("Arial", 16));
		vBox = new VBox(10);
		vBox.setStyle("-fx-padding: 10;" +
				"-fx-border-style: solid inside;" +
				"-fx-border-width: 1;" +
				"-fx-border-insets: 10, 10, 10, 10;" +
				"-fx-border-color: lightgray;");
		vBox.getChildren().addAll(label, table);
	}
	
	//configures the look of the input vbox
	private void configureInput() {
		//vBox input labels
		final Label labelIn = new Label("Find Property Assessment");
		labelIn.setFont(new Font("Arial", 16));
		labelIn.setStyle("-fx-font-weight: bold");
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
		
		//currency
		UnaryOperator<Change> intFilter = change -> {
			String newText = change.getControlNewText();
			if (newText.matches("[0-9]*")) {
				return change;
			}
			return null;
		};
		
		lowerValField = new TextField();
		upperValField = new TextField();
		lowerValField.setTextFormatter(new TextFormatter<Integer> (new IntegerStringConverter(), 0, intFilter));
		upperValField.setTextFormatter(new TextFormatter<Integer> (new IntegerStringConverter(), 0, intFilter));
		HBox hBoxCur = new HBox(10);
		hBoxCur.getChildren().addAll(lowerValField, upperValField);
		
		//vBox input comboBox
		ObservableList<String> options = FXCollections.observableArrayList(
				"Residential",
				"Non Residential",
				"Other Residential"
				);
		classComboBox = new ComboBox<String>(options);
		classComboBox.setValue("");
		
		//textfields
		accNumField = new TextField();
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
		Separator separator = new Separator();
		statistics = new TextArea();
		statistics.setEditable(false);
		
		//vBoxIn
		vBoxIn = new VBox(10);
		vBoxIn.setStyle("-fx-padding: 10;" +
				"-fx-border-style: solid inside;" +
				"-fx-border-width: 1;" +
				"-fx-border-insets: 10, 10, 10, 10;" +
				"-fx-border-color: lightgray;");
		vBoxIn.getChildren().addAll(labelIn, labelAcc, accNumField,
				labelAddr, addrField, labelNBH, nbhField, labelVal, hBoxCur,
				labelClass, classComboBox, hBoxBtn, separator, statistics);
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
	
	//Search button handling
	private class SearchButtonListener implements EventHandler <MouseEvent>{
		@Override
		public void handle(MouseEvent event) {
			String accNum = accNumField.getText().strip();
			String addr = addrField.getText().strip();
			String nbh = nbhField.getText().strip().toUpperCase();
			String res = classComboBox.getValue();
			int lower = Integer.parseInt(lowerValField.getText());
			int upper = Integer.parseInt(upperValField.getText());
			int min = Lab2Main.getMin(rawData);
			
			/*TO DO: REPLACE GETASSESSEDVAL MIN CHECK 
			 * 
			 * 
			 * */
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
	}
	
	//Reset Button handling
	private class ResetButtonListener implements EventHandler <MouseEvent>{
		@Override
		public void handle(MouseEvent event) {
			classComboBox.valueProperty().set("");
			accNumField.clear();
			addrField.clear();
			nbhField.clear();
			lowerValField.setText("0");
			upperValField.setText("0");
		}
	}
	
	//Creates the text to be shown in the statistics field
	public String getStatistics(FilteredList<Property> data) {
		if (data.size() == 0) {
			return "";
		}
		int min = Lab2Main.getMin(data);
		int max = Lab2Main.getMax(data);
		long mean = Lab2Main.getMean(data);
		return "Statistics of Assessed Values\n" + 
				"\nNumber of Properties: " + Lab2Main.getNum(data) + "\nMin: " + format(min) + 
				"\nMax: " + format(max) + "\nRange: " + format(Lab2Main.getRange(max, min)) +
				"\nMean: " + format(mean) + "\nMedian: " + format(Lab2Main.getMedian(data)) + 
				"\nStandard Deviation: " + format(Lab2Main.getSD(data, mean));
	}
	
	//Converts to currencyFormatter, stripping cent values.
	public String format(long num) {
		currencyFormatter.setMaximumFractionDigits(0);
		String cf = currencyFormatter.format(num);
		return cf;
	}
	
	//Creates a List of Properties from the file.
	public List<Property> readFile(String filename){
		List <Property> pa = new ArrayList<>();
		try {
			pa = Lab2Main.readFile(filename);
			return pa;
		}
		catch (IOException e) {
			return pa;
		}
	}
	
	//Returns the List of Properties
	public List<Property> getTableData() {
		String file = "Property_Assessment_Data_2019.csv";
		List <Property> propertyValues = readFile(file);
		return propertyValues;
	}
}