package models;

import helpers.Indicator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.database.SQLException;

/*
 * Class Name: Search.
 * This class is responsible for performing a search based on indicator.
 */
public class Search extends Bean {
	public static int COURSE = 0;
	public static int INSTITUTION = 1;

	private int searchId;
	private Date searchDate;
	private int evaluationYear;
	private int searchOption;
	private Indicator searchIndicator;
	private int minIndicatorValue;
	private int maxIndicatorValue;

	// Empty constructor.
	public Search() {
		this.searchId = 0;
		this.identifier = "search";
		this.relationship = "";
	}

	// Declaration of a non-default constructor
	public Search(int searchId) {
		this.searchId = searchId;
		this.identifier = "search";
		this.relationship = "";
	}

	// Access variable searchDate. 
	public Date getDate() {
		return searchDate;
	}

	// Modify variable searchDate.
	public void setDate(Date searchDate) {
		this.searchDate = searchDate;
	}

	// Access variable evaluationYear. 
	public int getYear() {
		return evaluationYear;
	}

	// Modify variable evaluationYear.
	public void setYear(int evaluationYear) {
		this.evaluationYear = evaluationYear;
	}

	// Access variable searchOption. 
	public int getOption() {
		return searchOption;
	}

	// Modify variable searchOption.
	public void setOption(int searchOption) {
		this.searchOption = searchOption;
	}

	// Access variable searchIndicator. 
	public Indicator getIndicator() {
		return searchIndicator;
	}

	// Modify variable searchIndicator.
	public void setIndicator(Indicator searchIndicator) {
		this.searchIndicator = searchIndicator;
	}

	// Access variable minIndicatorValue. 
	public int getMinValue() {
		return minIndicatorValue;
	}

	// Modify variable minIndicatorValue.
	public void setMinValue(int minIndicatorValue) {
		this.minIndicatorValue = minIndicatorValue;
	}

	// Access variable maxIndicatorValue. 
	public int getMaxValue() {
		return maxIndicatorValue;
	}

	// Modify variable maxIndicatorValue.
	public void setMaxValue(int maxIndicatorValue) {
		this.maxIndicatorValue = maxIndicatorValue;
	}
	
	// This method saves one Search into Database.
	public boolean saveSearch() throws SQLException {
		boolean result = false;
		GenericBeanDAO GenericBeanDAO = new GenericBeanDAO();

		 //QualCurso can't have more than 10 searches.
		if(Search.numberOfSearch() >= 10) {
			
			// Deleting the first search.
			Search.firstSearch().deleteSearch();
		}
		
		result = GenericBeanDAO.insertBean(this);
		this.setId(Search.lastSearch().getId());

		return result;
	}

	// This method get an Search on Database based on his id.
	public static Search getSearchById(int searchId) throws SQLException {
		Search result = new Search(searchId);
		GenericBeanDAO GenericBeanDAO = new GenericBeanDAO();	
		
		result = (Search) GenericBeanDAO.selectBean(result);
		
		return result;
	}

	// This method get all Search from database.
	public static ArrayList<Search> getAllSearch() throws  SQLException {
		Search type = new Search();
		GenericBeanDAO GenericBeanDAO = new GenericBeanDAO();
		
		ArrayList<Search> result = new ArrayList<Search>();
		
		for(Bean bean : GenericBeanDAO.selectAllBeans(type,null)) {
			result.add((Search) bean);
		}
		
		return result;
	}
	
	 //The method numberOfSearch() counts the number of Search on Database.  
	public static int numberOfSearch() throws  SQLException {
		Search type = new Search();
		GenericBeanDAO GenericBeanDAO = new GenericBeanDAO();
		
		return GenericBeanDAO.countBean(type);
	}

	
	 //The method firstSearch() returns the first Search on Database.
	public static Search firstSearch() throws SQLException {
		GenericBeanDAO GenericBeanDAO = new GenericBeanDAO();
		
		Search result = new Search();
		result = (Search) GenericBeanDAO.firstOrLastBean(result, false);
		
		return result;
	}

	
	 //The method lastSearch() returns the last Search on database. 
	public static Search lastSearch() throws SQLException {
		GenericBeanDAO GenericBeanDAO = new GenericBeanDAO();
		
		Search result = new Search();
		result = (Search) GenericBeanDAO.firstOrLastBean(result, true);
		
		return result;
	}

	// This method will try to find an Search based on a search. 
	public static ArrayList<Search> getWhere(String field, String value, boolean like) throws SQLException {
		Search type = new Search();
		GenericBeanDAO GenericBeanDAO = new GenericBeanDAO();
		
		ArrayList<Search> result = new ArrayList<Search>();
		
		for(Bean bean : GenericBeanDAO.selectBeanWhere(type, field, value, like,null)) {
			result.add((Search) bean);
		}
		
		return result;
	}

	// This method deletes an Search from Database.
	public boolean deleteSearch() throws SQLException {
		boolean result = false;
		
		GenericBeanDAO GenericBeanDAO = new GenericBeanDAO();
		result = GenericBeanDAO.deleteBean(this);
		
		return result;
	}

	@Override
	public void setId(int searchId) {
		this.searchId = searchId;
	}

	// Rewriting Search fields to String.
	@Override
	public String get(String field) {
		if(field.equals("_id")) {
			return Integer.toString(this.getId());
		}
		else if(field.equals("date")) {
			return new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy",Locale.US).format(this.searchDate);
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
		return this.searchId;
	}

}
