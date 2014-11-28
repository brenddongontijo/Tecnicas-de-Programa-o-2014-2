package unb.mdsgpp.qualcurso;

import helpers.Indicator;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import models.Course;
import models.Institution;
import models.Search;
import android.app.Activity;
import android.database.SQLException;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Class name: SearchListFragment.
 * This class is responsible for create a fragment related with the search
 * of institution or course.
 */
public class SearchListFragment extends ListFragment {
	// Logging system.
	private final static Logger LOGGER = Logger.getLogger(SearchListFragment.
			class.getName()); 
	
	// Variable used to keep the year of the evaluation.
	private static final String evaluationYear = "year";
	
	// Variable used to take the indicator and put on the field.
	private static final String indicatorField = "field";
	
	// Minimum value for search.
	private static final String minimumValue = "rangeA";
	
	// Minimum value for search.
	private static final String maximumValue = "rangeB";
	
	// List that will contain beans.
	private static final String listOfBeans = "beanList";

	// Used to call methods in Activity.
	BeanListCallbacks beanCallbacks;

	// Empty constructor.
	public SearchListFragment() {

	}
	
	/**
	 * This method creates a new instance of SearchListFragment containing the
	 * arguments from parcelable of search.
	 * 
	 * @param parcelableList
	 * @param search
	 * @return
	 */
	public static SearchListFragment newInstance(
			ArrayList<? extends Parcelable> parcelableList, Search search) {

		// Creating the search list fragment.
		SearchListFragment searchListFragment = new SearchListFragment();

		// Creating a bundle and filling it with evaluation year, indicator
		// minimum value, maximum value and a list of beans(course or institution)
		Bundle fieldsToBeFilled = new Bundle();
		fieldsToBeFilled.putInt(evaluationYear, search.getYear());
		fieldsToBeFilled.putString(indicatorField, search.getIndicator()
				.getValue());
		fieldsToBeFilled.putInt(minimumValue, search.getMinValue());
		fieldsToBeFilled.putInt(maximumValue, search.getMaxValue());
		fieldsToBeFilled.putParcelableArrayList(listOfBeans, parcelableList);

		// Setting search list fragment arguments.
		searchListFragment.setArguments(fieldsToBeFilled);

		return searchListFragment;
	}

	/**
	 * Called once the fragment is associated with its activity.
	 */
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			// Creating a callback for activity.
			beanCallbacks = (BeanListCallbacks) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement BeanListCallbacks.");
		}
	}

	/**
	 * Called immediately prior to the fragment no longer being associated with its activity.
	 */
	@Override
	public void onDetach() {
		super.onDetach();
		beanCallbacks = null;
	}

	/**
	 * This method creates the compare choose view associated with the SearchListFragment.
	 * 
	 * @param inflater					responsible to inflate a view.
	 * @param container					responsible to generate the LayoutParams of the view.
	 * @param savedInstanceState		responsible for verifying that the fragment will be recreated.
	 * 
	 * @return							current view of SearchListFragment associated with parameters chosen.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		ArrayList<Parcelable> beanList;
		
		// Constant to verify if there is already any saved instance.
		final boolean noneSavedInstanceState = (getArguments().
				getParcelableArrayList(listOfBeans) != null);
		
		if(noneSavedInstanceState) {
			// Creating the new arguments.
			beanList = getArguments().getParcelableArrayList(listOfBeans);
		} 
		else {
			// Getting arguments from saved instance.
			beanList = savedInstanceState.getParcelableArrayList(listOfBeans);
		}

		// Creating the ListView for the selected option (course/institution)
		ListView courseInstitutionListView = (ListView) inflater.inflate(R.
				layout.fragment_list, container, false);
		courseInstitutionListView = (ListView) courseInstitutionListView.findViewById(android.R.id.list);
		
		// Filling the ListView with courses or institutions.
		courseInstitutionListView.setAdapter(new ArrayAdapter<Parcelable>(getActionBar()
				.getThemedContext(), R.layout.custom_textview, beanList));

		return courseInstitutionListView;
	}

	/**
	 * Called to ask the fragment to save its current dynamic state, so it can
	 * later be reconstructed in a new instance of its process is restarted.
	 */
	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putParcelableArrayList(listOfBeans, getArguments()
				.getParcelableArrayList(listOfBeans));

		super.onSaveInstanceState(outState);
	}

	/**
	 * This method will be called when an item in the list is selected.
	 */
	@Override
	public void onListItemClick(ListView listView, View currentView,
			int selectedBeanPosition, long id) {
		
		// Create a parcelabe for the selected bean.
		Parcelable typeBean = (Parcelable) listView
				.getItemAtPosition(selectedBeanPosition);

		// Getting the selected indicator.
		Indicator selectedIndicator = Indicator
				.getIndicatorByValue(getArguments().getString(indicatorField));

		// Getting the values of year, min and max values.
		final int selectedYear = getArguments().getInt(evaluationYear);
		final int minValue = getArguments().getInt(minimumValue);
		final int maxValue = getArguments().getInt(maximumValue);

		// Filling the search with all fields.
		Search search = new Search();
		search.setIndicator(selectedIndicator);
		search.setYear(selectedYear);
		search.setMinValue(minValue);
		search.setMaxValue(maxValue);
		
		// Selecting what kind of search is gonna be made.
		typeOfSearch(search, typeBean);

		beanCallbacks.onSearchBeanSelected(search, typeBean);

		super.onListItemClick(listView, currentView, selectedBeanPosition, id);
	}
	
	/**
	 * Method to show the type of search.
	 * 
	 * @param search
	 * @param typeBean
	 */
	private void typeOfSearch(Search search, Parcelable typeBean ) {
		assert(search != null): "Search can't be null!";
		
		// Constants to verify which type of bean has been selected.
		final boolean beanIsInstitution = (typeBean instanceof Institution);
		final boolean beanIsCourse = (typeBean instanceof Course);
		
		if(beanIsInstitution) {
			// Setting to be a institution search.
			search.setOption(Search.INSTITUTION);
		} 
		else if(beanIsCourse) {
			// Setting to be a course search.
			search.setOption(Search.COURSE);
		}

	}
	
	/**
	 * Retrieve a reference to this activity's ActionBar.
	 * 
	 * @return
	 */
	private ActionBar getActionBar() {
		return ((ActionBarActivity) getActivity()).getSupportActionBar();
	}

}
