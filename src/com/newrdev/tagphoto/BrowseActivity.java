package com.newrdev.tagphoto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class BrowseActivity extends Activity implements OnItemClickListener{
	private PhotosAdapter _adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browse);
		
		ListView list = (ListView)findViewById(R.id.list);
		PhotoManager manager = new PhotoManager(this);
		this._adapter = new PhotosAdapter(this, manager.getAll());
	    list.setAdapter(_adapter);
	    list.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Photo photo =  this._adapter.getPhoto(position);
		Intent intent = new Intent(this, DetailsActivity.class);
		intent.putExtra("PHOTO_ID", photo.getId());
		startActivity(intent);
	}
}
