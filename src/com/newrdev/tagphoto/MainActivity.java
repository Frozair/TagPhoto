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
import android.content.Intent;

public class MainActivity extends Activity{
	private String[] _drawerItems;
	private CharSequence _actionBarTitle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		_drawerItems = getResources().getStringArray(R.array.drawer_items);
		DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
		ListView drawerList = (ListView)findViewById(R.id.left_drawer);
		
		drawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_item, _drawerItems));
		drawerList.setOnItemClickListener(new DrawerItemClickListener());
	}
	
	private class DrawerItemClickListener implements ListView.OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			selectItem(position);
		}
		
		private void selectItem(int position){
			Intent intent = null;
			if(_drawerItems[position].contains("Camera")){
				intent = new Intent(getApplicationContext(), CameraActivity.class);
			}else{
				intent = new Intent(getApplicationContext(), BrowseActivity.class);
			}
			startActivity(intent);
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
