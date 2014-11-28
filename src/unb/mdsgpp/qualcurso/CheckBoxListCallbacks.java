package unb.mdsgpp.qualcurso;

import android.widget.CheckBox;

/**
 * Class Name: CheckBoxListCallbacks
 *
 * This class is responsible to notify another Class of an asynchronous 
 * action that has completed with success or error. Because Fragments 
 * should be modular and a callback is used in the Fragment to call 
 * methods in the Activity.
 */
public interface CheckBoxListCallbacks {
	public void onCheckedItem(CheckBox checkbox);
	public void onUnchekedItem(CheckBox checkbox) throws InstitutionNotFoundException;
}
