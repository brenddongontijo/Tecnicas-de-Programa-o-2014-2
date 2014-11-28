package unb.mdsgpp.qualcurso;

import helpers.Indicator;

import java.util.ArrayList;
import java.util.HashMap;

import models.Article;
import models.Bean;
import models.Book;
import models.Course;
import models.Evaluation;
import models.Institution;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Class Name: CompareShowFragment
 * 
 * This class is responsible for create a fragment related with the show comparison between two institutions.
 */
public class CompareShowFragment extends Fragment{
	
	// Use to keep the evaluation of a chosen course.
	private static String ID_EVALUATION_A = "idEvaluationA";
	
	// Use to keep the evaluation of a chosen course.
	private static String ID_EVALUATION_B = "idEvaluationB";
	
	// Callback to call methods in Activity.
	BeanListCallbacks beanCallbacks;

	// Compare the first chosen institution	with others with better results.
	private TextView compareFirstInstitutionBetterResults;
	
	// Compare the second chosen institution with others with better results.
	private TextView compareSecondInstitutionBetterResults;

	// Keep the best value for the first institution.
	private int totalBetterEvaluationInstitutionA;
	
	// Keep the best value for the second institution.
	private int totalBetterEvaluationInstitutionB;
	
	/**
	 * Empty constructor.
	 */
	public CompareShowFragment() {
		// TODO Auto-generated constructor stub
		super();
		
		Bundle bundle = fillBundleWithEvaluationsIds(0, 0);
		
		this.setArguments(bundle);
	}
	
	/**
	 * This method creates a new instance of CompareShowFragment containing the
	 * id from two evaluations for compare.
	 * 
	 * @param idEvaluationA
	 * @param idEvaluationB
	 * @return
	 */
	public static CompareShowFragment newInstance(final int idEvaluationA, final int idEvaluationB){
		Bundle bundle = fillBundleWithEvaluationsIds(idEvaluationA, idEvaluationB);
		
		CompareShowFragment compareShowFragment = new CompareShowFragment();
		compareShowFragment.setArguments(bundle);
		
		return compareShowFragment;
	}
	
	/**
	 * This method initiate the Bundle with id of two evaluations. 
	 * 
	 * @param idInstitution			institution id present on Database.
	 * @param evaluationYear		year of the evaluation
	 * @param coursesArray			array of courses.
	 * @return						a filled Bundle.
	 */
	private static Bundle fillBundleWithEvaluationsIds(final int idFirstEvaluation, final int idSecondEvaluation){
		Bundle bundle = new Bundle();
		bundle.putInt(ID_EVALUATION_A, idFirstEvaluation);
		bundle.putInt(ID_EVALUATION_B, idSecondEvaluation);
		
		return bundle;
	}
	
