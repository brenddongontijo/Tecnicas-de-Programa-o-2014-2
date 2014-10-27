package unb.mdsgpp.qualcurso;

import models.Course;
import models.Institution;
import models.Search;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;
import android.text.Html;


public class MainActivity extends ActionBarActivity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks, BeanListCallbacks, OnQueryTextListener {

	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;
	
	public static String DRAWER_POSITION = "drawerPosition";
	public static String CURRENT_TITLE = "currentTitle";
	private int drawerPosition = 10;
	private SearchView mSearchView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		checksSavedInstanceState(savedInstanceState);
		
		mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
				.findFragmentById(R.id.navigation_drawer);

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));		
	}
	
	/**
	 * This method checks if already exist a saved instance and set positions 
	 * the drawer position and title menu name. 
	 * 
	 * @param savedInstanceState
	 */
	private void checksSavedInstanceState(Bundle savedInstanceState) {
		if(savedInstanceState != null){
			drawerPosition = savedInstanceState.getInt(DRAWER_POSITION);
			mTitle = savedInstanceState.getCharSequence(CURRENT_TITLE);
		}
		else {
			mTitle = getFormatedTitle(getTitle());
		}
	}
	
	public CharSequence getFormatedTitle(CharSequence titleName){
		int actionBarTitleColor = getResources().getColor(R.color.actionbar_title_color);
		
		CharSequence newTitleName = Html.fromHtml("<font color='#"+Integer.
				toHexString(actionBarTitleColor).substring(2)+"'><b>"+titleName+"</b></font>");
		
		return newTitleName;
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putInt(DRAWER_POSITION, drawerPosition);
		outState.putCharSequence(CURRENT_TITLE, mTitle);
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onNavigationDrawerItemSelected(int drawerPosition) {
		
		// update the main content by replacing fragments
		FragmentManager fragmentManager = getSupportFragmentManager();
		
		ActionBar actionBar = getSupportActionBar();
		Fragment fragment = setFragmentOfDrawerPosition(drawerPosition);
		
		if(fragment != null){
			String formatedTitle = updatesMenuName(drawerPosition);
			
			actionBar.setTitle(getFormatedTitle(formatedTitle));
			
			mTitle = getFormatedTitle(formatedTitle);
			
			if(fragment instanceof TabsFragment){
				fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
				
				fragmentManager.beginTransaction().replace(R.id.container,fragment).commit();
			}
			else {
				fragmentManager.beginTransaction().replace(R.id.container,fragment).addToBackStack(null).commit();
			}
		}
		else{
			
		}
	}
	
	/**
	 * This method change the corresponding fragment to the position of the drawer.
	 * 
	 * @param choosenOption
	 * @param formatedTitle
	 * @return
	 */
	private Fragment setFragmentOfDrawerPosition(final int choosenOption){
		
		Fragment fragment = null;

		final int evaluations = 0;
		final int search = 1;
		final int ranking = 2;
		final int listHistory = 3;
		final int compareInstitutions = 4;
		
		switch(choosenOption) {
			case evaluations:
				fragment = new TabsFragment();
				drawerPosition = 0;
				break;
			case search:
				fragment = new SearchByIndicatorFragment();
				drawerPosition = 1;
				break;
			case ranking:
				fragment = new RankingFragment();
				drawerPosition = 2;
				break;
			case listHistory:
				fragment = new HistoryFragment();
				drawerPosition = 3;
				break;
			case compareInstitutions:
				fragment = new CompareChooseFragment();
				drawerPosition = 4;
				break;
			default:
				fragment = null;
				break;
		}
		
		return fragment;
	}

	/**
	 * This function change the name of menu based on drawer position selected.
	 * 
	 * @param choosenOption				drawer position.
	 * @return							name of corresponding drawer. 
	 */
	private String updatesMenuName(final int choosenOption){
		String formatedTitle = "";
		
		final int evaluations = 0;
		final int search = 1;
		final int ranking = 2;
		final int listHistory = 3;
		final int compareInstitutions = 4;
		
		switch(choosenOption) {
			case evaluations:
				formatedTitle = getString(R.string.title_section1);
				break;
			case search:
				formatedTitle = getString(R.string.title_section2);
				break;
			case ranking:
				formatedTitle = getString(R.string.title_section3);
				break;
			case listHistory:
				formatedTitle = getString(R.string.title_section4);
				break;
			case compareInstitutions:
				formatedTitle = getString(R.string.title_section5);
				break;
			default:
				break;
		}
		
		return formatedTitle;
	}
	
	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if(!mNavigationDrawerFragment.isDrawerOpen()) {
			
			/* Only show items in the action bar relevant to this screen if the drawer is not showing. 
			 * Otherwise, let the drawer decide what to show in the action bar.
			 */
			getMenuInflater().inflate(R.menu.main, menu);
			restoreActionBar();
			
			return true;
		}
		else{
			
		}
		return super.onCreateOptionsMenu(menu);
	}
	
	private void setupSearchView(final MenuItem searchItem){
		searchItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM
				|MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
		mSearchView.setOnQueryTextListener(this);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		/* 
		 * Handle action bar item clicks here. The action bar will automatically handle clicks 
		 * on the Home/Up button, so long as you specify a parent activity in AndroidManifest.xml.
		 */
		switch(item.getItemId()) {
			case R.id.action_about:
				aboutApplication();
				return true;
			case R.id.action_exit:
				closeApplication();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void closeApplication() {
		finish();
		System.exit(1);
	}
	
	private void aboutApplication() {
		onBeanListItemSelected(AboutFragment.newInstance());
	}

	@Override
	public void onBeanListItemSelected(Fragment fragment) {
		// update the main content by replacing fragments
		FragmentManager fragmentManager = getSupportFragmentManager();

		fragmentManager.beginTransaction().replace(R.id.container,
				fragment).addToBackStack(null).commit();
	}

	@Override
	public void onBeanListItemSelected(Fragment fragment, int container) {
		FragmentManager fragmentManager = getSupportFragmentManager();

		fragmentManager.beginTransaction().replace(container,
			fragment).commit();
	}

	@Override
	public void onSearchBeanSelected(Search search, Parcelable bean) {
		if(bean instanceof Institution){
			onBeanListItemSelected(CourseListFragment.newInstance(((Institution)bean).getId(),
					search.getYear(),
					Institution.getCoursesByEvaluationFilter(((Institution)bean).getId(),search)));
		}
		else if(bean instanceof Course){
			onBeanListItemSelected(InstitutionListFragment.newInstance(((Course)bean).getId(),
					search.getYear(),
					Course.getInstitutionsByEvaluationFilter(((Course)bean).getId(),search)));
		}		
	}

	@Override
	public boolean onQueryTextChange(String arg0) {
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}
}
