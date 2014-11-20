package unb.mdsgpp.qualcurso;

import java.util.ArrayList;
import java.util.Collections;

import models.Course;
import models.Institution;
import models.Search;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class HistoryFragment extends Fragment {

	BeanListCallbacks beanCallbacks;

	public HistoryFragment() {
		super();
	}

	/**
	 * Called when a fragment is first attached to its activity.
	 * onCreate(Bundle) will be called after this.
	 * 
	 * We use the Try- Catch to see if beanCallBacks was initialized with
	 * CurrentActivity .
	 */
	@Override
	public void onAttach(Activity currentActivity) {
		super.onAttach(currentActivity);

		try {
			beanCallbacks = (BeanListCallbacks) currentActivity;
		} catch (ClassCastException e) {
			throw new ClassCastException(currentActivity.toString()
					+ " must implement BeanListCallbacks.");
		}
	}

	/**
	 * Called when the fragment is no longer attached to its activity.
	 */
	@Override
	public void onDetach() {
		super.onDetach();
		beanCallbacks = null;
	}

	/**
	 * Creates and returns the view hierarchy associated with the fragment.
	 * 
	 * @param inflater
	 *            The LayoutInflater object that can be used to inflate any
	 *            views in the fragment.
	 * 
	 * @param container
	 *            If non-null, this is the parent view that the fragment's UI
	 *            should be attached to. The fragment should not add the view
	 *            itself, but this can be used to generate the LayoutParams of
	 *            the view.
	 * @param savedInstanceState
	 *            If non-null, this fragment is being re-constructed from a
	 *            previous saved state as given here.
	 * 
	 * @return historyView view of layout fragment_history instantiated.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View historyView = inflater.inflate(R.layout.fragment_history,
				container, false);

		final ListView historycList = (ListView) historyView
				.findViewById(R.id.listHistory);

		ArrayList<Search> searchRealized = Search.getAllSearch();

		Collections.reverse(searchRealized);

		ListHistoryAdapter histotyAdapter = new ListHistoryAdapter(this
				.getActivity().getApplicationContext(), R.id.listHistory,
				searchRealized);

		historycList.setAdapter((ListAdapter) histotyAdapter);

		historycList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Search search = (Search) parent.getItemAtPosition(position);
				int chosenResearch = search.getOption();

				if (chosenResearch == Search.INSTITUTION) {
					displayList(search);
				} else if (chosenResearch == Search.COURSE) {
					displayList(search);
				}
			}
		});

		return historyView;
	}
	
	/**
	 * Search a list of institution and course receiving a search object.
	 * 
	 * @param search
	 *            Object Search , which searches the items searched using
	 *            institution or courses
	 */
	public void displayList(Search search) {
		ArrayList<Institution> institutions = Institution
				.getInstitutionsByEvaluationFilter(search);

		ArrayList<Course> courses = Course.getCoursesByEvaluationFilter(search);
		int numberOfInstitutions = institutions.size();
		int numberOfCourses = courses.size();
		
		if ((numberOfInstitutions == 0) && (numberOfCourses == 0)) {
			displayToastMessage(getResources().getString(
					R.string.empty_histoty_search_result));
		}
		if (numberOfInstitutions != 0) {
			beanCallbacks.onBeanListItemSelected(SearchListFragment
					.newInstance(institutions, search));
		}
		if (numberOfCourses != 0) {
			beanCallbacks.onBeanListItemSelected(SearchListFragment
					.newInstance(courses, search));
		}

	}

	/**
	 * Displays a message on the screen.
	 * 
	 * @param textMenssage
	 *            text of message.
	 */
	private void displayToastMessage(String textMenssage) {
		Toast toast = Toast.makeText(
				this.getActivity().getApplicationContext(), textMenssage,
				Toast.LENGTH_LONG);
		toast.show();
	}
}
