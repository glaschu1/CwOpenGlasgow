package com.Jamesglasgow.Cw.OpenGlasgow;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;

import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.Jamesglasgow.Cw.adapters.NavigationDrawerListAdapter;
import com.Jamesglasgow.Cw.models.Items;

/**
 * This activity will add Navigation Drawer for our application and all the code related to navigation drawer.
 * We are going to extend all our other activites from this BaseActivity so that every activity will have Navigation Drawer in it.
 * This activity layout contain one frame layout in which we will add our child activity layout.    
 */
public class BaseActivity extends Activity {


	/**
	 *  Frame layout: Which is going to be used as parent layout for child activity layout.
	 *  This layout is protected so that child activity can access this  
	 *  */
	protected FrameLayout frameLayout;

    /**
	 * ListView to add navigation drawer item in it.
	 * We have made it protected to access it in child class. We will just use it in child class to make item selected according to activity opened.  
	 */
	
	protected ListView mDrawerList;
	

	protected String[] listArray = { "Open Glasgow", "News & Weather", "RoadWorks", "Parking", "About", "Settings" };
	protected ArrayList<Items> _items;
	
	/**
	 * Static variable for selected item position. Which can be used in child activity to know which item is selected from the list.  
	 * */
	protected static int position;
	
	/**
	 *  This flag is used just to check that launcher activity is called first time
	 * */
	private static boolean isLaunch = true;
	

	private DrawerLayout mDrawerLayout;
	

	private ActionBarDrawerToggle actionBarDrawerToggle;


    FragmentManager fmAboutDialogue;

	public int Pos;

    /**
     *
     * his oncreates a items list that will be added to are nav drawers it then build the togoolls for the navigation bar
     */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.navigation_drawer_base_layout);
		
		frameLayout = (FrameLayout)findViewById(R.id.content_frame);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);


        fmAboutDialogue = this.getFragmentManager();
		// set a custom shadow that overlays the main content when the drawer opens
		//mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        
		_items = new ArrayList<Items>();
		_items.add(new Items("News & Weather", "About news and weather", R.drawable.ic_inbox_black_24dp));
		_items.add(new Items("RoadWorks", "Current Roadwork", R.drawable.ic_error_outline_black_24dp));
		_items.add(new Items("Parking", "Carpark spaces", R.drawable.carpark_img));
		_items.add(new Items("About", "About Popup", R.drawable.ic_favorite_black_24dp));
		_items.add(new Items("Settings", "Set map and postcode", R.drawable.ic_radio_button_checked_black_24dp));
		
		//Adding header on list view 
		View header = (View)getLayoutInflater().inflate(R.layout.list_view_header_layout, null);
		mDrawerList.addHeaderView(header);
		
		// set up the drawer's list view with items and click listener
		mDrawerList.setAdapter(new NavigationDrawerListAdapter(this, _items));
		mDrawerList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				openActivity(position);
			}
		});
		
		// enable ActionBar app icon to behave as action to toggle nav drawer
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		
		// ActionBarDrawerToggle ties together the the proper interactions between the sliding drawer and the action bar app icon
		actionBarDrawerToggle = new ActionBarDrawerToggle(
				this,						/* host Activity */
				mDrawerLayout, 				/* DrawerLayout object */
				R.drawable.carpark_img,     /* nav drawer image to replace 'Up' caret */
				R.string.open_drawer,       /* "open drawer" description for accessibility */
				R.string.close_drawer)      /* "close drawer" description for accessibility */ 
		{ 
			@Override
			public void onDrawerClosed(View drawerView) {
				getActionBar().setTitle(listArray[position]);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
				super.onDrawerClosed(drawerView);
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(getString(R.string.app_name));
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
				super.onDrawerOpened(drawerView);
			}

			@Override
			public void onDrawerSlide(View drawerView, float slideOffset) {
				super.onDrawerSlide(drawerView, slideOffset);
			}

			@Override
			public void onDrawerStateChanged(int newState) {
				super.onDrawerStateChanged(newState);
			}
		};
		mDrawerLayout.setDrawerListener(actionBarDrawerToggle);
		

		/**
		 * As we are calling BaseActivity from manifest file and this base activity is intended just to add navigation drawer in our app.
		 * We have to open some activity with layout on launch. So we are checking if this BaseActivity is called first time then we are opening our first activity.
		 * */
		if(isLaunch){

			isLaunch = false;
			openActivity(0);
		}
	}
	
	/**
	 * Launching activity when any list item is clicked. 
	 */
	protected void openActivity(int position) {
		
		/**
		 * We can set title & itemChecked here but as this BaseActivity is parent for other activity, 
		 * So whenever any activity is going to launch this BaseActivity is also going to be called and 
		 * it will reset this value because of initialization in onCreate method.
		 * So that we are setting this in child activity.    
		 */

		mDrawerLayout.closeDrawer(mDrawerList);
		BaseActivity.position = position; //Setting currently selected position in this field so that it will be available in our child activities. 
		
		switch (position) {
		case 0:
			startActivity(new Intent(this, HomeActivity.class));
			break;
		case 1:
			startActivity(new Intent(this, NewsActivity.class));

			break;
		case 2:
			startActivity(new Intent(this, RoadWorksActivity.class));
			break;
		case 3:
			startActivity(new Intent(this, ParkingActivity.class));
			break;
		case 4:
            DialogFragment mcAboutDlg = new AboutDialogue();
            mcAboutDlg.show(fmAboutDialogue, "menu");
			break;
		case 5:
			startActivity(new Intent(this, SettingsActivity.class));
			break;

		default:
			break;
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {


			getMenuInflater().inflate(R.menu.main, menu);
			return super.onCreateOptionsMenu(menu);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		// The action bar home/up action should open or close the drawer. 
		// ActionBarDrawerToggle will take care of this.
		if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

			return super.onOptionsItemSelected(item);
		}

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);





        return super.onPrepareOptionsMenu(menu);
    }

    
    /* We can override onBackPressed method to toggle navigation drawer*/
	@Override
	public void onBackPressed() {
		if(mDrawerLayout.isDrawerOpen(mDrawerList)){
			mDrawerLayout.closeDrawer(mDrawerList);
		}else {
			mDrawerLayout.openDrawer(mDrawerList);
		}
	}

}
