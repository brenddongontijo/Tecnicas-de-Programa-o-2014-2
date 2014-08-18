package unb.mdsgpp.qualcurso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import models.Institution;
import models.Search;
import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class ListCompareAdapter extends ArrayAdapter<Institution> implements OnCheckedChangeListener{
	
	public static int INSTITUTION = R.string.institution;
	public static int POSITION = R.id.checkbox;
	private Fragment callingFragment = null;
	private CheckBoxListCallbacks checkBoxCallBacks;
	private CheckBox cb = null;
	private ArrayList<Boolean> checkedItems = new ArrayList<Boolean>();
	
	public ListCompareAdapter(Context context, int resource, List<Institution> item, Fragment callingFragment) {
		super(context, resource, item);
		this.callingFragment = callingFragment;
		checkedItems = new ArrayList<Boolean>();
		for (int i = 0; i < this.getCount(); i++) {
			checkedItems.add(false);
		}
	}
	
	@Override
	public View getView(int position, View contextView, ViewGroup parent){
		View currentView = contextView;
		checkBoxCallBacks = (CheckBoxListCallbacks)this.callingFragment;
		if(currentView == null){
			LayoutInflater li;
			li = LayoutInflater.from(getContext());
			currentView = li.inflate(R.layout.compare_choose_list_item, null);
		}

		Institution i = getItem(position);

		if(i != null){
			cb = (CheckBox) currentView.findViewById(R.id.compare_institution_checkbox);
			cb.setText(i.getAcronym());
			cb.setTag(INSTITUTION, i);
			cb.setTag(POSITION, position);
			cb.setChecked(checkedItems.get(position));
			cb.setOnCheckedChangeListener(this);
			currentView.setTag(cb);
		}
		
		return currentView;
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
        int pos = (Integer)buttonView.getTag(ListCompareAdapter.POSITION);
        if (pos != ListView.INVALID_POSITION) {
        	if(isChecked){
				checkBoxCallBacks.onCheckedItem((CheckBox)buttonView);
			}else{
				checkBoxCallBacks.onUnchekedItem((CheckBox)buttonView);
			}
        	checkedItems.set(pos, isChecked);
        }
	}
}
