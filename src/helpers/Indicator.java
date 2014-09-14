package helpers;

import java.util.ArrayList;

import models.Article;
import models.Book;
import models.Evaluation;
import unb.mdsgpp.qualcurso.QualCurso;
import unb.mdsgpp.qualcurso.R;

/*
 * Class Name: Indicator.
 * The class Indicator helps to make one search based on the evaluation indicators.
 */
public class Indicator {
	private String name;
	private String value;

	public static final String DEFAULT_INDICATOR = "defaultIndicator";

	// Declaration of a non-default constructor.
	public Indicator(String name, String value) {
		this.name = name;
		this.value = value;
	}
	
	// Access variable name. 
	public String getName() {
		return name;
	}

	// Modify variable name.
	public void setName(String name) {
		this.name = name;
	}

	// Access variable value.
	public String getValue() {
		return value;
	}

	// Modify variable value.
	public void setValue(String value) {
		this.value = value;
	}

	// Rewriting variable name.
	@Override
	public String toString() {
		return this.getName();
	}

	// This method keep all indicators in one ArrayList.
	public static ArrayList<Indicator> getIndicators(){
		String [] indicatorList = QualCurso.getInstance().getResources().getStringArray(R.array.indicator);
		ArrayList<Indicator> result = new ArrayList<Indicator>();

		result.add(new Indicator(indicatorList[0], DEFAULT_INDICATOR));
		
		// Adding triennial_evaluation.
		result.add(new Indicator(indicatorList[1], new Evaluation().
				fieldsList().get(7)));
		// Adding master_degree_start_year.
		result.add(new Indicator(indicatorList[2], new Evaluation().
				fieldsList().get(5)));
		// Adding doctorate_start_year.
		result.add(new Indicator(indicatorList[3], new Evaluation().
				fieldsList().get(6)));
		// Adding permanent_teachers.
		result.add(new Indicator(indicatorList[4], new Evaluation().
				fieldsList().get(8)));
		// Adding theses. 
		result.add(new Indicator(indicatorList[5], new Evaluation().
				fieldsList().get(9)));
		// Adding dissertations.
		result.add(new Indicator(indicatorList[6], new Evaluation().
				fieldsList().get(10)));
		// Adding artistic_production.
		result.add(new Indicator(indicatorList[7], new Evaluation().
				fieldsList().get(13)));
		
		// Adding chapters.
		result.add(new Indicator(indicatorList[8], new Book().
				fieldsList().get(2)));
		// Adding integral_text.
		result.add(new Indicator(indicatorList[9], new Book().
				fieldsList().get(1)));
		// Adding collections.
		result.add(new Indicator(indicatorList[10], new Book().
				fieldsList().get(3)));
		// Adding entries.
		result.add(new Indicator(indicatorList[11], new Book().
				fieldsList().get(4)));
		
		// Adding published_journals.
		result.add(new Indicator(indicatorList[12], new Article().
				fieldsList().get(1)));
		// Adding published_conference_proceedings.
		result.add(new Indicator(indicatorList[13], new Article().
				fieldsList().get(2)));

		return result;
	}

	// This method find a specific indicator in all indicators.	 
	public static Indicator getIndicatorByValue(String value) {
		Indicator indicator = null;
		
		for(Indicator i : getIndicators()) {
			// Trying to find the indicator.
			if(i.getValue().equals(value)) {
				indicator = i;
				break;
			}
		}

		return indicator;
	}
	
}
