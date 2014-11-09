package models;

import android.database.SQLException;

import java.util.ArrayList;

/**
 * Class Name: Book
 * This class creates an Book with all their evaluation values.
 */
public class Book extends Bean {
	
	// Book id in the Database.
	private int id;
	
	// Number of integral texts of a book.
	private int integralText;
	
	// Number of chapters of a book.
	private int bookChapters;
	
	// Number of collections of a book.
	private int bookCollections;
	
	// Number of entries of a book.
	private int entries;

	// Empty constructor.
	public Book() {
		this.id = 0;
		this.identifier = "books";
		this.relationship = "";
	}

	// Declaration of a non-default constructor.
	public Book(int bookId) {
		this.id = bookId;
		this.identifier = "books";
		this.relationship = "";
	}

	// Access variable id. 
	public int getId() {
		return id;
	}

	// Modify variable id.
	public void setId(int id) {
		this.id = id;
	}

	// Access variable integralText. 
	public int getIntegralText() {
		return integralText;
	}

	// Modify variable integralText.
	public void setIntegralText(int integralText) {
		this.integralText = integralText;
	}

	// Access variable bookChapters. 
	public int getBookChapters() {
		return bookChapters;
	}

	// Modify variable bookChapters.
	public void setBookChapters(int chapters) {
		this.bookChapters = chapters;
	}

	// Access variable bookCollections. 
	public int getBookCollections() {
		return bookCollections;
	}

	// Modify variable bookCollections.
	public void setBookCollections(int collections) {
		this.bookCollections = collections;
	}

	// Access variable entries. 
	public int getEntries() {
		return entries;
	}

	// Modify variable entries.
	public void setEntries(int entries) {
		this.entries = entries;
	}

	/**
	 * This method saves one Book into Database.
	 * 
	 * @return				Book inserted on Database or not.
	 * @throws SQLException
	 */
	public boolean saveBook() throws  SQLException {
		boolean saveResult = false;
		GenericBeanDAO genericBeanDAO = new GenericBeanDAO();
		
		saveResult = genericBeanDAO.insertBean(this);
		this.setId(Book.lastBook().getId());
		
		return saveResult;
	}

	/**
	 * This method return an Book based on his id.
	 * 
	 * @param bookId					book id on Database.
	 * @return							book corresponding to the id.
	 * @throws SQLException
	 */
	public static Book getBookByValue(int bookId) throws SQLException {
		Book bookWithId = new Book(bookId);
		GenericBeanDAO genericBeanDAO = new GenericBeanDAO();
		
		// Casting to transform a Bean into a Book. 
		bookWithId = (Book) genericBeanDAO.selectBean(bookWithId);
		
		return bookWithId;
	}

	/**
	 * This method get all Books from database.
	 * 
	 * @return				array with all books.
	 * @throws SQLException
	 */
	public static ArrayList<Book> getAllBooks() throws SQLException {
		Book book = new Book();
		GenericBeanDAO genericBeanDAO = new GenericBeanDAO();
		
		ArrayList<Book> arrayOfBooks = new ArrayList<Book>();
		
		for(Bean bean : genericBeanDAO.selectAllBeans(book,null)) {
			arrayOfBooks.add((Book) bean);
		}
		
		return arrayOfBooks;
	}

	/**
	 * This method counts the number of Books into Database.
	 * 
	 * @return			number of books present on Database.
	 * @throws SQLException
	 */
	public static int numberOfBooks() throws SQLException {
		int numberOfBooks = 0;
		
		Book book = new Book();
		GenericBeanDAO genericBeanDAO = new GenericBeanDAO();
		
		numberOfBooks = genericBeanDAO.countBean(book);
		
		return numberOfBooks;
	}

	/**
	 * This method returns the first Book into Database.
	 * 
	 * @return			first book on Database.
	 * @throws SQLException
	 */
	public static Book firstBook() throws SQLException {
		Book book = new Book();
		GenericBeanDAO genericBeanDAO = new GenericBeanDAO();
		
		book = (Book) genericBeanDAO.firstOrLastBean(book, false);
		
		return book;
	}

	/**
	 * This method returns the last Book into Database.
	 * 
	 * @return			last book on Database.
	 * @throws SQLException
	 */
	public static Book lastBook() throws SQLException {
		Book book = new Book();
		GenericBeanDAO genericBeanDAO = new GenericBeanDAO();
		
		book = (Book) genericBeanDAO.firstOrLastBean(book, true);
		
		return book;
	}

	/**
	 * This method will try to find an Book based on a search. 
	 * 
	 * @param field
	 * @param value
	 * @param like
	 * @return
	 * @throws SQLException
	 */
	public static ArrayList<Book> getWhere(String field, String value, 
			boolean like) throws SQLException {
		
		Book book = new Book();
		GenericBeanDAO genericBeanDAO = new GenericBeanDAO();
		
		ArrayList<Book> arrayOfBooks = new ArrayList<Book>();
		
		for(Bean bean : genericBeanDAO.selectBeanWhere(book, field, value, like, null)) {
			arrayOfBooks.add((Book) bean);
		}
		
		return arrayOfBooks;
	}
	
	/**
	 * This method deletes an Book from Database.
	 * 
	 * @return			Book deleted from Database or not.
	 * @throws SQLException
	 */
	public boolean deleteBook() throws SQLException {
		boolean deleteResult = false;
		GenericBeanDAO genericBeanDAO = new GenericBeanDAO();
		
		deleteResult = genericBeanDAO.deleteBean(this);
		
		return deleteResult;
	}
	
	/**
	 * Creating an Parcelable to Book.
	 */
	@Override
	public ArrayList<String> fieldsList() {
		ArrayList<String> fields = new ArrayList<String>();
		
		fields.add("_id");
		fields.add("integral_text");
		fields.add("chapters");
		fields.add("collections");
		fields.add("entries");
		
		return fields;
	}
		
	/**
	 * Rewriting article Parcelable get parameters to String.
	 * 
	 * @return 			corresponding value into a String.
	 */
	@Override
	public String get(String field) {
		String correpondingField = "";
		
		if(field.equals("_id")) {
			correpondingField = Integer.toString(this.getId());
		}
		else if(field.equals("integral_text")) {
			correpondingField = Integer.toString(this.getIntegralText());
		}
		else if(field.equals("chapters")) {
			correpondingField = Integer.toString(this.getBookChapters());
		}
		else if(field.equals("collections")) {
			correpondingField = Integer.toString(this.getBookCollections());
		}
		else if(field.equals("entries")) {
			correpondingField = Integer.toString(this.getEntries());
		}
		else{
			correpondingField = "";
		}
		
		return correpondingField;
	}

	/**
	 * Rewriting article Parcelable set parameters to their specific types.
	 */
	@Override
	public void set(String field, String data) {
		if(field.equals("_id")) {
			this.setId(Integer.parseInt(data));
		} 
		else if(field.equals("integral_text")) {
			this.setIntegralText(Integer.parseInt(data));
		}
		else if(field.equals("chapters")) {
			this.setBookChapters(Integer.parseInt(data));
		}
		else if(field.equals("collections")) {
			this.setBookCollections(Integer.parseInt(data));
		}
		else if(field.equals("entries")) {
			this.setEntries(Integer.parseInt(data));
		}
	}
	
}
