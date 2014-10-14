package unb.mdsgpp.qualcurso;

import java.util.ArrayList;

import models.Bean;
import models.Course;
import models.Institution;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

public class TabsFragment extends Fragment implements OnTabChangeListener,
		OnQueryTextListener {

	// statement of objects
	BeanListCallbacks beanCallbacks;

	public static final String TAB_INSTITUTIONS = "tabInstitutions";
	public static final String TAB_COURSES = "tabCourses";
	
	private View mRoot;
	private TabHost mTabHost;
	private int currentSelectedTab; // Current selected tab: 0-Institution, 1-Course.
	private SearchView mSearchView;

	private ArrayList<Course> allCourses = Course.getAllCourses();
	private ArrayList<Institution> allInstitutions = Institution
			.getAllInstitutions();

	// Called When a fragment is first attached to its activity.
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		try {
			beanCallbacks = (BeanListCallbacks) activity;
		} catch(ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement BeanListCallbacks.");
		}
	}

	// Used to create a view.
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mRoot = inflater.inflate(R.layout.tabs_fragment, null);

		mTabHost = (TabHost) mRoot.findViewById(android.R.id.tabhost);

		setupTabs();

		return mRoot;
	}

	/*
	 * Called when all saved state has been restored into the view hierarchy ofthe fragment. 
	 * (non-Javadoc) see android.support.v4.app.Fragment#onActivityCreated(android.os.Bundle)
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		setRetainInstance(true);
		setHasOptionsMenu(true);

		mTabHost.setOnTabChangedListener(this);
		mTabHost.setCurrentTab(currentSelectedTab);

		// Manually start loading stuff in the first tab.
		updateTab(TAB_INSTITUTIONS, R.id.tab_1);
	}

	// Configuration tabs according to their type.
	private void setupTabs() {
		mTabHost.setup(); // You must call this before adding your tabs!
		mTabHost.addTab(mTabHost.newTabSpec(TAB_INSTITUTIONS)
				.setIndicator(getString(R.string.institutions))
				.setContent(R.id.tab_1));
		mTabHost.addTab(mTabHost.newTabSpec(TAB_COURSES)
				.setIndicator(getString(R.string.courses))
				.setContent(R.id.tab_2));

		TabWidget widget = mTabHost.getTabWidget();

		int counter = 0;
		for(counter = 0; counter < widget.getChildCount(); counter = counter + 1) {
			View currentView = widget.getChildAt(counter);

			TextView titleTextView = (TextView) currentView
					.findViewById(android.R.id.title);

			if(titleTextView == null) {
				continue;
			}
			currentView
					.setBackgroundResource(R.drawable.tab_indicator_ab_light_green_acb);
		}
	}

	/*
	 * Checks if the guide has changed , by the id of the tab , which maybe be
	 * an tab of institution or course.
	 */
	@Override
	public void onTabChanged(String selectedTabId) {

		if(TAB_INSTITUTIONS.equals(selectedTabId)) {
			updateTab(selectedTabId, R.id.tab_1);
			currentSelectedTab = 0;

			return;
		}

		if(TAB_COURSES.equals(selectedTabId)) {
			updateTab(selectedTabId, R.id.tab_2);
			currentSelectedTab = 1;

			return;
		}
	}

	// Initialize the contents of the Activity options menu.
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {

		menuInflater.inflate(R.menu.search_menu, menu);

		MenuItem searchItem = menu.findItem(R.id.action_search);

		mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
		setupSearchView(searchItem);

		super.onCreateOptionsMenu(menu, menuInflater);
	}

	// Search setting View the menu.
	private void setupSearchView(MenuItem searchItem) {

		searchItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM
				| MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);

		mSearchView.setOnQueryTextListener(this);
	}

	// Updates the tab.
	private void updateTab(String tabId, int placeholder) {

		FragmentManager currentFragment = getFragmentManager();

		if(currentFragment.findFragmentByTag(tabId) == null) {

			/*
			 * Updates the fragment according to idTab string , which may be
			 * TAB_INSTITUTIONS or TAB_COURSES.
			 */
			if(tabId.equalsIgnoreCase(TAB_INSTITUTIONS)) {
				beanCallbacks.onBeanListItemSelected(
						InstitutionListFragment.newInstance(0, 2010),
						placeholder);
			}
			else if(tabId.equalsIgnoreCase(TAB_COURSES)) {
				beanCallbacks.onBeanListItemSelected(
						CourseListFragment.newInstance(0, 2010), placeholder);
			}
		}

	}

	// Called when the query text is changed by the user.
	@Override
	public boolean onQueryTextChange(String writtenText) {

		// Checks if the string has valid size.
		if(writtenText.length() >= 1) {

			// If the tab is the first list the institutions.
			if(currentSelectedTab == 0) {
				ArrayList<Bean> arrayOfBeans = getFilteredList(writtenText, allInstitutions);
				beanCallbacks.onBeanListItemSelected(InstitutionListFragment
						.newInstance(0, 2010, castBeansToInstitutions(arrayOfBeans)),
						R.id.tab_1);
			}
			
			// If the tab is a second list courses.
			else if(currentSelectedTab == 1) {
				ArrayList<Bean> arrayOfBeans = getFilteredList(writtenText, allCourses);
				beanCallbacks.onBeanListItemSelected(CourseListFragment.newInstance(
								0, 2010, castBeansToCourses(arrayOfBeans)), R.id.tab_2);
			}
		}

		// If there is no change, checks the current tab and list according the same.
		else {
			if(currentSelectedTab == 0) {
				beanCallbacks.onBeanListItemSelected(
						InstitutionListFragment.newInstance(0, 2010),
						R.id.tab_1);
			} 
			else if(currentSelectedTab == 1) {
				beanCallbacks.onBeanListItemSelected(
						CourseListFragment.newInstance(0, 2010), R.id.tab_2);
			}
		}
		return false;
	}

	// Receives an arraylist of beans and turns into a arraylist of institution.
	public ArrayList<Institution> castBeansToInstitutions(ArrayList<Bean> allReceivedBeans) {
		
		ArrayList<Institution> arrayOfInstitutions = new ArrayList<Institution>();

		for(Bean bean : allReceivedBeans) {
			arrayOfInstitutions.add((Institution) bean);
		}

		return arrayOfInstitutions;
	}

	// Receives an arraylist of beans and turns into a arraylist of course.
	public ArrayList<Course> castBeansToCourses(ArrayList<Bean> allReceivedBeans) {
		ArrayList<Course> arrayOfCourses = new ArrayList<Course>();

		for(Bean bean : allReceivedBeans) {
			arrayOfCourses.add((Course) bean);
		}

		return arrayOfCourses;
	}

	/*
	 * Filter takes a string and an arraylist from a list,and filters the same
	 * arrylist returning a bean.
	 */
	private ArrayList<Bean> getFilteredList(String filter,
			ArrayList<? extends Bean> allReceivedBeans) {

		ArrayList<Bean> arrayOfBeans = new ArrayList<Bean>();

		for(Bean bean : allReceivedBeans) {
			if (bean.toString().toLowerCase().startsWith(filter.toLowerCase())) {
				arrayOfBeans.add(bean);
			}
		}

		return arrayOfBeans;
	}

	@Override
	public boolean onQueryTextSubmit(String arg0) {
		return false;
	}

}
