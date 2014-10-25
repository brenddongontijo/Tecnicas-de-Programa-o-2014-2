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
	
	private static Bundle initiateBundle(final int idInstitution, final int evaluationYear, final ArrayList<Course> coursesArray){
		Bundle bundle = new Bundle();
		bundle.putInt(ID_INSTITUTION, idInstitution);
		bundle.putInt(YEAR_OF_EVALUATION, evaluationYear);
		
		final boolean coursesArrayNotEmpty = coursesArray != null;
		
		if(coursesArrayNotEmpty){
			bundle.putParcelableArrayList(IDS_COURSES, coursesArray);
		}
		else{
			
		}
		
		return bundle;
	}
	
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
			
			if(arrayCoursesNotEmpty){
				coursesView.setAdapter(new ArrayAdapter<Course>(
			        getActionBar().getThemedContext(),
			        R.layout.custom_textview,
			        coursesArray));
			}
			else{
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return coursesView;
	}
	
	private ArrayList<Course> fillArrayWithCourses(Bundle savedInstanceState){
		ArrayList<Course> coursesArray;
		
		final boolean noneSavedInstancesWereMade = getArguments().getParcelableArrayList(IDS_COURSES) != null;
		if(noneSavedInstancesWereMade){
			coursesArray = getArguments().getParcelableArrayList(IDS_COURSES);
		}
		else{
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
	public void onListItemClick(ListView listView, View view, int position, long id) {
		if(getArguments().getInt(ID_INSTITUTION) == 0){
			
			beanCallbacks.onBeanListItemSelected(InstitutionListFragment.
					newInstance((((Course)listView.getAdapter().getItem(position)).getId()), 
							getArguments().getInt(YEAR_OF_EVALUATION)));
			
		}else{
			
			beanCallbacks.onBeanListItemSelected(EvaluationDetailFragment.
					newInstance(getArguments().getInt(ID_INSTITUTION), 
							((Course)listView.getAdapter().getItem(position)).
							getId(),getArguments().getInt(YEAR_OF_EVALUATION)));
			
		}
		super.onListItemClick(listView, view, position, id);
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
	
	private static ArrayList<Course> getCoursesList(int idInstitution) throws SQLException{
		if(idInstitution == 0){
			return Course.getAllCourses();
		}else{
			return Institution.getInstitutionByValue(idInstitution).getCoursesByYear();
		}
	}
	
	private ActionBar getActionBar() {
        return ((ActionBarActivity) getActivity()).getSupportActionBar();
    }
	
}
