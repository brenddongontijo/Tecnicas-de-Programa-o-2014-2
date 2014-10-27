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

/**
 * Class name: ListAdapter
 * 
 * This class makes a text view for each ranking result fields.
 */
public class RankingListAdapter extends ArrayAdapter<HashMap<String, String>> {

	public RankingListAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
	}

	public RankingListAdapter(Context context, int resource, List<HashMap<String, String>> items) {
		super(context, resource, items);
	}

	/**
	 * Method tha inflates the ranking view.
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rankingView = convertView;

		if (rankingView == null) {
			LayoutInflater inflateView;
			inflateView = LayoutInflater.from(getContext());
			rankingView = inflateView.inflate(R.layout.list_item, null);
		}

		rankingView = makeRankingView(rankingView, position);
		
    	return rankingView;
	}
	
	/**
	 * Method that create the ranking view.
	 * 
	 * @param rankingView
	 * @param position
	 * @return
	 */
	private View makeRankingView(View rankingView, int position) {
		
		HashMap<String, String> hashMap = getItem(position);
		
		if (hashMap != null) {
			TextView rank = (TextView) rankingView.findViewById(R.id.position);
			TextView institutionName = (TextView) rankingView.findViewById(R.id.university);
			TextView value = (TextView) rankingView.findViewById(R.id.data);
			ImageView trophy = (ImageView) rankingView.findViewById(R.id.trophyIcon);

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
		else {
			
		}
		
		return rankingView;
	}
	
	/**
	 * Method that put the trophy image in the ranking.
	 * 
	 * @param position
	 * @return
	 */
	public Drawable getTrophyImage(int position) {
		Drawable trophy = null;

		switch(position) {
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
