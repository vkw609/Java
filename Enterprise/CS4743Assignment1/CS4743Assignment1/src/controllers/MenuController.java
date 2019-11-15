package controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import application.Books;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import view.ViewType;

public class MenuController {
private static Logger logger = LogManager.getLogger();
	
	@FXML private MenuItem menuQuit;
	@FXML private MenuItem menuAdd, menuList;
	
	public MenuController() {
		
	}
	
	@FXML private void handleMenuAction(ActionEvent action) throws Exception {
		Object source = action.getSource();
		if(source == menuQuit) {
			Platform.exit();
		}
		if(source == menuList) {
			logger.info("Clicked on Books. Show menuItem 'BookList'");
			MainController.showView(ViewType.BookList, null);
			logger.info("Called MainController BookList");
			return;
		}
		if(source == menuAdd) {
			logger.info("Clicked on 'Add Books'. Add new item to 'BookList'");
			Books book = new Books();
			book.setGateway(MainController.getBookGateway());
			logger.info("Get book gateway to add");
			MainController.showView(ViewType.BookAdd, book);
			logger.info("Show view");
			return;
		}
	}
	
	public void initialize() {
		logger.info("Controller init called");
		
	}

}
