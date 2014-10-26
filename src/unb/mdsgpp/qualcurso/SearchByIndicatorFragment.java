package unb.mdsgpp.qualcurso;

import helpers.Indicator;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

import models.Course;
import models.Institution;
import models.Search;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

// Class "SearchByIndicatorFragment" used in the search for indicator.
public class SearchByIndicatorFragment extends Fragment {

	BeanListCallbacks beanCallbacks;

	public SearchByIndicatorFragment() {
		super();
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

	Spinner listSelectionSpinner = null;
	Spinner filterFieldSpinner = null;
	Spinner yearSpinner = null;

	CheckBox maximum = null;

	EditText firstNumber = null;
	EditText secondNumber = null;

	Button searchButton = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.search_fragment, container,
				false);

		listSelectionSpinner = (Spinner) rootView
				.findViewById(R.id.course_institution);
		filterFieldSpinner = (Spinner) rootView.findViewById(R.id.field);
		filterFieldSpinner.setAdapter(new ArrayAdapter<Indicator>(
				getActionBar().getThemedContext(),
				R.layout.simple_spinner_item, R.id.spinner_item_text, Indicator
						.getIndicators()));
		yearSpinner = (Spinner) rootView.findViewById(R.id.year);
		maximum = (CheckBox) rootView.findViewById(R.id.maximum);
		firstNumber = (EditText) rootView.findViewById(R.id.firstNumber);
		secondNumber = (EditText) rootView.findViewById(R.id.secondNumber);
		searchButton = (Button) rootView.findViewById(R.id.buttonSearch);

		searchButton.setOnClickListener(getClickListener());

		maximum.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			// Event to disable second number when MAX is checked
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {

				if (maximum.isChecked()) {
					secondNumber.setEnabled(false);
				} else {
					secondNumber.setEnabled(true);
				}
			}
		});

		return rootView;
	}

	/**
	 * If nothing was marked in the field of the first number is inserted the
	 * value 0.
	 * 
	 * @param firstNumber
	 */
	public EditText markedFirstNumberInsertZero(EditText firstNumber) {
		if (firstNumber.getText().length() == 0) {
			firstNumber.setText("0");
			return firstNumber;

		} else {
			return firstNumber;
		}
	}

	/**
	 * If nothing was selected in the first field number, the maximum checkbox
	 * is marked.
	 * 
	 * @param secondNumber
	 */
	public EditText markedSecondNumberInsertMaximum(EditText secondNumber) {
		if (secondNumber.getText().length() == 0) {
			maximum.setChecked(true);
			return firstNumber;
		} else {
			return firstNumber;
		}
	}

	// Method to perform the action after the click.
	public OnClickListener getClickListener() {
		return new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				int lowerNumber;
				int higherNumber;
				int yearResearched;
				int max;
				int listSelectionPosition;

				markedFirstNumberInsertZero(firstNumber);

				markedSecondNumberInsertMaximum(secondNumber);

				// Getting values of the numbers, and going to the strings.
				String firstNumberValue = firstNumber.getText().toString();
				String secondNumberValue = secondNumber.getText().toString();

				lowerNumber = Integer.parseInt(firstNumberValue);
				if (maximum.isChecked()) {
					higherNumber = -1;
				} else {
					higherNumber = Integer.parseInt(secondNumberValue);
				}

				listSelectionPosition = listSelectionSpinner
						.getSelectedItemPosition();

				// Gets the value of the year, contained in Spinner.
				if (yearSpinner.getSelectedItemPosition() != 0) {
					yearResearched = Integer.parseInt(yearSpinner
							.getSelectedItem().toString());
				}

				/*
				 * If even a selected year, it sent the last value contained in
				 * the Adapter.
				 */
				else {
					yearResearched = Integer.parseInt(yearSpinner.getAdapter()
							.getItem(yearSpinner.getAdapter().getCount() - 1)
							.toString());
				}

				// Checking if the checkBox is selected.
				if (maximum.isChecked()) {
					max = -1;
				} else {
					max = higherNumber;
				}

				firstNumber.clearFocus();
				secondNumber.clearFocus();

				hideKeyboard(arg0);

				updateSearchList(lowerNumber, max, yearResearched,
						listSelectionPosition,
						((Indicator) filterFieldSpinner
								.getItemAtPosition(filterFieldSpinner
										.getSelectedItemPosition())));
			}
		};
	}

	/*
	 * Request to hide the soft input window from the context of the window that
	 * is currently accepting input.
	 */
	private void hideKeyboard(View view) {
		InputMethodManager inputManager = (InputMethodManager) getActivity()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.hideSoftInputFromWindow(view.getWindowToken(),
				InputMethodManager.RESULT_UNCHANGED_SHOWN);
	}

	/*
	 * Method to define the data in the search fields. Uses the data
	 * NumberPosition of search field .
	 */
	private void SetDataFields(final int min, final int max, final int year,
			Indicator filterField, final int numberPosition) {

		Calendar calendar = Calendar.getInstance();

		Search search = new Search();
		search.setDate(new Date(calendar.getTime().getTime()));
		search.setYear(year);
		search.setOption(0);
		search.setIndicator(filterField);
		search.setMinValue(min);
		search.setMaxValue(max);
		search.saveSearch();

		if ((numberPosition == 2) || (numberPosition == 0)) {
			ArrayList<Institution> institutionList = Institution
					.getInstitutionsByEvaluationFilter(search);
			beanCallbacks.onBeanListItemSelected(
					SearchListFragment.newInstance(institutionList, search),
					R.id.search_list);
		} else {
			ArrayList<Course> courseList = Course
					.getCoursesByEvaluationFilter(search);
			beanCallbacks.onBeanListItemSelected(
					SearchListFragment.newInstance(courseList, search),
					R.id.search_list);
		}

	}

	// Updates the list of survey information.
	private void updateSearchList(final int min, final int max, final int year,
			final int listSelectionPosition, Indicator filterField) {

		if (filterField.getValue() == Indicator.DEFAULT_INDICATOR) {

			Context context = QualCurso.getInstance();
			String emptySearchFilter = getResources().getString(
					R.string.empty_search_filter);

			// Message that should make the choice of the indicator.
			Toast toast = Toast.makeText(context, emptySearchFilter,
					Toast.LENGTH_SHORT);
			toast.show();
		} else {

			// if not selected a field , is inserted a institution default.
			if (listSelectionPosition == 0) {
				listSelectionSpinner.setSelection(listSelectionSpinner
						.getAdapter().getCount() - 1);
				yearSpinner
						.setSelection(yearSpinner.getAdapter().getCount() - 1);
				SetDataFields(min, max, year, filterField,
						listSelectionPosition);
			} else {
				SetDataFields(min, max, year, filterField,
						listSelectionPosition);
			}
		}
	}

	private ActionBar getActionBar() {
		return ((ActionBarActivity) getActivity()).getSupportActionBar();
	}
}
