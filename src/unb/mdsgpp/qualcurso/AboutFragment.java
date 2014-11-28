package unb.mdsgpp.qualcurso;

import java.util.logging.Logger;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Class Name: AboutFragment
 * 
 * This class is responsible for create a fragment with the software informations. 
 *
 */
public class AboutFragment extends Fragment {
	// Logging system.
	private final static Logger LOGGER = Logger.getLogger(AboutFragment.
			class.getName()); 
	
	// Callback to call methods in Activity.
	BeanListCallbacks beanCallbacks;

	/**
	 * Empty constructor.
	 */
	public AboutFragment() {
		super();
	}
	
	/**
	 * Created a new instance of AboutFragment.
	 * @return
	 */
	public static AboutFragment newInstance() {
		AboutFragment about = new AboutFragment();
		return about;
	}

	/**
	 * Called when a fragment is first attached to its activity.
	 * 
	 * @param activity				   single, focused thing that the user can do.
	 */
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			// Creating a callback for activity.
			beanCallbacks = (BeanListCallbacks) activity;
			
			LOGGER.info("Callback for AboutFragment successfully created!");
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement BeanListCallbacks.");
		}
	}

	/**
	 * Called once the fragment is associated with its activity.
	 */
	@Override
	public void onDetach() {
		super.onDetach();
		beanCallbacks = null;
	}

	/**
	 * This method creates the compare choose view associated with the AboutFragment.
	 * 
	 * @param inflater					responsible to inflate a view.
	 * @param container					responsible to generate the LayoutParams of the view.
	 * @param savedInstanceState		responsible for verifying that the fragment will be recreated.
	 * 
	 * @return							current view of AboutFragment associated with parameters chosen.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 	Bundle savedInstanceState) {
		// Inflating the view.
		View rootView = inflater.inflate(R.layout.about_fragment, container, false);

		LOGGER.info("AboutFragment View successfully created!");
		
		return rootView;
	}
}
