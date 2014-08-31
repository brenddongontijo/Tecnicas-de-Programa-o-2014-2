package models;

import android.database.SQLException;

import java.util.ArrayList;

public class Evaluation extends Bean {

	private int id;
	private int idInstitution;
	private int idCourse;
	private int year;
	private String modality;
	private int masterDegreeStartYear;
	private int doctorateStartYear;
	private int triennialEvaluation;
	private int permanentTeachers;
	private int theses;
	private int dissertations;
	private int idArticles;
	private int idBooks;
	private int artisticProduction;

	public Evaluation() {
		this.id = 0;
		this.identifier = "evaluation";
		this.relationship = "";

	}

	public Evaluation(int id) {
		this.id = id;
		this.identifier = "evaluation";
		this.relationship = "";
	}

	@Override
	public String get(String field) {
		if (field.equals("_id")) {
			return Integer.toString(this.getId());
		} else if (field.equals("id_institution")) {
			return Integer.toString(this.getIdInstitution());
		} else if (field.equals("id_course")) {
			return Integer.toString(this.getIdCourse());
		} else if (field.equals("year")) {
			return Integer.toString(this.getYear());
		} else if (field.equals("modality")) {
			return getModality();
		} else if (field.equals("master_degree_start_year")) {
			return Integer.toString(this.getMasterDegreeStartYear());
		} else if (field.equals("doctorate_start_year")) {
			return Integer.toString(this.getDoctorateStartYear());
		} else if (field.equals("triennial_evaluation")) {
			return Integer.toString(this.getTriennialEvaluation());
		} else if (field.equals("permanent_teachers")) {
			return Integer.toString(this.getPermanentTeachers());
		} else if (field.equals("theses")) {
			return Integer.toString(this.getTheses());
		} else if (field.equals("dissertations")) {
			return Integer.toString(this.getDissertations());
		} else if (field.equals("id_articles")) {
			return Integer.toString(this.getIdArticles());
		} else if (field.equals("id_books")) {
			return Integer.toString(this.getIdBooks());
		} else if (field.equals("artistic_production")) {
			return Integer.toString(this.getArtisticProduction());
		} else
			return "";

	}

	@Override
	public void set(String field, String data){
		if (field.equals("_id")) {
			this.setId(Integer.parseInt(data));
		} else if (field.equals("id_institution")) {
			this.setIdInstitution(Integer.parseInt(data));
		} else if (field.equals("id_course")) {
			this.setIdCourse(Integer.parseInt(data));
		} else if (field.equals("year")) {
			this.setYear(Integer.parseInt(data));
		} else if (field.equals("modality")) {
			this.setModality(data);
		} else if (field.equals("master_degree_start_year")) {
			this.setMasterDegreeStartYear(Integer.parseInt(data));
		} else if (field.equals("doctorate_start_year")) {
			this.setDoctorateStartYear(Integer.parseInt(data));
		} else if (field.equals("triennial_evaluation")) {
			this.setTriennialEvaluation(Integer.parseInt(data));
		} else if (field.equals("permanent_teachers")) {
			this.setPermanentTeachers(Integer.parseInt(data));
		} else if (field.equals("theses")) {
			this.setTheses(Integer.parseInt(data));
		} else if (field.equals("dissertations")) {
			this.setDissertations(Integer.parseInt(data));
		} else if (field.equals("id_articles")) {
			this.setIdArticles(Integer.parseInt(data));
		} else if (field.equals("id_books")) {
			this.setIdBooks(Integer.parseInt(data));
		} else if (field.equals("artistic_production")) {
			this.setArtisticProduction(Integer.parseInt(data));
		} else{
			
		}
		

	}

	@Override
	public ArrayList<String> fieldsList() {
		ArrayList<String> fields = new ArrayList<String>();
		fields.add("_id");
		fields.add("id_institution");
		fields.add("id_course");
		fields.add("year");
		fields.add("modality");
		fields.add("master_degree_start_year");
		fields.add("doctorate_start_year");
		fields.add("triennial_evaluation");
		fields.add("permanent_teachers");
		fields.add("theses");
		fields.add("dissertations");
		fields.add("id_articles");
		fields.add("id_books");
		fields.add("artistic_production");
		return fields;
	}
	
	/*
	 * The method save() receives an instance from Evaluation and saves into 
	 * Database also setting its Id using the method last() returning true if
	 * the insertion was made correct or false otherwise.
	 */
	public boolean save() throws  SQLException {
		boolean result = false;
		GenericBeanDAO gDB = new GenericBeanDAO();
		result = gDB.insertBean(this);
		this.setId(Evaluation.last().getId());
		return result;
	}

	public static Evaluation get(int id) throws  SQLException {
		Evaluation result = new Evaluation(id);
		GenericBeanDAO gDB = new GenericBeanDAO();
		result = (Evaluation) gDB.selectBean(result);
		return result;
	}

	public static ArrayList<Evaluation> getAll()
			throws  SQLException {
		Evaluation type = new Evaluation();
		ArrayList<Evaluation> result = new ArrayList<Evaluation>();
		GenericBeanDAO gDB = new GenericBeanDAO();
		for (Bean b : gDB.selectAllBeans(type,null)) {
			result.add((Evaluation) b);
		}
		return result;
	}
	
