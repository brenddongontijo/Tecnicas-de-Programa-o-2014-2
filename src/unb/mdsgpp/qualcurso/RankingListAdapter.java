package unb.mdsgpp.qualcurso;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Class name: RankingListAdapter
 * 
 * This class makes a text view for each ranking result fields.
 */
public class RankingListAdapter extends ArrayAdapter<HashMap<String, String>> {
	// Logging system.
	private final static Logger LOGGER = Logger.getLogger(RankingListAdapter.
			class.getName()); 
	
	// Constructor with context and text view id.
	public RankingListAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
	}

	// Constructor with context, text view id and institution items.
	public RankingListAdapter(Context context, int resource, List<HashMap<String, String>> items) {
		super(context, resource, items);
	}

	/**
	 * Get the previous view and displays a list of ranking institutions.
	 * 
	 * @param position				Position of the item within the adapter's data.
	 * @param convertView			The old view to reuse.
	 * @param parent				The parent that this view will eventually be attached to.
	 * @return
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rankingView = convertView;
		
		final boolean rankingViewNotInitialized = (rankingView == null);
		
		if (rankingViewNotInitialized) {
			// Inflating the view.
			LayoutInflater inflateView;
			inflateView = LayoutInflater.from(getContext());
			rankingView = inflateView.inflate(R.layout.list_item, null);
			
			LOGGER.info("RankingListAdapterView sucefully inflated!");
		}
		else{
			LOGGER.info("RankingListAdapterView not inflated!");
		}

		rankingView = makeRankingView(rankingView, position);
		
    	return rankingView;
	}
	
	/**
	 * Method that create the ranking view.
	 * 
	 * @param rankingView
	 * @param position
	 * 
	 * @return
	 */
	private View makeRankingView(View rankingView, int position) {
		HashMap<String, String> hashMap = getItem(position);
		
		final boolean hashMapInitialized = (hashMap != null);
		
		if (hashMapInitialized) {
			// Creating text view for rank and filling it.
			TextView rank = (TextView) rankingView.findViewById(R.id.position);
			rank.setText(Integer.toString(position+1));
			
			// Creating text view for institution name and filling it.
			TextView institutionName = (TextView) rankingView.findViewById(R.id.university);
			institutionName.setText(hashMap.get("acronym"));
			
			// Creating text view for indicator value and filling it.
			TextView indicatorValue = (TextView) rankingView.findViewById(R.id.data);
			indicatorValue.setText(hashMap.get(hashMap.get("order_field")));
			
			// Creating a ImageView for first, second and third place.
			ImageView trophy = (ImageView) rankingView.findViewById(R.id.trophyIcon);
			trophy.setImageDrawable(getTrophyImage(position+1));
    	}
		else {
			LOGGER.severe("Out of memory to create a hashmap of ranking institutions!");
		}
		
		return rankingView;
	}
	
	/**
	 * Method that put the trophy image in the ranking.
	 * 
	 * @param position
	 * 
	 * @return
	 */
	public Drawable getTrophyImage(int position) {
		Drawable trophy = null;

		switch(position) {
			// First place in rank.
			case 1:
				trophy = QualCurso.getInstance().getResources().getDrawable(R.drawable.gold_trophy);
				break;
				
			// Second place in rank.
			case 2:
				trophy = QualCurso.getInstance().getResources().getDrawable(R.drawable.silver_trophy);
				break;
				
			// Third place in rank.
			case 3:
				trophy = QualCurso.getInstance().getResources().getDrawable(R.drawable.bronze_trophy);
				break;
				
			default:
				break;
		}
		return trophy;
	}
}