	/**
	 * Called when a fragment is first attached to its activity.
	 * 
	 * @param activity				   single, focused thing that the user can do.
	 */
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		try {
			// Creating a callback for activity.
            beanCallbacks = (BeanListCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()+" must implement BeanListCallbacks.");
        }
	}
	
	/**
	 * Called once the fragment is associated with its activity.
	 */
	@Override
    public void onDetach() {
        super.onDetach();
        beanCallbacks = null;
    }
	
	/**
	 * This method creates the compare choose view associated with the CompareShowFragment.
	 * 
	 * @param inflater					responsible to inflate a view.
	 * @param container					responsible to generate the LayoutParams of the view.
	 * @param savedInstanceState		responsible for verifying that the fragment will be recreated.
	 * 
	 * @return							current view of CompareShowFragment associated with parameters chosen.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 	
			Bundle savedInstanceState) {
		
		// Inflating the view for this fragment.
		View rootView = inflater.inflate(R.layout.compare_show_fragment, 
				container, false);
		
		// Getting Database ids for the two evaluations, two institutions and 
		// the course of comparison.
		Evaluation evaluationA = Evaluation.getEvaluationById(getArguments().
				getInt(ID_EVALUATION_A));
		Evaluation evaluationB = Evaluation.getEvaluationById(getArguments().
				getInt(ID_EVALUATION_B));
		
		Institution institutionA = Institution.getInstitutionByValue(evaluationA.
				getIdInstitution());
		Institution institutionB = Institution.getInstitutionByValue(evaluationB.
				getIdInstitution());
		
		Course course = Course.getCourseByValue(evaluationA.getIdCourse());
		
		// Creating a TextView for course name, first and second institutions acronym.
		TextView courseNameTextView = (TextView) rootView.findViewById(R.id.compare_course_name);
		TextView firstAcronymTextView = (TextView) rootView.
				findViewById(R.id.compare_first_institution_acronym);
		TextView secondAcronymTextView = (TextView) rootView.
				findViewById(R.id.compare_second_institution_acronym);

		// Setting the names for the TextViews.
		courseNameTextView.setText(course.getName());
		firstAcronymTextView.setText(institutionA.getAcronym());
		secondAcronymTextView.setText(institutionB.getAcronym());
		
		// Creating a compare show fragment.
		CompareShowFragment compareShowFragment = new CompareShowFragment();
		
		// Creating two text views for compare the results from indicators.
		compareShowFragment.compareFirstInstitutionBetterResults = (TextView) rootView.
				findViewById(R.id.compare_first_institution_better_results);
		compareShowFragment.compareSecondInstitutionBetterResults = (TextView) rootView.
				findViewById(R.id.compare_second_institution_better_results);

		// Initializing the total of better results for both institutions.
		compareShowFragment.totalBetterEvaluationInstitutionA = 0;
		compareShowFragment.totalBetterEvaluationInstitutionB = 0;

		// Creating a list view containing all the comparisons.
		ListView compareIndicatorList = (ListView) rootView.findViewById(R.id.
				compare_indicator_list);
		compareIndicatorList.setAdapter(new CompareListAdapter(getActivity().
				getApplicationContext(), R.layout.compare_show_list_item, 
				getListItems(evaluationA, evaluationB, compareShowFragment)));
		
		// Filling the compare list with results.
		compareShowFragment.setBetterInstitutionsValues(compareShowFragment);

		super.onCreateView(inflater, container, savedInstanceState);
		
		return rootView;
	}

	/**
	 * This method will fill all the fields from the comparison between two
	 * evaluations based on necessary indicators.
	 * @param evaluationA
	 * @param evaluationB
	 * @return
	 */
	public ArrayList<HashMap<String, String>> getListItems(Evaluation firstEvaluation,
			Evaluation secondEvaluation, CompareShowFragment compareShowFragment){
		
		// Asserting that first and second evaluation won't null.
		assert(firstEvaluation != null): "First evaluation can't be null";
		assert(secondEvaluation != null): "Second evaluation can't be null";
		
		ArrayList<HashMap<String, String>> hashList = new ArrayList<HashMap<String,String>>();
		
		// Creating list with all indicators.
		ArrayList<Indicator> indicators = Indicator.getIndicators();

		// Creating two books and two articles from current evaluation.
		Book bookA = Book.getBookByValue(firstEvaluation.getIdBooks());
		Book bookB = Book.getBookByValue(secondEvaluation.getIdBooks());

		Article articleA = Article.getArticleByValue(firstEvaluation.getIdArticles());
		Article articleB = Article.getArticleByValue(secondEvaluation.getIdArticles());

		// Searching where the indicators appears. 
		for(Indicator indicator : indicators){
			// Setting two beans.
			Bean firstBeanToCompare = null;
			Bean secondBeanToCompare = null;
			
			// Finding which bean contain the current indicator.
			if(firstEvaluation.fieldsList().contains(indicator.getValue())){
				firstBeanToCompare = firstEvaluation;
				secondBeanToCompare = secondEvaluation;
			}
			else if(bookA.fieldsList().contains(indicator.getValue())){
				firstBeanToCompare = bookA;
				secondBeanToCompare = bookB;
			}
			else if(articleA.fieldsList().contains(indicator.getValue())) {
				firstBeanToCompare = articleA;
				secondBeanToCompare = articleB;
			}
			
			HashMap<String, String> hashMap = new HashMap<String, String>();
			
			// Constant to verify if firstBeanToCompare is not null.
			final boolean firstBeanNotNull = (firstBeanToCompare != null);
			
			if(firstBeanNotNull){
				// Filling the hashmap with three fields.
				hashMap.put(CompareListAdapter.INDICATOR_VALUE, indicator.getValue());
				hashMap.put(CompareListAdapter.FIRST_VALUE, firstBeanToCompare.
						get(indicator.getValue()));
				hashMap.put(CompareListAdapter.SECOND_VALUE, secondBeanToCompare.
						get(indicator.getValue()));
				
				// Constants to verify what indicators will be ignored.
				boolean currentIndicatorEqualsMasterDegreeStartYear = (indicator.
						getValue().equals(new Evaluation().fieldsList().get(5)));
				boolean currentIndicatorEqualsDoctorateStartYear = (indicator.
						getValue().equals(new Evaluation().fieldsList().get(6)));
				
				if(currentIndicatorEqualsMasterDegreeStartYear){
					// Ignoring master degree start year indicator.
					hashMap.put(CompareListAdapter.IGNORE_INDICATOR, "true");
				}
				else if(currentIndicatorEqualsDoctorateStartYear){
					// Ignoring doctorate start year indicator.
					hashMap.put(CompareListAdapter.IGNORE_INDICATOR, "true");
				}
				else{
					// Comparing which bean is winner in current indicator. 
					compareShowFragment.incrementBetterValues(firstBeanToCompare.
							get(indicator.getValue()), secondBeanToCompare.
							get(indicator.getValue()), compareShowFragment);
					
					// Adding a non-ignored indicator.
					hashMap.put(CompareListAdapter.IGNORE_INDICATOR, "false");
				}
				
				hashList.add(hashMap);
			}
			else{
				
			}
		}

		return hashList;
	}

	/**
	 * This method increments the total of better results of comparison between
	 * two evaluations based on indicator value.
	 * 
	 * @param evaluationValueA
	 * @param evaluationValueB
	 * @param compareShowFragment
	 */
	private void incrementBetterValues(String evaluationValueA, String evaluationValueB,
			CompareShowFragment compareShowFragment) {
		
		// Creating the values to be compared.
		int firstEvaluationValue = Integer.parseInt(evaluationValueA);
		int secondEvaluationValue = Integer.parseInt(evaluationValueB);
		
		// Constants to verify which evaluation value is winner.
		final boolean firstEvaluationValueWinner = 
				(firstEvaluationValue > secondEvaluationValue);
		final boolean secondEvaluationValueWinner = 
				(secondEvaluationValue > firstEvaluationValue);
		
		if(firstEvaluationValueWinner) {
			// Adding +1 better results for firstEvaluation.
			compareShowFragment.totalBetterEvaluationInstitutionA += 1;
		}	
		else if(secondEvaluationValueWinner){
			// Adding +1 better results for firstEvaluation.
			compareShowFragment.totalBetterEvaluationInstitutionB += 1;
		}
		else{
			// In this case occurred a draw.
		}
	}

	/**
	 * This method fill the two texts views with the total of values of better
	 * results from both institutions.
	 * @param compareShowFragment
	 */
	private void setBetterInstitutionsValues(CompareShowFragment compareShowFragment) {
		// Filling the first total better results TextView.
		compareShowFragment.compareFirstInstitutionBetterResults.
		setText(Integer.toString(this.totalBetterEvaluationInstitutionA));
		
		// Filling the second total better results TextView.
		compareShowFragment.compareSecondInstitutionBetterResults.
		setText(Integer.toString(this.totalBetterEvaluationInstitutionB));
	}

	
}
