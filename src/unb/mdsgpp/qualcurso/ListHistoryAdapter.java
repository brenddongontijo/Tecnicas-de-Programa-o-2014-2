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

	TextView searchType = null;
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
		View historyView = contextView;

		// Check if a view exists, otherwise it will be inflated.
		if(historyView == null) {
			LayoutInflater layoutInflater;
			layoutInflater = LayoutInflater.from(getContext());
			historyView = layoutInflater.inflate(R.layout.history_list_item, null);
		}

		// Creating fields for a survey.
		Search searchByIndicator = getItem(position);
		if(searchByIndicator != null) {
			searchType = (TextView) historyView.findViewById(R.id.option);
			year = (TextView) historyView.findViewById(R.id.year);
			indicator = (TextView) historyView.findViewById(R.id.indicator);
			firstValue = (TextView) historyView.findViewById(R.id.firstValue);
			secondValue = (TextView) historyView.findViewById(R.id.secondValue);
			searchDate = (TextView) historyView.findViewById(R.id.searchDate);
			
			setListRow(searchByIndicator);
		}
		return historyView;
	}

	// Method used to populate the list of historic.
	public void setListRow(Search search) {
		
		/*
		 * Checks if a course was selected in spinner indicators. Or if the
		 * selection is an institution.
		 */
		if(search.getOption() == Search.COURSE) {
			setItem(searchType, R.string.course);
		}
		else if(search.getOption() == Search.INSTITUTION) {
			setItem(searchType, R.string.institution);
		}
		
		// Sets the values ​​of year, indicator , value one and two and date​​.
		setItem(year, Integer.toString(search.getYear()));
		setItem(indicator, search.getIndicator().getSearchIndicatorName());
		setItem(firstValue, Integer.toString(search.getMinValue()));
		int maximum = search.getMaxValue();
		
		if(maximum == -1) {
			setItem(secondValue, R.string.maximum);
		}
		else {
			setItem(secondValue, Integer.toString(maximum));
		}
		
		setItem(searchDate,
				SimpleDateFormat.getDateTimeInstance().format(search.getDate()));
	}

	public void setItem(TextView view, String data) {
		if(view != null) {
			view.setText(data);
		}
	}

	public void setItem(TextView view, int resId) {
		if(view != null) {
			view.setText(resId);
		}
	}
}
