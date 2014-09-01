package unb.mdsgpp.qualcurso;

import models.Search;
import android.os.Parcelable;
import android.support.v4.app.Fragment;

	/*  
	 *  This interface define the methods of the classes that will
	 *  implements them. But it does not contain the implementation 
	 *  of the methods. 
	 */
public abstract interface BeanListCallbacks {
	void onBeanListItemSelected(Fragment fragment);
	void onBeanListItemSelected(Fragment fragment, int container);
	void onSearchBeanSelected(Search search, Parcelable bean);
}
