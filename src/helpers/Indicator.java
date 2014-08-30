package helpers;

import java.util.ArrayList;

import models.Article;
import models.Book;
import models.Evaluation;
import unb.mdsgpp.qualcurso.QualCurso;
import unb.mdsgpp.qualcurso.R;

public class Indicator {
	private String name;
	private String value;
	
	public static final String DEFAULT_INDICATOR = "defaultIndicator"; 

	public Indicator(String name, String value) {		
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return this.getName();
	} 

	/*
	 * The method getIndicators creates one ArrayList of Indicator containing 
	 * some indicators from the classes on package "models". 
	 */
	public static ArrayList<Indicator> getIndicators(){
		String [] indicatorList = QualCurso.getInstance().getResources().getStringArray(R.array.indicator);
		ArrayList<Indicator> result = new ArrayList<Indicator>();

		result.add(new Indicator(indicatorList[0], DEFAULT_INDICATOR));
		// Evaluation().fieldsList().get(7) = triennial_evaluation.
		result.add(new Indicator(indicatorList[1], new Evaluation().fieldsList().get(7)));
		// Evaluation().fieldsList().get(5) = master_degree_start_year.
		result.add(new Indicator(indicatorList[2], new Evaluation().fieldsList().get(5)));
		// Evaluation().fieldsList().get(6) = doctorate_start_year.
		result.add(new Indicator(indicatorList[3], new Evaluation().fieldsList().get(6)));
		// Evaluation().fieldsList().get(8) = permanent_teachers.
		result.add(new Indicator(indicatorList[4], new Evaluation().fieldsList().get(8)));
		// Evaluation().fieldsList().get(9) = theses. 
		result.add(new Indicator(indicatorList[5], new Evaluation().fieldsList().get(9)));
		// Evaluation().fieldsList().get(10) = dissertations.
		result.add(new Indicator(indicatorList[6], new Evaluation().fieldsList().get(10)));
		// Evaluation().fieldsList().get(13) = artistic_production.
		result.add(new Indicator(indicatorList[7], new Evaluation().fieldsList().get(13)));
		// Evaluation().fieldsList().get(2) = chapters.
		result.add(new Indicator(indicatorList[8], new Book().fieldsList().get(2)));
		// Book().fieldsList().get(1) = integral_text.
		result.add(new Indicator(indicatorList[9], new Book().fieldsList().get(1)));
		// Book().fieldsList().get(3) = collections.
		result.add(new Indicator(indicatorList[10], new Book().fieldsList().get(3)));
		// Book().fieldsList().get(4) = entries.
		result.add(new Indicator(indicatorList[11], new Book().fieldsList().get(4)));
		// Article().fieldsList().get(1) = published_journals.
		result.add(new Indicator(indicatorList[12], new Article().fieldsList().get(1)));
		// Article().fieldsList().get(2) = published_conference_proceedings.
		result.add(new Indicator(indicatorList[13], new Article().fieldsList().get(2)));

		return result;
	}

	/*
	 * The method getIndicatorByValue receives one "value" and try to find it
	 * in one ArrayList of indicators.
	 */
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
