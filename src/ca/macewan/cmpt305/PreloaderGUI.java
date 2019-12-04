package ca.macewan.cmpt305;

import javafx.application.Preloader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/*
 * Loading screen class to be shown while the data is loading. 
 */
public class PreloaderGUI extends Preloader{
	private Stage preloaderStage;
	
	/*
	 * Start function displays the loading screen when instantiated.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception{
		this.preloaderStage = primaryStage;
		VBox loading = new VBox(10);
		
		//YEG image
		Image image = new Image("file:yeglogo.png");
		ImageView yeglogo = new ImageView();
		yeglogo.setImage(image);
		
		//Wait label
		final Label wait = new Label("Please wait...");
		wait.setFont(new Font("Arial", 30));
		wait.setAlignment(Pos.CENTER);
		
		//VBox settings
		loading.getChildren().addAll(yeglogo, wait);
		loading.setMaxWidth(Region.USE_PREF_SIZE);
		loading.setMaxHeight(Region.USE_PREF_SIZE);
		
		//set the scene
		BorderPane root = new BorderPane(loading);
		Scene scene = new Scene(root);
		
		//set the stage
		preloaderStage.setTitle("Edmonton Property Assessments");
		preloaderStage.getIcons().add(new Image("file:edmonton-logo.png"));
		preloaderStage.setScene(scene);
		preloaderStage.setMaximized(true);
		preloaderStage.show();
	}
}