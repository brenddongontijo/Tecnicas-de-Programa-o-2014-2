package models;

import android.database.SQLException;
import java.util.ArrayList;

public class Book extends Bean {
	private int id;
	private int integralText;
	private int chapters;
	private int collections;
	private int entries;

	public Book() {
		this.id = 0;
		this.identifier = "books";
		this.relationship = "";
	}

	public Book(int id) {
		this.id = id;
		this.identifier = "books";
		this.relationship = "";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIntegralText() {
		return integralText;
	}

	public void setIntegralText(int integralText) {
		this.integralText = integralText;
	}

	public int getChapters() {
		return chapters;
	}

	public void setChapters(int chapters) {
		this.chapters = chapters;
	}

	public int getCollections() {
		return collections;
	}

	public void setCollections(int collections) {
		this.collections = collections;
	}

	public int getEntries() {
		return entries;
	}

	public void setEntries(int entries) {
		this.entries = entries;
	}

	/*
	 * The method save() receives an instance from Book and saves into 
	 * Database also setting his Id using the method last().
	 */
	public boolean save() throws  SQLException {
		boolean result = false;
		GenericBeanDAO gDB = new GenericBeanDAO();
		result = gDB.insertBean(this);
		this.setId(Book.last().getId());
		return result;
	}

	public static Book get(int id) throws  SQLException {
		Book result = new Book(id);
		GenericBeanDAO gDB = new GenericBeanDAO();
		result = (Book) gDB.selectBean(result);
		return result;
	}

	/*
	 * The method getAll() get all "Beans" on Database and put them withim a
	 * arraylist of Book before make a casting from Bean to Book.
	 */
	public static ArrayList<Book> getAll() throws  SQLException {
		Book type = new Book();
		ArrayList<Book> result = new ArrayList<Book>();
		GenericBeanDAO gDB = new GenericBeanDAO();
		for(Bean b : gDB.selectAllBeans(type,null)) {
			result.add((Book) b);
		}
		return result;
	}

	public static int count() throws  SQLException {
		Book type = new Book();
		GenericBeanDAO gDB = new GenericBeanDAO();
		return gDB.countBean(type);
	}

	public static Book first() throws 
			SQLException {
		Book result = new Book();
		GenericBeanDAO gDB = new GenericBeanDAO();
		result = (Book) gDB.firstOrLastBean(result, false);
		return result;
	}

	public static Book last() throws 
			SQLException {
		Book result = new Book();
		GenericBeanDAO gDB = new GenericBeanDAO();
		result = (Book) gDB.firstOrLastBean(result, true);
		return result;
	}

	public static ArrayList<Book> getWhere(String field, String value, boolean like) 
			throws  SQLException {
		Book type = new Book();
		ArrayList<Book> result = new ArrayList<Book>();
		GenericBeanDAO gDB = new GenericBeanDAO();
		for (Bean b : gDB.selectBeanWhere(type, field, value, like, null)) {
			result.add((Book) b);
		}
		return result;
	}
	
	public boolean delete() throws  SQLException {
		boolean result = false;
		GenericBeanDAO gDB = new GenericBeanDAO();
		result = gDB.deleteBean(this);
		return result;
	}

	@Override
	public String get(String field) {
		if(field.equals("_id")) {
			return Integer.toString(this.getId());
		}
		
		else if(field.equals("integral_text")) {
			return Integer.toString(this.getIntegralText());
		}
		
		else if (field.equals("chapters")) {
			return Integer.toString(this.getChapters());
		}
		
		else if(field.equals("collections")) {
			return Integer.toString(this.getCollections());
		}
		
		else if(field.equals("entries")) {
			return Integer.toString(this.getEntries());
		}
		
		else {
			return "";
		}
	}

	@Override
	public void set(String field, String data) {
		if (field.equals("_id")) {
			this.setId(Integer.parseInt(data));
		} 
		
		else if (field.equals("integral_text")) {
			this.setIntegralText(Integer.parseInt(data));
		}
		
		else if (field.equals("chapters")) {
			this.setChapters(Integer.parseInt(data));
		}
		
		else if (field.equals("collections")) {
			this.setCollections(Integer.parseInt(data));
		}
		
		else if (field.equals("entries")) {
			this.setEntries(Integer.parseInt(data));
		}
		
		else {

		}
		
	}

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
