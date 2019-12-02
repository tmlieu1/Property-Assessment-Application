package ca.macewan.cmpt305;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;

import org.json.JSONException;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;

public class InputGUI {
	
	//data
	private Map<String, Integer> map;
	private FilteredList <Property> filteredData;
	private List <Property> rawData;
	private ObservableList<Property> data;
	private SortedList <Property> sortedData;
	private File file;
	private ApiEdmonton API;
	
	//inputs
	private TextField accNumField;
	private TextField addrField;
	private TextField nbhField;
	private ComboBox<String> classComboBox;
	private TextField lowerValField;
	private TextField upperValField;
	
	//displays
	private VBox vBoxIn;
	private TextArea statistics;
	private Button button;
	private Button buttonJSON;
	private FileChooser fileChooser;
	private Label labelCurr;
	private TableView <Property> table;
	
	/**
	 * Initializes the data for the class.
	 * @param filteredData
	 * @param rawData
	 * @param file
	 * */
	public InputGUI(FilteredList<Property> filteredData, List <Property> rawData, File file) {
		this.filteredData = filteredData;
		this.rawData = rawData;
		this.file = file;
		this.statistics = new TextArea();
		table = new TableView<Property>();
		rawFileData(file.getName());
		populateData();
		API = new ApiEdmonton();
	}
	
	public FilteredList<Property> getFiltered() {
		return this.filteredData;
	}

	public VBox configureInput() {
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
		
		//integer filter
		UnaryOperator<Change> intFilter = change -> {
			String newText = change.getControlNewText();
			if (newText.matches("^[0-9]*+$|^$")) {
				return change;
			}
			return null;
		};
		
		//currency textfields
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
		
		//property textfields
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
		
		//button and current file label
		final Label labelFile = new Label("Current Dataset");
		labelFile.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		labelCurr = new Label(file.getName());
		labelCurr.setFont(new Font("Arial", 12));
		
		//filechooser
		button = new Button("Select File");
		fileChooser = new FileChooser();
		button.setOnAction(e -> {
			file = fileChooser.showOpenDialog(null);
			rawFileData(file.getName());
			populateData();
			labelCurr.setText(file.getName());
			updateTable();
			reset();
			search();
		});
		
		//jsonchooser
		buttonJSON = new Button("Load data from API");
		buttonJSON.setOnAction(e ->{
			System.out.println("Test");
			try {
				rawJSONData();
			} catch (IOException | JSONException | NullPointerException e1) {
				e1.printStackTrace();
			}
			populateData();
			labelCurr.setText("https://data.edmonton.ca/resource/q7d6-ambg.json?$limit=401117");
			updateTable();
			reset();
			search();
		});
		
		//button hbox
		HBox hBox = new HBox(10);
		hBox.getChildren().addAll(button, buttonJSON);
		
		//separator
		Separator sep1 = new Separator();
		
		//vBoxIn configuration
		vBoxIn = new VBox(10);
		vBoxIn.setStyle("-fx-padding: 10;" +
				"-fx-border-style: solid inside;" +
				"-fx-border-width: 1;" +
				"-fx-border-insets: 10, 10, 10, 10;" +
				"-fx-border-color: lightgray;");
		vBoxIn.getChildren().addAll(labelFile, labelCurr, hBox, sep1, labelIn, labelAcc, 
				accNumField, labelAddr, addrField, labelNBH, nbhField, labelVal, hBoxCur,
				labelClass, classComboBox, hBoxBtn);
		
		return vBoxIn;
	}
	
	/**
	 * populates and updates the columns of the table in the class and returns it as a table.
	 * @return
	 * */
	@SuppressWarnings("unchecked")
	public TableView <Property> configureTable() {
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
	            return new SimpleStringProperty(CurrencyFormatter.format(p.getValue().getAssessedVal()));                
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
		table.getColumns().setAll(accNumCol, addressCol, valCol, classCol, nbhCol, latCol, longCol);
		updateTable();
		return table;
	}
	
	/**
	 * updates the table based on sorted data.
	 * */
	private void updateTable() {
		table.setItems(sortedData);
		sortedData.comparatorProperty().bind(table.comparatorProperty());
	}
	
	/**
	 * configures the statistics textarea and returns it
	 * @return
	 * */
	public TextArea configureStats() {
		statistics.setEditable(false);
		statistics.setText(getStatistics(filteredData));
		return statistics;
	}
	
	/**
	 * creates the text to be shown in the statistics field and returns it as a String
	 * @return
	 * */
	public String getStatistics(FilteredList<Property> data) {
		if (data.size() == 0) {
			return "";
		}
		int min = Statistics.getMin(data);
		int max = Statistics.getMax(data);
		long mean = Statistics.getMean(data);
		return "Statistics of Assessed Values\n" + 
				"\nNumber of Properties: " + Statistics.getNum(data) + "\nMin: " + CurrencyFormatter.format(min) + 
				"\nMax: " + CurrencyFormatter.format(max) + "\nRange: " + CurrencyFormatter.format(Statistics.getRange(max, min)) +
				"\nMean: " + CurrencyFormatter.format(mean) + "\nMedian: " + CurrencyFormatter.format(Statistics.getMedian(data)) + 
				"\nStandard Deviation: " + CurrencyFormatter.format(Statistics.getSD(data, mean));
	}
	
	//search
	/**
	 * search function helper for the searchbuttonlistener
	 * */
	private void search() {
		//initialize the textfields into data types.
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
	//update the statistics class	
	statistics.clear();
	statistics.setText(getStatistics(filteredData));
	}
	
	/**
	 * search button listener utilizing the search function
	 * */
	private class SearchButtonListener implements EventHandler <MouseEvent>{
		@Override
		public void handle(MouseEvent event) {
			search();
		}
	}
	
	/**
	 * reset helper function for the resetbuttonlistener
	 * */
	private void reset() {
		//clears all of the textfields and the combobox
		classComboBox.valueProperty().set("");
		accNumField.clear();
		addrField.clear();
		nbhField.clear();
		lowerValField.setText("0");
		upperValField.setText("0");
	}
	
	/**
	 * reset button listener utilizing the reset function
	 * */
	private class ResetButtonListener implements EventHandler <MouseEvent>{
		@Override
		public void handle(MouseEvent event) {
			reset();
			search();
		}
	}
	
	public void rawFileData(String filename){
		System.out.println("Okay");
		rawData = FileReader.getTableData(filename);
	}
	
	public void rawJSONData() throws IOException, JSONException{
		System.out.println("-1: API");
		rawData = API.getAPIData();
	}
	
	/**
	 * populates the data of the class using a given filename.
	 * */
	public void populateData() {
		data = FXCollections.observableArrayList(rawData);
		filteredData = new FilteredList<Property>(data);
		sortedData = new SortedList<Property>(filteredData);
	}
}