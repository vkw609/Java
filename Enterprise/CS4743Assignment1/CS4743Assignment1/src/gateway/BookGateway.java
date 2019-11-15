package gateway;

import java.util.ArrayList;
import java.util.List;
import application.*;

public interface BookGateway {
	
		public List<Books> getBook();
		public List<Publishers> getPublisher();
		public ArrayList<AuditTrailEntry> getAudits(Books book);
		public void addAuditTrailBook(String message, Books book, int bookId);
		public void updateBook(Books book) throws GatewayException;
		public void deleteBook(Books book);
		public void addBook(Books book);
		public boolean getBook(Books book);
		public void commitConnection();
		public void close();
		public void saveAuditTrailBook(String message, Books books);
}
