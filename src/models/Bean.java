package models;

import java.util.ArrayList;

/*
 * Generic  abstract class, is extremely important in this application 
 * since they are worked in other models to avoid DAO repetition code.
 */
public abstract class Bean {
	// "identifier" will identifier the other models.
	// Models: Article, Book, Course, Evaluation, Institution and Search.
	protected String identifier;
	protected String relationship;

	public abstract String get(String field);

	public abstract void set(String field, String data);

	public abstract ArrayList<String> fieldsList();

	public abstract int getId();

	public abstract void setId(int id);
}