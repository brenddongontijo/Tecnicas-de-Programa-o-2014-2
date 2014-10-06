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
		View view = convertView;

		if (view == null) {
			LayoutInflater inflateView;
			inflateView = LayoutInflater.from(getContext());
			view = inflateView.inflate(R.layout.list_item, null);
		}

		HashMap<String, String> hashMap = getItem(position);

		if (hashMap != null) {
			TextView rank = (TextView) view.findViewById(R.id.position);
			TextView institutionName = (TextView) view.findViewById(R.id.university);
			TextView value = (TextView) view.findViewById(R.id.data);
			ImageView trophy = (ImageView) view.findViewById(R.id.trophyIcon);

        	if (rank != null) {
            	rank.setText(Integer.toString(position+1));
        	}
        	if (trophy != null) {
        		trophy.setImageDrawable(getTrophyImage(position+1));
        	}
        	if (institutionName != null) {
        		institutionName.setText(hashMap.get("acronym"));
        	}
        	if (value != null) {
            	value.setText(hashMap.get(hashMap.get("order_field")));
        	}
    	}
    	return view;
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
