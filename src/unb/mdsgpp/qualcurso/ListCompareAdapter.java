package unb.mdsgpp.qualcurso;

import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import models.Institution;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.CompoundButton.OnCheckedChangeListener;

/**
 * Class name: ListCompareAdapter.
 * 
 * Class designed to generate a list of comparisons between two institutions.
 */
public class ListCompareAdapter extends ArrayAdapter<Institution> implements
		OnCheckedChangeListener {
	
	// Logging system.
	private final static Logger LOGGER = Logger.getLogger(ListCompareAdapter.
			class.getName());
	// Institution to be selected.
	public static int INSTITUTION = R.string.institution;
	
	// Position of the checkbox item.
	public static int POSITION = R.id.checkbox;
	
	// Fragment to be called.
	private Fragment callingFragment = null;
	
	// Callback to call selected items on checkbox.
	private CheckBoxListCallbacks checkBoxCallBacks;
	
	// Checkbox to be used in comparison.
	private CheckBox checkBox = null;
	
	// Itens of the checkbox selected.
	private ArrayList<Boolean> checkedItems = new ArrayList<Boolean>();

	// Unchecking checkboxes.
	public ListCompareAdapter(Context context, int resource,
			List<Institution> item, Fragment callingFragment) {
		super(context, resource, item);
		
		this.callingFragment = callingFragment;
		
		checkedItems = new ArrayList<Boolean>();
		
		// Unmarking all positions of the checkbox.
		for(int counter = 0; counter < this.getCount(); counter = counter + 1) {
			checkedItems.add(false);
		}
	}
 
	/**
	 * Get the previous view and displays a list of checkbox.
	 * 
	 * @param position				Position of the item within the adapter's data.
	 * @param convertView			The old view to reuse.
	 * @param parent				The parent that this view will eventually be attached to.
	 * @return
	 */
	@Override
	public View getView(final int selectedPosition, View contextView, ViewGroup parent) {
		View currentView = contextView;
		
		checkBoxCallBacks = (CheckBoxListCallbacks) this.callingFragment;
		
		currentView = nullCurrentView(currentView);
		
		currentView = institutionForComparation(selectedPosition, currentView);

		return currentView;
	}
	
	/**
	 * Method that checks if the view been used is null.
	 * 
	 * @param currentView
	 * @return
	 */
	private View nullCurrentView(View currentView) {
		// Constant to verify if the view has been not initialized;
		final boolean viewNotInitialized = currentView == null;
		
		if (viewNotInitialized) {
			// Inflating the view and setting the fields.
			LayoutInflater screenLayout;
			screenLayout = LayoutInflater.from(getContext());
			currentView = screenLayout.inflate(R.layout.compare_choose_list_item, null);
			
			LOGGER.info("View for ListCompareAdapter created!");
		}
		else {
			LOGGER.info("Getting the previous View for ListCompareAdapter.");
		}
		
		return currentView;
		
	}
	
    /**
     * Method that checks if the institution to compare isn't null.
     * 
     * @param selectedPosition
     * @param currentView
     * @return
     */
	private View institutionForComparation(final int selectedPosition, View currentView) {
		// Getting selected item from checkbox.
		Institution institutionForComparation = getItem(selectedPosition);
		
		final boolean institutionCheckBoxSelected = (institutionForComparation != null);
		if (institutionCheckBoxSelected) {
			// Setting all fields for the checkbox selected.
			checkBox = (CheckBox) currentView.findViewById(R.id.compare_institution_checkbox);
			checkBox.setText(institutionForComparation.getAcronym());
			checkBox.setTag(INSTITUTION, institutionForComparation);
			checkBox.setTag(POSITION, selectedPosition);
			checkBox.setChecked(checkedItems.get(selectedPosition));
			checkBox.setOnCheckedChangeListener(this);
			
			currentView.setTag(checkBox);
		}
		
		return currentView;
		
	}

	/**
	 * Used in changing buttons.
	 */
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// Position of the selected checkbox.
		int position = (Integer) buttonView.getTag(ListCompareAdapter.POSITION);
		
		// Constant to verify is a valid position has been selected.
		final boolean validPositionSelected = (position != ListView.INVALID_POSITION);
		
		if (validPositionSelected) {
			if (isChecked) {
				// Adding the selected institution to callback.
				checkBoxCallBacks.onCheckedItem((CheckBox) buttonView);
			}
			else {
				try {
					checkBoxCallBacks.onUnchekedItem((CheckBox) buttonView);
				} catch (InstitutionNotFoundException e) {
					e.printStackTrace();
				}
			}
			checkedItems.set(position, isChecked);
		}
	}
}
