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
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class EvaluationDetailFragment extends Fragment{
	
	private static final String ID_COURSE = "idCourse";
	private static final String ID_INSTITUTION = "idInstitution";
	private static final String YEAR = "year";
	BeanListCallbacks beanCallbacks;
	
	public EvaluationDetailFragment() {
		super();
		Bundle args = new Bundle();
		args.putInt(ID_COURSE, 0);
		args.putInt(ID_INSTITUTION, 0);
		args.putInt(YEAR, 0);
		this.setArguments(args);
	}
	
	public static EvaluationDetailFragment newInstance(int id_institution, int id_course,int year){
		EvaluationDetailFragment fragment = new EvaluationDetailFragment();
		Bundle args = new Bundle();
		args.putInt(ID_COURSE, id_course);
		args.putInt(ID_INSTITUTION, id_institution);
		args.putInt(YEAR, year);
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);
		TextView textView1 = (TextView) rootView
				.findViewById(R.id.university_acronym);
		textView1.setText(Institution.getInstitutionByValue(getArguments().getInt(ID_INSTITUTION)).getAcronym());
		Evaluation evaluation = Evaluation.getFromRelation(getArguments().getInt(ID_INSTITUTION), 
				getArguments().getInt(ID_COURSE),
				getArguments().getInt(YEAR));
		
		TextView textView2 = (TextView) rootView
				.findViewById(R.id.general_data);
		textView2.setText(getString(R.string.evaluation_date)+": " + evaluation.getYear() +
				"\n"+getString(R.string.course)+": " + Course.get(getArguments().getInt(ID_COURSE)).getName() +
				"\n"+getString(R.string.modality)+": " + evaluation.getModality());
		
		ListView indicatorList = (ListView) rootView.findViewById(R.id.indicator_list);
		indicatorList.setAdapter(new IndicatorListAdapter(getActivity().getApplicationContext(), R.layout.evaluation_list_item, getListItems(evaluation)));
		
		return rootView;
	}
	
	public ArrayList<HashMap<String, String>> getListItems(Evaluation evaluation){
		ArrayList<HashMap<String, String>> hashList = new ArrayList<HashMap<String,String>>();
		ArrayList<Indicator> indicators = Indicator.getIndicators();
		Book book = Book.get(evaluation.getIdBooks());
		Article article = Article.get(evaluation.getIdArticles());
		Bean bean = null;
		for(Indicator i : indicators){
			HashMap<String, String> hashMap = new HashMap<String, String>();
			if(evaluation.fieldsList().contains(i.getValue())){
				bean = evaluation;
			}else if(book.fieldsList().contains(i.getValue())){
				bean = book;
			}else if(article.fieldsList().contains(i.getValue())) {
				bean = article;
			}
			if(bean!=null){
				hashMap.put(IndicatorListAdapter.INDICATOR_VALUE, i.getValue());
				hashMap.put(IndicatorListAdapter.VALUE, bean.get(i.getValue()));
				hashList.add(hashMap);
			}
		}
		return hashList;
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
