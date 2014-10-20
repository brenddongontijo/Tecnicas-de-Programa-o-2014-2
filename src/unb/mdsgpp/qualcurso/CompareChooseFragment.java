package unb.mdsgpp.qualcurso;

import java.util.ArrayList;

import models.Course;
import models.Evaluation;
import models.Institution;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;

import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public class CompareChooseFragment extends Fragment implements
		CheckBoxListCallbacks {
	BeanListCallbacks beanCallbacks;
	
	// Name a course.
	private static final String COURSE = "course";
	
	// Used when the year of the evaluation has been chosen.
	private int selectedYear;
	
	// Used when the course has been chosen.
	private Course selectedCourse;
	
	private Spinner yearSpinner = null;
	private AutoCompleteTextView autoCompleteField = null;
	private ListView institutionList = null;
	private ListCompareAdapter compareAdapterList = null;
	
	private ArrayList<Institution> selectedInstitutions = new ArrayList<Institution>();

	public CompareChooseFragment() {
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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.compare_choose_fragment,
				container, false);
		
		checkSavedInstances(savedInstanceState);
		
		// Bound variables with layout objects.
		this.yearSpinner = (Spinner) rootView.findViewById(R.id.compare_year);
		this.autoCompleteField = (AutoCompleteTextView) rootView
				.findViewById(R.id.autoCompleteTextView);
		this.institutionList = (ListView) rootView
				.findViewById(R.id.institutionList);

		this.autoCompleteField.setAdapter(new ArrayAdapter<Course>(
				getActionBar().getThemedContext(), R.layout.custom_textview,
				Course.getAllCourses()));
		
		// Set objects events.
		this.yearSpinner.setOnItemSelectedListener(getYearSpinnerListener());
		this.autoCompleteField
				.setOnItemClickListener(getAutoCompleteListener(rootView));
		return rootView;
	}

	/*
	 * This method checks if there is already an instance saved.
	 */
	private void checkSavedInstances(Bundle savedInstanceState) {
		if(savedInstanceState != null && savedInstanceState.getParcelable(COURSE) != null) {
			setCurrentSelection((Course) savedInstanceState
					.getParcelable(COURSE));
		}
		else{
			
		}
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

	public OnItemSelectedListener getYearSpinnerListener() {
		return new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				
				if(selectedCourse != null) {
					updateList();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		};
	}

	public void setCurrentSelection(Course currentSelection) {
		this.selectedCourse = currentSelection;
	}

	public void updateList() {
		selectedInstitutions = new ArrayList<Institution>();
		
		verifySelecterYear(yearSpinner);
		
		verifySelectedCourse(this.selectedCourse);
		
	}

	/*
	 * This method checks if any year was already selected and select the last
	 * evaluation year by default.
	 */
	private void verifySelecterYear(Spinner yearSpinner) {
		boolean anyYearWasSelected = (yearSpinner.getSelectedItemPosition() != 0);
		
		if(anyYearWasSelected) {
				selectedYear = Integer.parseInt(yearSpinner.getSelectedItem()
						.toString());
		} 
		else {
			final int lastEvaluationYear = yearSpinner.getAdapter().getCount() - 1;
			
			yearSpinner.setSelection(lastEvaluationYear);
			
			selectedYear = Integer.parseInt(yearSpinner.getAdapter()
					.getItem(lastEvaluationYear)
					.toString());
		}
	}
	
	/*
	 * This method checks if any course was selected.
	 */
	private void verifySelectedCourse(Course selectedCourse) {
		boolean courseIsValid = (this.selectedCourse != null);
		if(courseIsValid) {
			
			ArrayList<Institution> courseInstitutions = this.selectedCourse
					.getInstitutionsByYear(selectedYear);
			
			compareAdapterList = new ListCompareAdapter(getActionBar()
					.getThemedContext(), R.layout.compare_choose_list_item,
					courseInstitutions, this);

			this.institutionList.setAdapter(compareAdapterList);
		}
		else{
			
		}
		
	}

	// Check the checkBox items. 
	@Override
	public void onCheckedItem(CheckBox checkBox) {
		
		// TODO Auto-generated method stub
		Institution institution = ((Institution) checkBox
				.getTag(ListCompareAdapter.INSTITUTION));
		
		if(!selectedInstitutions.contains(institution)) {
			selectedInstitutions.add(institution);
			
			
			final int maximumNumberOfInstitutionsToCompare = 2;
			
			/*
			 * Restricted to two selections in checkBox, checkBox if two are
			 * selected generates a list of results.
			 */
			boolean allInstitutionsIsSelected = (selectedInstitutions.size() == 
					(maximumNumberOfInstitutionsToCompare));
			
			if(allInstitutionsIsSelected) {
				Evaluation evaluationA = Evaluation.getFromRelation(
						selectedInstitutions.get(0).getId(),
						selectedCourse.getId(), selectedYear);
				Evaluation evaluationB = Evaluation.getFromRelation(
						selectedInstitutions.get(1).getId(),
						selectedCourse.getId(), selectedYear);
				
				beanCallbacks.onBeanListItemSelected(CompareShowFragment
						.newInstance(evaluationA.getId(), evaluationB.getId()));
			}
			else{
				
			}
		}
	}

	@Override
	public void onPause() {
		selectedInstitutions = new ArrayList<Institution>();
		super.onPause();
	}
	
	// Remove the checkbox selection. 
	@Override
	public void onUnchekedItem(CheckBox checkBox) {
		
		// TODO Auto-generated method stub
		Institution institution = ((Institution) checkBox
				.getTag(ListCompareAdapter.INSTITUTION));
		
		if(selectedInstitutions.contains(institution)) {
			selectedInstitutions.remove(institution);
		}
	}

	// Method to hide the keyboard.
	private void hideKeyboard(View view) {
		InputMethodManager inputMethodManager = (InputMethodManager) getActivity()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),
				InputMethodManager.RESULT_UNCHANGED_SHOWN);
	}

	// Method for obtaining a ActionBar.
	private ActionBar getActionBar() {
		return ((ActionBarActivity) getActivity()).getSupportActionBar();
	}
}
