package controllers;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.ValidationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import application.Books;
import application.Publishers;
import gateway.GatewayException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class BookAddController {
	private Books book;
	@FXML private ComboBox<Publishers> publishers;
	@FXML private TextField title;
	@FXML private TextField isbn;
	@FXML private TextField year;
	@FXML private TextField summary;
	@FXML private Button bookSave;
    private List<Publishers>  bookPublishers;
	private static Logger logger = LogManager.getLogger();
	
	@FXML private Button bookDelete;
	@FXML private Button bookAdd;
	
	private boolean addButtonPressed;
	
	public BookAddController(Books book) throws Exception {
		this.book = book;
		this.addButtonPressed = false;
	}

	@FXML public void handleAdd(ActionEvent action) throws IOException {
		Object source = action.getSource();
		if(source == bookAdd) {
			this.addButtonPressed = true;
			add();
		}
	}

	public boolean add(){
			try {
				
				logger.info("Begin adding...");
				book.setId(book.getId());
				book.setTitle(title.getText());
				book.setISBN(isbn.getText());
				book.setSummary(summary.getText());
				book.setYear(Integer.parseInt(year.getText()));
				book.addAudit(book.getId());
				book.add();
				
				this.disableFields();
				logger.info("Book added!");
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
	
	private void disableFields() {
		bookAdd.setDisable(true); 
		title.setDisable(true);
		isbn.setDisable(true);
		year.setDisable(true);
		summary.setDisable(true);
	}

	public boolean hasChanged() {
		logger.info("Yo is this doing anything 1");
		if(this.addButtonPressed)
			return false;
		logger.info("Yo is this doing anything 2");
		//if(book.getPublisher().getId() != publishers.getValue().getId())
			//return true;
		logger.info("Yo is this doing anything 3");
		
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
			
		}
		return false;
	}

	

	public void initialize() {
		logger.info("Set text for variables");
		title.setText(book.getTitle());
		isbn.setText(book.getISBN());
		year.setText("" + book.getYear());
		summary.setText(book.getSummary());
	}
}
