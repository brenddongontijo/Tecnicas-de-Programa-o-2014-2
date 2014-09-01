package models;

import android.database.SQLException;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;


public class Course extends Bean implements Parcelable{
	private int id;
	private String name;

	public Course() {
		this.id = 0;
		this.identifier= "course";
		this.relationship = "courses_institutions";
	}
	
	public Course(int id){
		this.id = id;
		this.identifier= "course";
		this.relationship = "courses_institutions";
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	/*
	 * The method save() receives an instance from Course and saves into 
	 * Database also setting his Id using the method last() returning true if
	 * the insertion was made correct or false otherwise.
	 */
	public boolean save() throws  SQLException {
		boolean result = false;
		GenericBeanDAO gDB = new GenericBeanDAO();
		
		result = gDB.insertBean(this);
		this.setId(Course.last().getId());
		
		return result;
	}
	
	/*
	 * The method addInstitution() relates one Course with one Institution
	 * passing as a parameter to addBeanRelationship() the current instance 
	 * of Course and one institution.
	 */
	public boolean addInstitution(Institution institution) throws  SQLException {
		boolean result = false;
		GenericBeanDAO gDB = new GenericBeanDAO();
		
		result = gDB.addBeanRelationship(this, institution);
		
		return result;
	}
	/*
	 * The method get() receives one "id" witch will be the search parameter 
	 * to find a determinate Course on Database. 
	 */
	public static Course get(int id) throws SQLException {
		Course result = new Course(id);
		GenericBeanDAO gDB = new GenericBeanDAO();
		
		result = (Course) gDB.selectBean(result);
		
		return result;
	}

	/*
	 * The method getAll() get all "Beans" on Database and put them within a
	 * ArrayList of Course before make a casting from Bean to Course.
	 */
	public static ArrayList<Course> getAll() throws SQLException {
		Course type = new Course();
		ArrayList<Course> result = new ArrayList<Course>();
		GenericBeanDAO gDB = new GenericBeanDAO();
		
		for(Bean b : gDB.selectAllBeans(type,"name")) {
			result.add((Course) b);
		}
		
		return result;
	}

	/*
	 * The method count() uses the method countBean() parsing one object from  
	 * Course to access Database and return the number of Courses 
	 * into it.
	 */
	public static int count() throws  SQLException {
		Course type = new Course();
		GenericBeanDAO gDB = new GenericBeanDAO();
		
		return gDB.countBean(type);
	}
	
	/*
	 * The method first() uses method firstOrLastBean() from GenericBeanDAO 
	 * parsing one object from Course and a boolean condition "false" to get 
	 * the first "Bean" on Database and then turn it into a Course using casting.
	 */
	public static Course first() throws SQLException {
		Course result = new Course();
		GenericBeanDAO gDB = new GenericBeanDAO();
		
		result = (Course) gDB.firstOrLastBean(result, false);
		
		return result;
	}

	/*
	 * The method last() uses the method firstOrLastBean() from GenericBeanDAO 
	 * parsing one object from Course and a boolean condition "true" to get 
	 * the last "Bean" on Database and then turn it into an Course using the 
	 * casting.
	 */
	public static Course last() throws SQLException {
		Course result = new Course();
		GenericBeanDAO gDB = new GenericBeanDAO();
		
		result = (Course) gDB.firstOrLastBean(result, true);
		
		return result;
	}

	public ArrayList<Institution> getInstitutions() throws SQLException {
		ArrayList<Institution> institutions = new ArrayList<Institution>();
		GenericBeanDAO gDB = new GenericBeanDAO();
		
		for(Bean b : gDB.selectBeanRelationship(this, "institution", "acronym")) {
			institutions.add((Institution) b);
		}
		
		return institutions;
	}
	
	public ArrayList<Institution> getInstitutions(int year) throws 
			SQLException {
		ArrayList<Institution> institutions = new ArrayList<Institution>();
		GenericBeanDAO gDB = new GenericBeanDAO();
		
		for (Bean b : gDB.selectBeanRelationship(this, "institution",year,"acronym")) {
			institutions.add((Institution) b);
		}
		
		return institutions;
}

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
	
	public static ArrayList<Course> getCoursesByEvaluationFilter(Search search) throws  SQLException {
		ArrayList<Course> result = new ArrayList<Course>();
		String sql = "SELECT c.* FROM course AS c, evaluation AS e, articles AS a, books AS b "+
					" WHERE year="+Integer.toString(search.getYear())+
					" AND e.id_course = c._id"+
					" AND e.id_articles = a._id"+
					" AND e.id_books = b._id"+
					" AND "+search.getIndicator().getValue();

		if(search.getMaxValue() == -1){
			sql+=" >= "+Integer.toString(search.getMinValue());
		}else{
			sql+=" BETWEEN "+Integer.toString(search.getMinValue())+" AND "+Integer.toString(search.getMaxValue());
		}
		sql+=" GROUP BY c._id";
		GenericBeanDAO
		gDB = new GenericBeanDAO();

		for (Bean b : gDB.runSql(new Course(), sql)){
			result.add((Course)b);
		}

		return result;
	}

	public static ArrayList<Institution> getInstitutionsByEvaluationFilter(
			int id_course, Search search) throws  SQLException {
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

		for (Bean b : gDB.runSql(new Institution(), sql)){
			result.add((Institution)b);
		}
		
		return result;
	}
	
	/*
	 * The method delete() access the Database and deletes the current Course
	 * returning "true" if the deletion was made correct or "false" otherwise.
	 */
	public boolean delete() throws SQLException {
		boolean result = false;
		
		GenericBeanDAO gDB = new GenericBeanDAO();
		
		for(Institution i : this.getInstitutions()){
			gDB.deleteBeanRelationship(this,i);
		}
		
		result = gDB.deleteBean(this);
		
		return result;
	}
	
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
	
	/*
	 * Interface that must be implemented and provided as a public CREATOR field that generates 
	 * instances for Parcelable class from a Parcel.
	 */
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
