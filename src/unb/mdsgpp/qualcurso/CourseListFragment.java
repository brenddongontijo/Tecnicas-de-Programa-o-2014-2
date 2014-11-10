package unb.mdsgpp.qualcurso;

import java.util.ArrayList;

import models.Course;
import models.Institution;
import android.database.SQLException;
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
 * Class Name: CourseListFragment
 * 
 * This class is responsible for create a fragment containing all courses.
 */
public class CourseListFragment extends ListFragment{

	// Use the id of the institution to list them.
	private static final String ID_INSTITUTION = "idInstitution";
	
	// Use the id of the course to list them.
	private static final String IDS_COURSES = "idsCourses";
	
	// Year that the evaluation was made.
	private static final String YEAR_OF_EVALUATION = "year";
	
	BeanListCallbacks beanCallbacks;
	
	
	public CourseListFragment() {
		super();
		
		Bundle bundle = initiateBundle(0, 0, null);
		
		this.setArguments(bundle);
	}
	
	public static CourseListFragment newInstance(int idInstitution, int evaluationYear){
		Bundle bundle = initiateBundle(idInstitution, evaluationYear, getCoursesList(idInstitution));
		
		CourseListFragment courseListFragment = initiateAndSetArgumentsOfCourseListFragment(bundle);
		
		return courseListFragment;
	}
	
	public static CourseListFragment newInstance(int idInstitution, int evaluationYear, ArrayList<Course> coursesList){
		Bundle bundle = initiateBundle(idInstitution, evaluationYear, coursesList);
		
		CourseListFragment courseListFragment = initiateAndSetArgumentsOfCourseListFragment(bundle);
		
		return courseListFragment;
	}
	
	/**
	 * This method initiate the Bundle with id institution, evaluation year and if necessary an array of courses. 
	 * 
	 * @param idInstitution			institution id present on Database.
	 * @param evaluationYear		year of the evaluation
	 * @param coursesArray			array of courses.
	 * @return						a filled Bundle.
	 */
	private static Bundle initiateBundle(final int idInstitution, final int evaluationYear, final ArrayList<Course> coursesArray){
		Bundle bundle = new Bundle();
		bundle.putInt(ID_INSTITUTION, idInstitution);
		bundle.putInt(YEAR_OF_EVALUATION, evaluationYear);
		
		final boolean coursesArrayNotEmpty = coursesArray != null;
		
		if(coursesArrayNotEmpty) {
			bundle.putParcelableArrayList(IDS_COURSES, coursesArray);
		}
		else {
			
		}
		
		return bundle;
	}
	
	/**
	 * This method set arguments from CourseListFragment to possess a Bundle.
	 * 
	 * @param bundle				
	 * @return						CourseListFragment with bundle.
	 */
	private static CourseListFragment initiateAndSetArgumentsOfCourseListFragment(Bundle bundle){
		CourseListFragment courseListFragment = new CourseListFragment();
		courseListFragment.setArguments(bundle);
		
		return courseListFragment;
	}
	
	/**
	 * This method creates the course list view associated with the CourseListFragment.
	 * 
	 * @param inflater					responsible to inflate a view.
	 * @param container					responsible to generate the LayoutParams of the view.
	 * @param savedInstanceState		responsible for verifying that the fragment will be recreated.
	 * 
	 * @return							view containing all courses.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		ArrayList<Course> coursesArray = fillArrayWithCourses(savedInstanceState);
		
		ListView coursesView = (ListView) inflater.inflate(R.layout.fragment_list, container,
				false);
		coursesView = (ListView) coursesView.findViewById(android.R.id.list);
		
		try {
			final boolean arrayCoursesNotEmpty = (coursesArray != null);
			
			if(arrayCoursesNotEmpty) {
				coursesView.setAdapter(new ArrayAdapter<Course>(
			        getActionBar().getThemedContext(),
			        R.layout.custom_textview,
			        coursesArray));
			}
			else {
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return coursesView;
	}
	
	/**
	 * This method verify if already exists an savedInstance from CourseListFragment and fill the array with
	 * the id of the courses present on Database.
	 * 
	 * @param savedInstanceState		responsible for verifying that the fragment will be recreated.
	 * @return							array with id courses.
	 */
	private ArrayList<Course> fillArrayWithCourses(Bundle savedInstanceState){
		ArrayList<Course> coursesArray;
		
		final boolean noneSavedInstancesWereMade = (getArguments().getParcelableArrayList(IDS_COURSES) != null);
		if(noneSavedInstancesWereMade) {
			coursesArray = getArguments().getParcelableArrayList(IDS_COURSES);
		}
		else {
			coursesArray = savedInstanceState.getParcelableArrayList(IDS_COURSES);
		}
		
		return coursesArray;
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putParcelableArrayList(IDS_COURSES, getArguments().getParcelableArrayList(IDS_COURSES));	
	}
	
	@Override
	public void onListItemClick(ListView coursesListView, View view, final int coursePosition, final long id) {
		forwardsToInstitutionsOrEvaluation(coursesListView, coursePosition);
		
		super.onListItemClick(coursesListView, view, coursePosition, id);
	}
	
	/**
	 * This method verify if any institution was already selected and directs to InstitutionListFragment otherwise
	 * directs to EvaluationDetailFragment.
	 * 
	 * @param coursesListView					ListView containing all courses.
	 * @param coursePosition					position of any position of the chosen course.
	 * @return									instance of InstitutionListFragment or EvaluationDetailFragment.
	 */
	private BeanListCallbacks forwardsToInstitutionsOrEvaluation(ListView coursesListView, final int coursePosition){
		final boolean noneInstitutionSelected = (getArguments().getInt(ID_INSTITUTION) == 0);
		
		if(noneInstitutionSelected) {
			beanCallbacks.onBeanListItemSelected(InstitutionListFragment.
					newInstance((((Course)coursesListView.getAdapter().getItem(coursePosition)).getId()), 
							getArguments().getInt(YEAR_OF_EVALUATION)));
		}
		else {
			beanCallbacks.onBeanListItemSelected(EvaluationDetailFragment.
					newInstance(getArguments().getInt(ID_INSTITUTION), 
							((Course)coursesListView.getAdapter().getItem(coursePosition)).
							getId(),getArguments().getInt(YEAR_OF_EVALUATION)));
		}
		
		return beanCallbacks;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		try {
            beanCallbacks = (BeanListCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()+" must implement BeanListCallbacks.");
        }
	}
	
	@Override
    public void onDetach() {
        super.onDetach();
        beanCallbacks = null;
    }
	
	/**
	 * This method picks all courses or institutions linked to a specific course.
	 * 
	 * @param idInstitution					id institution to be searched on Database.
	 * @return								all courses or institutions linked to a specific course.
	 * @throws SQLException
	 */
	private static ArrayList<Course> getCoursesList(final int idInstitution) throws SQLException{
		if(idInstitution == 0) {
			return Course.getAllCourses();
		}
		else {
			return Institution.getInstitutionByValue(idInstitution).getCoursesByYear();
		}
	}
	
	private ActionBar getActionBar() {
        return ((ActionBarActivity) getActivity()).getSupportActionBar();
    }
	
}
