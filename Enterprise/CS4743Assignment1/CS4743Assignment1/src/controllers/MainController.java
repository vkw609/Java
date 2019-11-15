package controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.application.Platform;
import application.AuditTrailEntry;
import application.Books;
import gateway.BookGateway;
import gateway.BooksTableGateway;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import view.ViewType;

public class MainController{
	private static final Logger logger = LogManager.getLogger();
	
	private static BorderPane rootPane;

	private static BookGateway bookGateway;
	
	private static BookDetailController currentController;
	private static BookAddController aCurrentController;

	
	private MainController() {
		currentController = null;
		aCurrentController = null;
	}
	
	public static void initGateway() throws SQLException {
		logger.info("Init gateway!!!");
		try {
			bookGateway = new BooksTableGateway();
		} catch (Exception e) {
			e.printStackTrace();
			Platform.exit();
		}
	}

	@FXML
    void onBookList(ActionEvent event) throws Exception{
		logger.info("Clicked on Books. Show menuItem 'BookList'");
		
		showView(ViewType.BookList, null);
    }
	
	public static boolean showView(ViewType viewType, Object info) throws Exception{
		//Deals with detail view only should not be touched when addDetail is called
		if(currentController != null)
		{
			logger.info("CC is not null");
			if(currentController.hasChanged())
			{
				Alert alert = new Alert(AlertType.CONFIRMATION);
				
				alert.getButtonTypes().clear();
				ButtonType buttonTypeYes = new ButtonType("Yes");
				ButtonType buttonTypeNo = new ButtonType("No");
				ButtonType buttonTypeCancel = new ButtonType("Cancel");
				alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo, buttonTypeCancel);
				
				alert.setTitle("Save Changes?");
				alert.setHeaderText("Current record has unsaved changes!");
				alert.setContentText("Would you like to save before leaving?");
				Optional<ButtonType> result = alert.showAndWait();
				if(result.get().getText().equalsIgnoreCase("Yes"))
				{
					logger.info("Saving from main controller before focus loss");
					if(!currentController.save())
						return false;
				
				} else if(result.get().getText().equalsIgnoreCase("Cancel"))
				{
					return false;
				} else if(result.get().getText().equalsIgnoreCase("No"))
				{
					currentController.commitBook();
				}
			}	
		} else if (aCurrentController != null)
		{
			if(aCurrentController.hasChanged())
			{
				Alert alert = new Alert(AlertType.CONFIRMATION);
				
				alert.getButtonTypes().clear();
				ButtonType buttonTypeYes = new ButtonType("Yes");
				ButtonType buttonTypeNo = new ButtonType("No");
				ButtonType buttonTypeCancel = new ButtonType("Cancel");
				alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo, buttonTypeCancel);
	
				alert.setTitle("Add new record?");
				alert.setHeaderText("Current record has not been added!");
				alert.setContentText("Would you like to add new record before leaving?");
				Optional<ButtonType> result = alert.showAndWait();
	
				if(result.get().getText().equalsIgnoreCase("Yes"))
				{
					logger.info("Adding new book from main controller before focus loss");
					aCurrentController.add();
				} else if(result.get().getText().equalsIgnoreCase("Cancel"))
				{
					return false;
				} 
			}
		}
		
		FXMLLoader loader = null;
		Parent viewNode;
		switch(viewType) {
			case BookDetail : 
				currentController = null;
				aCurrentController = null;
				logger.info("Viewing book detail.");
				loader = new FXMLLoader(MainController.class.getResource("/view/BookDetailView.fxml"));
				logger.info("Book Detail Loaded");
				currentController = new BookDetailController((Books) info, bookGateway.getPublisher());
				loader.setController(currentController);
				break;
				
			case BookList : 
				logger.info("Viewing book list.");
				List<Books> books = bookGateway.getBook();
				logger.info("Books loaded.");
				loader = new FXMLLoader(MainController.class.getResource("/view/BookListView.fxml"));
				logger.info("Book List Loaded.");
				loader.setController(new BookListController(books));
				aCurrentController = null;
				currentController = null;
				break;
				
			case BookAdd :
				currentController = null;
				aCurrentController = null;
				logger.info("Viewing book detail to add new book.");
				loader = new FXMLLoader(MainController.class.getResource("/view/BookDetailAddView.fxml"));
				logger.info("Book detail for Adding Loaded");
				aCurrentController = new BookAddController((Books) info);
				loader.setController(aCurrentController);
				break;
			case BookAudit :
				//List<Books> bookAudit = bookGateway.getBook();
				List<AuditTrailEntry> audits = bookGateway.getAudits((Books) info);
				loader = new FXMLLoader(MainController.class.getResource("/view/AuditTrailView.fxml"));
				loader.setController(new  AuditTrailController(audits));
				break;
		default:
			break;
		}
		viewNode = null;
		
		try {
			viewNode = loader.load();
		} catch (Exception e) {
			logger.error("Exception due to book already locked");
			currentController = null;
			return false;
			//e.printStackTrace();
		}
		rootPane.setCenter(viewNode);
		return true;
	}
	

	public static void close() {
		bookGateway.close();
	}
		
	public BorderPane getRootPane() {
		return rootPane;
	}

	public static void setRootPane(BorderPane view) {
		rootPane = view;
	}

	public static BookGateway getBookGateway() {
		return bookGateway;
	}

	public void setBookGateway(BooksTableGateway bookGateway) {
		this.bookGateway = bookGateway;
	}


}
