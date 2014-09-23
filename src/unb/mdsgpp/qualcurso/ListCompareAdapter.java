package unb.mdsgpp.qualcurso;

import java.util.ArrayList;
import java.util.List;

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

// Class designed to generate a list of comparisons.
public class ListCompareAdapter extends ArrayAdapter<Institution> implements
		OnCheckedChangeListener {

	public static int INSTITUTION = R.string.institution;
	public static int POSITION = R.id.checkbox;
	private Fragment callingFragment = null;
	private CheckBoxListCallbacks checkBoxCallBacks;
	private CheckBox checkBox = null;
	
	private ArrayList<Boolean> checkedItems = new ArrayList<Boolean>();

	// Instantiating the builder.
	public ListCompareAdapter(Context context, int resource,
			List<Institution> item, Fragment callingFragment) {
		super(context, resource, item);
		this.callingFragment = callingFragment;
		checkedItems = new ArrayList<Boolean>();
		for (int i = 0; i < this.getCount(); i = i +1) {
			checkedItems.add(false);
		}
	}
 
	// Method used to inflate the view.
	@Override
	public View getView(int position, View contextView, ViewGroup parent) {
		View currentView = contextView;
		checkBoxCallBacks = (CheckBoxListCallbacks) this.callingFragment;
		if (currentView == null) {
			LayoutInflater screenLayout;
			screenLayout = LayoutInflater.from(getContext());
			currentView = screenLayout.inflate(R.layout.compare_choose_list_item, null);
		}

		Institution institution = getItem(position);

		if (institution != null) {
			checkBox = (CheckBox) currentView.findViewById(R.id.compare_institution_checkbox);
			checkBox.setText(institution.getAcronym());
			checkBox.setTag(INSTITUTION, institution);
			checkBox.setTag(POSITION, position);
			checkBox.setChecked(checkedItems.get(position));
			checkBox.setOnCheckedChangeListener(this);
			currentView.setTag(checkBox);
		}
		return currentView;
	}

	// Used in changing buttons.
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		int position = (Integer) buttonView.getTag(ListCompareAdapter.POSITION);
		
		if (position != ListView.INVALID_POSITION) {
			if (isChecked) {
				checkBoxCallBacks.onCheckedItem((CheckBox) buttonView);
			}
			else {
				checkBoxCallBacks.onUnchekedItem((CheckBox) buttonView);
			}
			checkedItems.set(position, isChecked);
		}
	}
}
