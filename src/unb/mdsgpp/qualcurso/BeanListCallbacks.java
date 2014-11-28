package unb.mdsgpp.qualcurso;

import models.Search;
import android.os.Parcelable;
import android.support.v4.app.Fragment;

/**
 * Class name: BeanListCallbacks
 * 
 * This class is responsible to notify another Class of an asynchronous 
 * action that has completed with success or error. Because Fragments 
 * should be modular and a callback is used in the Fragment to call 
 * methods in the Activity.
 */
public abstract interface BeanListCallbacks {
	void onBeanListItemSelected(Fragment fragment);
	void onBeanListItemSelected(Fragment fragment, int container);
	void onSearchBeanSelected(Search search, Parcelable bean);
}
