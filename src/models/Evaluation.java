package models;

import android.database.SQLException;

import java.util.ArrayList;

/**
 * Class Name: Evaluation.
 * 
 * This class receives all data based on university evaluation from
 * "Portal Brasileiro de Dados Abertos" xls spreadsheet.
 */
public class Evaluation extends Bean {

	// Database id to Evaluation.
	private int id;
	
	// Use the Database id of Institution.
	private int idInstitution;
	
	// Use the Database id of Course.
	private int idCourse;
	
	// Year that the evaluation of a course was made.
	private int evaluationYear;
	
	// Modality of the evaluation.
	private String evaluationModality;
	
	// Year of creation of the master degree.
	private int masterDegreeStartYear;
	
	// Year of creation of the doctorate degree.
	private int doctorateStartYear;
	
	// Show the triennial Evaluation of a course.
	private int triennialEvaluation;
	
	// Show the permanent Teachers.
	private int permanentTeachers;
	
	// Number of theses.
	private int theses;
	
	// Number of dissertations.
	private int dissertations;
	
	// Use database id of Articles.
	private int idArticles;
	
	// Use database id of Books.
	private int idBooks;
	
	private int artisticProduction;

	// Empty constructor.
	public Evaluation() {
		this.id = 0;
		this.identifier = "evaluation";
		this.relationship = "";

	}

