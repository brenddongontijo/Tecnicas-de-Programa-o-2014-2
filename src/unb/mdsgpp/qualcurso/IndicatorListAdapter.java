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
	
	public static String INDICATOR_VALUE = "indicatorValue";
	public static String VALUE = "value";
	private int itemLayout = 0;

	public IndicatorListAdapter(Context context, int resource, List<HashMap<String,String>> items) {
		super(context, resource, items);
		this.itemLayout = resource;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;

		if (v == null) {
			LayoutInflater vi;
			vi = LayoutInflater.from(getContext());
			v = vi.inflate(itemLayout, null);
		}

		HashMap<String,String> h = getItem(position);

		if (h != null) {
			TextView indicator = (TextView) v.findViewById(R.id.indicator);
			TextView indicatorText = (TextView) v.findViewById(R.id.indicator_text);

        	if (indicator != null) {
            	indicator.setText(h.get(VALUE));
        	}
        	if (indicatorText != null) {
        		indicatorText.setText(Indicator.getIndicatorByValue(h.get(INDICATOR_VALUE)).getName());
        	}
    	}

    	return v;
	}
}