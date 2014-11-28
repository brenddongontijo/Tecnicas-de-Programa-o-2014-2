package unb.mdsgpp.qualcurso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Logger;

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

/**
 * Class name: HistoryFragment
 * This class is responsible for create a fragment related with all search made.
 */
public class HistoryFragment extends Fragment implements OnItemClickListener{
	// Logging system.
	private final static Logger LOGGER = Logger.getLogger(HistoryFragment.
			class.getName()); 
	
	// Callback to call methods in Activity.
	BeanListCallbacks beanCallbacks;

	/**
	 * Empty constructor.
	 */
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
			// Creating a callback for activity.
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

		// Inflating the view for this fragment.
		View historyView = inflater.inflate(R.layout.fragment_history,
				container, false);
		
		// Creating a ListView that will contain all search made.
		final ListView historyList = (ListView) historyView
				.findViewById(R.id.listHistory);

		// Filling a list in a descending order with all search. 
		ArrayList<Search> allSearchRealized = Search.getAllSearch();
		Collections.reverse(allSearchRealized);

		// Calling the adapter that will fill all fields of the ListView.
		ListHistoryAdapter historyAdapter = new ListHistoryAdapter(this
				.getActivity().getApplicationContext(), R.id.listHistory,
				allSearchRealized);
		historyList.setAdapter((ListAdapter) historyAdapter);
		
		// Preparing the action by clicking in one of the search made.
		historyList.setOnItemClickListener((OnItemClickListener) this);
		
		LOGGER.info("HistoryFragment view sucefully created!");
		
		return historyView;
	}
	
	/**
	 * This method will direct to the search selected.
	 * 
	 * @param search		
	 *            
	 */
	public void displaySearchPerformed(Search search) {
		// Getting all course or institution researched made.
		ArrayList<Institution> institutions = Institution
				.getInstitutionsByEvaluationFilter(search);
		ArrayList<Course> courses = Course.getCoursesByEvaluationFilter(search);
		
		// Getting the number of institutions and courses researched.
		final int numberOfInstitutions = institutions.size();
		final int numberOfCourses = courses.size();
		
		// Constants to verify what type of research has been made.
		final boolean onlyResearchedInstitutions = (numberOfInstitutions != 0);
		final boolean onlyResearchedCourses = (numberOfCourses != 0);
		
		if (onlyResearchedInstitutions) {
			// Directing to institution search.
			beanCallbacks.onBeanListItemSelected(SearchListFragment
					.newInstance(institutions, search));
		}
		else if (onlyResearchedCourses) {
			// Directing to course search.
			beanCallbacks.onBeanListItemSelected(SearchListFragment
					.newInstance(courses, search));
		}
		else {
			// None research made, displaying a toast message.
			displayToastMessage(getResources().getString(
					R.string.empty_histoty_search_result));
		}

	}

	/**
	 * Displays a message on the screen.
	 * 
	 * @param textMenssage
	 *            text of message.
	 */
	private void displayToastMessage(String textMessage) {
		// Creating the toast message with the text message.
		Toast toast = Toast.makeText(
				this.getActivity().getApplicationContext(), textMessage,
				Toast.LENGTH_LONG);
		
		// Showing the message.
		toast.show();
	}

	/**
	 * This method is called when a item in ListHistoryAdapter has been clicked.
	 * Will directs to the searched made.
	 * 
	 * This is an Android default method, DON'T RENAME IT!
	 * 
	 * @param parentView 		Current view where the click happened.
	 * @param adapterView		View of the ListHistoryAdapter.
	 * @param adapterPosition	adapterView position.
	 * @param itemClickId		id of the clicked item.
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View adapterView,
			int adapterPosition, long itemClickId) {
		
		// Preparing the search made and getting his position.
		Search searchMade = (Search) parent.getItemAtPosition(adapterPosition);
		final int chosenResearch = searchMade.getOption();
		
		// Constants to tell what type of search has been made.
		final boolean institutionSearchMade = (chosenResearch == Search.INSTITUTION);
		final boolean courseSearchMade = (chosenResearch == Search.COURSE);
		
		if (institutionSearchMade) {
			// Directing to the institutionSearch.
			displaySearchPerformed(searchMade);
			
			LOGGER.info("Directing to institution search made...");
		} 
		else if (courseSearchMade) {
			// Directing to the courseSearch.
			displaySearchPerformed(searchMade);
			
			LOGGER.info("Directing to course search made...");
		}
	}
}
