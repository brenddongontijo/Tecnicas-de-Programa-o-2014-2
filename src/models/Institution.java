package models;

import android.database.SQLException;
import android.os.Parcel;
import android.os.Parcelable;


import java.util.ArrayList;

/*
 * Class Name: Institution.
 * 
 * This class creates an Course with their respective acronym.
 */
public class Institution extends Bean implements Parcelable {
	private int id;
	private String acronym;

	// Empty constructor.
	public Institution() {
		this.id = 0;
		this.identifier = "institution";
		this.relationship = "courses_institutions";
	}

	// Declaration of a non-default constructor.
	public Institution(int id) {
		this.id = id;
		this.identifier = "institution";
		this.relationship = "courses_institutions";
	}

	// Access variable id. 
	public int getId() {
		return id;
	}
	
	// Modify variable id.
	public void setId(int id) {
		this.id = id;
	}
		
	// Access variable acronym. 
	public String getAcronym() {
		return acronym;
	}

	// Modify variable acronym.
	public void setAcronym(String acronym) {
		this.acronym = acronym;
	}
	
	// This method saves one Institution into Database.
	public boolean saveInstitution() throws SQLException {
		boolean result = false;
		
		GenericBeanDAO gDB = new GenericBeanDAO();
		result = gDB.insertBean(this);
		
		this.setId(Institution.lastInstitution().getId());
		
		return result;
	}
	
	// This method relates a course with a institution.
	public boolean addCourse(Course course) throws SQLException {
		boolean result = false;
		GenericBeanDAO gDB = new GenericBeanDAO();
		
		result = gDB.addBeanRelationship(this, course);
		
		return result;
	}
	
	// This method picks an Institution on Database based on his id.
	public static Institution getInstitutionByValue(int id) throws SQLException {
		Institution result = new Institution(id);
		GenericBeanDAO gDB = new GenericBeanDAO();
		
		result = (Institution) gDB.selectBean(result);
		
		return result;
	}
	
	// This method get all Institutions from database.
	public static ArrayList<Institution> getAllInstitutions() throws SQLException {
		Institution type = new Institution();
		GenericBeanDAO gDB = new GenericBeanDAO();
		
		ArrayList<Institution> result = new ArrayList<Institution>();
		
		for(Bean b : gDB.selectAllBeans(type,"acronym")) {
			result.add((Institution) b);
		}
		
		return result;
	}

	// This method counts the number of Institutions into Database.
	public static int numberOfInstitutions() throws SQLException {
		Institution type = new Institution();
		GenericBeanDAO gDB = new GenericBeanDAO();
		
		return gDB.countBean(type);
	}

	// This method get the first Institution into Database.
	public static Institution firstInstitution() throws SQLException {
		Institution result = new Institution();
		GenericBeanDAO gDB = new GenericBeanDAO();
		
		result = (Institution) gDB.firstOrLastBean(result, false);
		
		return result;
	}

	// This method get the last Institution into Database.
	public static Institution lastInstitution() throws SQLException {
		Institution result = new Institution();
		GenericBeanDAO gDB = new GenericBeanDAO();
		
		result = (Institution) gDB.firstOrLastBean(result, true);
		
		return result;
	}

	// This method get courses related with an institution.
	public ArrayList<Course> getCourses() throws SQLException {
		ArrayList<Course> courses = new ArrayList<Course>();
		GenericBeanDAO gDB = new GenericBeanDAO();
		
		for (Bean b : gDB.selectBeanRelationship(this, "course","name")) {
			courses.add((Course) b);
		}
		
		return courses;
	}
	
	// This method get courses related with an institution based on year.
	public ArrayList<Course> getCourses(int year) throws SQLException {
		ArrayList<Course> courses = new ArrayList<Course>();
		GenericBeanDAO gDB = new GenericBeanDAO();
		
		for(Bean b : gDB.selectBeanRelationship(this, "course", year,"name")) {
			courses.add((Course) b);
		}
		
		return courses;
	}
	
