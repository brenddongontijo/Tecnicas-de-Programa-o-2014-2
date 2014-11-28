package unb.mdsgpp.qualcurso;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Class name: NavigationDrawerFragment.
 * 
 * Fragment used for managing interactions for and presentation of a navigation drawer.
 * See the <a href="https://developer.android.com/design/patterns/navigation-drawer.html#Interaction">
 * design guidelines</a> for a complete explanation of the behaviors implemented here.
 */
public class NavigationDrawerFragment extends Fragment {

	// Remember the position of the selected item.
	private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

	/*
	 * Per the design guidelines, you should show the drawer on launch until the
	 * user manually expands it. This shared preference tracks this.
	 */
	private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";

	// A pointer to the current callbacks instance (the Activity).
	private NavigationDrawerCallbacks mCallbacks;

	// Helper component that ties the action bar to the navigation drawer.
	private ActionBarDrawerToggle mDrawerToggle;

	// Layout from the drawer.
	private DrawerLayout mDrawerLayout;
	
	// List view of the options in the drawer.
	private ListView mDrawerListView;
	
	// View of the drawer.
	private View mFragmentContainerView;

	// Position of the option selected in the drawer.
	private int mCurrentSelectedPosition = 0;
	
	// Verify saved instance from drawer.
	private boolean mFromSavedInstanceState;
	
	// Verify what user selected from drawer.
	private boolean mUserLearnedDrawer;

	// Empty constructor.
	public NavigationDrawerFragment() {
	}

	/**
	 * Change selected position.
	 * 
	 * @param savedInstanceState
	 * @return
	 */
	public Boolean changedSelected(Bundle savedInstanceState) {
		boolean drawerChanged = true;
		
		final boolean positionOfDrawerNotSynchronized = (mCurrentSelectedPosition 
				!= savedInstanceState.getInt(STATE_SELECTED_POSITION));
		
		if (positionOfDrawerNotSynchronized) {
			// Synchronizing drawer with correct position 
			mCurrentSelectedPosition = savedInstanceState
					.getInt(STATE_SELECTED_POSITION);
			
			drawerChanged = true;
		} else {
			drawerChanged = false;
		}
		return drawerChanged;
	}
	
