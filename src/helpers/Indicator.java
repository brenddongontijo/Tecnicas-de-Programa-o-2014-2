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
	
	// Search indicator name.
	private String searchIndicatorName;
	
	// Search indicator value.
	private String searchIndicatorValue;

	public static final String DEFAULT_INDICATOR = "defaultIndicator";

	// Declaration of a non-default constructor.
	public Indicator(String indicatorName, String indicatorValue) {
		this.searchIndicatorName = indicatorName;
		this.searchIndicatorValue = indicatorValue;
	}
	
	// Access variable name. 
	public String getSearchIndicatorName() {
		return searchIndicatorName;
	}

	// Modify variable name.
	public void setSearchIndicatorName(String name) {
		this.searchIndicatorName = name;
	}

	// Access variable searchIndicatorValue.
	public String getValue() {
		return searchIndicatorValue;
	}

	// Modify variable searchIndicatorValue.
	public void setValue(String indicatorValue) {
		this.searchIndicatorValue = indicatorValue;
	}

	// Rewriting variable name.
	@Override
	public String toString() {
		return this.getSearchIndicatorName();
	}

	// This method keep all indicators in one ArrayList.
	public static ArrayList<Indicator> getIndicators(){
		
		// Through an instance of QualCurso ,we obtain an  array of string institutions.
		String [] indicatorList = QualCurso.getInstance().getResources().getStringArray(R.array.indicator);
		
		ArrayList<Indicator> arrayOfIndicators = new ArrayList<Indicator>();

		arrayOfIndicators.add(new Indicator(indicatorList[0], DEFAULT_INDICATOR));
		
		// Adding triennial_evaluation.
		arrayOfIndicators.add(new Indicator(indicatorList[1], new Evaluation().
				fieldsList().get(7)));
		
		// Adding master_degree_start_year.
		arrayOfIndicators.add(new Indicator(indicatorList[2], new Evaluation().
				fieldsList().get(5)));
		
		// Adding doctorate_start_year.
		arrayOfIndicators.add(new Indicator(indicatorList[3], new Evaluation().
				fieldsList().get(6)));
		
		// Adding permanent_teachers.
		arrayOfIndicators.add(new Indicator(indicatorList[4], new Evaluation().
				fieldsList().get(8)));
		
		// Adding theses. 
		arrayOfIndicators.add(new Indicator(indicatorList[5], new Evaluation().
				fieldsList().get(9)));
		
		// Adding dissertations.
		arrayOfIndicators.add(new Indicator(indicatorList[6], new Evaluation().
				fieldsList().get(10)));
		
		// Adding artistic_production.
		arrayOfIndicators.add(new Indicator(indicatorList[7], new Evaluation().
				fieldsList().get(13)));
		
		// Adding chapters.
		arrayOfIndicators.add(new Indicator(indicatorList[8], new Book().
				fieldsList().get(2)));
		
		// Adding integral_text.
		arrayOfIndicators.add(new Indicator(indicatorList[9], new Book().
				fieldsList().get(1)));
		
		// Adding collections.
		arrayOfIndicators.add(new Indicator(indicatorList[10], new Book().
				fieldsList().get(3)));
		
		// Adding entries.
		arrayOfIndicators.add(new Indicator(indicatorList[11], new Book().
				fieldsList().get(4)));
		
		// Adding published_journals.
		arrayOfIndicators.add(new Indicator(indicatorList[12], new Article().
				fieldsList().get(1)));
		
		// Adding published_conference_proceedings.
		arrayOfIndicators.add(new Indicator(indicatorList[13], new Article().
				fieldsList().get(2)));

		return arrayOfIndicators;
	}

	// This method find a specific indicator in all indicators.	 
	public static Indicator getIndicatorByValue(String value) {
		Indicator indicator = null;
		
		for(Indicator finderIndicator : getIndicators()) {
			
			// Trying to find the indicator.
			if(finderIndicator.getValue().equals(value)) {
				indicator = finderIndicator;
				break;
			}
		}

		return indicator;
	}
	
}
