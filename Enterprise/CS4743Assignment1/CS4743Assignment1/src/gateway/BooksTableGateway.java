package gateway;

import com.mysql.cj.jdbc.MysqlDataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import application.AuditTrailEntry;
import application.Books;
import application.Publishers;
import controllers.AuditTrailController;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BooksTableGateway implements BookGateway {
	
	private Connection connector;
	private static final int QUERY_TIMEOUT = 70;
	private static Logger logger = LogManager.getLogger();
	private static final Random roller = new Random();
	
	public List<Books> getBook() {
		logger.info("List<Books>");
		List<Books> books = new ArrayList<Books>();
		PreparedStatement st = null;
		ResultSet result = null;
		
		try {
			connector.setAutoCommit(false);
			
			System.out.println("locking test record...");

			logger.info("Try list");
			st = connector.prepareStatement("select BookList.id, BookList.title, BookList.isbn, BookList.year_published, BookList.summary, BookList.publisher_id"
					+ " FROM BookList");
			result = st.executeQuery();
			
			logger.info("ExecuteQ");
			while(result.next()) {
				Books book = new Books(result.getInt("id"), result.getString("title"), result.getString("isbn"), result.getInt("year_published"), result.getString("summary"));
				book.setId(result.getInt("id"));
				book.setGateway(this);
				books.add(book);	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} finally {
			try {
				if(result != null)
					result.close();
				if(st != null)
					st.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return books;
	}
	
	public List<Publishers> getPublisher() {
		List<Publishers> ret = new ArrayList<Publishers>();
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = connector.prepareStatement("select * from Publishers ");
			rs = st.executeQuery();
			
			while(rs.next()) {
				Publishers publisher = new Publishers(rs.getInt("id"), rs.getString("publisher_name"));
				ret.add(publisher);	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(rs != null)
					rs.close();
				if(st != null)
					st.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ret;
	}

	
	
	public BooksTableGateway(){
		connector = null;
		Properties property = new Properties();
		FileInputStream fis = null;
        try {
			fis = new FileInputStream("db.properties");
			property.load(fis);
	        fis.close();

	        MysqlDataSource database = new MysqlDataSource();
	        database.setURL("jdbc:mysql://easel2.fulgentcorp.com:3306/vkw609?serverTimezone=UTC#");
	        database.setUser("vkw609");
	        database.setPassword("XR7Ewbzp2QBZWZXAQczU");
	        
			
			connector = database.getConnection();

        } catch (IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    
	}

	public void updateBook(Books book) throws GatewayException {
		logger.info("update book");
		PreparedStatement update = null;
		
		try{
			
			connector.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ); //default
			connector.setAutoCommit(false);
			
			logger.info("update book AUTO COMMIT");
			update = connector.prepareStatement("Update BookList set title = ?, summary =  ?, year_published = ?, publisher_id = ?, isbn = ? WHERE id = ?");
			update.setQueryTimeout(QUERY_TIMEOUT);
			
			update.setString(1, book.getTitle());
			logger.info("update title");
			update.setString(2, book.getSummary());
			logger.info("update summary");
			update.setInt(3, book.getYear());
			logger.info("update year");
			update.setInt(4, book.getPublisher().getId());
			logger.info("update publisher Id");
			update.setString(5, book.getISBN());
			logger.info("update IPSN");
			update.setInt(6, book.getId());
			update.executeUpdate();
			
			connector.commit();
			connector.setAutoCommit(true);
			logger.info("Update executed");
			
		} catch (SQLException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			update.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void deleteBook(Books book){
		
		PreparedStatement delete = null;
		try {
			
			delete = connector.prepareStatement("delete from BookList where id = ?");
			delete.setInt(1, book.getId());
			delete.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(delete != null)
					delete.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void addBook(Books book){
		PreparedStatement add = null;
		try {
			add = connector.prepareStatement("insert into BookList (title, isbn, "
					+ "year_published, summary) values (?, ?, ?, ?)");
			add.setString(1, book.getTitle());
			add.setString(2, book.getISBN());
			add.setInt(3, book.getYear());
			add.setString(4, book.getSummary());
			//add.setInt(5, book.getPublisher().getId());
			add.executeUpdate();
			logger.info("Adding executed");
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(add != null)
					add.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public ArrayList<AuditTrailEntry> getAudits(Books book) {
		ArrayList<AuditTrailEntry> audits = new ArrayList<AuditTrailEntry>();
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = connector.prepareStatement("select * from Book_Audit_Trail");
			//st.setInt(1, book.getId());

			rs = st.executeQuery();
			while (rs.next()) {
				audits.add(new AuditTrailEntry(rs.getInt("id"), rs.getInt("book_id"), rs.getString("entry_msg"), rs.getString("date_added")));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(rs != null)
					rs.close();
				if(st != null)
					st.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return audits;

	}
	
	public void addAuditTrailBook(String message, Books book, int id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = connector.prepareStatement("insert into Book_Audit_Trail (entry_msg, book_id) values (?,?)");
			st.setString(1, message);
			st.setInt(2, id);
			st.executeUpdate();
			
			logger.info("Audit created for bookId: " + book.getId());
		} catch (SQLException e) {
			e.getMessage();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (st != null)
					st.close();
			} catch (SQLException e) {
				e.getMessage();
			}
		}
	}
	
	public void saveAuditTrailBook(String message, Books book) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = connector.prepareStatement("insert into Book_Audit_Trail (entry_msg, book_id) values (?,?)");
			st.setString(1, message);
			st.setInt(2, book.getId());
			st.executeUpdate();
			
			logger.info("Audit created for bookId: " + book.getId());
		} catch (SQLException e) {
			e.getMessage();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (st != null)
					st.close();
			} catch (SQLException e) {
				e.getMessage();
			}
		}
	}
	
	public boolean getBook(Books book) {
		logger.info("Lock book");
		PreparedStatement bookLock = null;
		try
		{
			connector.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
			connector.setAutoCommit(false);
			bookLock = connector.prepareStatement("select * from BookList where id = ? for update");
			bookLock.setInt(1, book.getId());
			bookLock.setQueryTimeout(3);
			bookLock.executeQuery();
		} catch (SQLException e) {
			return false;
		}
		return true;
	}
	
	public void commitConnection()
	{
		try
		{
			connector.commit();
		} catch(SQLException e)
		{
			logger.error("TRIED TO COMMIT WITH NO CONNECTION");
		}
	}
	
	
	public void close() {
		logger.info("close");
		if(connector != null) {
			try {
				connector.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}

