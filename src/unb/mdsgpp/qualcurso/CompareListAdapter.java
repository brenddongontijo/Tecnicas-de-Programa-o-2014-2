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

public class CompareListAdapter extends ArrayAdapter<HashMap<String, String>> {

	public static String INDICATOR_VALUE = "indicatorValue";
	public static String FIRST_VALUE = "firstValue";
	public static String SECOND_VALUE = "secondValue";
	public static String IGNORE_INDICATOR = "ignoreIndicator";

	public CompareListAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
	}

	public CompareListAdapter(Context context, int resource,
			List<HashMap<String, String>> items) {
		super(context, resource, items);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;

		if (v == null) {
			LayoutInflater vi;
			vi = LayoutInflater.from(getContext());
			v = vi.inflate(R.layout.compare_show_list_item, null);
		}

		HashMap<String, String> h = getItem(position);

		if (h != null) {
			TextView indicatorNameTextView = (TextView) v
					.findViewById(R.id.compare_indicator_name);
			TextView firstIndicatorTextView = (TextView) v
					.findViewById(R.id.compare_first_institution_indicator);
			TextView secondIndicatorTextView = (TextView) v
					.findViewById(R.id.compare_second_institution_indicator);

			if (indicatorNameTextView != null) {
				indicatorNameTextView.setText(Indicator.getIndicatorByValue(
						h.get(this.INDICATOR_VALUE)).getName());
			}
			if (firstIndicatorTextView != null
					|| secondIndicatorTextView != null) {
				int first = Integer.parseInt(h.get(this.FIRST_VALUE));
				int second = Integer.parseInt(h.get(this.SECOND_VALUE));
				firstIndicatorTextView.setText(Integer.toString(first));
				secondIndicatorTextView.setText(Integer.toString(second));
				if (h.get(IGNORE_INDICATOR).equals("false")) {
					if (first > second) {
						firstIndicatorTextView.setBackgroundColor(QualCurso
								.getInstance().getResources()
								.getColor(R.color.light_green));
						secondIndicatorTextView.setBackgroundColor(QualCurso
								.getInstance().getResources()
								.getColor(R.color.smooth_red));
					} else if (second > first) {
						secondIndicatorTextView.setBackgroundColor(QualCurso
								.getInstance().getResources()
								.getColor(R.color.light_green));
						firstIndicatorTextView.setBackgroundColor(QualCurso
								.getInstance().getResources()
								.getColor(R.color.smooth_red));
					} else {
						secondIndicatorTextView.setBackgroundColor(QualCurso
								.getInstance().getResources()
								.getColor(R.color.white));
						firstIndicatorTextView.setBackgroundColor(QualCurso
								.getInstance().getResources()
								.getColor(R.color.white));
					}
				} else {
					secondIndicatorTextView.setBackgroundColor(QualCurso
							.getInstance().getResources()
							.getColor(R.color.white));
					firstIndicatorTextView.setBackgroundColor(QualCurso
							.getInstance().getResources()
							.getColor(R.color.white));
				}
			}
		}

		return v;
	}

}
