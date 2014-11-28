package unb.mdsgpp.qualcurso;

import helpers.Indicator;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Class name: IndicatorListAdapter.
 * 
 * This class is responsible for create the adapter for a View that represents a
 * list of all indicators.
 */
public class IndicatorListAdapter extends ArrayAdapter<HashMap<String,String>> {
	// Logging system.
	private final static Logger LOGGER = Logger.getLogger(IndicatorListAdapter.
			class.getName()); 
	
	// Use to keep the value of the indicator.
	public static String INDICATOR_VALUE = "indicatorValue";
	
	// Use to keep a value.
	public static String VALUE = "value";
	
	// Item layout selected.
	private int itemLayout = 0;

	// Constructor with context, text view id and indicator items.
	public IndicatorListAdapter(Context context, int resource, List<HashMap<String,String>> items) {
		super(context, resource, items);
		this.itemLayout = resource;
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
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;

		view = nullView(view);

		// Getting indicators in their positions.
		HashMap<String,String> hashMap = getItem(position);

		if (hashMap != null) {
			// Creating text view for indicator and his text.
			TextView indicator = (TextView) view.findViewById(R.id.indicator);
			TextView indicatorText = (TextView) view.findViewById(R.id.indicator_text);
			
			// Constants to verify is indicators textView are not null.
			final boolean indicatorInitialized = (indicator != null);
			final boolean indicatorTextInitialized = (indicatorText != null);
			
        	if (indicatorInitialized && indicatorTextInitialized) {
            	// Setting value for indicator.
        		indicator.setText(hashMap.get(VALUE));
            	
        		// Setting value for indicator text.
            	indicatorText.setText(Indicator.getIndicatorByValue(hashMap.get(INDICATOR_VALUE)).
        				getSearchIndicatorName());
        	}
        	else{
        		LOGGER.warning("Out of memory to create indicators TextView!");
        	}
    	}
		else{
			LOGGER.warning("Out of memory to create hashmap for indicators!");
		}

    	return view;
	}
	
	/**
	 * Method that checks if the view is null.
	 * 
	 * @param view
	 * 
	 * @return view
	 */
	private View nullView(View view) {
		final boolean viewNotInitialized = (view == null);
		
		if (viewNotInitialized) {
			// Inflating the view
			LayoutInflater inflateView;
			inflateView = LayoutInflater.from(getContext());
			view = inflateView.inflate(itemLayout, null);
			
			LOGGER.info("IndicatorListAdapter View sucefully initialized!");
		} 
		else {
			LOGGER.warning("IndicatorListAdapter View not initialized!");
		}
		
		return view;
	}
}