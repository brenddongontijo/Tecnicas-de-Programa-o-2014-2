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

		// Event to disable second number when MAX is checked
		maximum.setOnCheckedChangeListener(new OnCheckedChangeListener() {
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

	// Method to perform the action after the click.
	public OnClickListener getClickListener() {
		return new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				int number1, number2, year, max, listSelectionPosition;

				/*
				 * If nothing was marked in the field of the first number is
				 * inserted the value 0.
				 */
				if (firstNumber.getText().length() == 0) {
					firstNumber.setText("0");
				}
				/*
				 * If nothing was selected in the first field number, the
				 * maximum checkbox is marked.
				 */
				if (secondNumber.getText().length() == 0) {
					maximum.setChecked(true);
				}

				// Getting values ​​of the numbers, and going to the strings.
				String firstNumberValue = firstNumber.getText().toString();
				String secondNumberValue = secondNumber.getText().toString();

				number1 = Integer.parseInt(firstNumberValue);
				number2 = maximum.isChecked() ? -1 : Integer
						.parseInt(secondNumberValue);
				listSelectionPosition = listSelectionSpinner
						.getSelectedItemPosition();

				// Gets the value of the year, contained in Spinner.
				if (yearSpinner.getSelectedItemPosition() != 0) {
					year = Integer.parseInt(yearSpinner.getSelectedItem()
							.toString());
				}
				/*
				 * If even a selected year, it sent the last value contained in
				 * the Adapter.
				 */
				else {
					year = Integer.parseInt(yearSpinner.getAdapter()
							.getItem(yearSpinner.getAdapter().getCount() - 1)
							.toString());
				}

				// Checking if the checkBox is selected.
				if (maximum.isChecked()) {
					max = -1;
				} else {
					max = number2;
				}

				firstNumber.clearFocus();
				secondNumber.clearFocus();
				hideKeyboard(arg0);

				updateSearchList(number1, max, year, listSelectionPosition,
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
		InputMethodManager imm = (InputMethodManager) getActivity()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(),
				InputMethodManager.RESULT_UNCHANGED_SHOWN);
	}

	/*
	 * Method created to call a list of institutions according to the parameters
	 * entered.
	 */
	private void callInstitutionList(int min, int max, int year,
			Indicator filterField) {
		Calendar c = Calendar.getInstance();
		Search search = new Search();
		search.setDate(new Date(c.getTime().getTime()));
		search.setYear(year);
		search.setOption(1);
		search.setIndicator(filterField);
		search.setMinValue(min);
		search.setMaxValue(max);
		search.save();
		ArrayList<Institution> beanList = Institution
				.getInstitutionsByEvaluationFilter(search);
		beanCallbacks.onBeanListItemSelected(
				SearchListFragment.newInstance(beanList, search),
				R.id.search_list);
	}

	/*
	 * Method created to call a list of courses according to the parameters
	 * entered.
	 */
	private void callCourseList(int min, int max, int year,
			Indicator filterField) {
		Calendar c = Calendar.getInstance();
		Search search = new Search();
		search.setDate(new Date(c.getTime().getTime()));
		search.setYear(year);
		search.setOption(0);
		search.setIndicator(filterField);
		search.setMinValue(min);
		search.setMaxValue(max);
		search.save();
		ArrayList<Course> beanList = Course
				.getCoursesByEvaluationFilter(search);
		beanCallbacks.onBeanListItemSelected(
				SearchListFragment.newInstance(beanList, search),
				R.id.search_list);
	}

	
	// Updates the list of survey information.
	private void updateSearchList(int min, int max, int year,
			int listSelectionPosition, Indicator filterField) {
		if (filterField.getValue() == Indicator.DEFAULT_INDICATOR) {
			Context c = QualCurso.getInstance();
			String emptySearchFilter = getResources().getString(
					R.string.empty_search_filter);

			/*
			 * Send a message informing you that should make the choice of
			 * indicator.
			 */
			Toast toast = Toast.makeText(c, emptySearchFilter,
					Toast.LENGTH_SHORT);
			toast.show();
		} else {
			switch (listSelectionPosition) {
			/*
			 * In the case where neither course nor institution are selected,
			 * returns a list of institution.
			 */
			case 0:
				listSelectionSpinner.setSelection(listSelectionSpinner
						.getAdapter().getCount() - 1);
				yearSpinner
						.setSelection(yearSpinner.getAdapter().getCount() - 1);

				callInstitutionList(min, max, year, filterField);
				break;

			//Returns a list of course.

			case 1:
				callCourseList(min, max, year, filterField);
				break;

			// Returns a list of institution.
			case 2:
				callInstitutionList(min, max, year, filterField);
				break;

			default:
				break;
			}
		}

	}

	private ActionBar getActionBar() {
		return ((ActionBarActivity) getActivity()).getSupportActionBar();
	}
}