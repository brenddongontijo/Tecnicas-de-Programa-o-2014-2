package unb.mdsgpp.qualcurso;

import android.database.SQLException;

import java.util.ArrayList;


import java.util.logging.Logger;

import models.Course;
import models.Institution;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Class Name: InstitutionListFragment
 * 
 * This class is responsible for create a fragment containing all institutions.
 */
public class InstitutionListFragment extends ListFragment{
	// Logging system.
	private final static Logger LOGGER = Logger.getLogger(InstitutionListFragment.
			class.getName()); 
	
	// Use the id to show the course.
	private static final String ID_COURSE = "idCourse";
	
	// Use the id to show the institution that have that course.
	private static final String IDS_INSTITUTIONS = "idsInstitutions";
	
	// Show the year that the course was evaluated.
	private static final String YEAR_OF_EVALUATION = "year";
	
	// Callback to call methods in Activity.
	BeanListCallbacks beanCallbacks;
	
	/**
	 * Empty constructor.
	 */
	public InstitutionListFragment() {
		super();
		
		Bundle args = new Bundle();
		args.putInt(ID_COURSE, 0);
		args.putInt(YEAR_OF_EVALUATION, 0);
		args.putParcelableArrayList(IDS_INSTITUTIONS, getInstitutionsList(0));
		
		this.setArguments(args);
	}
	
	/**
	 * This method creates a list of all institutions in Database based on
	 * course.
	 * @param courseId				Database course id.
	 * @param evaluationYear		Evaluation year.
	 * @return
	 */
	public static InstitutionListFragment newInstance(final int courseId, final int evaluationYear){
		InstitutionListFragment fragmentOfInstitutions = new InstitutionListFragment();
		
		// Setting the arguments to get all institutions of a course.
		Bundle args = new Bundle();
		args.putInt(ID_COURSE, courseId);
		args.putInt(YEAR_OF_EVALUATION, evaluationYear);
		args.putParcelableArrayList(IDS_INSTITUTIONS, getInstitutionsList(courseId));
		
		fragmentOfInstitutions.setArguments(args);
		
		return fragmentOfInstitutions;
	}
	
	/**
	 * This method creates a list of all institutions in Database.
	 * @param courseId					Database id for course.
	 * @param evaluationYear			Evaluation year.
	 * @param allInstitutions			List with all institutions.
	 * @return
	 */
	public static InstitutionListFragment newInstance(final int courseId, final int evaluationYear, 
			ArrayList<Institution> allInstitutions){
		
		InstitutionListFragment fragmentOfInstitutions = new InstitutionListFragment();
		
		// Setting the arguments to get all institutions.
		Bundle args = new Bundle();
		args.putInt(ID_COURSE, courseId);
		args.putInt(YEAR_OF_EVALUATION, evaluationYear);
		args.putParcelableArrayList(IDS_INSTITUTIONS, allInstitutions);
		
		LOGGER.info("Bundle successfully initialized and filled!");
		
		fragmentOfInstitutions.setArguments(args);
		
		return fragmentOfInstitutions;
	}
	
