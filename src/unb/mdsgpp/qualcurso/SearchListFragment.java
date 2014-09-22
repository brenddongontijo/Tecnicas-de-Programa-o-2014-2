package unb.mdsgpp.qualcurso;

import helpers.Indicator;

import java.util.ArrayList;

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

public class SearchListFragment extends ListFragment {

	private static final String evaluationYear = "year";
	private static final String indicatorField = "field";
	private static final String minimumValue = "rangeA";
	private static final String maximumValue = "rangeB";
	private static final String listOfBeans = "beanList";

	BeanListCallbacks beanCallbacks;

	// Empty constructor.
	public SearchListFragment() {
		
	}

	public static SearchListFragment newInstance(
			ArrayList<? extends Parcelable> list, Search search) {
		
		SearchListFragment searchListFragment = new SearchListFragment();
		
		Bundle fieldsToBeFilled = new Bundle();
		fieldsToBeFilled.putInt(evaluationYear, search.getYear());
		fieldsToBeFilled.putString(indicatorField, search.getIndicator().getValue());
		fieldsToBeFilled.putInt(minimumValue, search.getMinValue());
		fieldsToBeFilled.putInt(maximumValue, search.getMaxValue());
		fieldsToBeFilled.putParcelableArrayList(listOfBeans, list);
		
		searchListFragment.setArguments(fieldsToBeFilled);
		
		return searchListFragment;
	}

	// Called once the fragment is associated with its activity.
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			beanCallbacks = (BeanListCallbacks) activity;
		} 
		catch(ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement BeanListCallbacks.");
		}
	}

	// Called immediately prior to the fragment no longer being associated with its activity.
	@Override
	public void onDetach() {
		super.onDetach();
		beanCallbacks = null;
	}

	
	 // Called to have the fragment instantiate its user interface view.
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		ArrayList<Parcelable> beanList;
		
		if(getArguments().getParcelableArrayList(listOfBeans) != null) {
			beanList = getArguments().getParcelableArrayList(listOfBeans);
		} 
		else {
			beanList = savedInstanceState.getParcelableArrayList(listOfBeans);
		}

		ListView rootView = (ListView) inflater.inflate(R.layout.fragment_list,
				container, false);
		rootView = (ListView) rootView.findViewById(android.R.id.list);
		
		try {
			rootView.setAdapter(new ArrayAdapter<Parcelable>(getActionBar()
					.getThemedContext(), R.layout.custom_textview, beanList));
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rootView;
	}

	/*
	 * Called to ask the fragment to save its current dynamic state, so it can
	 * later be reconstructed in a new instance of its process is restarted.
	 */
	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putParcelableArrayList(listOfBeans, getArguments()
				.getParcelableArrayList(listOfBeans));
		
		super.onSaveInstanceState(outState);
	}

	// This method will be called when an item in the list is selected.
	@Override
	public void onListItemClick(ListView listView, View currentView, int selectedBeanPosition, long id) {
		Parcelable bean = (Parcelable) listView.getItemAtPosition(selectedBeanPosition);
		
		Indicator selectedIndicator = Indicator.getIndicatorByValue(getArguments().getString(
				indicatorField));
		
		int selectedYear = getArguments().getInt(evaluationYear);
		
		int minValue = getArguments().getInt(minimumValue);
		int maxValue = getArguments().getInt(maximumValue);
		
		Search search = new Search();
		search.setIndicator(selectedIndicator);
		search.setYear(selectedYear);
		
		if(bean instanceof Institution) {
			search.setOption(Search.INSTITUTION);
		}
		else if(bean instanceof Course) {
			search.setOption(Search.COURSE);
		}
		
		search.setMinValue(minValue);
		search.setMaxValue(maxValue);
		
		beanCallbacks.onSearchBeanSelected(search, bean);
		
		super.onListItemClick(listView, currentView, selectedBeanPosition, id);
	}

	// Retrieve a reference to this activity's ActionBar.
	private ActionBar getActionBar() {
		return ((ActionBarActivity) getActivity()).getSupportActionBar();
	}

}
