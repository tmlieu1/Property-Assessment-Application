package ca.macewan.cmpt305;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.Chart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
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
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;

public class InputGUI {
	
	//data
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
	private FileChooser fileChooser;
	private Label labelCurr;
	private VBox vBoxIn;
	private TextArea statistics;
	private Button button;
	private Button buttonJSON;
	
	//table
	private TableView <Property> table;
	
	//chart
	private VBox vBoxChart;
	private String chartType = "";
	private String dataType = "";
	private Chart chart;
	
	/**
	 * Initializes the data for the class.
	 * @param filteredData
	 * @param rawData
	 * @param file
	 * */
	public InputGUI(FilteredList<Property> filteredData, List <Property> rawData, File file, ApiEdmonton API) {
		this.filteredData = filteredData;
		this.rawData = rawData;
		this.file = file;
		this.statistics = new TextArea();
		this.API = API;
		table = new TableView<Property>();
		rawFileData(file.getName());
		populateData();
	}
	public FilteredList<Property> getFiltered() {
		return this.filteredData;
	}
	/**
	 * Configures the input VBox and returns it.
	 * @return
	 * */
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
		String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
		fileChooser.setInitialDirectory(new File(currentPath));
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
		rawData = FileReader.getTableData(filename);
	}
	
	public void rawJSONData() throws IOException, JSONException{
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


	/******************************************************************************************************************
	 * CHART STUFF
	 ******************************************************************************************************************/
	/*
	 * Purpose: Create a map, from factoring the Assessment class
	 * Parameters: None
	 * Returns: Map<String, Integer>
	 *  
	 */
	public Map<String, Integer> createMapAssClass() {
		Map<String, Integer> map = new HashMap<String,Integer>();
		String name;
		// if the data is empty go to the next line of data
		for (int i = 0; i < this.filteredData.size(); i++) {
			if (this.filteredData.get(i).getAssessedClass().contentEquals("")) {
				continue;
			}
			// try and catch for adding or appending new keys to the map
			try {
				// if the name exists inside the map
				name = this.filteredData.get(i).getAssessedClass();
				int val = map.get(this.filteredData.get(i).getAssessedClass());
				// replace value with an incremented one
				map.replace(name,++val);
				
			} catch (Exception e) {
				// adds new key to map
				name = this.filteredData.get(i).getAssessedClass();
				map.put(name, 1);
			}
		}
		return map;
	}
	
	
	/*
	 * Purpose: Create a map, from factoring the Ward area
	 * Parameters: None
	 * Return: Map<String, Integer>
	 * 
	 */
	public Map<String, Integer> createMapWard() {
		Map<String, Integer> map = new HashMap<String,Integer>();
		String name;
		// similar to the assessment class one
		for (int i = 0; i < this.filteredData.size()-1; i++) {
			if (this.filteredData.get(i).getNeighbourhood().getNBHWard().contentEquals("")) {
				continue;
			}
			try {
				name = this.filteredData.get(i).getNeighbourhood().getNBHWard();
				int val = map.get(this.filteredData.get(i).getNeighbourhood().getNBHWard());
				map.replace(name,++val);
				
			} catch (Exception e) {
				name = this.filteredData.get(i).getNeighbourhood().getNBHWard();
				map.put(name, 1);
			}
		}
		return map;
	}
	
	/*
	 * Purpose: Create a map from factoring the Neighbourhoods
	 * Parameter: None
	 * Return: Map<String, Integer>
	 */
	public Map<String, Integer> createMapNeigh() {
		Map<String, Integer> map = new HashMap<String,Integer>();
		String name;
		// similar to the ones above
		for (int i = 0; i < this.filteredData.size(); i++) {
			if (this.filteredData.get(i).getNBHName().contentEquals("")) {
				continue;
			}
			try {
				name = this.filteredData.get(i).getNBHName();
				int val = map.get(this.filteredData.get(i).getNBHName());
				map.replace(name,++val);
				
			} catch (Exception e) {
				name = this.filteredData.get(i).getNBHName();
				map.put(name, 1);
			}
		}
		return map;
	}

	/**
	 * creates a hashmap based on the data choice
	 * @param dataChoice
	 * @return
	 */
	private Map<String, Integer> getChartData(String dataChoice){
		Map <String, Integer> chartData = new HashMap<String, Integer>();
		if (dataChoice == "Neighbourhood") {
			chartData = createMapNeigh();
		}
		else if (dataChoice == "Assessment Class") {
			chartData = createMapAssClass();
		}
		else {
			chartData = createMapWard();
		}
		return chartData;
	}
	
	/*
	 * Purpose: This function will create a chart depending on the type of chart and data
	 * 			and returns the chart
	 * Parameter: None
	 * Return: Chart Class
	 * 
	 */
	@SuppressWarnings({ "unchecked" })
	public Chart configureChart(){
		System.out.println("create chart");
		System.out.println(chartType);
		System.out.println(chartType.contentEquals("Bar"));
		
		// initializes null chart as an empty chart
		PieChart null_chart = new PieChart();
		if (dataType.contentEquals("") || chartType.contentEquals("")) {
			// if the data does not exist then it cannot produce a graph
			return null_chart;
		}
		
		// create a buffer map
		Map<String, Integer> chartData = getChartData(dataType);
		
		// if the chart type is pie, get all the keys in the map and add all the data to the pie chart.
		if (chartType.contentEquals("Pie")) {
			PieChart pieChart = new PieChart();
			System.out.println("Im baking pie");
			// gets all the keys in the map
			Set<String> keys = chartData.keySet();
			// for loop to add all the data to the pie chart
			for (String key: keys){
				pieChart.getData().add(new PieChart.Data(key, chartData.get(key)));
			}
			return pieChart;
		}
		
		// if chart type is Bar
		else if (this.chartType.contentEquals("Bar")) {
			System.out.println("Bar is here");
			final CategoryAxis xAxis = new CategoryAxis();
			final NumberAxis yAxis = new NumberAxis();
			xAxis.setLabel(this.dataType);
			yAxis.setLabel("Amount");
			// buffer barChart
			BarChart<String, Number> barChart = new BarChart<String, Number>(xAxis,yAxis);
			barChart.setTitle("Bar Graph");
			System.out.println("im in the milky way");
			XYChart.Series<String, Number> bar = new XYChart.Series<String, Number>();
			System.out.println("found the 3 musketters");
			Set<String> keys = chartData.keySet();
			for (String key: keys) {
				bar.getData().add(new XYChart.Data<String, Number>(key, chartData.get(key)));
			}
			barChart.getData().addAll(bar);
			System.out.println("Thats a candy bar");
			return barChart;
		}
		else {
			System.out.println("OMG");
			return null_chart;
		}
	}
	
	public VBox configureChartInput() {
		vBoxChart = new VBox(10);
		vBoxChart.setAlignment(Pos.CENTER);
		//labels
		final Label labelChoice = new Label("Chart Selection");
		labelChoice.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		final Label labelChart = new Label("Charts");
		labelChart.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		final Label labelTypeData = new Label("Data Type Selection");
		labelTypeData.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		final Label labelData = new Label("Data Type");
		labelData.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		
		//comboBox for chart type
		ObservableList<String> options = FXCollections.observableArrayList(
				"Pie",
				"Bar"
				);
		ComboBox<String>chartComboBox = new ComboBox<String>(options);
		chartComboBox.setValue("");
		
		//combobox for chart parameters
		ObservableList<String> optionData = FXCollections.observableArrayList(
				"Neighbourhood",
				"Assessment Class",
				"Ward");
		ComboBox<String> dataComboBox = new ComboBox<String>(optionData);
		dataComboBox.setValue("");
		
		//hbox and button
		HBox hBoxBtn = new HBox(10);
		Button confirmBtn = new Button("Confirm");
		hBoxBtn.getChildren().add(confirmBtn);
		confirmBtn.setOnAction(event -> {
			System.out.println("Help");
			chartType = chartComboBox.valueProperty().getValue();
			dataType = dataComboBox.valueProperty().getValue();
			chart = configureChart();
			if (chart == null) {
				throw new NullPointerException("Error Null");
			}
			vBoxChart.getChildren().clear();
			vBoxChart.getChildren().add(chart);
		});
		
		//vbox
		VBox vBoxChartInput = new VBox(10);
		vBoxChartInput.getChildren().addAll(labelChoice, chartComboBox,labelTypeData, dataComboBox, hBoxBtn);
		return vBoxChartInput;
	}
	
	public VBox getChartBox() {
		return vBoxChart;
	}
}