	// Declaration of a non-default constructor.
	public Evaluation(int evaluationID) {
		this.id = evaluationID;
		this.identifier = "evaluation";
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

	// Access variable idInstitution. 
	public int getIdInstitution() {
		return idInstitution;
	}

	// Modify variable idInstitution.
	public void setIdInstitution(int idInstitution) {
		this.idInstitution = idInstitution;
	}

	// Access variable idCourse. 
	public int getIdCourse() {
		return idCourse;
	}

	// Modify variable idCourse.
	public void setIdCourse(int idCourse) {
		this.idCourse = idCourse;
	}

	// Access variable evaluationYear. 
	public int getEvaluationYear() {
		return evaluationYear;
	}

	// Modify variable evaluationYear.
	public void setEvaluationYear(int year) {
		this.evaluationYear = year;
	}

	// Access variable evaluationModality. 
	public String getEvaluationModality() {
		return evaluationModality;
	}

	// Modify variable evaluationModality.
	public void setEvaluationModality(String modality) {
		this.evaluationModality = modality;
	}

	// Access variable masterDegreeStartYear. 
	public int getMasterDegreeStartYear() {
		return masterDegreeStartYear;
	}

	// Modify variable masterDegreeStartYear.
	public void setMasterDegreeStartYear(int masterDegreeStartYear) {
		this.masterDegreeStartYear = masterDegreeStartYear;
	}

	// Access variable doctorateStartYear. 
	public int getDoctorateStartYear() {
		return doctorateStartYear;
	}

	// Modify variable doctorateStartYear.
	public void setDoctorateStartYear(int doctorateStartYear) {
		this.doctorateStartYear = doctorateStartYear;
	}

	// Access variable triennialEvaluation. 
	public int getTriennialEvaluation() {
		return triennialEvaluation;
	}

	// Modify variable triennialEvaluation.
	public void setTriennialEvaluation(int triennialEvaluation) {
		this.triennialEvaluation = triennialEvaluation;
	}

	// Access variable permanentTeachers. 
	public int getPermanentTeachers() {
		return permanentTeachers;
	}

	// Modify variable permanentTeachers.
	public void setPermanentTeachers(int permanentTeachers) {
		this.permanentTeachers = permanentTeachers;
	}

	// Access variable theses. 
	public int getTheses() {
		return theses;
	}

	// Modify variable theses.
	public void setTheses(int theses) {
		this.theses = theses;
	}

	// Access variable dissertations. 
	public int getDissertations() {
		return dissertations;
	}

	// Modify variable dissertations.
	public void setDissertations(int dissertations) {
		this.dissertations = dissertations;
	}

	// Access variable dissertations. 
	public int getIdArticles() {
		return idArticles;
	}

	// Modify variable dissertations.
	public void setIdArticles(int idArticles) {
		this.idArticles = idArticles;
	}

	// Access variable idBooks. 
	public int getIdBooks() {
		return idBooks;
	}

	// Modify variable idBooks.
	public void setIdBooks(int idBooks) {
		this.idBooks = idBooks;
	}

	// Access variable artisticProduction. 
	public int getArtisticProduction() {
		return artisticProduction;
	}

	// Modify variable artisticProduction.
	public void setArtisticProduction(int artisticProduction) {
		this.artisticProduction = artisticProduction;
	}

	/**
	 * Rewriting fields to String.
	 */
	@Override
	public String get(String field) {
		if(field.equals("_id")) {
			return Integer.toString(this.getId());
		} 
		else if(field.equals("id_institution")) {
			return Integer.toString(this.getIdInstitution());
		}
		else if(field.equals("id_course")) {
			return Integer.toString(this.getIdCourse());
		}
		else if(field.equals("year")) {
			return Integer.toString(this.getEvaluationYear());
		}
		else if(field.equals("modality")) {
			return getEvaluationModality();
		}
		else if(field.equals("master_degree_start_year")) {
			return Integer.toString(this.getMasterDegreeStartYear());
		}
		else if(field.equals("doctorate_start_year")) {
			return Integer.toString(this.getDoctorateStartYear());
		}
		else if(field.equals("triennial_evaluation")) {
			return Integer.toString(this.getTriennialEvaluation());
		}
		else if(field.equals("permanent_teachers")) {
			return Integer.toString(this.getPermanentTeachers());
		}
		else if(field.equals("theses")) {
			return Integer.toString(this.getTheses());
		}
		else if(field.equals("dissertations")) {
			return Integer.toString(this.getDissertations());
		}
		else if(field.equals("id_articles")) {
			return Integer.toString(this.getIdArticles());
		}
		else if(field.equals("id_books")) {
			return Integer.toString(this.getIdBooks());
		}
		else if(field.equals("artistic_production")) {
			return Integer.toString(this.getArtisticProduction());
		}
		else {
			return "";
		}
	}

	/**
	 * Rewriting fields to Integer.
	 */
	@Override
	public void set(String field, String data){
		if(field.equals("_id")) {
			this.setId(Integer.parseInt(data));
		} 
		else if(field.equals("id_institution")) {
			this.setIdInstitution(Integer.parseInt(data));
		} 
		else if(field.equals("id_course")) {
			this.setIdCourse(Integer.parseInt(data));
		} 
		else if(field.equals("year")) {
			this.setEvaluationYear(Integer.parseInt(data));
		} 
		else if(field.equals("modality")) {
			this.setEvaluationModality(data);
		} 
		else if(field.equals("master_degree_start_year")) {
			this.setMasterDegreeStartYear(Integer.parseInt(data));
		} 
		else if(field.equals("doctorate_start_year")) {
			this.setDoctorateStartYear(Integer.parseInt(data));
		} 
		else if(field.equals("triennial_evaluation")) {
			this.setTriennialEvaluation(Integer.parseInt(data));
		} 
		else if(field.equals("permanent_teachers")) {
			this.setPermanentTeachers(Integer.parseInt(data));
		} 
		else if(field.equals("theses")) {
			this.setTheses(Integer.parseInt(data));
		} 
		else if(field.equals("dissertations")) {
			this.setDissertations(Integer.parseInt(data));
		} 
		else if(field.equals("id_articles")) {
			this.setIdArticles(Integer.parseInt(data));
		} 
		else if(field.equals("id_books")) {
			this.setIdBooks(Integer.parseInt(data));
		} 
		else if(field.equals("artistic_production")) {
			this.setArtisticProduction(Integer.parseInt(data));
		} 
	}

	/**
	 * Creating an ArrayList of String with evaluation book values.
	 */
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
	
	/**
	 * This method saves one Evaluation into Database.
	 * 
	 * @return inserted evaluation.
	 * @throws SQLException
	 */
	public boolean saveEvaluation() throws SQLException {
		boolean saveResult = false;
		
		GenericBeanDAO genericBeanDAO = new GenericBeanDAO();
		
		saveResult = genericBeanDAO.insertBean(this);
		this.setId(Evaluation.lastEvaluation().getId());
		
		return saveResult;
	}

	/**
	 * This method picks an Evaluation on Database based on his id.
	 * 
	 * @param evaluationId 
	 * @return evaluation that mathes the id.
	 * @throws SQLException
	 */
	public static Evaluation getEvaluationById(int evaluationId) throws SQLException {
		Evaluation evaluation = new Evaluation(evaluationId);
		GenericBeanDAO genericBeanDAO = new GenericBeanDAO();
		
		evaluation = (Evaluation) genericBeanDAO.selectBean(evaluation);
		
		return evaluation;
	}

	/**
	 * This method get all Evaluations from database.
	 * 
	 * @return an array of evaluation.
	 * @throws SQLException
	 */
	public static ArrayList<Evaluation> getAllEvaluations() throws SQLException {
		
		Evaluation evaluation = new Evaluation();
		GenericBeanDAO genericBeanDAO = new GenericBeanDAO();
		
		ArrayList<Evaluation> arrayOfEvaluations = new ArrayList<Evaluation>();
		
		for (Bean bean : genericBeanDAO.selectAllBeans(evaluation,null)) {
			arrayOfEvaluations.add((Evaluation) bean);
		}
		
		return arrayOfEvaluations;
	}
	
	/**
	 * This method counts the number of Evaluations into Database.
	 * 
	 * @return the numbers of evaluations in database.
	 * @throws SQLException
	 */
	public static int numberOfEvaluations() throws SQLException {
		int numberOfEvaluations = 0;
		
		Evaluation evaluation = new Evaluation();
		GenericBeanDAO genericBeanDAO = new GenericBeanDAO();
		
		numberOfEvaluations = genericBeanDAO.countBean(evaluation);
		
		return numberOfEvaluations;
	}

	/**
	 * This method returns the first Evaluation into Database.
	 * 
	 * @return the first evaluation.
	 * @throws SQLException
	 */
	public static Evaluation firstEvaluation() throws SQLException {
		Evaluation evaluation = new Evaluation();
		GenericBeanDAO genericBeanDAO = new GenericBeanDAO();
		
		evaluation = (Evaluation) genericBeanDAO.firstOrLastBean(evaluation, false);
		
		return evaluation;
	}

	/**
	 * This method returns the last Evaluation into Database.
	 * 
	 * @return last evaluation.
	 * @throws SQLException
	 */
	public static Evaluation lastEvaluation() throws SQLException {
		Evaluation evaluation = new Evaluation();
		GenericBeanDAO genericBeanDAO = new GenericBeanDAO();
		
		evaluation = (Evaluation) genericBeanDAO.firstOrLastBean(evaluation, true);
		
		return evaluation;
	}

	/**
	 * This method will try to find an Evaluation based on a search.
	 *   
	 * @param field
	 * @param value
	 * @param like
	 * @return an array of evaluation.
	 * @throws SQLException
	 */
	public static ArrayList<Evaluation> getWhere(String field, String value, boolean like) 
			throws SQLException {
		
		Evaluation evaluation = new Evaluation();
		GenericBeanDAO genericBeanDAO = new GenericBeanDAO();
		
		ArrayList<Evaluation> arrayOfEvaluations = new ArrayList<Evaluation>();
		
		for(Bean bean : genericBeanDAO.selectBeanWhere(evaluation, field, value, like, null)) {
			arrayOfEvaluations.add((Evaluation) bean);
		}
		
		return arrayOfEvaluations;
	}
	
	/**
	 * This method returns a Evaluation between a Institution and Course.
	 * 
	 * @param idInstitution
	 * @param idCourse
	 * @param evaluationYear
	 * @return the evaluation.
	 */
	public static Evaluation getFromRelation(int idInstitution, int idCourse, int evaluationYear){
		Evaluation evaluation = new Evaluation();
		evaluation.setIdInstitution(idInstitution);
		evaluation.setIdCourse(idCourse);
		evaluation.setEvaluationYear(evaluationYear);
		
		ArrayList<String> fieldsOfInstitutionCourse = new ArrayList<String>();
		fieldsOfInstitutionCourse.add("id_institution");
		fieldsOfInstitutionCourse.add("id_course");
		fieldsOfInstitutionCourse.add("year");
		
		GenericBeanDAO genericBeanDAO = new GenericBeanDAO();
		
		ArrayList<Bean> restricted = genericBeanDAO.selectFromFields(evaluation, fieldsOfInstitutionCourse, null);
		
		if(restricted.size() != 0) {
			evaluation = (Evaluation)restricted.get(0);
		}
		else {
			ArrayList<String> simplefields = new ArrayList<String>();
			simplefields.add("id_institution");
			simplefields.add("id_course");
			
			ArrayList<Bean> beans = genericBeanDAO.selectFromFields(evaluation, simplefields, null);
			
			evaluation = (Evaluation)beans.get(beans.size()-1);
		}
		
		return evaluation;
	}
	
	/**
	 * This method deletes an Evaluation from Database.
	 * 
	 * @return the deleted evaluation.
	 * @throws SQLException
	 */
	public boolean deleteEvaluation() throws  SQLException {
		boolean deleteResult = false;
		
		GenericBeanDAO genericBeanDAO = new GenericBeanDAO();
		
		deleteResult = genericBeanDAO.deleteBean(this);
		
		return deleteResult;
	}

}
