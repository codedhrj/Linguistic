package com.example.sidemenu;

import java.util.ArrayList;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;


import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.support.v4.view.GravityCompat;
import android.text.Html;

public class MainActivity extends SherlockFragmentActivity {
	// Declare Variable
	DrawerLayout mDrawerLayout;
	ListView mDrawerList;
	ActionBarDrawerToggle mDrawerToggle;
	MenuListAdapter mMenuAdapter;
	String[] title;
	String[] subtitle;
databasehelper db;
ProductTable model1;
int counter;
	int[] icon;
	// Fragmensts Name

	//
	Fragment fragment1 = new Fragment1();
	Fragment fragment2 = new Fragment2();
	Fragment fragment3 = new Fragment3();
	Fragment fragment4 = new Fragment4();
	Fragment fragment5 = new Fragment5();

	AlertDialog alertDialog = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.drawer_main);
		
		counter=getItem();
        if (counter>0&&counter!=20000) {
     		
     			
     			
     			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
     			 
     			// set title
     			alertDialogBuilder.setTitle("Subscription");
     			
     			alertDialogBuilder
     			
     				.setMessage("Please Upgrade your app")
     				.setCancelable(true)
     				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
     					
     					@Override
     					public void onClick(DialogInterface dialog, int which) {
     						// TODO Auto-generated method stub
     					//MainActivity.this.finish();
     						Intent intent1 = new Intent(getApplicationContext(),
         							PaypalActivity.class);
         					startActivity(intent1);
         
     						
     					}
     				})
     				.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
     					
     					@Override
     					public void onClick(DialogInterface dialog, int which) {
     						alertDialog.dismiss();
     					}
     				});
     			// create alert dialog
     			 alertDialog = alertDialogBuilder.create();
     			alertDialog.show();
     			
     		}
        
		// Generate title
		title = new String[] { "Home", "Subscription", "Help", "Settings",
				"About Us" };

		// Generate subtitle
		subtitle = new String[] { "Translate", "Pay using PayPal",
				"Help youself", "set default language", "know us ?" };

		// Generate icon
		icon = new int[] { R.drawable.action_about, R.drawable.action_settings,
				R.drawable.collections_cloud, R.drawable.about_author,
				R.drawable.book, R.drawable.key_notes };

		// Locate DrawerLayout in drawer_main.xml
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		// Locate ListView in drawer_main.xml
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		// Set a custom shadow that overlays the main content when the drawer
		// opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);

		// Pass results to MenuListAdapter Class
		mMenuAdapter = new MenuListAdapter(this, title, subtitle, icon);

		// Set the MenuListAdapter to the ListView
		mDrawerList.setAdapter(mMenuAdapter);

		// Capture button clicks on side menu
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		// Enable ActionBar app icon to behave as action to toggle nav drawer
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		// Set Action color
		getSupportActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#2F302B")));
		getSupportActionBar().setTitle(
				Html.fromHtml("<font color='#ffffff'>App Name</font>"));

		// ActionBarDrawerToggle ties together the the proper interactions
		// between the sliding drawer and the action bar app icon
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {

			public void onDrawerClosed(View view) {
				// TODO Auto-generated method stub
				getSupportActionBar().setTitle(
						Html.fromHtml("<font color='#ffffff'>Linguistic</font>"));
				super.onDrawerClosed(view);
			}

			public void onDrawerOpened(View drawerView) {
				// TODO Auto-generated method stub
				getSupportActionBar().setTitle(
						Html.fromHtml("<font color='#ffffff'>Slider Menu</font>"));
				super.onDrawerOpened(drawerView);
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			selectItem(0);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.main, menu);
		return true;

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == android.R.id.home) {

			if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
				mDrawerLayout.closeDrawer(mDrawerList);
			} else {
				mDrawerLayout.openDrawer(mDrawerList);
			}
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		this.finish();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				MainActivity.this);

		// set title
		alertDialogBuilder.setTitle("Exit Application");

		alertDialogBuilder
				.setMessage(" Do you want to exit ?? ")
				.setCancelable(true)
				.setPositiveButton("Exit",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								MainActivity.this.finish();
							}
						})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								alertDialog.dismiss();
							}
						});
		// create alert dialog
		alertDialog = alertDialogBuilder.create();
		alertDialog.show();
		// set dialog message

	}

	// The click listener for ListView in the navigation drawer
	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position);
		}
	}
	 public int getItem() {
			Object tb = null;
			db = new databasehelper(getApplicationContext());
			db.getWritableDatabase();
			model1 = new ProductTable();

			db.getproducts();
			Log.d("amama", "amaj");
			// Stack st=new Stack();
			ArrayList al = new ArrayList();

			al = db.getproducts();
			int size = al.size();
			if (size == 0)
				tb = 0;
			else
				tb = al.get(size - 1);
			int tb1 = (Integer) tb;
			counter = tb1;
			
			Log.d("ckckc", String.valueOf(counter));
			
			return counter;
			
		}
	private void selectItem(int position) {

		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		// Locate Position
		switch (position) {
		case 0:
			ft.replace(R.id.content_frame, fragment1);
			
			break;
		case 1:
			ft.replace(R.id.content_frame, fragment2);
			
			break;
		case 2:
			ft.replace(R.id.content_frame, fragment3);
			break;
		case 3:
			ft.replace(R.id.content_frame, fragment4);
			break;
		case 4:
			ft.replace(R.id.content_frame, fragment5);
			break;
		case 5:
			ft.replace(R.id.content_frame, fragment5);
			break;
		}
		ft.commit();
		mDrawerList.setItemChecked(position, true);
		// Close drawer
		mDrawerLayout.closeDrawer(mDrawerList);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggles
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	protected void onActivityResult(int request, int result, Intent data) {
		super.onActivityResult(request, result, data);
	}
	 
}
