package unb.mdsgpp.qualcurso;

import helpers.Indicator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import models.Article;
import models.Bean;
import models.Book;
import models.Course;
import models.Evaluation;
import models.Institution;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
/**
 * Class name: EvaluationDetailFragment
 * This class is responsible for create a fragment related with all evaluation information.
 */
public class EvaluationDetailFragment extends Fragment{
	
	// Logging system for EvaluationDetailFragment.
	private final static Logger LOGGER = Logger.getLogger(EvaluationDetailFragment.
			class.getName()); 
	
	// Use to show the details of the evaluation of the course.
	private static final String ID_COURSE = "idCourse";
	
	// Use to show the institution that have a course.
	private static final String ID_INSTITUTION = "idInstitution";
	
	// Year that the evaluation was made.
	private static final String YEAR_OF_EVALUATION = "year";
	
	// Callback to call methods in Activity.
	BeanListCallbacks beanCallbacks;
	
	/**
	 * Empty constructor.
	 */
	public EvaluationDetailFragment() {
		super();
		Bundle args = fillBundleWithEvaluationFields(0, 0, 0);
		this.setArguments(args);
	}
	
	/**
	 * Constructor containing idCourse, idInstitution and evaluationYear.
	 * @param id_course
	 * @param id_institution
	 * @param evaluationYear
	 * @return
	 */
	public static EvaluationDetailFragment newInstance(final int id_course, final int id_institution,
			final int evaluationYear){
		
		// Filling the bundle with idCourse, inInstitution and evaluationYear. 
		Bundle bundle = fillBundleWithEvaluationFields(id_course, id_institution, evaluationYear);
		
		EvaluationDetailFragment fragment = new EvaluationDetailFragment();
		fragment.setArguments(bundle);
		
		return fragment;
	}
	
	/**
	 * This method initiate the Bundle with idCourse, idInstitution and evaluationYear. 
	 * 
	 * @param idInstitution			institution id present on Database.
	 * @param evaluationYear		year of the evaluation
	 * @param coursesArray			array of courses.
	 * @return						a filled Bundle.
	 */
	private static Bundle fillBundleWithEvaluationFields(final int id_course, final int id_institution, 
			final int evaluationYear){
	
		Bundle bundle = new Bundle();
		bundle.putInt(ID_COURSE, id_course);
		bundle.putInt(ID_INSTITUTION, id_institution);
		bundle.putInt(YEAR_OF_EVALUATION, evaluationYear);
		
		LOGGER.info("Bundle sucefully created and filled!");
		
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
            
            LOGGER.info("Callback for EvaluationDetailFragment successfully created!");
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
		
		// Inflating the view.
		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);
		
		// Creating a TextView for university acronym.
		TextView textView1 = (TextView) rootView
				.findViewById(R.id.university_acronym);
		textView1.setText(Institution.getInstitutionByValue(getArguments().
				getInt(ID_INSTITUTION)).getAcronym());
		
		// Getting a evaluation related of course, institution and the year of last evaluation.
		Evaluation evaluation = Evaluation.getFromRelation(getArguments().getInt(ID_INSTITUTION), 
				getArguments().getInt(ID_COURSE),
				getArguments().getInt(YEAR_OF_EVALUATION));
		
		// Creating a TextView with evaluation date, course name and evaluation modality.
		TextView textView2 = (TextView) rootView.findViewById(R.id.general_data);
		textView2.setText(getString(R.string.evaluation_date)+": " + evaluation.getEvaluationYear() +
				"\n"+getString(R.string.course)+": " + Course.getCourseByValue(getArguments().
						getInt(ID_COURSE)).getName() +
				"\n"+getString(R.string.modality)+": " + evaluation.getEvaluationModality());
		
		// Creating the list of all indicators using IndicatorListAdapter.
		ListView indicatorList = (ListView) rootView.findViewById(R.id.indicator_list);
		indicatorList.setAdapter(new IndicatorListAdapter(getActivity().getApplicationContext(),
				R.layout.evaluation_list_item, getListItems(evaluation)));
		
		LOGGER.info("EvaluationDetailFragment View successfully created!");
		
		return rootView;
	}
	
	/**
	 * This method will search if a determined indicator has been found in Evaluation,
	 * Book or Article.
	 * 
	 * @param evaluation
	 * @return
	 */
	public ArrayList<HashMap<String, String>> getListItems(final Evaluation evaluation){
		assert(evaluation != null): "Evaluation null!";
		
		// Array list of HashMaps that will keep all indicators.
		ArrayList<HashMap<String, String>> hashListOfIndicators = new ArrayList<HashMap<String,String>>();
		
		// Creating a book and article based on evaluation id.
		Book book = Book.getBookByValue(evaluation.getIdBooks());
		Article article = Article.getArticleByValue(evaluation.getIdArticles());
		
		// Creating one array with all indicators.
		ArrayList<Indicator> indicators = Indicator.getIndicators();
		
		for(Indicator indicator : indicators){
			Bean bean = null;
			
			// Finding if indicator value is present in evaluation, book or article.
			if(evaluation.fieldsList().contains(indicator.getValue())){
				bean = evaluation;
			}
			else if(book.fieldsList().contains(indicator.getValue())){
				bean = book;
			}
			else if(article.fieldsList().contains(indicator.getValue())) {
				bean = article;
			}
			
			// Creating a HashMap of indicators with indicatorValue and value.
			HashMap < String, String > hashMapWithIndicators = new HashMap < String, String>();
			
			// Constant that tells if a bean has been found.
			final boolean beanFound = (bean != null);
			
			if(beanFound){
				// Filling the HashMap
				hashMapWithIndicators.put(IndicatorListAdapter.INDICATOR_VALUE, indicator.getValue());
				hashMapWithIndicators.put(IndicatorListAdapter.VALUE, bean.get(indicator.getValue()));
				
				// Adding the HassMap to hashListOfIndicators.
				hashListOfIndicators.add(hashMapWithIndicators);
			}
			else{
				LOGGER.info("Indicator " + indicator.getSearchIndicatorName() + " not found!");
			}
		}
		
		return hashListOfIndicators;
	}
}
