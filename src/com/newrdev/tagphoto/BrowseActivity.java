package com.newrdev.tagphoto;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class BrowseActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browse);
		
		ListView list = (ListView)findViewById(R.id.list);
		PhotoManager manager = new PhotoManager(this);
		PhotosAdapter adapter = new PhotosAdapter(this, manager.getAll());
	    list.setAdapter(adapter);
	}
}
