package unb.mdsgpp.qualcurso;

import helpers.Indicator;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Class Name: CompareListAdapter
 * 
 * This class is responsible for create the View that represents a comparison between two institutions.
 */
public class CompareListAdapter extends ArrayAdapter<HashMap<String, String>> {

	// Value for one indicator.
	public static String INDICATOR_VALUE = "indicatorValue";
	
	// Value for the first indicator chosen.
	public static String FIRST_VALUE = "firstValue";
	
	// Value for the second indicator chosen.
	public static String SECOND_VALUE = "secondValue";
	
	public static String IGNORE_INDICATOR = "ignoreIndicator";

	public CompareListAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
	}

	public CompareListAdapter(Context context, int resource,
			List<HashMap<String, String>> items) {
		super(context, resource, items);
	}
	
	/**
	 * 
	 */
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View compareView = convertView;

		compareView = inflateView(compareView);

		HashMap<String, String> hashMap = getItem(position);
		
		final boolean hashMapIsntEmpty = (hashMap != null);
		if (hashMapIsntEmpty) {
			
			TextView indicatorNameTextView = (TextView) compareView
					.findViewById(R.id.compare_indicator_name);
			TextView firstIndicatorTextView = (TextView) compareView
					.findViewById(R.id.compare_first_institution_indicator);
			TextView secondIndicatorTextView = (TextView) compareView
					.findViewById(R.id.compare_second_institution_indicator);
			
			final boolean indicatorNameTextViewIsntEmpty = (indicatorNameTextView != null);
			
			if (indicatorNameTextViewIsntEmpty) {
				indicatorNameTextView.setText(Indicator.getIndicatorByValue(
						hashMap.get(CompareListAdapter.INDICATOR_VALUE)).getSearchIndicatorName());
			}
			else{
				
			}
			
			checksWhichIndicatorIsWinner(hashMap, firstIndicatorTextView, secondIndicatorTextView);
		}
		else{
			
		}

		return compareView;
	}
	
	/**
	 * This method check witch indicator is winner.
	 * @param hashMap							hashMap containing fields of first and second indicator.
	 * @param firstIndicatorTextView			text view of first indicator.
	 * @param secondIndicatorTextView			text view of second indicator.
	 */
	private void checksWhichIndicatorIsWinner(HashMap<String, String> hashMap,
			TextView firstIndicatorTextView, TextView secondIndicatorTextView) {
		
		final boolean firstIndicatorTextViewIsntEmpty = (firstIndicatorTextView != null);
		final boolean secondIndicatorTextViewIsntEmpty = (secondIndicatorTextView != null);
		
		if (firstIndicatorTextViewIsntEmpty || secondIndicatorTextViewIsntEmpty) {
			
			int firstIndicator = Integer.parseInt(hashMap.get(CompareListAdapter.FIRST_VALUE));
			int secondIndicator = Integer.parseInt(hashMap.get(CompareListAdapter.SECOND_VALUE));
			
			firstIndicatorTextView.setText(Integer.toString(firstIndicator));
			secondIndicatorTextView.setText(Integer.toString(secondIndicator));
			
			final int firstIndicatorWinner = 1;
			final int secondIndicatorWinner = 2;
			final int drawOnIndicators = 3;
			if (hashMap.get(IGNORE_INDICATOR).equals("false")) {
				if (firstIndicator > secondIndicator) {
					paintWinnerAndLoserIndicators(firstIndicatorWinner, firstIndicatorTextView, secondIndicatorTextView);
				} 
				else if (secondIndicator > firstIndicator) {
					paintWinnerAndLoserIndicators(secondIndicatorWinner, firstIndicatorTextView, secondIndicatorTextView);
					
				} 
				else {
					paintWinnerAndLoserIndicators(drawOnIndicators, firstIndicatorTextView, secondIndicatorTextView);
				 }
			} 
			else {
				paintWinnerAndLoserIndicators(drawOnIndicators, firstIndicatorTextView, secondIndicatorTextView);
			 }
		}
	}

	/**
	 * This method inflate the view with the content of the indicators.
	 * 
	 * @param compareView				the current view.
	 * @return							view containing a list of indicator.
	 */
	private View inflateView(View compareView){
		final boolean emptyView = (compareView == null);
		
		if (emptyView) {
			LayoutInflater inflateView;
			inflateView = LayoutInflater.from(getContext());
			
			compareView = inflateView.inflate(R.layout.compare_show_list_item, null);
		}
		else {
			
		}
		
		return compareView;
	}
	
	/**
	 * This method paint the background of the winners or losers indicators.
	 * 
	 * @param winner					indicator winner. 1 - first indicator winnner, 2 - second indicator winner, 3 - tie.
	 * @param firstIndicator			TextView of the first indicator to be paint.
	 * @param secondIndicator			TextView of the first second to be paint.s
	 */
	private void paintWinnerAndLoserIndicators(final int winner, TextView firstIndicator,
			TextView secondIndicator){
		
		switch(winner){
			case 1:
				firstIndicator.setBackgroundColor(QualCurso
						.getInstance().getResources()
						.getColor(R.color.light_green));
				secondIndicator.setBackgroundColor(QualCurso
						.getInstance().getResources()
						.getColor(R.color.smooth_red));
				break;
			case 2:
				secondIndicator.setBackgroundColor(QualCurso
						.getInstance().getResources()
						.getColor(R.color.light_green));
				firstIndicator.setBackgroundColor(QualCurso
						.getInstance().getResources()
						.getColor(R.color.smooth_red));
				break;
			case 3:
				secondIndicator.setBackgroundColor(QualCurso
						.getInstance().getResources()
						.getColor(R.color.white));
				firstIndicator.setBackgroundColor(QualCurso
						.getInstance().getResources()
						.getColor(R.color.white));
				break;
			default:
				break;
		}
	}
}
