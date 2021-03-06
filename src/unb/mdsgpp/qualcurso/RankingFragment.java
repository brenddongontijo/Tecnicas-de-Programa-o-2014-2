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
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Class name: RankingFragment
 */
public class RankingFragment extends Fragment {

	BeanListCallbacks beanCallbacks;
	
	// Variable used to show a course in the ranking.
	private static final String COURSE = "course";
	
	private static final String INDICATOR_FILTER_FIELD = "filterField";

	public RankingFragment() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			beanCallbacks = (BeanListCallbacks) activity;
		} catch(ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement BeanListCallbacks.");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		beanCallbacks = null;
	}

	Spinner indicatorFieldSpinner = null;
	Spinner yearSpinner = null;
	ListView evaluationList = null;
	AutoCompleteTextView autoCompleteField = null;
	Course currentSelectionCourse = null;
	String indicatorField = Indicator.DEFAULT_INDICATOR;

	/**
	 * Method that calls the updateList method according to the conditions bellow.
	 */
	public void toCallUpdateList(){
		
		if (currentSelectionCourse != null
				&& indicatorField != Indicator.DEFAULT_INDICATOR) {
			updateList();
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.ranking_fragment, container,
				false);

		rootView = reloadFields(rootView, savedInstanceState);
		
		this.indicatorFieldSpinner = (Spinner) rootView.findViewById(R.id.field);
		this.indicatorFieldSpinner.setAdapter(new ArrayAdapter<Indicator>(
				getActivity().getApplicationContext(),
				R.layout.simple_spinner_item, R.id.spinner_item_text, Indicator
						.getIndicators()));
		this.indicatorFieldSpinner
		.setOnItemSelectedListener(getFilterFieldSpinnerListener());
		
		this.yearSpinner = (Spinner) rootView.findViewById(R.id.year);
		this.yearSpinner.setOnItemSelectedListener(getYearSpinnerListener());
		
		this.evaluationList = (ListView) rootView.findViewById(R.id.evaluationList);
		evaluationList.setOnItemClickListener(getEvaluationListListener());

		autoCompleteField = (AutoCompleteTextView) rootView
				.findViewById(R.id.autoCompleteTextView);
		
		ArrayList<Course> coursesFound = Course.getAllCourses();
		autoCompleteField.setAdapter(new ArrayAdapter<Course>(getActivity()
				.getApplicationContext(), R.layout.custom_textview, coursesFound));
		autoCompleteField.setOnItemClickListener(getAutoCompleteListener(rootView));
		
		toCallUpdateList();
		
		return rootView;
	}
	
	/**
	 * Method that reload course and indicator fields. 
	 * 
	 * @param rootView
	 * @param savedInstanceState
	 * 
	 * @return
	 */
	private View reloadFields(View rootView, Bundle savedInstanceState) {
		
		if (savedInstanceState != null) {
			if (savedInstanceState.getParcelable(COURSE) != null) {
				setCurrentSelectionCourse((Course) savedInstanceState
						.getParcelable(COURSE));
			}
			if (savedInstanceState.getString(INDICATOR_FILTER_FIELD) != null) {
				setFilterField(savedInstanceState.getString(INDICATOR_FILTER_FIELD));
			}
		}
		
		return rootView;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putParcelable(COURSE, this.currentSelectionCourse);
		outState.putString(INDICATOR_FILTER_FIELD, this.indicatorField);
		super.onSaveInstanceState(outState);
	}

	public OnItemClickListener getAutoCompleteListener(final View rootView) {
		return new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long rowId) {
				setCurrentSelectionCourse((Course) parent
						.getItemAtPosition(position));
				updateList();

				hideKeyboard(rootView);
			}
		};
	}

	public OnItemClickListener getEvaluationListListener() {
		return new OnItemClickListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, final long id) {

				beanCallbacks.onBeanListItemSelected(EvaluationDetailFragment
						.newInstance(
								Integer.parseInt(((HashMap<String, String>) parent
										.getItemAtPosition(position))
										.get("id_institution")), 
								Integer.parseInt(((HashMap<String, String>) parent
										.getItemAtPosition(position))
										.get("id_course")), 
								Integer.parseInt(((HashMap<String, String>) parent
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
				
				toCallUpdateList();
				
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
				
				toCallUpdateList();

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		};
	}

	// ArrayList created to store all the fields present in the rankings.
	public ArrayList<String> getRankingFields() {
		ArrayList<String> rankingFields = new ArrayList<String>();
		rankingFields.add(this.indicatorField);
		rankingFields.add("id_institution");
		rankingFields.add("id_course");
		rankingFields.add("acronym");
		rankingFields.add("year");
		
		return rankingFields;
	}

	// Get this year by spinner.
	public int getSelectedEvaluationYear() {
		int year = 0;

		/*
		 * If the "0" position is marked ( == 0), is selected last year in the
		 * Adapter, otherwise takes the selected position.
		 */
		if(yearSpinner.getSelectedItemPosition() == 0) {
			yearSpinner.setSelection(yearSpinner.getAdapter().getCount() - 1);
			
			year = Integer.parseInt(yearSpinner.getAdapter()
					.getItem(yearSpinner.getAdapter().getCount() - 1)
					.toString());
		} 
		else {
			year = Integer.parseInt(yearSpinner.getSelectedItem().toString());
		}
		
		return year;
	}

	public void setFilterField(String indicatorField) {
		this.indicatorField = indicatorField;
	}

	public void setCurrentSelectionCourse(Course currentSelectionCourse) {
		this.currentSelectionCourse = currentSelectionCourse;
	}

	// Sends a warning on the screen.
	private void displayToastMessage(final String textMenssage) {
		Toast toastMessage = Toast.makeText(
				this.getActivity().getApplicationContext(), textMenssage,
				Toast.LENGTH_SHORT);
		toastMessage.show();
	}

	// Used to update the list, seeking the actual value.
	public void updateList() {
		if(this.indicatorField != Indicator.DEFAULT_INDICATOR) {

			final ArrayList<String> rankingFields = getRankingFields();
			int evaluationYear = getSelectedEvaluationYear();

			GenericBeanDAO genericBeanDao = new GenericBeanDAO();
			
			RankingListAdapter orderedCourseList = new RankingListAdapter(getActivity()
					.getApplicationContext(), R.layout.list_item,
					genericBeanDao.selectOrdered(rankingFields, rankingFields.get(0),
							"id_course =" + this.currentSelectionCourse.getId()
									+ " AND year =" + evaluationYear, "id_institution",
							true));
			
			evaluationList.setAdapter(orderedCourseList);
		} 
		else {
			String emptySearchFilter = getResources().getString(
					R.string.empty_search_filter);
			
			displayToastMessage(emptySearchFilter);
		}
	}

	// Used to hide the keyboard smoothly when required.
	private void hideKeyboard(View view) {
		InputMethodManager inputMethodManager = (InputMethodManager) getActivity()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),
				InputMethodManager.RESULT_UNCHANGED_SHOWN);
	}
}
