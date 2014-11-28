package unb.mdsgpp.qualcurso;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Logger;

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
 * This class is responsible for create the adapter for a View that represents a
 * history of all search made.
 */
public class ListHistoryAdapter extends ArrayAdapter<Search> {
	// Constructor with context, text view id and history items.
	public ListHistoryAdapter(Context context, int resource, List<Search> items) {
		super(context, resource, items);
	}

	// Logging system.
	private final static Logger LOGGER = Logger.getLogger(ListHistoryAdapter.
			class.getName()); 
	
	// Field responsible for the type of search.
	TextView searchType = null;
	
	// Field responsible for the researched year. 
	TextView year = null;
	
	// Field responsible for the researched indicator
	TextView indicator = null;
	
	// Field responsible for the researched minimum value.
	TextView firstValue = null;
	
	// Field responsible for the researched maximum value.
	TextView secondValue = null;
	
	// Field responsible for the researched date.
	TextView searchDate = null;

	/**
	 * Get the previous view and displays a list of search made.
	 * 
	 * @param position				Position of the item within the adapter's data.
	 * @param convertView			The old view to reuse.
	 * @param parent				The parent that this view will eventually be attached to.
	 * @return
	 */
	@Override
	public View getView(int position, View contextView, ViewGroup parent) {
		View historyView = contextView;

		final boolean historyViewNotInitialized = (historyView == null);
		
		// Check if a view exists, otherwise it will be inflated.
		if (historyViewNotInitialized) {
			// Inflating the view.
			LayoutInflater layoutInflater;
			layoutInflater = LayoutInflater.from(getContext());
			historyView = layoutInflater.inflate(R.layout.history_list_item, null);
			
			LOGGER.info("ListHistoryAdapter view sucefully inflated!");
		}
		else {
			LOGGER.info("ListHistoryAdapter view not initialized!");
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
		
		// Constant to verify if search indicator is not empty.
		final boolean searchIndicatorNotEmpty = (searchByIndicator != null);
		
		if (searchIndicatorNotEmpty) {
			// Creating the fields of the search.
			searchType = (TextView) historyView.findViewById(R.id.option);
			year = (TextView) historyView.findViewById(R.id.year);
			indicator = (TextView) historyView.findViewById(R.id.indicator);
			firstValue = (TextView) historyView.findViewById(R.id.firstValue);
			secondValue = (TextView) historyView.findViewById(R.id.secondValue);
			searchDate = (TextView) historyView.findViewById(R.id.searchDate);
			
			setListRow(searchByIndicator);
		}
		else{
			LOGGER.warning("None researchs made!");
		}
		
		return historyView;
		
	}

	/**
	 * Method used to populate the list of historic.
	 * 
	 * @param search
	 */
	public void setListRow(Search search) {
		// Checking what type of search has been made previously.
		checkSelection(search);
		
		//	Setting fields of year, indicator and minimum value.
		setItem(year, Integer.toString(search.getYear()));
		setItem(indicator, search.getIndicator().getSearchIndicatorName());
		setItem(firstValue, Integer.toString(search.getMinValue()));
		
		final int maximumValueChoosen = search.getMaxValue();
		
		final boolean noneMaximumValueChoosen = (maximumValueChoosen == -1);
		
		if (noneMaximumValueChoosen) {
			// Setting the default value to maximum value.
			setItem(secondValue, R.string.maximum);
		}
		else {
			// Setting the choosen value to maximum value.
			setItem(secondValue, Integer.toString(maximumValueChoosen));
		}
		
		// Setting the date and time from search.
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
		// Constants to verify what kind of search has been made.
		final boolean courseSearchChoosen = (search.getOption() == Search.COURSE);
		final boolean institutionSearchChoosen = (search.getOption() == Search.
				INSTITUTION);
		
		if (courseSearchChoosen) {
			// Setting the search to be a course search.
			setItem(searchType, R.string.course);
		}
		else if (institutionSearchChoosen) {
			// Setting the search to be a institution search.
			setItem(searchType, R.string.institution);
		}
		else{
			LOGGER.warning("No search conducted!");
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
