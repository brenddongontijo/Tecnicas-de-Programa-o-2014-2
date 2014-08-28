package models;

import android.database.SQLException;
import java.util.ArrayList;

public class Article extends Bean {
	private int id;
	private int publishedJournals;
	private int publishedConferenceProceedings;

	// Empty constructor.
	public Article() {
		this.id = 0;
		this.identifier = "articles";
		this.relationship = "";
	}
	
	// Declaration a non-default constructor
	public Article(Integer id) {
		this.id = id;
		this.identifier = "articles";
		this.relationship = "";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPublishedJournals() {
		return publishedJournals;
	}

	public void setPublishedJournals(int publishedJournals) {
		this.publishedJournals = publishedJournals;
	}

	public int getPublishedConferenceProceedings() {
		return publishedConferenceProceedings;
	}

	public void setPublishedConferenceProceedings(
			int publishedConferenceProceedings) {
		this.publishedConferenceProceedings = publishedConferenceProceedings;
	}

	/*
	 * The method save() receives an instance from Article and saves into 
	 * Database also setting his Id using the method last().
	 */
	public boolean save() throws  SQLException {
		boolean result = false;
		GenericBeanDAO gDB = new GenericBeanDAO();
		result = gDB.insertBean(this);
		this.setId(Article.last().getId());
		return result;
	}
	
	/*
	 * The method get() creates a new object from Article using his Id and uses
	 * a casting to transform one object Bean to Article.
	 */
	public static Article get(Integer id) throws  SQLException {
		Article result = new Article(id);
		GenericBeanDAO gDB = new GenericBeanDAO();
		result = (Article) gDB.selectBean(result);
		return result;
	}

	/*
	 * The method getAll() get all "Beans" on Database and put them withim a
	 * arraylist of Articles before make a casting from Bean to Article.
	 */
	public static ArrayList<Article> getAll() throws  SQLException {
		Article type = new Article();
		ArrayList<Article> result = new ArrayList<Article>();
		GenericBeanDAO gDB = new GenericBeanDAO();
		for(Bean b : gDB.selectAllBeans(type,null)) {
			result.add((Article) b);
		}
		return result;
	}
	
	/*
	 * The method count() uses the method countBean() parsing one object from  
	 * Article to access Database and return the number of Articles into it.
	 */
	public static int count() throws  SQLException {
		Article type = new Article();
		GenericBeanDAO gDB = new GenericBeanDAO();
		return gDB.countBean(type);
	}

	/*
	 * The method first() uses the method firstOrLastBean() from GenericBeanDAO 
	 * parsing one object from Article and a boolean condition "false" to get 
	 * the first "Bean" on Database and then turn it into an Article using the 
	 * casting.
	 */
	public static Article first() throws SQLException {
		Article result = new Article();
		GenericBeanDAO gDB = new GenericBeanDAO();
		result = (Article) gDB.firstOrLastBean(result, false);
		return result;
	}

	/*
	 * The method last() uses the method firstOrLastBean() from GenericBeanDAO 
	 * parsing one object from Article and a boolean condition "true" to get 
	 * the last "Bean" on Database and then turn it into an Article using the 
	 * casting.
	 */
	public static Article last() throws 
			SQLException {
		Article result = new Article();
		GenericBeanDAO gDB = new GenericBeanDAO();
		result = (Article) gDB.firstOrLastBean(result, true);
		return result;
	}

	public static ArrayList<Article> getWhere(String field, String value, boolean like) 
			throws  SQLException {
		Article type = new Article();
		ArrayList<Article> result = new ArrayList<Article>();
		GenericBeanDAO gDB = new GenericBeanDAO();
		for (Bean b : gDB.selectBeanWhere(type, field, value, like, null)) {
			result.add((Article) b);
		}
		return result;
	}
	
	/*
	 * The method delete() access the Database and deletes the current Article
	 * returning "true" if the deletion was made correct or "false" otherwise.
	 */
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
		else if(field.equals("published_journals")) {
			return Integer.toString(this.getPublishedJournals());
		}
		else if (field.equals("published_conference_proceedings")) {
			return Integer.toString(this.getPublishedConferenceProceedings());
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
		else if (field.equals("published_journals")) {
			this.setPublishedJournals(Integer.parseInt(data));
		}
		else if (field.equals("published_conference_proceedings")) {
			this.setPublishedConferenceProceedings(Integer.parseInt(data));
		}
		else {
		}
	}

	@Override
	public ArrayList<String> fieldsList() {
		ArrayList<String> fields = new ArrayList<String>();
		fields.add("_id");
		fields.add("published_journals");
		fields.add("published_conference_proceedings");
		return fields;
	}
}
