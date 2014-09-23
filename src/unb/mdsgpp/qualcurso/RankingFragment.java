package unb.mdsgpp.qualcurso;

import helpers.Indicator;

import java.util.ArrayList;
import java.util.HashMap;

import models.Course;
import models.GenericBeanDAO;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class RankingFragment extends Fragment {
	BeanListCallbacks beanCallbacks;
	private static final String COURSE = "course";
	private static final String FILTER_FIELD = "filterField";

	public RankingFragment() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			beanCallbacks = (BeanListCallbacks) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement BeanListCallbacks.");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		beanCallbacks = null;
	}

	Spinner filterFieldSpinner = null;
	Spinner yearSpinner = null;
	ListView evaluationList = null;
	AutoCompleteTextView autoCompleteField = null;
	Course currentSelection = null;
	String filterField = Indicator.DEFAULT_INDICATOR;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.ranking_fragment, container,
				false);
		
		if (savedInstanceState != null) {
			if (savedInstanceState.getParcelable(COURSE) != null) {
				setCurrentSelection((Course) savedInstanceState
						.getParcelable(COURSE));
			}
			if (savedInstanceState.getString(FILTER_FIELD) != null) {
				setFilterField(savedInstanceState.getString(FILTER_FIELD));
			}
		}
		
		this.filterFieldSpinner = (Spinner) rootView.findViewById(R.id.field);
		this.filterFieldSpinner.setAdapter(new ArrayAdapter<Indicator>(
				getActivity().getApplicationContext(),
				R.layout.simple_spinner_item, R.id.spinner_item_text, Indicator
						.getIndicators()));
		this.yearSpinner = (Spinner) rootView.findViewById(R.id.year);
		this.filterFieldSpinner
				.setOnItemSelectedListener(getFilterFieldSpinnerListener());
		this.yearSpinner.setOnItemSelectedListener(getYearSpinnerListener());
		this.evaluationList = (ListView) rootView
				.findViewById(R.id.evaluationList);
		
		ArrayList<Course> courses = Course.getAllCourses();
		autoCompleteField = (AutoCompleteTextView) rootView
				.findViewById(R.id.autoCompleteTextView);
		autoCompleteField.setAdapter(new ArrayAdapter<Course>(getActivity()
				.getApplicationContext(), R.layout.custom_textview, courses));
		autoCompleteField
				.setOnItemClickListener(getAutoCompleteListener(rootView));
		evaluationList.setOnItemClickListener(getEvaluationListListener());
		
		if (currentSelection != null
				&& filterField != Indicator.DEFAULT_INDICATOR) {
			updateList();
		}
		return rootView;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putParcelable(COURSE, this.currentSelection);
		outState.putString(FILTER_FIELD, this.filterField);
		super.onSaveInstanceState(outState);
	}

	public OnItemClickListener getAutoCompleteListener(final View rootView) {
		return new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long rowId) {
				setCurrentSelection((Course) parent.getItemAtPosition(position));
				updateList();

				hideKeyboard(rootView);
			}
		};
	}

	public OnItemClickListener getEvaluationListListener() {
		return new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				beanCallbacks.onBeanListItemSelected(EvaluationDetailFragment
						.newInstance(Integer
								.parseInt(((HashMap<String, String>) parent
										.getItemAtPosition(position))
										.get("id_institution")), Integer
								.parseInt(((HashMap<String, String>) parent
										.getItemAtPosition(position))
										.get("id_course")), Integer
								.parseInt(((HashMap<String, String>) parent
										.getItemAtPosition(position))
										.get("year"))));
			}
		};
	}

	/*
	 * Always use a new field of filters is selected, calls the updateList ()
	 * method to update the result list.
	 */
	public OnItemSelectedListener getFilterFieldSpinnerListener() {
		return new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				setFilterField(((Indicator) arg0.getItemAtPosition(arg2))
						.getValue());
				if (currentSelection != null
						&& filterField != Indicator.DEFAULT_INDICATOR) {
					updateList();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		};
	}

	/*
	 * Used whenever a new year is selected, calls the updateList () method to
	 * update the result list.
	 */
	public OnItemSelectedListener getYearSpinnerListener() {
		return new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if (currentSelection != null
						&& filterField != Indicator.DEFAULT_INDICATOR) {
					updateList();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		};
	}
	
	// ArrayList created to store all the fields present in the rankings.
	public ArrayList<String> getListFields() {
		ArrayList<String> fields = new ArrayList<String>();
		fields.add(this.filterField);
		fields.add("id_institution");
		fields.add("id_course");
		fields.add("acronym");
		fields.add("year");
		return fields;
	}

	// Get this year by spinner.
	public int getYear() {
		int year = 0;

		/*
		 * If the "0" position is marked ( == 0), is selected last year in the
		 * Adapter, otherwise takes the selected position.
		 */
		if (yearSpinner.getSelectedItemPosition() != 0) {
			year = Integer.parseInt(yearSpinner.getSelectedItem().toString());
		}
		else {
			yearSpinner.setSelection(yearSpinner.getAdapter().getCount() - 1);
			year = Integer.parseInt(yearSpinner.getAdapter()
					.getItem(yearSpinner.getAdapter().getCount() - 1)
					.toString());
		}
		return year;
	}

	public void setFilterField(String filterField) {
		this.filterField = filterField;
	}

	public void setCurrentSelection(Course currentSelection) {
		this.currentSelection = currentSelection;
	}

	// Sends a warning on the screen.
	private void displayToastMessage(String textMenssage) {
		Toast toast = Toast.makeText(
				this.getActivity().getApplicationContext(), textMenssage,
				Toast.LENGTH_SHORT);
		toast.show();
	}
	
	// Used to update the list, seeking the actual value.
	public void updateList() {
		if (this.filterField != Indicator.DEFAULT_INDICATOR) {
			final ArrayList<String> fields = getListFields();
			int year = getYear();
			GenericBeanDAO gDB = new GenericBeanDAO();
			ListAdapter adapter = new ListAdapter(getActivity()
					.getApplicationContext(), R.layout.list_item,
					gDB.selectOrdered(fields, fields.get(0), "id_course ="
							+ this.currentSelection.getId() + " AND year ="
							+ year, "id_institution", true));
			evaluationList.setAdapter(adapter);
		}
		else {
			String emptySearchFilter = getResources().getString(
					R.string.empty_search_filter);
			displayToastMessage(emptySearchFilter);
		}
	}

	// Used to hide the keyboard smoothly when required.
	private void hideKeyboard(View view) {
		InputMethodManager imm = (InputMethodManager) getActivity()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(),
				InputMethodManager.RESULT_UNCHANGED_SHOWN);
	}
}
