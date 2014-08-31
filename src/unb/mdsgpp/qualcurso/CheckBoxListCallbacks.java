package unb.mdsgpp.qualcurso;

import android.widget.CheckBox;

	/*  
	 *  This interface define the methods of the classes that will
	 *  implements them. But it does not contain the implementation 
	 *  of the methods. 
	 */
public interface CheckBoxListCallbacks {
	public void onCheckedItem(CheckBox checkbox);
	public void onUnchekedItem(CheckBox checkbox);
}
