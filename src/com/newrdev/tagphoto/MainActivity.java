package com.newrdev.tagphoto;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;

public class MainActivity extends Activity{
	final static String CAMERA_FRAG = "Camera";
	final static String BROWSE_FRAG = "Browser";
	
	private String[] _drawerItems;
	private CharSequence _actionBarTitle;
	private ListView _drawerList;
	private DrawerLayout _drawerLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		_drawerItems = getResources().getStringArray(R.array.drawer_items);
		_drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
		_drawerList = (ListView)findViewById(R.id.left_drawer);
		
		_drawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_item, _drawerItems));
		
		DrawerItemClickListener listener = new DrawerItemClickListener();
		_drawerList.setOnItemClickListener(listener);
		
		if (savedInstanceState == null) {
			listener.selectItem(0);
		}
	}
	
	private class DrawerItemClickListener implements ListView.OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			selectItem(position);
		}
		
		private void selectItem(int position){
			// Highlight the selected item, update the title, and close the drawer
		    _drawerList.setItemChecked(position, true);
		    setTitle(_drawerItems[position]);
		    _drawerLayout.closeDrawer(_drawerList);
		    
		    FragmentManager fragMngr = getFragmentManager();
			FragmentTransaction xact = fragMngr.beginTransaction();
			switch(position){
			case 0:
				if(fragMngr.findFragmentByTag(CAMERA_FRAG) == null){
					xact.replace(R.id.content, new CameraFragment(), CAMERA_FRAG);
				}
				break;
				
			case 1:
				if(fragMngr.findFragmentByTag(BROWSE_FRAG) == null){
					xact.replace(R.id.content, new BrowseFragment(), BROWSE_FRAG);
				}
				break;
			}
			xact.commit();
		}
	}
	
	@Override
    public boolean onPrepareOptionsMenu(Menu menu) {
		return false;
	}
	
	public void setTitle(CharSequence title){
		_actionBarTitle = title;
		this.getActionBar().setTitle(this._actionBarTitle);
	}
}
