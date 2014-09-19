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
	public Article(Integer idArticle) {
		this.id = idArticle;
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
	public boolean saveArticle() throws SQLException {
		boolean saveResult = false;
		GenericBeanDAO genericBeanDAO = new GenericBeanDAO();
		
		saveResult = genericBeanDAO.insertBean(this);
		
		this.setId(Article.lastArticle().getId());
		
		return saveResult;
	}
	
	// This method return an Article based on his id.
	public static Article getArticleByValue(Integer id) throws SQLException {
		Article articleWithId = new Article(id);
		GenericBeanDAO genericBeanDAO = new GenericBeanDAO();
		
		articleWithId = (Article) genericBeanDAO.selectBean(articleWithId);
		
		return articleWithId;
	}

	// This method get all Articles from database.
	public static ArrayList<Article> getAllArticles() throws SQLException {
		Article article = new Article();
		GenericBeanDAO genericBeanDAO = new GenericBeanDAO();
		
		ArrayList<Article> arrayOfArticles = new ArrayList<Article>();
		
		for(Bean bean : genericBeanDAO.selectAllBeans(article,null)) {
			arrayOfArticles.add((Article) bean);
		}
		
		return arrayOfArticles;
	}
	
	// This method counts the number of Articles into Database.
	public static int numberOfArticles() throws SQLException {
		Article article = new Article();
		GenericBeanDAO genericBeanDAO = new GenericBeanDAO();
		
		return genericBeanDAO.countBean(article);
	}

	// This method returns the first Article into Database.
	public static Article firstArticle() throws SQLException {
		Article article = new Article();
		GenericBeanDAO genericBeanDAO = new GenericBeanDAO();
		
		article = (Article) genericBeanDAO.firstOrLastBean(article, false);
		
		return article;
	}

	// This method returns the last Article into Database.
	public static Article lastArticle() throws SQLException {
		Article article = new Article();
		GenericBeanDAO genericBeanDAO = new GenericBeanDAO();
		
		article = (Article) genericBeanDAO.firstOrLastBean(article, true);
		
		return article;
	}

	// This method will try to find an Article based on a search.  
	public static ArrayList<Article> getWhere(String field, String value, boolean like) 
			throws SQLException {
		Article article = new Article();
		GenericBeanDAO genericBeanDAO = new GenericBeanDAO();
		
		ArrayList<Article> arrayOfArticles = new ArrayList<Article>();
		
		for (Bean bean : genericBeanDAO.selectBeanWhere(article, field, value, like, null)) {
			arrayOfArticles.add((Article) bean);
		}
		
		return arrayOfArticles;
	}
	
	// This method deletes an Article from Database.
	public boolean deleteArticle() throws SQLException {
		boolean deleteResult = false;
		GenericBeanDAO genericBeanDAO = new GenericBeanDAO();
		
		deleteResult = genericBeanDAO.deleteBean(this);
		
		return deleteResult;
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
