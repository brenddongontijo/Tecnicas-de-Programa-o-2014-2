package models;

import android.database.SQLException;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/*
 * Class Name: Course.
 * 
 * This class creates an Course with their respective name.
 */
public class Course extends Bean implements Parcelable{
	private int id;
	private String name;

	// Empty constructor.
	public Course() {
		this.id = 0;
		this.identifier= "course";
		this.relationship = "courses_institutions";
	}
	
	// Declaration of a non-default constructor.
	public Course(int id){
		this.id = id;
		this.identifier= "course";
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
	
	// Access variable name. 
	public String getName() {
		return name;
	}

	// Modify variable name.
	public void setName(String name) {
		this.name = name;
	}
	
	// This method saves one Course into Database.
	public boolean save() throws SQLException {
		boolean result = false;
		GenericBeanDAO gDB = new GenericBeanDAO();
		
		result = gDB.insertBean(this);
		this.setId(Course.last().getId());
		
		return result;
	}
	
	// This method relates a institution with a course.
	public boolean addInstitution(Institution institution) throws  SQLException {
		boolean result = false;
		GenericBeanDAO gDB = new GenericBeanDAO();
		
		result = gDB.addBeanRelationship(this, institution);
		
		return result;
	}
	
	// This method picks an Book on Database based on his id.
	public static Course get(int id) throws SQLException {
		Course result = new Course(id);
		GenericBeanDAO gDB = new GenericBeanDAO();
		
		result = (Course) gDB.selectBean(result);
		
		return result;
	}

	// This method get all Articles from database.
	public static ArrayList<Course> getAll() throws SQLException {
		Course type = new Course();
		GenericBeanDAO gDB = new GenericBeanDAO();
		
		ArrayList<Course> result = new ArrayList<Course>();
		
		for(Bean b : gDB.selectAllBeans(type,"name")) {
			result.add((Course) b);
		}
		
		return result;
	}

	// This method counts the number of Courses into Database.
	public static int count() throws  SQLException {
		Course type = new Course();
		GenericBeanDAO gDB = new GenericBeanDAO();
		
		return gDB.countBean(type);
	}
	
	// This method returns the first Course into Database.
	public static Course first() throws SQLException {
		Course result = new Course();
		GenericBeanDAO gDB = new GenericBeanDAO();
		
		result = (Course) gDB.firstOrLastBean(result, false);
		
		return result;
	}

	// This method returns the last Course into Database.
	public static Course last() throws SQLException {
		Course result = new Course();
		GenericBeanDAO gDB = new GenericBeanDAO();
		
		result = (Course) gDB.firstOrLastBean(result, true);
		
		return result;
	}

	// This method get institutions related with an course.
	public ArrayList<Institution> getInstitutions() throws SQLException {
		GenericBeanDAO gDB = new GenericBeanDAO();
		ArrayList<Institution> institutions = new ArrayList<Institution>();
		
		for(Bean b : gDB.selectBeanRelationship(this, "institution", "acronym")) {
			institutions.add((Institution) b);
		}
		
		return institutions;
	}
	
	// This method get institutions related with an course based on year.
	public ArrayList<Institution> getInstitutions(int year) throws 
			SQLException {
		ArrayList<Institution> institutions = new ArrayList<Institution>();
		GenericBeanDAO gDB = new GenericBeanDAO();
		
		for (Bean b : gDB.selectBeanRelationship(this, "institution",year,"acronym")) {
			institutions.add((Institution) b);
		}
		
		return institutions;
	}

	// This method will try to find an Course based on a search. 
	public static ArrayList<Course> getWhere(String field, String value,
			boolean like) throws  SQLException {
		Course type = new Course();
		ArrayList<Course> result = new ArrayList<Course>();
		GenericBeanDAO gDB = new GenericBeanDAO();
		for (Bean b : gDB.selectBeanWhere(type, field, value, like,"name")) {
			result.add((Course) b);
		}
		return result;
	}
	
	// This method will get all courses relates to a specific evaluation filter.
	public static ArrayList<Course> getCoursesByEvaluationFilter(Search search) throws SQLException {
		ArrayList<Course> result = new ArrayList<Course>();
		
		String sql = "SELECT c.* FROM course AS c, evaluation AS e, articles AS a, books AS b "+
					" WHERE year="+Integer.toString(search.getYear())+
					" AND e.id_course = c._id"+
					" AND e.id_articles = a._id"+
					" AND e.id_books = b._id"+
					" AND "+search.getIndicator().getValue();

		if(search.getMaxValue() == -1) {
			sql+=" >= "+Integer.toString(search.getMinValue());
		}
		else {
			sql+=" BETWEEN "+Integer.toString(search.getMinValue())+" AND "+Integer.toString(search.getMaxValue());
		}
		
		sql+=" GROUP BY c._id";
		
		GenericBeanDAO gDB = new GenericBeanDAO();

		for(Bean b : gDB.runSql(new Course(), sql)) {
			result.add((Course)b);
		}

		return result;
	}

	// This method will get all institutions relates to a specific evaluation filter and to a course.
	public static ArrayList<Institution> getInstitutionsByEvaluationFilter(
			int id_course, Search search) throws SQLException {
		ArrayList<Institution> result = new ArrayList<Institution>();
		
		String sql = "SELECT i.* FROM institution AS i, evaluation AS e, articles AS a, books AS b "+
					" WHERE e.id_course="+Integer.toString(id_course)+
					" AND e.id_institution = i._id"+
					" AND e.id_articles = a._id"+
					" AND e.id_books = b._id"+
					" AND year="+Integer.toString(search.getYear())+
					" AND "+search.getIndicator().getValue();
		
		if(search.getMaxValue() == -1) {
			sql+=" >= "+search.getMinValue();
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
	
	// This method deletes an Course from Database.
	public boolean delete() throws SQLException {
		boolean result = false;
		
		GenericBeanDAO gDB = new GenericBeanDAO();
		
		for(Institution i : this.getInstitutions()){
			gDB.deleteBeanRelationship(this,i);
		}
		
		result = gDB.deleteBean(this);
		
		return result;
	}
	
	// Rewriting fields to String.
	@Override
	public String get(String field) {
		if(field.equals("_id")) {
			return Integer.toString(this.getId());
		}
		else if(field.equals("name")) {
			return this.getName();
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
		else if(field.equals("name")) {
			this.setName(data);
		}
		else {
		
		}
	}

	// Creating an ArrayList of String with course name.
	@Override
	public ArrayList<String> fieldsList() {
		ArrayList<String> fields = new ArrayList<String>();
		
		fields.add("_id");
		fields.add("name");
		
		return fields;
	}

	@Override
	public String toString() {
		return getName();
	}
	
	private Course(Parcel in){
		this.id = in.readInt();
		this.name = in.readString();
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
		dest.writeString(this.name);
		dest.writeString(this.identifier);
		dest.writeString(this.relationship);
		
	}
	
	public static final Parcelable.Creator<Course> CREATOR = new Parcelable.Creator<Course>() {

		//Create a new instance of the Course, instantiating it from the given Parcel. 
		@Override
		public Course createFromParcel(Parcel source) {
			//Returns a new instance of Course.
			return new Course(source);
		}

		@Override
		public Course[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Course[size];
		}
	};
	
}
