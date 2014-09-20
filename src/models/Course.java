package models;

import android.database.SQLException;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/*
 * Class Name: Course.
 * This class creates an Course with their respective name.
 */
public class Course extends Bean implements Parcelable{
	private int courseId;
	private String courseName;

	// Empty constructor.
	public Course() {
		this.courseId = 0;
		this.identifier= "course";
		this.relationship = "courses_institutions";
	}
	
	// Declaration of a non-default constructor.
	public Course(int courseId){
		this.courseId = courseId;
		this.identifier= "course";
		this.relationship = "courses_institutions";
	}
	
	// Access variable id. 
	public int getId() {
		return courseId;
	}
	
	// Modify variable id.
	public void setId(int courseId) {
		this.courseId = courseId;
	}
	
	// Access variable name. 
	public String getName() {
		return courseName;
	}

	// Modify variable name.
	public void setName(String courseName) {
		this.courseName = courseName;
	}
	
	// This method saves one Course into Database.
	public boolean saveCourse() throws SQLException {
		boolean result = false;
		GenericBeanDAO genericBeanDAO = new GenericBeanDAO();
		
		result = genericBeanDAO.insertBean(this);
		this.setId(Course.lastCourse().getId());
		
		return result;
	}
	
	// This method relates a institution with a course.
	public boolean addInstitution(Institution institution) throws  SQLException {
		boolean result = false;
		GenericBeanDAO genericBeanDAO = new GenericBeanDAO();
		
		result = genericBeanDAO.addBeanRelationship(this, institution);
		
		return result;
	}
	
	// This method picks an Book on Database based on his id.
	public static Course getCourseByValue(int courseId) throws SQLException {
		Course result = new Course(courseId);
		GenericBeanDAO genericBeanDAO = new GenericBeanDAO();
		
		result = (Course) genericBeanDAO.selectBean(result);
		
		return result;
	}

	// This method get all Articles from database.
	public static ArrayList<Course> getAllCourses() throws SQLException {
		Course type = new Course();
		GenericBeanDAO genericBeanDAO = new GenericBeanDAO();
		
		ArrayList<Course> result = new ArrayList<Course>();
		
		for(Bean bean : genericBeanDAO.selectAllBeans(type,"name")) {
			result.add((Course) bean);
		}
		
		return result;
	}

	// This method counts the number of Courses into Database.
	public static int numberOfCourses() throws  SQLException {
		Course type = new Course();
		GenericBeanDAO genericBeanDAO = new GenericBeanDAO();
		
		return genericBeanDAO.countBean(type);
	}
	
	// This method returns the first Course into Database.
	public static Course firstCourse() throws SQLException {
		Course result = new Course();
		GenericBeanDAO genericBeanDAO = new GenericBeanDAO();
		
		result = (Course) genericBeanDAO.firstOrLastBean(result, false);
		
		return result;
	}

	// This method returns the last Course into Database.
	public static Course lastCourse() throws SQLException {
		Course result = new Course();
		GenericBeanDAO genericBeanDAO = new GenericBeanDAO();
		
		result = (Course) genericBeanDAO.firstOrLastBean(result, true);
		
		return result;
	}

	// This method get institutions related with an course.
	public ArrayList<Institution> getInstitutions() throws SQLException {
		GenericBeanDAO genericBeanDAO = new GenericBeanDAO();
		ArrayList<Institution> institutions = new ArrayList<Institution>();
		
		for(Bean bean : genericBeanDAO.selectBeanRelationship(this, "institution", "acronym")) {
			institutions.add((Institution) bean);
		}
		
		return institutions;
	}
	
	// This method get institutions related with an course based on year.
	public ArrayList<Institution> getInstitutionsByYear(int year) throws 
			SQLException {
		ArrayList<Institution> institutions = new ArrayList<Institution>();
		GenericBeanDAO genericBeanDAO = new GenericBeanDAO();
		
		for (Bean bean : genericBeanDAO.selectBeanRelationship(this, "institution",year,"acronym")) {
			institutions.add((Institution) bean);
		}
		
		return institutions;
	}

	// This method will try to find an Course based on a search. 
	public static ArrayList<Course> getWhere(String field, String value,
			boolean like) throws  SQLException {
		Course type = new Course();
		ArrayList<Course> result = new ArrayList<Course>();
		GenericBeanDAO genericBeanDAO = new GenericBeanDAO();
		for (Bean bean : genericBeanDAO.selectBeanWhere(type, field, value, like,"name")) {
			result.add((Course) bean);
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
		
		GenericBeanDAO genericBeanDAO = new GenericBeanDAO();

		for(Bean bean : genericBeanDAO.runSql(new Course(), sql)) {
			result.add((Course)bean);
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
		
		GenericBeanDAO genericBeanDAO = new GenericBeanDAO();

		for(Bean bean : genericBeanDAO.runSql(new Institution(), sql)){
			result.add((Institution)bean);
		}
		
		return result;
	}
	
	// This method deletes an Course from Database.
	public boolean deleteCourse() throws SQLException {
		boolean result = false;
		
		GenericBeanDAO genericBeanDAO = new GenericBeanDAO();
		
		for(Institution institution : this.getInstitutions()){
			genericBeanDAO.deleteBeanRelationship(this,institution);
		}
		
		result = genericBeanDAO.deleteBean(this);
		
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
		this.courseId = in.readInt();
		this.courseName = in.readString();
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
		dest.writeInt(this.courseId);
		dest.writeString(this.courseName);
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
