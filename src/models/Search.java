package models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Locale;


import android.database.SQLException;

import helpers.Indicator;

/*
 * Class Name: Search.
 * 
 * This class is responsible for performing a search based on indicator.
 */
public class Search extends Bean {
	public static int COURSE = 0;
	public static int INSTITUTION = 1;

	private int id;
	private Date date;
	private int year;
	private int option;
	private Indicator indicator;
	private int minValue;
	private int maxValue;

	// Empty constructor.
	public Search() {
		this.id = 0;
		this.identifier = "search";
		this.relationship = "";
	}

	// Declaration of a non-default constructor
	public Search(int id) {
		this.id = id;
		this.identifier = "search";
		this.relationship = "";
	}

	// Access variable date. 
	public Date getDate() {
		return date;
	}

	// Modify variable date.
	public void setDate(Date date) {
		this.date = date;
	}

	// Access variable year. 
	public int getYear() {
		return year;
	}

	// Modify variable year.
	public void setYear(int year) {
		this.year = year;
	}

	// Access variable option. 
	public int getOption() {
		return option;
	}

	// Modify variable option.
	public void setOption(int option) {
		this.option = option;
	}

	// Access variable indicator. 
	public Indicator getIndicator() {
		return indicator;
	}

	// Modify variable indicator.
	public void setIndicator(Indicator indicator) {
		this.indicator = indicator;
	}

	// Access variable minValue. 
	public int getMinValue() {
		return minValue;
	}

	// Modify variable minValue.
	public void setMinValue(int minValue) {
		this.minValue = minValue;
	}

	// Access variable maxValue. 
	public int getMaxValue() {
		return maxValue;
	}

	// Modify variable maxValue.
	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}
	
	// This method saves one Search into Database.
	public boolean save() throws SQLException {
		boolean result = false;
		GenericBeanDAO gDB = new GenericBeanDAO();

		 //QualCurso can't have more than 10 searches.
		if(Search.count() >= 10) {
			// Deleting the first search.
			Search.first().delete();
		}
		
		result = gDB.insertBean(this);
		this.setId(Search.last().getId());

		return result;
	}

	// This method get an Search on Database based on his id.
	public static Search get(int id) throws SQLException {
		Search result = new Search(id);
		GenericBeanDAO gDB = new GenericBeanDAO();	
		
		result = (Search) gDB.selectBean(result);
		
		return result;
	}

	// This method get all Search from database.
	public static ArrayList<Search> getAll() throws  SQLException {
		Search type = new Search();
		GenericBeanDAO gDB = new GenericBeanDAO();
		
		ArrayList<Search> result = new ArrayList<Search>();
		
		for(Bean b : gDB.selectAllBeans(type,null)) {
			result.add((Search) b);
		}
		
		return result;
	}
	
	 //The method count() counts the number of Search on Database.  
	public static int count() throws  SQLException {
		Search type = new Search();
		GenericBeanDAO gDB = new GenericBeanDAO();
		
		return gDB.countBean(type);
	}

	
	 //The method first() returns the first Search on Database.
	public static Search first() throws SQLException {
		GenericBeanDAO gDB = new GenericBeanDAO();
		
		Search result = new Search();
		result = (Search) gDB.firstOrLastBean(result, false);
		
		return result;
	}

	
	 //The method last() returns the last Search on database. 
	public static Search last() throws SQLException {
		GenericBeanDAO gDB = new GenericBeanDAO();
		
		Search result = new Search();
		result = (Search) gDB.firstOrLastBean(result, true);
		
		return result;
	}

	// This method will try to find an Search based on a search. 
	public static ArrayList<Search> getWhere(String field, String value, boolean like) throws SQLException {
		Search type = new Search();
		GenericBeanDAO gDB = new GenericBeanDAO();
		
		ArrayList<Search> result = new ArrayList<Search>();
		
		for(Bean b : gDB.selectBeanWhere(type, field, value, like,null)) {
			result.add((Search) b);
		}
		
		return result;
	}

	// This method deletes an Search from Database.
	public boolean delete() throws SQLException {
		boolean result = false;
		
		GenericBeanDAO gDB = new GenericBeanDAO();
		result = gDB.deleteBean(this);
		
		return result;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	// Rewriting Search fields to String.
	@Override
	public String get(String field) {
		if(field.equals("_id")) {
			return Integer.toString(this.getId());
		}
		else if(field.equals("date")) {
			return new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy",Locale.US).format(this.date);
		}
		else if(field.equals("year")) {
			return Integer.toString(this.getYear());
		}
		else if(field.equals("option")) {
			return Integer.toString(this.getOption());
		}
		else if(field.equals("indicator")) {
			return this.getIndicator().getValue();
		}
		else if(field.equals("min_value")) {
			return Integer.toString(this.getMinValue());
		}
		else if(field.equals("max_value")) {
			return Integer.toString(this.getMaxValue());
		}
		else {
			return "";
		}
	}

	// Rewriting Search fields to Integer.
	@Override
	public void set(String field, String data){
		if(field.equals("_id")) {
			this.setId(Integer.parseInt(data));
		}
		else if(field.equals("date")) {
			Date dateData = null;
			
			try {
				dateData = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy",Locale.US).parse(data);
			} 
			catch(ParseException e) {
				e.printStackTrace();
			}
			
			this.setDate(dateData);
		}
		else if(field.equals("year")) {
			this.setYear(Integer.parseInt(data));
		}
		else if(field.equals("option")) {
			this.setOption(Integer.parseInt(data));
		}
		else if(field.equals("indicator")) {
			this.setIndicator(Indicator.getIndicatorByValue(data));
		}
		else if(field.equals("min_value")) {
			this.setMinValue(Integer.parseInt(data));
		}
		else if(field.equals("max_value")) {
			this.setMaxValue(Integer.parseInt(data));
		}
	}

	// Creating an ArrayList of Search with his attributes.
	@Override
	public ArrayList<String> fieldsList() {
		ArrayList<String> fields = new ArrayList<String>();
		
		fields.add("_id");
		fields.add("date");
		fields.add("year");
		fields.add("option");
		fields.add("indicator");
		fields.add("min_value");
		fields.add("max_value");
		
		return fields;
	}

	@Override
	public int getId() {
		return this.id;
	}

}