	/**
	 * This method get the all institutions variables from a saved state. 
	 */
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putParcelableArrayList(IDS_INSTITUTIONS, getArguments().
				getParcelableArrayList(IDS_INSTITUTIONS));
	}
	
	/**
	 * Called when a fragment is first attached to its activity.
	 * 
	 * @param activity				   single, focused thing that the user can do.
	 */
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		try {
			// Creating a callback for activity.
            beanCallbacks = (BeanListCallbacks) activity;
            
            LOGGER.info("Callback for InstitutionListFragment successfully created!");
        } catch(ClassCastException e) {
            throw new ClassCastException(activity.toString()+" must implement BeanListCallbacks.");
        }
	}
	
	/**
	 * Called once the fragment is associated with its activity.
	 */
	@Override
    public void onDetach() {
        super.onDetach();
        beanCallbacks = null;
    }
	
	/**
	 * This method creates the view associated with the InstitutionListFragment.
	 * 
	 * @param inflater					Responsible to inflate a view.
	 * @param container					Responsible to generate the LayoutParams
	 * 									of the view.
	 * @param savedInstanceState		Responsible for verifying that the fragment 
	 * 									will be recreated.
	 * 
	 * @return							view containing all institutions.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		// Filling the array of institutions bases on saved instance state.
		ArrayList<Institution> listOfInstitutions = fillArrayWithInstitutions(savedInstanceState); 
	
		// Creating a ListView for keep all institutions.
		ListView rootView = (ListView) inflater.inflate(R.layout.fragment_list, 
				container, false);
		rootView = (ListView) rootView.findViewById(android.R.id.list);
		
		// Constant to verify if array of institutions is not empty.
		final boolean arrayInstitutionsNotEmpty = (listOfInstitutions != null);
		
		if(arrayInstitutionsNotEmpty){
			// Calling the adapter to fill the ListView with institutions.
			rootView.setAdapter(new ArrayAdapter<Institution>(
					getActionBar().getThemedContext(),
					R.layout.custom_textview, listOfInstitutions));
			
			LOGGER.info("InstitutionListFragment View sucefully created!");
		}
		else{
			LOGGER.warning("Out of memory to create array of institutions!");
		}
		return rootView;
	}
	
	/**
	 * This method verify if already exists an savedInstance from 
	 * InstitutionListFragment and fill the array of institutions based on this
	 * saved state.
	 * 
	 * @param savedInstanceState		responsible for verifying that the fragment 
	 * 									will be recreated.
	 * @return							array with id courses.
	 */
	private ArrayList<Institution> fillArrayWithInstitutions(Bundle savedInstanceState){
		ArrayList<Institution> listOfInstitutions;
		
		// Constant to verify if there if not saved instances from the fragment. 
		final boolean noneSavedInstancesWereMade = (getArguments().
				getParcelableArrayList(IDS_INSTITUTIONS) != null);
		
		if(noneSavedInstancesWereMade){
			// Creates a new array with institutions.
			listOfInstitutions = getArguments().getParcelableArrayList(IDS_INSTITUTIONS);
		}
		else {
			// Get the array with institutions from saved instance state.
			listOfInstitutions = savedInstanceState.getParcelableArrayList(IDS_INSTITUTIONS);
		}
		
		return listOfInstitutions;
	}
	
	/**
	 * This method directs the click of a institution in course list to the next
	 * fragment.
	 */
	@Override
	public void onListItemClick(ListView listView, View view, int position, long id) {
		final boolean noneCoursesSelected = (getArguments().getInt(ID_COURSE)==0);
		
		// Current selected institution.
		Institution selectedInstitution = (Institution)listView.
				getItemAtPosition(position);
		
		if(noneCoursesSelected) { 
			// Directs to CourseListFragment with selected course.
			beanCallbacks.onBeanListItemSelected(CourseListFragment.newInstance
					(selectedInstitution.getId(), getArguments().
							getInt(YEAR_OF_EVALUATION)));
			
			LOGGER.info("Directing to CourseListFragment...");
		}
		else {
			// Directs to EvaluationDetailFragment with selected institution.
			beanCallbacks.onBeanListItemSelected(EvaluationDetailFragment.newInstance
					(selectedInstitution.getId(), getArguments().getInt(ID_COURSE),
							getArguments().getInt(YEAR_OF_EVALUATION)));
			
			LOGGER.info("Directing to EvaluationDetailFragment...");
		}
		
		super.onListItemClick(listView, view, position, id);
	}
	
	/**
	 * This method picks all courses or institutions linked to a specific
	 * institution.
	 * @param idCourse
	 * @return
	 * @throws SQLException
	 */
	private static ArrayList<Institution> getInstitutionsList(int idCourse) 
			throws SQLException{
		
		// Constant to verify if none institutions has been selected.
		final boolean noneCoursesSelected = (idCourse == 0);
		
		if(noneCoursesSelected) {
			// Getting all institutions.
			return Institution.getAllInstitutions();
		}
		else {
			// Getting institutions related to this course.
			return Course.getCourseByValue(idCourse).getInstitutions();
		}
	}
	
	/**
	 * This method direct to the selected action bar.
	 * @return
	 */
	private ActionBar getActionBar() {
		ActionBar actionBar = ((ActionBarActivity) getActivity()).
				getSupportActionBar();
        
		return actionBar;
    }
}
