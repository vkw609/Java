package application;

import java.time.LocalDateTime;

import javax.xml.bind.ValidationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import gateway.BookGateway;
import gateway.BooksTableGateway;
import gateway.GatewayException;
import javafx.beans.property.SimpleObjectProperty;

public class Books {
	private static final Logger logger = LogManager.getLogger();
	
	private BooksTableGateway gateway;
	private int id;
	private int bookId;
	private String title;
	private String summary;
	private int yearPublished;
	private String isbn;
	private SimpleObjectProperty<Publishers> publishers;
	private LocalDateTime lastModified;
	
	private BookGateway bookGateway;
	
	public Books() {
		this.title = "";
		this.summary = "";
		this.yearPublished = 0;
		this.isbn = "";
		this.bookGateway = null;
		this.id = 0;
	}

	
	public Books(int id, String title, String isbn, int yearPublished, String summary) {
		this.id = id;
		this.title = title;
		this.isbn = isbn;
		this.yearPublished = yearPublished;
		this.summary = summary;
		this.publishers = new SimpleObjectProperty<Publishers>(new Publishers());
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		if(isValidTitle(title)) {
			this.title = title;
		}else {
			logger.error("Title must be at least than one character, no greater than 255.");
		}
	}
	
	public String getISBN() {
		return isbn;
	}

	public void setISBN(String isbn) {
		if(isValidIsbn(isbn))
			this.isbn = isbn;
		else
			logger.error("ISBN must be less than 13 characters.");
	}
	
	public int getYear() {
		return this.yearPublished;
	}

	public void setYear(int year) {
		if(isValidYear(year))
			this.yearPublished = year;
		else
			logger.error("Invalid year. Must be from year 1990 or newer.");
	}
	
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		if(isValidSummary(summary))
			this.summary = summary;
		else
			logger.error("Incorrect Summary length. Too long.");
	}
	
	public Publishers getPublisher() {
		return publishers.getValue();
	}
	public void setPublisher(Publishers publisher) {
		this.publishers.setValue(publisher);
	}
	
	public BookGateway getGateway() {
		return bookGateway;
	}

	public void setGateway(BookGateway gateway) {
		this.bookGateway = gateway;
	}
	
	public boolean isValidId(int id) {
		if(id < 0)
			return false;
		return true;
	}
	
	public boolean isValidTitle(String title) {
		if(title.length() < 1 || title.length() > 255)
			return false;
		return true;
	}
	
	public boolean isValidIsbn(String isbn) {
		if(isbn.length() > 13)
			return false;
		return true;
	}
	
	public boolean isValidYear(int year) {
		if(year < 1989)
			return false;
		return true;
	}
	
	public boolean isValidSummary(String summary) {
		if(summary == null || summary.length() > 65536)
			return false;
		return true;
	}

	public LocalDateTime getLastModified() {
		return lastModified;
	}

	public void setLastModified(LocalDateTime lastModified) {
		this.lastModified = lastModified;
	}
	
	public void save() throws ValidationException, GatewayException {
		if(!isValidId(getId()))
			throw new ValidationException("Invalid Id: " + getId());
		bookGateway.updateBook(this);
	}
	
	public void addAudit(int bookId) throws ValidationException, GatewayException {
		if(!isValidId(getId()))
			throw new ValidationException("Invalid Id: " + getId());
		bookGateway.addAuditTrailBook("Book created", this, getId());
	}
	
	public void saveAudit(String message) throws ValidationException, GatewayException {
		if(!isValidId(getId()))
			throw new ValidationException("Invalid Id: " + getId());
		bookGateway.saveAuditTrailBook(message, this);
	}
	
	public void delete() throws ValidationException, GatewayException {
		if(!isValidId(getId()))
			throw new ValidationException("Invalid Id: " + getId());
		bookGateway.deleteBook(this);
	}
	
	public void add() throws ValidationException, GatewayException {
		logger.info("add in book");
		bookGateway.addBook(this);
	}
	
	public boolean lockBook() throws ValidationException, GatewayException {
		if(!isValidId(getId()))
			throw new ValidationException("Invalid Id: " + getId());
		return bookGateway.getBook(this);
	}
	
	public void bookCommit() {
		bookGateway.commitConnection();
	}
	
	@Override
	public String toString() {
		return id + " : " + title;
	}
	
	
}