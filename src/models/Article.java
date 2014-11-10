package models;

import java.util.ArrayList;

import android.database.SQLException;

/**
 * Class Name: Article.
 * This class creates an Article with all their evaluation values.
 */
public class Article extends Bean {
	
	// Database id from article.
	private int id;
	
	// Number of published journals.
	private int publishedJournals;
	
	// Number of published conference proceedings.
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

	/**
	 * This method saves one Article into Database.
	 * 
	 * @return				Article inserted on Database or not.
	 * @throws SQLException
	 */
	public boolean saveArticle() throws SQLException {
		boolean saveResult = false;
		GenericBeanDAO genericBeanDAO = new GenericBeanDAO();
		
		saveResult = genericBeanDAO.insertBean(this);
		
		this.setId(Article.lastArticle().getId());
		
		return saveResult;
	}
	
	/**
	 * This method return an Article based on his id.
	 * 
	 * @param articleId					article id on Database.
	 * @return							article corresponding to the id.
	 * @throws SQLException
	 */
	public static Article getArticleByValue(final Integer articleId) throws SQLException {
		Article articleWithId = new Article(articleId);
		GenericBeanDAO genericBeanDAO = new GenericBeanDAO();
		
		articleWithId = (Article) genericBeanDAO.selectBean(articleWithId);
		
		return articleWithId;
	}

	/**
	 * This method get all Articles from database.
	 * 
	 * @return				array with all articles.
	 * @throws SQLException
	 */
	public static ArrayList<Article> getAllArticles() throws SQLException {
		Article article = new Article();
		GenericBeanDAO genericBeanDAO = new GenericBeanDAO();
		
		ArrayList<Article> arrayOfArticles = new ArrayList<Article>();
		
		for(Bean bean : genericBeanDAO.selectAllBeans(article,null)) {
			arrayOfArticles.add((Article) bean);
		}
		
		return arrayOfArticles;
	}
	
	/**
	 * This method counts the number of Articles into Database.
	 * 
	 * @return			number of articles present on Database.
	 * @throws SQLException
	 */
	public static int numberOfArticles() throws SQLException {
		Article article = new Article();
		GenericBeanDAO genericBeanDAO = new GenericBeanDAO();
		
		return genericBeanDAO.countBean(article);
	}

	/**
	 * This method returns the first Article into Database.
	 * 
	 * @return			first article on Database.
	 * @throws SQLException
	 */
	public static Article firstArticle() throws SQLException {
		Article article = new Article();
		GenericBeanDAO genericBeanDAO = new GenericBeanDAO();
		
		article = (Article) genericBeanDAO.firstOrLastBean(article, false);
		
		return article;
	}

	/**
	 * This method returns the last Article into Database.
	 * 
	 * @return			last article on Database.
	 * @throws SQLException
	 */
	public static Article lastArticle() throws SQLException {
		Article article = new Article();
		GenericBeanDAO genericBeanDAO = new GenericBeanDAO();
		
		article = (Article) genericBeanDAO.firstOrLastBean(article, true);
		
		return article;
	}

	/**
	 * This method will try to find an Article based on a search. 
	 * 
	 * @param field
	 * @param value
	 * @param like
	 * @return
	 * @throws SQLException
	 */
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

	/**
	 * This method deletes an Article from Database.
	 * 
	 * @return			Article deleted from Database or not.
	 * @throws SQLException
	 */
	public boolean deleteArticle() throws SQLException {
		boolean deleteResult = false;
		GenericBeanDAO genericBeanDAO = new GenericBeanDAO();
		
		deleteResult = genericBeanDAO.deleteBean(this);
		
		return deleteResult;
	}

	/**
	 * Creating an Parcelable to Article.
	 */
	@Override
	public ArrayList<String> fieldsList() {
		ArrayList<String> articleParcelable = new ArrayList<String>();
		
		articleParcelable.add("_id");
		articleParcelable.add("published_journals");
		articleParcelable.add("published_conference_proceedings");
		
		return articleParcelable;
	}
	
	/**
	 * Rewriting article Parcelable get parameters to String.
	 * 
	 * @return 			corresponding value into a String.
	 */
	@Override
	public String get(String field) {
		String correpondingField = "";
		
		if (field.equals("_id")) {
			correpondingField = Integer.toString(this.getId());
		}
		else if (field.equals("published_journals")) {
			correpondingField = Integer.toString(this.getPublishedJournals());
		}
		else if (field.equals("published_conference_proceedings")) {
			correpondingField = Integer.toString(this.getPublishedConferenceProceedings());
		}
		else {
			correpondingField = "";
		}
		
		return correpondingField;
	}

	/**
	 * Rewriting article Parcelable set parameters to their specific types.
	 */
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
	}
	
}
