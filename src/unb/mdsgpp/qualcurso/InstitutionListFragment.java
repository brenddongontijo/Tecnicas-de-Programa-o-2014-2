package unb.mdsgpp.qualcurso;

import android.database.SQLException;

import java.util.ArrayList;


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

public class InstitutionListFragment extends ListFragment{
	
	// Use the id to show the course.
	private static final String ID_COURSE = "idCourse";
	
	// Use the id to show the institution that have that course.
	private static final String IDS_INSTITUTIONS = "idsInstitutions";
	
	// Show the year that the course was evaluated.
	private static final String YEAR_OF_EVALUATION = "year";
	
	BeanListCallbacks beanCallbacks;
	
	public InstitutionListFragment() {
		super();
		
		Bundle args = new Bundle();
		args.putInt(ID_COURSE, 0);
		args.putInt(YEAR_OF_EVALUATION, 0);
		args.putParcelableArrayList(IDS_INSTITUTIONS, getInstitutionsList(0));
		
		this.setArguments(args);
	}
	
	public static InstitutionListFragment newInstance(final int courseId, final int evaluationYear){
		InstitutionListFragment fragmentOfInstitutions = new InstitutionListFragment();
		
		Bundle args = new Bundle();
		args.putInt(ID_COURSE, courseId);
		args.putInt(YEAR_OF_EVALUATION, evaluationYear);
		args.putParcelableArrayList(IDS_INSTITUTIONS, getInstitutionsList(courseId));
		
		fragmentOfInstitutions.setArguments(args);
		
		return fragmentOfInstitutions;
	}
	
	public static InstitutionListFragment newInstance(final int courseId, final int evaluationYear, 
			ArrayList<Institution> allInstitutions){
		
		InstitutionListFragment fragmentOfInstitutions = new InstitutionListFragment();
		
		Bundle args = new Bundle();
		args.putInt(ID_COURSE, courseId);
		args.putInt(YEAR_OF_EVALUATION, evaluationYear);
		args.putParcelableArrayList(IDS_INSTITUTIONS, allInstitutions);
		
		fragmentOfInstitutions.setArguments(args);
		
		return fragmentOfInstitutions;
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putParcelableArrayList(IDS_INSTITUTIONS, getArguments().
				getParcelableArrayList(IDS_INSTITUTIONS));
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		ArrayList<Institution> listOfInstitutions; 
	
		if(getArguments().getParcelableArrayList(IDS_INSTITUTIONS) != null){
			listOfInstitutions = getArguments().getParcelableArrayList(IDS_INSTITUTIONS);
		}
		else {
			listOfInstitutions = savedInstanceState.getParcelableArrayList(IDS_INSTITUTIONS);
		}
		
		ListView rootView = (ListView) inflater.inflate(R.layout.fragment_list, container,
				false);
		rootView = (ListView) rootView.findViewById(android.R.id.list);
		
		try {
			if(listOfInstitutions != null){
				rootView.setAdapter(new ArrayAdapter<Institution>(
						getActionBar().getThemedContext(),
						R.layout.custom_textview, listOfInstitutions));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return rootView;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		try {
            beanCallbacks = (BeanListCallbacks) activity;
        } catch(ClassCastException e) {
            throw new ClassCastException(activity.toString()+" must implement BeanListCallbacks.");
        }
	}
	
	@Override
    public void onDetach() {
        super.onDetach();
        beanCallbacks = null;
    }
	
	@Override
	public void onListItemClick(ListView listView, View view, int position, long id) {
		if(getArguments().getInt(ID_COURSE)==0) {
			beanCallbacks.onBeanListItemSelected(CourseListFragment.newInstance
					(((Institution)listView.getItemAtPosition(position)).getId(),getArguments()
							.getInt(YEAR_OF_EVALUATION)));
		}
		else {
			beanCallbacks.onBeanListItemSelected(EvaluationDetailFragment.newInstance
					(((Institution)listView.getItemAtPosition(position)).getId() ,getArguments().
							getInt(ID_COURSE),getArguments().getInt(YEAR_OF_EVALUATION)));
		}
		super.onListItemClick(listView, view, position, id);
	}
	
	private static ArrayList<Institution> getInstitutionsList(int idCourse) throws SQLException{
		if(idCourse == 0) {
			return Institution.getAllInstitutions();
		}
		else {
			return Course.getCourseByValue(idCourse).getInstitutions();
		}
	}
	
	private ActionBar getActionBar() {
        return ((ActionBarActivity) getActivity()).getSupportActionBar();
    }
}
