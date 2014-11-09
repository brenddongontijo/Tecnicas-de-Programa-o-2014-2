package models;

import java.util.ArrayList;

/**
 * Class Name: Bean.
 * This class transforms an Article, Book, Course, Evaluation, Institution or Search
 * into a Bean witch will be able to access GenericBeanDAO.
 */
public abstract class Bean {
	
	/* 
	 * "identifier" will identifier the other models. 
	 * Models: Article, Book, Course, Evaluation, Institution and Search.
	 */
	protected String identifier;
	
	// Make a relationship between models.
	protected String relationship;

	public abstract String get(String field);

	public abstract void set(String field, String data);

	public abstract ArrayList<String> fieldsList();

	public abstract int getId();

	public abstract void setId(int id);
}

