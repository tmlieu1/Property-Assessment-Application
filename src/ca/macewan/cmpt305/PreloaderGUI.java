package ca.macewan.cmpt305;

import javafx.application.Preloader;
import javafx.application.Preloader.StateChangeNotification.Type;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PreloaderGUI extends Preloader{
	private Stage preloaderStage;
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		this.preloaderStage = primaryStage;
		VBox loading = new VBox(10);
		
		final ProgressBar progress = new ProgressBar();
		
		progress.prefWidthProperty().bind(preloaderStage.widthProperty().multiply(0.5));
		final Label wait = new Label("Please wait. . .");
		
		loading.setMaxWidth(Region.USE_PREF_SIZE);
		loading.setMaxHeight(Region.USE_PREF_SIZE);
		loading.getChildren().addAll(progress, wait);
		
		BorderPane root = new BorderPane(loading);
		Scene scene = new Scene(root);
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
   @Override
   public void handleStateChangeNotification(StateChangeNotification stateChangeNotification) {
      if (stateChangeNotification.getType() == Type.BEFORE_START) {
         preloaderStage.hide();
      }
   }
}