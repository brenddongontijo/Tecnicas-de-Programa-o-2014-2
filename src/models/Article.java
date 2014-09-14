package models;

import android.database.SQLException;
import java.util.ArrayList;

/*
 * Class Name: Article.
 * This class creates an Article with all their evaluation values.
 */
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
	
	// Declaration a non-default constructor.
	public Article(Integer id) {
		this.id = id;
		this.identifier = "articles";
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

	// Access variable publishedJournals. 
	public int getPublishedJournals() {
		return publishedJournals;
	}

	// Modify variable publishedJournals.
	public void setPublishedJournals(int publishedJournals) {
		this.publishedJournals = publishedJournals;
	}

	// Access variable publishedConferenceProceedings. 
	public int getPublishedConferenceProceedings() {
		return publishedConferenceProceedings;
	}

	// Modify variable publishedConferenceProceedings.
	public void setPublishedConferenceProceedings(
			int publishedConferenceProceedings) {
		this.publishedConferenceProceedings = publishedConferenceProceedings;
	}

	// This method saves one Article into Database.
	public boolean save() throws SQLException {
		boolean result = false;
		GenericBeanDAO gDB = new GenericBeanDAO();
		
		result = gDB.insertBean(this);
		
		this.setId(Article.last().getId());
		
		return result;
	}
	
	// This method return an Article based on his id.
	public static Article get(Integer id) throws  SQLException {
		Article result = new Article(id);
		GenericBeanDAO gDB = new GenericBeanDAO();
		
		result = (Article) gDB.selectBean(result);
		
		return result;
	}

	// This method get all Articles from database.
	public static ArrayList<Article> getAll() throws SQLException {
		Article type = new Article();
		GenericBeanDAO gDB = new GenericBeanDAO();
		
		ArrayList<Article> result = new ArrayList<Article>();
		
		for(Bean b : gDB.selectAllBeans(type,null)) {
			result.add((Article) b);
		}
		
		return result;
	}
	
	// This method counts the number of Articles into Database.
	public static int count() throws SQLException {
		Article type = new Article();
		GenericBeanDAO gDB = new GenericBeanDAO();
		
		return gDB.countBean(type);
	}

	// This method returns the first Article into Database.
	public static Article first() throws SQLException {
		Article result = new Article();
		GenericBeanDAO gDB = new GenericBeanDAO();
		
		result = (Article) gDB.firstOrLastBean(result, false);
		
		return result;
	}

	// This method returns the last Article into Database.
	public static Article last() throws SQLException {
		Article result = new Article();
		GenericBeanDAO gDB = new GenericBeanDAO();
		
		result = (Article) gDB.firstOrLastBean(result, true);
		
		return result;
	}

	// This method will try to find an Article based on a search.  
	public static ArrayList<Article> getWhere(String field, String value, boolean like) 
			throws SQLException {
		Article type = new Article();
		GenericBeanDAO gDB = new GenericBeanDAO();
		
		ArrayList<Article> result = new ArrayList<Article>();
		
		for (Bean b : gDB.selectBeanWhere(type, field, value, like, null)) {
			result.add((Article) b);
		}
		
		return result;
	}
	
	// This method deletes an Article from Database.
	public boolean delete() throws SQLException {
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

	// Rewriting fields to Integer.
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

	// Creating an ArrayList of String with evaluation article values.
	@Override
	public ArrayList<String> fieldsList() {
		ArrayList<String> fields = new ArrayList<String>();
		
		fields.add("_id");
		fields.add("published_journals");
		fields.add("published_conference_proceedings");
		
		return fields;
	}
	
}
