package application;
	
import javafx.application.Application;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controllers.MainController;
import controllers.MenuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;



public class Main extends Application {
	private static Logger logger = LogManager.getLogger();
	
	public static void main(String[] args) {
		launch(args);
	}

	private MainController controller;
	
	public void init() throws Exception {
		super.init();
				
		//create book gateway
		logger.info("Calling init");
		controller.initGateway();
	}

	@Override
	public void start(Stage stage) throws IOException {
		logger.info("Starting in the main.");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Menu.fxml"));
		
		MenuController controller = new MenuController();
		
		loader.setController(controller);
		 
		Parent view = loader.load();
		
		MainController.setRootPane((BorderPane) view);
		
		Scene scene = new Scene(view);
		
		stage.setScene(scene);
		stage.setTitle("Book List");
		stage.show();
		logger.info("stage setting");
	}
}
