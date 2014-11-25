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

/**
 * Class Name: CompareChooseFragment
 * 
 * This class is responsible for create a fragment related with the choose comparison between two institutions.
 */
public class CompareChooseFragment extends Fragment implements
		CheckBoxListCallbacks {
	
	// Used to call methods in Activity.
	BeanListCallbacks beanCallbacks;
	
	// Name a course.
	private static final String COURSE = "course";
	
	// Used when the year of the evaluation has been chosen.
	private int selectedYear = 0;;
	
	// Used when the course has been chosen.
	private Course selectedCourse = null;
	
	// Dropdown menu with all available years. Years: 2007 and 2010.
	private Spinner yearSpinner = null;
	
	// An editable text view that shows completion suggestions automatically 
	// while the user is typing.
	private AutoCompleteTextView autoCompleteField = null;
	
	// View group that displays a list of scrollable items.
	private ListView institutionList = null;
	
	// Adapter for create a list with all comparison parameters.
	private ListCompareAdapter compareAdapterList = null;
	
	// List with current selected institutions. Max of 2 institutions selected.
	private ArrayList<Institution> selectedInstitutions = new ArrayList<Institution>();

	public CompareChooseFragment() {
		super();
	}

	/**
	 * Called when a fragment is first attached to its activity
	 * 
	 * @param activity				   single, focused thing that the user can do.
	 */
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

	/**
	 * Called once the fragment is associated with its activity
	 */
	@Override
	public void onDetach() {
		super.onDetach();
		beanCallbacks = null;
	}

	/**
	 * This method creates the compare choose view associated with the CompareChooseFragment.
	 * 
	 * @param inflater					responsible to inflate a view.
	 * @param container					responsible to generate the LayoutParams of the view.
	 * @param savedInstanceState		responsible for verifying that the fragment will be recreated.
	 * 
	 * @return							current view of CompareChooseFragment associated with parameters chosen.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.compare_choose_fragment,
				container, false);
		
		checkSavedInstances(savedInstanceState);
		
		
		//CompareChooseFragment compareChooseFragment = new CompareChooseFragment();
		
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

	/** 
	 * This method checks if there is already an instance saved.
	 * 
	 * @param savedInstanceState		mapping from String values to Parcelable.
	 * @throws ObjectNullException 
	 */
	private void checkSavedInstances(Bundle savedInstanceState) {
		if((savedInstanceState != null) && (savedInstanceState.getParcelable(COURSE) != null)) {
			setCurrentSelectionCourse((Course) savedInstanceState
					.getParcelable(COURSE));
		}
		else{
			
		}
	}

	/** 
	 * This method is responsible to filter a list of courses based on what was written.
	 * 
	 * @param rootView		view of CompareChooseFragment.
	 * 
	 * @return 				list of institutions that possess the chosen course.			
	 */
	public OnItemClickListener getAutoCompleteListener(final View rootView) {
		
		return new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long rowId) {
				setCurrentSelectionCourse((Course) parent.getItemAtPosition(position));
				
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
				
				if (selectedCourse != null) {
					updateList();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		};
	}

	public void setCurrentSelectionCourse(Course currentSelection) {
		this.selectedCourse = currentSelection;
	}

	public void updateList() {
		selectedInstitutions = new ArrayList<Institution>();
		
		verifySelecterYear(yearSpinner);
		
		verifySelectedCourse(this.selectedCourse);
	}

	/**
	 * This method checks if any year was already selected and select the last
	 * evaluation year by default.
	 * 
	 * @param yearSpinner		year chosen.
	 */
	private void verifySelecterYear(Spinner yearSpinner) {
		final boolean anyYearWasSelected = (yearSpinner.getSelectedItemPosition() != 0);
		
		if (anyYearWasSelected) {
				selectedYear = Integer.parseInt(yearSpinner.getSelectedItem().toString());
		} 
		else {
			final int lastEvaluationYear = yearSpinner.getAdapter().getCount() - 1;
			
			yearSpinner.setSelection(lastEvaluationYear);
			
			selectedYear = Integer.parseInt(yearSpinner.getAdapter()
					.getItem(lastEvaluationYear).toString());
		}
	}
	
	/**
	 * This method checks if any course was selected.
	 * 
	 * @param selectedCourse		current selected course.
	 * @throws ObjectNullException 
	 */
	private void verifySelectedCourse(Course selectedCourse) {
		final boolean courseIsValid = (this.selectedCourse != null);
		
		if (courseIsValid) {
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
	
	/**
	 * This method check the selected checkBox items. 
	 */
	@Override
	public void onCheckedItem(CheckBox checkBox) {
		
		Institution institution = ((Institution) checkBox
				.getTag(ListCompareAdapter.INSTITUTION));
		
		if (!selectedInstitutions.contains(institution)) {
			selectedInstitutions.add(institution);
			
			final int maximumNumberOfInstitutionsToCompare = 2;
			
			/*
			 * Restricted to two selections in checkBox, checks if two are
			 * selected generates a list of results.
			 */
			boolean twoInstitutionsAreSelected = (selectedInstitutions.size() == 
					(maximumNumberOfInstitutionsToCompare));
			
			if (twoInstitutionsAreSelected) {
				// Filling two evaluations with the selected institutions.
				Evaluation evaluationA = Evaluation.getFromRelation(
						selectedInstitutions.get(0).getId(),
						selectedCourse.getId(), selectedYear);
				Evaluation evaluationB = Evaluation.getFromRelation(
						selectedInstitutions.get(1).getId(),
						selectedCourse.getId(), selectedYear);
				
				// Calling the next fragment: CompareShowFragment.
				beanCallbacks.onBeanListItemSelected(CompareShowFragment
						.newInstance(evaluationA.getId(), evaluationB.getId()));
			}
			else{
				
			}
		}
		else{
			// TO-DO: Make not found institution exception.
		}
	}

	@Override
	public void onPause() {
		selectedInstitutions = new ArrayList<Institution>();
		super.onPause();
	}
	
	/**
	 * This method verify all checkboxes and unmark then. 
	 * @throws ObjectNullException 
	 * @throws InstitutionNotFoundException 
	 */
	@Override
	public void onUnchekedItem(CheckBox checkBox) throws InstitutionNotFoundException {
		// Getting all institutions from checkbox.
		Institution institution = ((Institution) checkBox.getTag(ListCompareAdapter.INSTITUTION));
		
		if (selectedInstitutions.contains(institution)) {
			selectedInstitutions.remove(institution);
		}
		else{
			throw new InstitutionNotFoundException("Lista não contém instituições!");
		}
	}

	/**
	 * Method to hide the keyboard after a message has been written.
	 * 
	 * @param view		current view.
	 */
	private void hideKeyboard(View view) {
		InputMethodManager inputMethodManager = (InputMethodManager) getActivity()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),
				InputMethodManager.RESULT_UNCHANGED_SHOWN);
	}

	/**
	 * Method for obtaining a ActionBar.
	 * 
	 * @return		action bar.
	 */
	private ActionBar getActionBar() {
		ActionBar actionBar = ((ActionBarActivity) getActivity()).getSupportActionBar();
		
		return actionBar;
	}
}
