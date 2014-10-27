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

public class IndicatorListAdapter extends ArrayAdapter<HashMap<String,String>> {
	
	// Use to keep the value of the indicator.
	public static String INDICATOR_VALUE = "indicatorValue";
	
	// Use to keep a value.
	public static String VALUE = "value";
	
	private int itemLayout = 0;

	public IndicatorListAdapter(Context context, int resource, List<HashMap<String,String>> items) {
		super(context, resource, items);
		this.itemLayout = resource;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;

		view = nullView(view);

		HashMap<String,String> hashMap = getItem(position);

		if (hashMap != null) {
			TextView indicator = (TextView) view.findViewById(R.id.indicator);
			TextView indicatorText = (TextView) view.findViewById(R.id.indicator_text);

        	if (indicator != null) {
            	indicator.setText(hashMap.get(VALUE));
        	}
        	
        	if (indicatorText != null) {
        		indicatorText.setText(Indicator.getIndicatorByValue(hashMap.get(INDICATOR_VALUE)).
        				getSearchIndicatorName());
        	}
    	}

    	return view;
	}
	
	/**
	 * Method that checks if the view is null.
	 * 
	 * @param view
	 * @return
	 */
	private View nullView(View view) {
		
		if (view == null) {
			LayoutInflater inflateView;
			inflateView = LayoutInflater.from(getContext());
			view = inflateView.inflate(itemLayout, null);
		} 
		else {
			
		}
		
		return view;
	}
}