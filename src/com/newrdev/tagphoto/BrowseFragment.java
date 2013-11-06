package com.newrdev.tagphoto;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class BrowseFragment extends Fragment implements OnItemClickListener{
	private PhotosAdapter _adapter;
	private Context _context;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		_context = this.getActivity();
		
		View view = inflater.inflate(R.layout.activity_browse, container, false); // False so that the container can handle the fragment layout correctly
		ListView list = (ListView)view.findViewById(R.id.list);
		PhotoManager manager = new PhotoManager(_context);
		this._adapter = new PhotosAdapter(_context, manager.getAll());
	    list.setAdapter(_adapter);
	    list.setOnItemClickListener(this);
	    
		return view;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Photo photo =  this._adapter.getPhoto(position);
		Intent intent = new Intent(_context, DetailsActivity.class);
		intent.putExtra("PHOTO_ID", photo.getId());
		startActivity(intent);
	}
}
