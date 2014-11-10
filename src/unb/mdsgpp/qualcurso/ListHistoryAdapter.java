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

/**
 * Class name: ListHistoryAdapter.
 * 
 * Make the view that shows list of the searches.
 */
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

	/**
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View,
	 * android.view.ViewGroup) Method rewritten, used to get a View.
	 */
	@Override
	public View getView(int position, View contextView, ViewGroup parent) {
		View historyView = contextView;

		// Check if a view exists, otherwise it will be inflated.
		if (historyView == null) {
			LayoutInflater layoutInflater;
			layoutInflater = LayoutInflater.from(getContext());
			historyView = layoutInflater.inflate(R.layout.history_list_item, null);
		}

		historyView = searchByIndicatorView(historyView, position);
		
		return historyView;
	}
	
	/**
	 * Method that creates the field for a survey.
	 * 
	 * @param historyView
	 * @param position
	 * 
	 * @return historyView
	 */
	private View searchByIndicatorView(View historyView, int position) {
		
		Search searchByIndicator = getItem(position);
		
		if (searchByIndicator != null) {
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

	/**
	 * Method used to populate the list of historic.
	 * 
	 * @param search
	 */
	public void setListRow(Search search) {
		
		checkSelection(search);
		
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
	
	/**
	 * Checks if a course was selected in spinner indicators. Or if the
	 * selection is an institution.
	 * 
	 * @param search	by institution or by course.
	 */
	private void checkSelection(Search search){
		
		if (search.getOption() == Search.COURSE) {
			setItem(searchType, R.string.course);
		}
		else if (search.getOption() == Search.INSTITUTION) {
			setItem(searchType, R.string.institution);
		}
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
