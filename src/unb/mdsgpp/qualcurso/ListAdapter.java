package unb.mdsgpp.qualcurso;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListAdapter extends ArrayAdapter<HashMap<String, String>> {

	public ListAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
	}

	public ListAdapter(Context context, int resource, List<HashMap<String, String>> items) {
		super(context, resource, items);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;

		if (v == null) {
			LayoutInflater vi;
			vi = LayoutInflater.from(getContext());
			v = vi.inflate(R.layout.list_item, null);
		}

		HashMap<String, String> h = getItem(position);

		if (h != null) {
			TextView rank = (TextView) v.findViewById(R.id.position);
			TextView institutionName = (TextView) v.findViewById(R.id.university);
			TextView value = (TextView) v.findViewById(R.id.data);
			ImageView trophy = (ImageView) v.findViewById(R.id.trophyIcon);

        	if (rank != null) {
            	rank.setText(Integer.toString(position+1));
        	}
        	if (trophy != null) {
        		trophy.setImageDrawable(getTrophyImage(position+1));
        	}
        	if (institutionName != null) {
        		institutionName.setText(h.get("acronym"));
        	}
        	if (value != null) {
            	value.setText(h.get(h.get("order_field")));
        	}
    	}
    	return v;
	}
	
	public Drawable  getTrophyImage(int position) {
		Drawable trophy = null;

		switch (position) {
		case 1:
			trophy = QualCurso.getInstance().getResources().getDrawable(R.drawable.gold_trophy);
			break;
		case 2:
			trophy = QualCurso.getInstance().getResources().getDrawable(R.drawable.silver_trophy);
			break;
		case 3:
			trophy = QualCurso.getInstance().getResources().getDrawable(R.drawable.bronze_trophy);
			break;
		default:
			break;
		}
		return trophy;
	}
}
