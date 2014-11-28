package unb.mdsgpp.qualcurso;

import helpers.Indicator;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Class Name: CompareListAdapter
 * 
 * This class is responsible for create the adapter for a View that represents a
 * comparison between two institutions.
 */
public class CompareListAdapter extends ArrayAdapter<HashMap<String, String>> {
	// Logging system.
	private final static Logger LOGGER = Logger.getLogger(CompareListAdapter.
			class.getName()); 
	
	// Value for one indicator.
	public static String INDICATOR_VALUE = "indicatorValue";
	
	// Value for the first indicator chosen.
	public static String FIRST_VALUE = "firstValue";
	
	// Value for the second indicator chosen.
	public static String SECOND_VALUE = "secondValue";
	
	// Value for a ingnored indicator.
	public static String IGNORE_INDICATOR = "ignoreIndicator";

	// Constructor with context and text view id.
	public CompareListAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
	}

	// Constructor with context, text view id and indicator items.
	public CompareListAdapter(Context context, int resource,
			List<HashMap<String, String>> items) {
		super(context, resource, items);
	}
	
	/**
	 * Get the previous view and displays a list of indicators.
	 * 
	 * @param position				Position of the item within the adapter's data.
	 * @param convertView			The old view to reuse.
	 * @param parent				The parent that this view will eventually be attached to.
	 * @return
	 */
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View compareView = convertView;

		// Checking if view was correct inflated.
		try {
			compareView = inflateView(compareView);
		} catch (ObjectNullException nullViewException) {
			LOGGER.log(Level.SEVERE, "An null view exception was thrown",
					nullViewException);
		}

		HashMap<String, String> hashMap = getItem(position);
		
		final boolean hashMapIsntEmpty = (hashMap != null);
		
		if (hashMapIsntEmpty) {
			// Creating TextViews for three fields of indicators.
			TextView indicatorNameTextView = (TextView) compareView
					.findViewById(R.id.compare_indicator_name);
			TextView firstIndicatorTextView = (TextView) compareView
					.findViewById(R.id.compare_first_institution_indicator);
			TextView secondIndicatorTextView = (TextView) compareView
					.findViewById(R.id.compare_second_institution_indicator);
			
			// Constant to verify if indicator name text view is not empty.
			final boolean indicatorNameTextViewIsntEmpty = (indicatorNameTextView != null);
			
			if (indicatorNameTextViewIsntEmpty) {
				// Filling the TextView for indicator name.
				indicatorNameTextView.setText(Indicator.getIndicatorByValue(
						hashMap.get(CompareListAdapter.INDICATOR_VALUE)).getSearchIndicatorName());
			}
			else{
				LOGGER.warning("Out of memory to create TextView for indicator name!");
			}
			
			try {
				checksWhichIndicatorIsWinner(hashMap, firstIndicatorTextView, 
						secondIndicatorTextView);
			} catch (ObjectNullException nullViewException) {
				LOGGER.log(Level.SEVERE, "An null view exception was thrown",
						nullViewException);
			}
		}
		else{
			LOGGER.warning("Out of memory to create the HashMap of indicators!");
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
			TextView firstIndicatorTextView, TextView secondIndicatorTextView) throws
			ObjectNullException {
		
		// Constants for first and second indicator text view not null.
		final boolean firstIndicatorTextViewIsntEmpty = (firstIndicatorTextView != null);
		final boolean secondIndicatorTextViewIsntEmpty = (secondIndicatorTextView != null);
		
		// Checking if indicators text views are valid.
		if (firstIndicatorTextViewIsntEmpty || secondIndicatorTextViewIsntEmpty) {
			
			// Values of first and second indicators.
			int firstIndicatorValue = Integer.parseInt(hashMap.
					get(CompareListAdapter.FIRST_VALUE));
			int secondIndicatorValue = Integer.parseInt(hashMap.
					get(CompareListAdapter.SECOND_VALUE));
			
			// Setting the text for both indicators.
			firstIndicatorTextView.setText(Integer.toString(firstIndicatorValue));
			secondIndicatorTextView.setText(Integer.toString(secondIndicatorValue));
			
			// Constants for witch indicator is winner.
			final int firstIndicatorWinner = 1;
			final int secondIndicatorWinner = 2;
			final int drawOnIndicators = 3;
			
			// Constant for check if current indicator is valid.
			final boolean indicatorValidForComparision = (hashMap.
					get(IGNORE_INDICATOR).equals("false"));
			
			if (indicatorValidForComparision) {
				if (firstIndicatorValue > secondIndicatorValue) {
					// Calling function to paint first indicator as winner.
					paintWinnerAndLoserIndicators(firstIndicatorWinner, 
							firstIndicatorTextView, secondIndicatorTextView);
				} 
				else if (secondIndicatorValue > firstIndicatorValue) {
					// Calling function to paint second indicator as winner.
					paintWinnerAndLoserIndicators(secondIndicatorWinner, 
							firstIndicatorTextView, secondIndicatorTextView);
				} 
				else {
					// Calling function to paint draw on indicators.
					paintWinnerAndLoserIndicators(drawOnIndicators, 
							firstIndicatorTextView, secondIndicatorTextView);
				 }
			} 
			else {
				// Ignored indicators are painting as draw indicators.
				paintWinnerAndLoserIndicators(drawOnIndicators, 
						firstIndicatorTextView, secondIndicatorTextView);
			}
		}
		else{
			// Throwing a exception because some of the indicators are null.
			throw new ObjectNullException("Indicator Text View null");
		}
	}

	/**
	 * This method inflate the view with the content of the indicators.
	 * 
	 * @param compareView				the current view.
	 * @return							view containing a list of indicator.
	 */
	private View inflateView(View compareView) throws ObjectNullException{
		final boolean emptyView = (compareView == null);
		
		if (emptyView) {
			// Inflating the view.
			LayoutInflater inflateView;
			inflateView = LayoutInflater.from(getContext());
			
			compareView = inflateView.inflate(R.layout.compare_show_list_item, null);
			
			LOGGER.info("Compare list layout sucefully created");
		}
		else {
			throw new ObjectNullException("View not initialized!");
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
			// Painting first Indicator winner.
			case 1:
				firstIndicator.setBackgroundColor(QualCurso
						.getInstance().getResources()
						.getColor(R.color.light_green));
				secondIndicator.setBackgroundColor(QualCurso
						.getInstance().getResources()
						.getColor(R.color.smooth_red));
				break;
			// Painting second Indicator winner.
			case 2:
				secondIndicator.setBackgroundColor(QualCurso
						.getInstance().getResources()
						.getColor(R.color.light_green));
				firstIndicator.setBackgroundColor(QualCurso
						.getInstance().getResources()
						.getColor(R.color.smooth_red));
				break;
			// Painting Draw.
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
