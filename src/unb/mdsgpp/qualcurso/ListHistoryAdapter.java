package unb.mdsgpp.qualcurso;

import java.text.SimpleDateFormat;
import java.util.List;

import models.Search;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ListHistoryAdapter extends ArrayAdapter<Search> {

	public ListHistoryAdapter(Context context, int resource, List<Search> items) {
		super(context, resource, items);
	}

	TextView option = null;
	TextView year = null;
	TextView indicator = null;
	TextView firstValue = null;
	TextView secondValue = null;
	TextView searchDate = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View,
	 * android.view.ViewGroup) Method rewritten, used to get a View
	 */
	@Override
	public View getView(int position, View contextView, ViewGroup parent) {
		View v = contextView;
		/*
		 * Check if a view exists, otherwise it will be inflated.
		 */
		if (v == null) {
			LayoutInflater li;
			li = LayoutInflater.from(getContext());
			v = li.inflate(R.layout.history_list_item, null);
		}
		/*
		 * Create an object of type "s" Search, and the instance.
		 */
		Search s = getItem(position);
		if (s != null) {
			option = (TextView) v.findViewById(R.id.option);
			year = (TextView) v.findViewById(R.id.year);
			indicator = (TextView) v.findViewById(R.id.indicator);
			firstValue = (TextView) v.findViewById(R.id.firstValue);
			secondValue = (TextView) v.findViewById(R.id.secondValue);
			searchDate = (TextView) v.findViewById(R.id.searchDate);
			setListRow(s);
		}
		return v;
	}

	/*
	 * Method used to populate the list of historic.
	 */
	public void setListRow(Search s) {
		/*
		 * checks if a course was selected in spinner indicators. Or if the
		 * selection is an institution.
		 */
		if (s.getOption() == Search.COURSE) {
			setItem(option, R.string.course);
		} else if (s.getOption() == Search.INSTITUTION) {
			setItem(option, R.string.institution);
		}
		/*
		 * sets the values ​​of year, indicator , value one and two and date​​.
		 */
		setItem(year, Integer.toString(s.getYear()));
		setItem(indicator, s.getIndicator().getSearchIndicatorName());
		setItem(firstValue, Integer.toString(s.getMinValue()));
		int max = s.getMaxValue();
		if (max == -1) {
			setItem(secondValue, R.string.maximum);
		} else {
			setItem(secondValue, Integer.toString(max));
		}
		setItem(searchDate,
				SimpleDateFormat.getDateTimeInstance().format(s.getDate()));
	}

	public void setItem(TextView view, String data) {
		if (view != null) {
			view.setText(data);
		}
	}

	public void setItem(TextView view, int resId) {
		if (view != null) {
			view.setText(resId);
		}
	}

}