	// This method will try to find an Institution based on a search. 
	public static ArrayList<Institution> getWhere(String field, String value,
			boolean like) throws SQLException {
		Institution type = new Institution();
		GenericBeanDAO gDB = new GenericBeanDAO();
		
		ArrayList<Institution> result = new ArrayList<Institution>();
		
		for (Bean b : gDB.selectBeanWhere(type, field, value, like,"acronym")) {
			result.add((Institution) b);
		}
		
		return result;
	}

	// This method will get all institutions relates to a specific evaluation filter.
	public static ArrayList<Institution> getInstitutionsByEvaluationFilter(Search search) throws SQLException {
		ArrayList<Institution> result = new ArrayList<Institution>();
		String sql = "SELECT i.* FROM institution AS i, evaluation AS e, articles AS a, books AS b "+
					" WHERE year="+Integer.toString(search.getYear())+
					" AND e.id_institution = i._id"+
					" AND e.id_articles = a._id"+
					" AND e.id_books = b._id"+
					" AND "+search.getIndicator().getValue();

		if(search.getMaxValue() == -1) {
			sql+=" >= "+Integer.toString(search.getMinValue());
		}
		else {
			sql+=" BETWEEN "+Integer.toString(search.getMinValue())+" AND "+Integer.toString(search.getMaxValue());
		}
		
		sql+=" GROUP BY i._id";
		
		GenericBeanDAO gDB = new GenericBeanDAO();

		for(Bean b : gDB.runSql(new Institution(), sql)){
			result.add((Institution)b);
		}

		return result;
	}

	// This method will get all courses relates to a specific evaluation filter and to a institution.
	public static ArrayList<Course> getCoursesByEvaluationFilter(int id_institution, Search search) throws  SQLException {
		ArrayList<Course> result = new ArrayList<Course>();
		
		String sql = "SELECT c.* FROM course AS c, evaluation AS e, articles AS a, books AS b "+
					" WHERE e.id_institution="+id_institution+
					" AND e.id_course = c._id"+
					" AND e.id_articles = a._id"+
					" AND e.id_books = b._id"+
					" AND year="+search.getYear()+
					" AND "+search.getIndicator().getValue();
		
		if(search.getMaxValue() == -1) {
			sql+=" >= "+search.getMinValue();
		}
		else {
			sql+=" BETWEEN "+search.getMinValue()+" AND "+search.getMaxValue();
		}
		
		sql+=" GROUP BY c._id";
		
		GenericBeanDAO gDB = new GenericBeanDAO();

		for(Bean b : gDB.runSql(new Course(), sql)){
			result.add((Course)b);
		}
		
		return result;
	}
	
	// This method deletes an Institution from Database.
	public boolean delete() throws  SQLException {
		boolean result = false;
		
		GenericBeanDAO gDB = new GenericBeanDAO();
		
		for(Course c : this.getCourses()) {
			gDB.deleteBeanRelationship(this,c);
		}
		
		result = gDB.deleteBean(this);
		
		return result;
	}

	// Rewriting fields to String.
	@Override
	public String get(String field) {
		if (field.equals("_id")) {
			return Integer.toString(this.getId());
		} 
		else if (field.equals("acronym")) {
			return this.getAcronym();
		} 
		else {
			return "";
		}
	}

	// Rewriting fields to Integer.
	@Override
	public void set(String field, String data) {
		if(field.equals("_id")) {
			this.setId(Integer.parseInt(data));
		} 
		else if(field.equals("acronym")) {
			this.setAcronym(data);
		} 
		else {

		}
	}

	// Creating an ArrayList of String with institution acronym.
	@Override
	public ArrayList<String> fieldsList() {
		ArrayList<String> fields = new ArrayList<String>();
		fields.add("_id");
		fields.add("acronym");
		return fields;
	}

	@Override
	public String toString() {
		return getAcronym();
	}
	
	private Institution(Parcel in){
		this.id = in.readInt();
		this.acronym = in.readString();
		this.identifier = in.readString();
		this.relationship = in.readString();
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.id);
		dest.writeString(this.acronym);
		dest.writeString(this.identifier);
		dest.writeString(this.relationship);
		
	}
	
	public static final Parcelable.Creator<Institution> CREATOR = new Parcelable.Creator<Institution>() {

		@Override
		public Institution createFromParcel(Parcel source) {
			return new Institution(source);
		}

		@Override
		public Institution[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Institution[size];
		}
	};

}