	/*
	 * The method count() uses the method countBean() parsing one object from  
	 * Evaluation to access Database and return the number of Evaluations 
	 * into it.
	 */
	public static int count() throws  SQLException {
		Evaluation type = new Evaluation();
		GenericBeanDAO gDB = new GenericBeanDAO();
		return gDB.countBean(type);
	}

	/*
	 * The method first() uses method firstOrLastBean() from GenericBeanDAO 
	 * parsing one object from Evaluation and a boolean condition "false" to get 
	 * the first "Bean" on Database and then turn it into a Evaluation using casting.
	 */
	public static Evaluation first() throws 
			SQLException {
		Evaluation result = new Evaluation();
		GenericBeanDAO gDB = new GenericBeanDAO();
		result = (Evaluation) gDB.firstOrLastBean(result, false);
		return result;
	}

	/*
	 * The method last() uses the method firstOrLastBean() from GenericBeanDAO 
	 * parsing one object from Evaluation and a boolean condition "true" to get 
	 * the last "Bean" on Database and then turn it into an Evaluation using the 
	 * casting.
	 */
	public static Evaluation last() throws 
			SQLException {
		Evaluation result = new Evaluation();
		GenericBeanDAO gDB = new GenericBeanDAO();
		result = (Evaluation) gDB.firstOrLastBean(result, true);
		return result;
	}

	public static ArrayList<Evaluation> getWhere(String field, String value, boolean like) 
			throws  SQLException {
		Evaluation type = new Evaluation();
		ArrayList<Evaluation> result = new ArrayList<Evaluation>();
		GenericBeanDAO gDB = new GenericBeanDAO();
		for (Bean b : gDB.selectBeanWhere(type, field, value, like,null)) {
			result.add((Evaluation) b);
		}
		return result;
	}
	
	public static Evaluation getFromRelation(int idInstitution, int idCourse, int year){
		Evaluation result = new Evaluation();
		result.setIdInstitution(idInstitution);
		result.setIdCourse(idCourse);
		result.setYear(year);
		ArrayList<String> simplefields = new ArrayList<String>();
		ArrayList<String> fields = new ArrayList<String>();
		fields.add("id_institution");
		fields.add("id_course");
		fields.add("year");
		simplefields.add("id_institution");
		simplefields.add("id_course");
		GenericBeanDAO gDB = new GenericBeanDAO();
		ArrayList<Bean> restricted = gDB.selectFromFields(result,fields,null);
		if(restricted.size() != 0){
			result = (Evaluation)restricted.get(0);
		}else{
			ArrayList<Bean> beans = gDB.selectFromFields(result, simplefields,null);
			result = (Evaluation)beans.get(beans.size()-1);
		}
		return result;
	}
	
	/*
	 * The method delete() access the Database and deletes the current Evaluation
	 * returning "true" if the deletion was made correct or "false" otherwise.
	 */
	public boolean delete() throws  SQLException {
		boolean result = false;
		GenericBeanDAO gDB = new GenericBeanDAO();
		result = gDB.deleteBean(this);
		return result;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdInstitution() {
		return idInstitution;
	}

	public void setIdInstitution(int idInstitution) {
		this.idInstitution = idInstitution;
	}

	public int getIdCourse() {
		return idCourse;
	}

	public void setIdCourse(int idCourse) {
		this.idCourse = idCourse;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getModality() {
		return modality;
	}

	public void setModality(String modality) {
		this.modality = modality;
	}

	public int getMasterDegreeStartYear() {
		return masterDegreeStartYear;
	}

	public void setMasterDegreeStartYear(int masterDegreeStartYear) {
		this.masterDegreeStartYear = masterDegreeStartYear;
	}

	public int getDoctorateStartYear() {
		return doctorateStartYear;
	}

	public void setDoctorateStartYear(int doctorateStartYear) {
		this.doctorateStartYear = doctorateStartYear;
	}

	public int getTriennialEvaluation() {
		return triennialEvaluation;
	}

	public void setTriennialEvaluation(int triennialEvaluation) {
		this.triennialEvaluation = triennialEvaluation;
	}

	public int getPermanentTeachers() {
		return permanentTeachers;
	}

	public void setPermanentTeachers(int permanentTeachers) {
		this.permanentTeachers = permanentTeachers;
	}

	public int getTheses() {
		return theses;
	}

	public void setTheses(int theses) {
		this.theses = theses;
	}

	public int getDissertations() {
		return dissertations;
	}

	public void setDissertations(int dissertations) {
		this.dissertations = dissertations;
	}

	public int getIdArticles() {
		return idArticles;
	}

	public void setIdArticles(int idArticles) {
		this.idArticles = idArticles;
	}

	public int getIdBooks() {
		return idBooks;
	}

	public void setIdBooks(int idBooks) {
		this.idBooks = idBooks;
	}

	public int getArtisticProduction() {
		return artisticProduction;
	}

	public void setArtisticProduction(int artisticProduction) {
		this.artisticProduction = artisticProduction;
	}

}
