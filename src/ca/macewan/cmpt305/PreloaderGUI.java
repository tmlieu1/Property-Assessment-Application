package ca.macewan.cmpt305;

import javafx.application.Preloader;
import javafx.application.Preloader.StateChangeNotification.Type;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class PreloaderGUI extends Preloader{
	private Stage preloaderStage;
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		this.preloaderStage = primaryStage;
		VBox loading = new VBox(10);
		
		final Label wait = new Label("Please wait. . .");
		wait.setFont(new Font("Verdana", 30));
		
		loading.getChildren().add(wait);
		loading.setMaxWidth(Region.USE_PREF_SIZE);
		loading.setMaxHeight(Region.USE_PREF_SIZE);
		
		BorderPane root = new BorderPane(loading);
		Scene scene = new Scene(root);
		
		preloaderStage.setTitle("Edmonton Property Assessments");
		preloaderStage.getIcons().add(new Image("file:edmonton-logo.png"));
		preloaderStage.setScene(scene);
		preloaderStage.setMaximized(true);
		preloaderStage.show();
	}
}