package helpers;

import java.util.ArrayList;

import models.Article;
import models.Book;
import models.Evaluation;
import unb.mdsgpp.qualcurso.QualCurso;
import unb.mdsgpp.qualcurso.R;

//Generic class help.
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
	 * Method that takes the object "indicator" and returns on a Arraylist,
	 * compound with their respective types depending on the position.
	 */
	public static ArrayList<Indicator> getIndicators() {
		String[] indicatorList = QualCurso.getInstance().getResources()
				.getStringArray(R.array.indicator);
		ArrayList<Indicator> result = new ArrayList<Indicator>();

		result.add(new Indicator(indicatorList[0], DEFAULT_INDICATOR));
		result.add(new Indicator(indicatorList[1], new Evaluation()
				.fieldsList().get(7)));
		result.add(new Indicator(indicatorList[2], new Evaluation()
				.fieldsList().get(5)));
		result.add(new Indicator(indicatorList[3], new Evaluation()
				.fieldsList().get(6)));
		result.add(new Indicator(indicatorList[4], new Evaluation()
				.fieldsList().get(8)));
		result.add(new Indicator(indicatorList[5], new Evaluation()
				.fieldsList().get(9)));
		result.add(new Indicator(indicatorList[6], new Evaluation()
				.fieldsList().get(10)));
		result.add(new Indicator(indicatorList[7], new Evaluation()
				.fieldsList().get(13)));
		result.add(new Indicator(indicatorList[8], new Book().fieldsList().get(
				2)));
		result.add(new Indicator(indicatorList[9], new Book().fieldsList().get(
				1)));
		result.add(new Indicator(indicatorList[10], new Book().fieldsList()
				.get(3)));
		result.add(new Indicator(indicatorList[11], new Book().fieldsList()
				.get(4)));
		result.add(new Indicator(indicatorList[12], new Article().fieldsList()
				.get(1)));
		result.add(new Indicator(indicatorList[13], new Article().fieldsList()
				.get(2)));

		return result;
	}

	//Get an Indicator for Value.
	public static Indicator getIndicatorByValue(String value) {
		Indicator indicator = null;
		for (Indicator i : getIndicators()) {
			if (i.getValue().equals(value)) {
				indicator = i;
				break;
			}
		}

		return indicator;
	}
}
