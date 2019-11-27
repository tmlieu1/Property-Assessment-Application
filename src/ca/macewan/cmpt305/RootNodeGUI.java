package ca.macewan.cmpt305;

import java.io.File;
import java.util.List;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Callback;
import java.text.NumberFormat;

/* Add constructor for the rootnode (table, input, etc)
 * 
 * */
public class RootNodeGUI {
	
	private NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
	private TableView<Property> table = new TableView<Property>();
	private FilteredList <Property> filteredData;
	private SortedList<Property> sortedData;
	private List <Property> rawData;
	private TextArea statistics;
	private File file;
	private InputGUI input;
	
	public RootNodeGUI(FilteredList <Property> filteredData, 
			List <Property> rawData, SortedList<Property> sortedData,
			File file, InputGUI input) {
		this.filteredData = filteredData;
		this.rawData = rawData;
		this.sortedData = sortedData;
		this.file = file;
		this.input = input;
	}
	
	public BorderPane Pane() {
		BorderPane rootNode = new BorderPane();
		configureTable();
		table.prefHeightProperty().bind(rootNode.heightProperty().multiply(0.90));
		
		Separator sep = new Separator();
		statistics = input.configureStats();
		
		VBox vBoxIn = input.configureInput();
		VBox vBoxTable = configureRight();
		vBoxIn.prefWidthProperty().bind(rootNode.widthProperty().multiply(0.22));
		vBoxTable.prefWidthProperty().bind(rootNode.widthProperty().multiply(0.78));
		vBoxIn.getChildren().addAll(sep, statistics);
		
		rootNode.setLeft(vBoxIn);
		rootNode.setCenter(vBoxTable);
		return rootNode;
		
	}
		
	//configures the look of the table vbox
	private VBox configureRight() {
		
		//vBox
		final Label label = new Label("Edmonton Property Assessments");
		label.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		VBox vBox = new VBox(10);
		vBox.setStyle("-fx-padding: 10;" +
				"-fx-border-style: solid inside;" +
				"-fx-border-width: 1;" +
				"-fx-border-insets: 10, 10, 10, 10;" +
				"-fx-border-color: lightgray;");
		vBox.getChildren().addAll(label, table);
		
		return vBox;
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
		
		table.setItems(sortedData);
		sortedData.comparatorProperty().bind(table.comparatorProperty());
		table.getColumns().setAll(accNumCol, addressCol, valCol, classCol, nbhCol, latCol, longCol);
	}

}
