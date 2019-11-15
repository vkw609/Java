package application;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AuditTrailEntry {	
	private static Logger logger = LogManager.getLogger();

	private int id;
	private int bookId;
	private String dateAdded;
	private String message;

	public AuditTrailEntry() {
		id = 0;
		bookId = 0;
		dateAdded = "";
		message = "";
	}

	public AuditTrailEntry(int id, int bookId, String dateAdded, String message) {
		this.id = id;
		this.bookId = bookId;
		this.dateAdded = dateAdded;
		this.message = message;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getBookId() {
		return bookId;
	}
	
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public String getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(String dateAdded) {
		this.dateAdded = dateAdded;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public String toString() {
		String text = this.dateAdded;
		return text + " " + this.message;
	}
	
}