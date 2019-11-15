package controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.xml.bind.ValidationException;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import application.AuditTrailEntry;
import application.Books;
import application.Publishers;
import gateway.BookGateway;
import gateway.BooksTableGateway;
import gateway.GatewayException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import view.ViewType;



public class BookDetailController {
	private Books book;
	private static BookGateway bookGateway;
	@FXML private ComboBox<Publishers> publishers;
	@FXML private TextField title;
	@FXML private TextField isbn;
	@FXML private TextField year;
	@FXML private TextField summary;
	@FXML private Button bookSave;
	private Stage stage;
    private List<Publishers>  bookPublishers;
    private List<AuditTrailEntry> auditList;
	private static Logger logger = LogManager.getLogger();
	
	@FXML private Button bookDelete;
	@FXML private Button bookAdd;
	@FXML private Button bookAudit;
	
	private int whichView;
	private boolean addButtonPressed;
	private boolean delButtonPressed;
	//if add button is pressed the save confirm box should not pop up
	//false equals no, true equals yes
	
	public BookDetailController(Books book, List<Publishers> publishers) throws Exception {
		this.book = book;
		this.bookPublishers = publishers;

		this.whichView = 0;
		this.addButtonPressed = false;
		this.delButtonPressed = false;
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
	
	@FXML public void handleSave(ActionEvent action) throws IOException {
		Object source = action.getSource();
		if(source == bookSave) {
			save();
		}
	}
	
	@FXML public void handleDelete(ActionEvent action) throws IOException {
		Object source = action.getSource();
		if(source == bookDelete) {
			delButtonPressed = true;
			delete();
		}
	}
	
	@FXML public void handleAudit(ActionEvent action) throws Exception {
		Object source = action.getSource();
		FXMLLoader loader = null;
		if(source == bookAudit) {
			logger.info("Clicked on Audits");
			Books book = new Books();
			book.setGateway(MainController.getBookGateway());
			logger.info("Get book gateway to add");
			MainController.showView(ViewType.BookAudit, book);
			logger.info("Show view");
		}

	}
	
	public void setWhichView(int switcher) {
		this.whichView = switcher;
	}
	public int getWhichView() {
		return this.whichView;
	}

	public boolean save(){
		try {
			
			if(!(book.getISBN().equals(isbn.getText())))
			{ 
				book.saveAudit("ISBN changed from <" + book.getISBN() + "> to <" + isbn.getText() + ">");
				book.setISBN(isbn.getText()); 
				logger.info("Changed ISBN");
			}
			if(!(book.getTitle().equals(title.getText())))
			{
				book.saveAudit("Title changed from <" + book.getTitle() + "> to <" + title.getText() + ">");
				book.setTitle(title.getText());
				logger.info("Changed title");
			} 
			if (!(book.getYear() == Integer.parseInt(year.getText())))
			{
				book.saveAudit("Title changed from <" + book.getYear() + "> to <" + year.getText() + ">");
				book.setYear(Integer.parseInt(year.getText())); 
				logger.info("Changed year");
			} 
			if (!(book.getSummary().equals(summary.getText())))
			{
				book.saveAudit("Summary changed from <" + book.getSummary() + "> to <" + summary.getText() + ">");
				book.setSummary(summary.getText());
				logger.info("Changed summary");
			} 
			if (!(book.getPublisher().equals(publishers.getValue()))){

				book.saveAudit("Publisher changed from <" + book.getPublisher() + "> to <" + publishers.getValue() + ">");
				book.setPublisher(publishers.getValue());
				logger.info("Changed publisher");
			}
			
			if(!(book.lockBook()))
			{
				Alert failAlert = new Alert(AlertType.INFORMATION);
				failAlert.setTitle("Query Failed!");
				failAlert.setHeaderText(null);
				failAlert.setContentText("Edit could not occur another user is modifying that record!");
				//failAlert.showAndWait();
				return false;
			}
			
			book.lockBook();
			//book.saveAudit();
			book.save();
			
			} catch (ValidationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			} catch (GatewayException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}	
			return true;
	}
	
	public boolean delete(){
		try {
			logger.info("Begin delete");
			
			book.delete();

			this.disableFields();
			logger.info("Deleted!");
			
			} catch (ValidationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			} catch (GatewayException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}	
			return true;
	}


	public boolean hasChanged() {
		logger.info("Detailed view has changed");
		if(this.delButtonPressed)
			return false;
		
		if(this.addButtonPressed)
			return false;
		
		if(this.book != null)
		{
			System.out.println(book.getISBN());
			System.out.println(isbn.getText());
			if(!(book.getISBN().equals(isbn.getText())))
			{ 
				logger.info("ISBN was edited");
				return true; 
			} else if(!(book.getTitle().equals(title.getText())))
			{
				logger.info("Title was edited");
				return true; 
			} else if (!(book.getYear() == Integer.parseInt(year.getText())))
			{
				logger.info("Year was edited");
				return true; 
			} else if (!(book.getSummary().equals(summary.getText())))
			{
				logger.info("Summary was edited");
				return true; 
			}
			book.bookCommit();
		}
		return false;
	}

	private void disableFields() {
		bookDelete.setDisable(true);
		if(whichView == 0)
		{ bookSave.setDisable(true); }
		else if (whichView == 1)
		{ bookAdd.setDisable(true); }
		title.setDisable(true);
		isbn.setDisable(true);
		year.setDisable(true);
		summary.setDisable(true);
	}
	
	public void commitBook() {
		book.bookCommit();
	}

	public void initialize() throws SQLException, ValidationException, GatewayException {
		logger.info("Set text for variables");
		
		if(book.lockBook()) {
			
			publishers.getItems().addAll(bookPublishers);
			publishers.setValue(book.getPublisher());
			
			title.setText(book.getTitle());
			isbn.setText(book.getISBN());
			year.setText("" + book.getYear());
			summary.setText(book.getSummary());
		} else
		{
			Alert failAlert = new Alert(AlertType.INFORMATION);
			failAlert.setTitle("Query Failed!");
			failAlert.setHeaderText(null);
			failAlert.setContentText("Edit could not occur another user is modifying that record!");
			failAlert.showAndWait();
			throw new SQLException();
		}
	}

}
