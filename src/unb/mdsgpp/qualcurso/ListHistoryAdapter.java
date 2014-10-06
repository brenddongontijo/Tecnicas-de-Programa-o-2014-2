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
	 * android.view.ViewGroup) Method rewritten, used to get a View.
	 */
	@Override
	public View getView(int position, View contextView, ViewGroup parent) {
		View view = contextView;

		// Check if a view exists, otherwise it will be inflated.
		if (view == null) {
			LayoutInflater inflateLayout;
			inflateLayout = LayoutInflater.from(getContext());
			view = inflateLayout.inflate(R.layout.history_list_item, null);
		}

		// Create an object of type "search" Search, and the instance.
		Search search = getItem(position);
		if (search != null) {
			option = (TextView) view.findViewById(R.id.option);
			year = (TextView) view.findViewById(R.id.year);
			indicator = (TextView) view.findViewById(R.id.indicator);
			firstValue = (TextView) view.findViewById(R.id.firstValue);
			secondValue = (TextView) view.findViewById(R.id.secondValue);
			searchDate = (TextView) view.findViewById(R.id.searchDate);
			setListRow(search);
		}
		return view;
	}

	// Method used to populate the list of historic.
	public void setListRow(Search search) {
		
		/*
		 * Checks if a course was selected in spinner indicators. Or if the
		 * selection is an institution.
		 */
		if (search.getOption() == Search.COURSE) {
			setItem(option, R.string.course);
		}
		else if (search.getOption() == Search.INSTITUTION) {
			setItem(option, R.string.institution);
		}
		
		// Sets the values ​​of year, indicator , value one and two and date​​.
		setItem(year, Integer.toString(search.getYear()));
		setItem(indicator, search.getIndicator().getSearchIndicatorName());
		setItem(firstValue, Integer.toString(search.getMinValue()));
		int maximum = search.getMaxValue();
		
		if (maximum == -1) {
			setItem(secondValue, R.string.maximum);
		}
		else {
			setItem(secondValue, Integer.toString(maximum));
		}
		
		setItem(searchDate,
				SimpleDateFormat.getDateTimeInstance().format(search.getDate()));
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