	/**
	 * This method is responsible to initialize the activity setting the drawer.
	 * 
	 * @param savedInstanceState 		Responsible for verifying if the fragment 
	 * 									will be recreated.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Setting the shared preferences.
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(getActivity());
		mUserLearnedDrawer = sharedPreferences.getBoolean(PREF_USER_LEARNED_DRAWER, false);
		
		boolean drawerChanged = true;
		
		final boolean notSavedInstanceState = (savedInstanceState != null);
		
		if (notSavedInstanceState) {
			drawerChanged = changedSelected(savedInstanceState);
			mFromSavedInstanceState = true;
		}

		// Select either the default item (0) or the last selected item.
		if (drawerChanged) {
			selectItem(mCurrentSelectedPosition);
		}
	}
	
	/**
	 * This method set the action to be realized after activy has been created.
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		/*
		 * Indicate that this fragment would like to influence the set of
		 * actions in the action bar.
		 */
		setHasOptionsMenu(true);
	}

	/**
	 * This method creates the view associated with the NavigationDrawerFragment.
	 * 
	 * @param inflater					Responsible to inflate a view.
	 * @param container					Responsible to generate the LayoutParams
	 * 									of the view.
	 * @param savedInstanceState		Responsible for verifying that the fragment 
	 * 									will be recreated.
	 * 
	 * @return							view containing the navigation drawer.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mDrawerListView = (ListView) inflater.inflate(
				R.layout.fragment_navigation_drawer, container, false);

		mDrawerListView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						selectItem(position);
					}
				});

		mDrawerListView.setAdapter(new ArrayAdapter<String>(getActionBar()
				.getThemedContext(), android.R.layout.simple_list_item_1,
				android.R.id.text1, new String[] {
						getString(R.string.title_section1),
						getString(R.string.title_section2),
						getString(R.string.title_section3),
						getString(R.string.title_section4),
						getString(R.string.title_section5), }));
		mDrawerListView.setItemChecked(mCurrentSelectedPosition, true);
		return mDrawerListView;
	}

	public boolean isDrawerOpen() {
		return mDrawerLayout != null
				&& mDrawerLayout.isDrawerOpen(mFragmentContainerView);
	}

	/**
	 * Users of this fragment must call this method to set up the navigation
	 * drawer interactions.
	 * 
	 * @param fragmentId
	 *            The android:id of this fragment in its activity's layout.
	 * @param drawerLayout
	 *            The DrawerLayout containing this fragment's UI.
	 */
	public void setUp(int fragmentId, DrawerLayout drawerLayout) {
		mFragmentContainerView = getActivity().findViewById(fragmentId);
		mDrawerLayout = drawerLayout;

		/*
		 * Set a custom shadow that overlays the main content when the drawer
		 * opens.
		 */
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);

		// Set up the drawer's list view with items and click listener.
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);

		/*
		 * ActionBarDrawerToggle ties together the the proper interactions
		 * between the navigation drawer and the action bar app icon.
		 */
		mDrawerToggle = new ActionBarDrawerToggle(getActivity(), // host Activity
				mDrawerLayout, // DrawerLayout object
				R.drawable.ic_drawer, // navigation drawer image to replace 'Up' caret
				R.string.navigation_drawer_open, // "open drawer" description for accessibility
				R.string.navigation_drawer_close // "close drawer" description for accessibility
		) {
			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
				if (!isAdded()) {
					return;
				}
				getActivity().supportInvalidateOptionsMenu(); // calls
																// onPrepareOptionsMenu()
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				if (!isAdded()) {
					return;
				}

				if (!mUserLearnedDrawer) {

					/*
					 * The user manually opened the drawer; store this flag to
					 * prevent auto-showing the navigation drawer automatically
					 * in the future.
					 */
					mUserLearnedDrawer = true;
					SharedPreferences sp = PreferenceManager
							.getDefaultSharedPreferences(getActivity());
					sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true)
							.apply();
				}

				getActivity().supportInvalidateOptionsMenu(); // calls
																// onPrepareOptionsMenu()
			}
		};

		learnDrawer();
		

		// Defer code dependent on restoration of previous instance state.
		mDrawerLayout.post(new Runnable() {
			@Override
			public void run() {
				mDrawerToggle.syncState();
			}
		});

		mDrawerLayout.setDrawerListener(mDrawerToggle);
	}
	
	/**
	 * If the user hasn't 'learned' about the drawer, open it to introduce
	 * them to the drawer, per the navigation drawer design guidelines.
	 */
	private void learnDrawer() {
		if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
			mDrawerLayout.openDrawer(mFragmentContainerView);
		}
	}

	private void selectItem(int position) {
		if (mDrawerListView != null) {
			if (position != mCurrentSelectedPosition) {
				mDrawerListView.setItemChecked(position, true);
			}
		}
		if (mDrawerLayout != null) {
			mDrawerLayout.closeDrawer(mFragmentContainerView);
		}
		if (mCallbacks != null) {
			if (position != mCurrentSelectedPosition) {
				mCallbacks.onNavigationDrawerItemSelected(position);
			} else if (mCurrentSelectedPosition == 0) {
				mCallbacks.onNavigationDrawerItemSelected(position);
			}
		}
		mCurrentSelectedPosition = position;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		try {
			mCallbacks = (NavigationDrawerCallbacks) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(
					"Activity must implement NavigationDrawerCallbacks.");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mCallbacks = null;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);

		// Forward the new configuration the drawer toggle component.
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	/**
	 * If the drawer is open, show the global app actions in the action bar.
	 * See also showGlobalContextActionBar, which controls the top-left area
	 * of the action bar.
	 */
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

		if (mDrawerLayout != null && isDrawerOpen()) {
			inflater.inflate(R.menu.global, menu);
			showGlobalContextActionBar();
		}

		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	/**
	 * Per the navigation drawer design guidelines, updates the action bar to
	 * show the global app 'context', rather than just what's in the current
	 * screen.
	 */
	private void showGlobalContextActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setTitle(getFormatedTitle(getString(R.string.app_name)));
	}

	public CharSequence getFormatedTitle(CharSequence charSequence) {
		int actionBarTitleColor = getResources().getColor(
				R.color.actionbar_title_color);

		return Html.fromHtml("<font color='#"
				+ Integer.toHexString(actionBarTitleColor).substring(2)
				+ "'><b>" + charSequence + "</b></font>");
	}

	private ActionBar getActionBar() {
		return ((ActionBarActivity) getActivity()).getSupportActionBar();
	}

	/**
	 * Callback interface that all activities using this fragment must
	 * implement.
	 */
	public static interface NavigationDrawerCallbacks {

		/**
		 * Called when an item in the navigation drawer is selected.
		 * 
		 * @param position
		 */
		void onNavigationDrawerItemSelected(int position);
	}
}
