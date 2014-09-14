package models;

import android.database.SQLException;
import java.util.ArrayList;

/*
 * Class Name: Article.
 * 
 * This class creates an Book with all their evaluation values.
 */
public class Book extends Bean {
	private int id;
	private int integralText;
	private int chapters;
	private int collections;
	private int entries;

	// Empty constructor.
	public Book() {
		this.id = 0;
		this.identifier = "books";
		this.relationship = "";
	}

	// Declaration of a non-default constructor
	public Book(int id) {
		this.id = id;
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

	// Access variable chapters. 
	public int getChapters() {
		return chapters;
	}

	// Modify variable chapters.
	public void setChapters(int chapters) {
		this.chapters = chapters;
	}

	// Access variable collections. 
	public int getCollections() {
		return collections;
	}

	// Modify variable collections.
	public void setCollections(int collections) {
		this.collections = collections;
	}

	// Access variable entries. 
	public int getEntries() {
		return entries;
	}

	// Modify variable entries.
	public void setEntries(int entries) {
		this.entries = entries;
	}

	// This method saves one Book into Database.
	public boolean save() throws  SQLException {
		boolean result = false;
		GenericBeanDAO gDB = new GenericBeanDAO();
		
		result = gDB.insertBean(this);
		this.setId(Book.last().getId());
		
		return result;
	}

	// This method picks an Book on Database based on his id.
	public static Book get(int id) throws  SQLException {
		Book result = new Book(id);
		GenericBeanDAO gDB = new GenericBeanDAO();
		
		// Casting to transform a Bean into a Book. 
		result = (Book) gDB.selectBean(result);
		
		return result;
	}

	// This method get all Articles from database.
	public static ArrayList<Book> getAll() throws  SQLException {
		Book type = new Book();
		ArrayList<Book> result = new ArrayList<Book>();
		GenericBeanDAO gDB = new GenericBeanDAO();
		
		for(Bean b : gDB.selectAllBeans(type,null)) {
			result.add((Book) b);
		}
		
		return result;
	}

	// This method counts the number of Books into Database.
	public static int count() throws SQLException {
		Book type = new Book();
		
		GenericBeanDAO gDB = new GenericBeanDAO();
		
		return gDB.countBean(type);
	}

	// This method returns the first Book into Database.
	public static Book first() throws SQLException {
		Book result = new Book();
		GenericBeanDAO gDB = new GenericBeanDAO();
		
		result = (Book) gDB.firstOrLastBean(result, false);
		
		return result;
	}

	// This method returns the last Book into Database.
	public static Book last() throws SQLException {
		Book result = new Book();
		GenericBeanDAO gDB = new GenericBeanDAO();
		
		result = (Book) gDB.firstOrLastBean(result, true);
		
		return result;
	}

	// This method will try to find an Book based on a search.  
	public static ArrayList<Book> getWhere(String field, String value, 
			boolean like) throws SQLException {
		Book type = new Book();
		ArrayList<Book> result = new ArrayList<Book>();
		GenericBeanDAO gDB = new GenericBeanDAO();
		
		for(Bean b : gDB.selectBeanWhere(type, field, value, like, null)) {
			result.add((Book) b);
		}
		
		return result;
	}
	
	// This method deletes an Book from Database.
	public boolean delete() throws  SQLException {
		boolean result = false;
		GenericBeanDAO gDB = new GenericBeanDAO();
		
		result = gDB.deleteBean(this);
		
		return result;
	}

	// Rewriting fields to String.
	@Override
	public String get(String field) {
		if(field.equals("_id")) {
			return Integer.toString(this.getId());
		}
		else if(field.equals("integral_text")) {
			return Integer.toString(this.getIntegralText());
		}
		else if(field.equals("chapters")) {
			return Integer.toString(this.getChapters());
		}
		else if(field.equals("collections")) {
			return Integer.toString(this.getCollections());
		}
		else if(field.equals("entries")) {
			return Integer.toString(this.getEntries());
		}
		else{
			return "";
		}
	}

	// Rewriting fields to Integer.
	@Override
	public void set(String field, String data) {
		if(field.equals("_id")) {
			this.setId(Integer.parseInt(data));
		} 
		else if(field.equals("integral_text")) {
			this.setIntegralText(Integer.parseInt(data));
		}
		else if(field.equals("chapters")) {
			this.setChapters(Integer.parseInt(data));
		}
		else if(field.equals("collections")) {
			this.setCollections(Integer.parseInt(data));
		}
		else if(field.equals("entries")) {
			this.setEntries(Integer.parseInt(data));
		}
		else {
		}
	}

	// Creating an ArrayList of String with evaluation book values.
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
	
}
