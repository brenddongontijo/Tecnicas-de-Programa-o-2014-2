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

public class CompareShowFragment extends Fragment{
	
	// Use to keep the evaluation of a chosen course.
	private static String ID_EVALUATION_A = "idEvaluationA";
	
	// Use to keep the evaluation of a chosen course.
	private static String ID_EVALUATION_B = "idEvaluationB";
	
	BeanListCallbacks beanCallbacks;

	// Compare the first chosen institution	with others with better results.
	private TextView compareFirstInstitutionBetterResults;
	
	// Compare the second chosen institution with others with better results.
	private TextView compareSecondInstitutionBetterResults;

	// Keep the best value for the first institution.
	private int totalBetterEvaluationInstitutionA;
	
	// Keep the best value for the second institution.
	private int totalBetterEvaluationInstitutionB;
	
	
	public CompareShowFragment() {
		// TODO Auto-generated constructor stub
		super();
		
		Bundle args = new Bundle();
		args.putInt(ID_EVALUATION_A, 0);
		args.putInt(ID_EVALUATION_B, 0);
		
		this.setArguments(args);
	}
	
	public static CompareShowFragment newInstance(final int idEvaluationA, final int idEvaluationB){
		CompareShowFragment compareShowFragment = new CompareShowFragment();
		
		Bundle args = new Bundle();
		args.putInt(ID_EVALUATION_A, idEvaluationA);
		args.putInt(ID_EVALUATION_B, idEvaluationB);
		
		compareShowFragment.setArguments(args);
		
		return compareShowFragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 	Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.compare_show_fragment, container, false);

		TextView courseNameTextView = (TextView) rootView.findViewById(R.id.compare_course_name);
		TextView firstAcronymTextView = (TextView) rootView.
				findViewById(R.id.compare_first_institution_acronym);
		TextView secondAcronymTextView = (TextView) rootView.
				findViewById(R.id.compare_second_institution_acronym);

		Evaluation evaluationA = Evaluation.getEvaluationById(getArguments().getInt(ID_EVALUATION_A));
		Evaluation evaluationB = Evaluation.getEvaluationById(getArguments().getInt(ID_EVALUATION_B));

		Course course = Course.getCourseByValue(evaluationA.getIdCourse());

		Institution institutionA = Institution.getInstitutionByValue(evaluationA.getIdInstitution());
		Institution institutionB = Institution.getInstitutionByValue(evaluationB.getIdInstitution());

		courseNameTextView.setText(course.getName());
		firstAcronymTextView.setText(institutionA.getAcronym());
		secondAcronymTextView.setText(institutionB.getAcronym());

		this.compareFirstInstitutionBetterResults = (TextView) rootView.
				findViewById(R.id.compare_first_institution_better_results);
		this.compareSecondInstitutionBetterResults = (TextView) rootView.
				findViewById(R.id.compare_second_institution_better_results);

		this.totalBetterEvaluationInstitutionA = 0;
		this.totalBetterEvaluationInstitutionB = 0;

		ListView compareIndicatorList = (ListView) rootView.findViewById(R.id.compare_indicator_list);
		
		compareIndicatorList.setAdapter(new CompareListAdapter(getActivity().getApplicationContext()
				,R.layout.compare_show_list_item, getListItems(evaluationA, evaluationB)));

		this.setBetterInstitutionsValues();

		super.onCreateView(inflater, container, savedInstanceState);
		
		return rootView;
	}


	public ArrayList<HashMap<String, String>> getListItems(Evaluation evaluationA, Evaluation evaluationB){
		ArrayList<HashMap<String, String>> hashList = new ArrayList<HashMap<String,String>>();
		
		ArrayList<Indicator> indicators = Indicator.getIndicators();

		Book bookA = Book.getBookByValue(evaluationA.getIdBooks());
		Book bookB = Book.getBookByValue(evaluationB.getIdBooks());

		Article articleA = Article.getArticleByValue(evaluationA.getIdArticles());
		Article articleB = Article.getArticleByValue(evaluationB.getIdArticles());

		Bean beanA = null;
		Bean beanB = null;
		
		for(Indicator indicator : indicators){
			HashMap<String, String> hashMap = new HashMap<String, String>();
			
			if(evaluationA.fieldsList().contains(indicator.getValue())){
				beanA = evaluationA;
				beanB = evaluationB;
			}
			else if(bookA.fieldsList().contains(indicator.getValue())){
				beanA = bookA;
				beanB = bookB;
			}
			else if(articleA.fieldsList().contains(indicator.getValue())) {
				beanA = articleA;
				beanB = articleB;
			}
			
			if(beanA != null){
				hashMap.put(CompareListAdapter.INDICATOR_VALUE, indicator.getValue());
				hashMap.put(CompareListAdapter.FIRST_VALUE, beanA.get(indicator.getValue()));
				hashMap.put(CompareListAdapter.SECOND_VALUE, beanB.get(indicator.getValue()));
				
				boolean currentIndicatorEqualsMasterDegreeStartYear = (indicator.getValue().
						equals(new Evaluation().fieldsList().get(5)));
				
				boolean currentIndicatorEqualsDoctorateStartYear = (indicator.getValue().
						equals(new Evaluation().fieldsList().get(6)));
				
				if(currentIndicatorEqualsMasterDegreeStartYear){
					hashMap.put(CompareListAdapter.IGNORE_INDICATOR, "true");
				}
				else if(currentIndicatorEqualsDoctorateStartYear){
					hashMap.put(CompareListAdapter.IGNORE_INDICATOR, "true");
				}
				else{
					this.incrementBetterValues(beanA.get(indicator.getValue()), beanB.get(indicator.getValue()));
					hashMap.put(CompareListAdapter.IGNORE_INDICATOR, "false");
				}
				
				hashList.add(hashMap);
			}
			else{
				
			}
		}

		return hashList;
	}

	private void incrementBetterValues(String evaluationValueA, String evaluationValueB) {
		int valueA = Integer.parseInt(evaluationValueA);
		int valueB = Integer.parseInt(evaluationValueB);

		if(valueA > valueB) {
			this.totalBetterEvaluationInstitutionA = (this.totalBetterEvaluationInstitutionA + 1);
		}	
		if(valueB > valueA){
			this.totalBetterEvaluationInstitutionB = (this.totalBetterEvaluationInstitutionB + 1);
		}
	}

	private void setBetterInstitutionsValues() {
		this.compareFirstInstitutionBetterResults.
		setText(Integer.toString(this.totalBetterEvaluationInstitutionA));
		
		this.compareSecondInstitutionBetterResults.
		setText(Integer.toString(this.totalBetterEvaluationInstitutionB));
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		try {
            beanCallbacks = (BeanListCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()+" must implement BeanListCallbacks.");
        }
	}
	
	@Override
    public void onDetach() {
        super.onDetach();
        beanCallbacks = null;
    }
}
