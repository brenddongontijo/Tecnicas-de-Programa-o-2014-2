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

	private static final String YEAR = "year";
	private static final String FIELD = "field";
	private static final String RANGE_A = "rangeA";
	private static final String RANGE_B = "rangeB";
	private static final String BEAN_LIST = "beanList";

	BeanListCallbacks beanCallbacks;

	public SearchListFragment() {
	}

	public static SearchListFragment newInstance(
			ArrayList<? extends Parcelable> list, Search search) {
		
		SearchListFragment fragment = new SearchListFragment();
		
		Bundle args = new Bundle();
		args.putInt(YEAR, search.getYear());
		args.putString(FIELD, search.getIndicator().getValue());
		args.putInt(RANGE_A, search.getMinValue());
		args.putInt(RANGE_B, search.getMaxValue());
		args.putParcelableArrayList(BEAN_LIST, list);
		
		fragment.setArguments(args);
		
		return fragment;
	}

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

	@Override
	public void onDetach() {
		super.onDetach();
		beanCallbacks = null;
	}

	
	 // Called to have the fragment instantiate its user interface view.
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		ArrayList<Parcelable> list;
		if(getArguments().getParcelableArrayList(BEAN_LIST) != null) {
			list = getArguments().getParcelableArrayList(BEAN_LIST);
		} 
		else {
			list = savedInstanceState.getParcelableArrayList(BEAN_LIST);
		}

		ListView rootView = (ListView) inflater.inflate(R.layout.fragment_list,
				container, false);
		rootView = (ListView) rootView.findViewById(android.R.id.list);
		
		try {

			rootView.setAdapter(new ArrayAdapter<Parcelable>(getActionBar()
					.getThemedContext(), R.layout.custom_textview, list));
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
		outState.putParcelableArrayList(BEAN_LIST, getArguments()
				.getParcelableArrayList(BEAN_LIST));
		
		super.onSaveInstanceState(outState);
	}

	// This method will be called when an item in the list is selected.
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Parcelable bean = (Parcelable) l.getItemAtPosition(position);
		
		Indicator i = Indicator.getIndicatorByValue(getArguments().getString(
				FIELD));
		
		int year = getArguments().getInt(YEAR);
		int rangeA = getArguments().getInt(RANGE_A);
		int rangeB = getArguments().getInt(RANGE_B);
		
		Search search = new Search();
		search.setIndicator(i);
		search.setYear(year);
		
		if(bean instanceof Institution) {
			search.setOption(Search.INSTITUTION);
		}
		else if (bean instanceof Course) {
			search.setOption(Search.COURSE);
		}
		search.setMinValue(rangeA);
		search.setMaxValue(rangeB);
		
		beanCallbacks.onSearchBeanSelected(search, bean);
		
		super.onListItemClick(l, v, position, id);
	}

	// Retrieve a reference to this activity's ActionBar.
	private ActionBar getActionBar() {
		return ((ActionBarActivity) getActivity()).getSupportActionBar();
	}

}
