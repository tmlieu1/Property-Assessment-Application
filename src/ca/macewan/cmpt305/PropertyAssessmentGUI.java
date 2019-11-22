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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

//implemented in Milestone 3
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

//java utilities
import java.util.List;
import java.util.ArrayList;
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
	private Separator separator;
	private Button searchBtn;
	private Button resetBtn;
	private ComboBox<String> classComboBox;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void start(Stage primaryStage) throws Exception {
		//table
		configureTable();
		table.prefHeightProperty().bind(primaryStage.heightProperty().multiply(0.90));
		
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
		searchBtn = new Button("Search");
		resetBtn = new Button("Reset");
		searchBtn.setOnMouseClicked(new SearchButtonListener());
		resetBtn.setOnMouseClicked(event ->{
			classComboBox.valueProperty().set("");
			accNumField.clear();
			addrField.clear();
			nbhField.clear();
		});
		hBoxBtn.getChildren().addAll(searchBtn, resetBtn);
		
		//separator and textarea
		separator = new Separator();
		statistics = new TextArea();
		statistics.setEditable(false);
		
		//vBox input
		VBox vBoxIn = new VBox(10);
		vBoxIn.setStyle("-fx-padding: 10;" +
				"-fx-border-style: solid inside;" +
				"-fx-border-width: 1;" +
				"-fx-border-insets: 10, 10, 10, 10;" +
				"-fx-border-color: lightgray;");
		vBoxIn.getChildren().addAll(labelIn, labelAcc, accNumField,
				labelAddr, addrField, labelNBH, nbhField, 
				labelClass, classComboBox, hBoxBtn, separator, statistics);
		vBoxIn.prefWidthProperty().bind(primaryStage.widthProperty().multiply(0.22));
		vBoxIn.prefHeightProperty().bind(primaryStage.heightProperty().multiply(0.90));
		
		//vBox
		primaryStage.setTitle("Edmonton Property Assessments");
		final Label label = new Label("Edmonton Property Assessments");
		label.setStyle("-fx-font-weight: bold");
		label.setFont(new Font("Arial", 16));
		VBox vBox = new VBox(10);
		vBox.setStyle("-fx-padding: 10;" +
				"-fx-border-style: solid inside;" +
				"-fx-border-width: 1;" +
				"-fx-border-insets: 10, 10, 10, 10;" +
				"-fx-border-color: lightgray;");
		vBox.getChildren().addAll(label, table);
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
		Scene scene = new Scene(tabPane);
		primaryStage.setMaximized(true);
		primaryStage.setScene(scene);
		primaryStage.show();
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
	
	private class SearchButtonListener implements EventHandler <MouseEvent>{
		@Override
		public void handle(MouseEvent event) {
			String accNum = accNumField.getText().strip();
			String addr = addrField.getText().strip();
			String nbh = nbhField.getText().strip().toUpperCase();
			String res = classComboBox.getValue();
			
			filteredData.predicateProperty().bind(Bindings.createObjectBinding(() ->
		    p -> Integer.toString(
		    		  p.getAccountNum()).contains(accNum)
		           && p.getAddress().toString().contains(addr) 
		           && p.getNBHName().contains(nbh)	
		           && (res == "" ? p.getAssessedClass().contains(res) : p.getAssessedClass().equals(res)),

		    accNumField.textProperty(),
		    addrField.textProperty(),
		    nbhField.textProperty(),
		    classComboBox.valueProperty()
		));

		statistics.clear();
		statistics.setText(getStatistics(filteredData));
		}
	}
	
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
	
	public String format(long num) {
		String cf = currencyFormatter.format(num);
		return cf;
	}
	
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
	
	public List<Property> getTableData() {
		String file = "Property_Assessment_Data_2019.csv";
		List <Property> propertyValues = readFile(file);
		return propertyValues;
	}
}