package controllers;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import application.Books;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import view.ViewType;
import javafx.event.EventHandler;

public class BookListController{

	private static final Logger logger = LogManager.getLogger();
	private Books book;

	@FXML
	private ListView<Books> bookList;
	private List<Books> books;
	
	@FXML private Button bookDelete;

	public BookListController(List<Books> books) {
		this.books = books;
	}
	
	public void initialize() {
		logger.info("Begining of init booklistcont");
		ObservableList<Books> items = bookList.getItems();
		
		items.addAll(books);

		bookList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent click) {
                if(click.getClickCount() == 2) {
                	Books selected = bookList.getSelectionModel().getSelectedItem();
                   
                	logger.info("User double-clicked");
						try {
							MainController.showView(ViewType.BookDetail, selected);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
        			
                	
                }
            }
        });

	}
}


