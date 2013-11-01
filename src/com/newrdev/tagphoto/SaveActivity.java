package com.newrdev.tagphoto;

import android.app.Activity;
import android.os.Bundle;

public class SaveActivity extends Activity{
	private PhotoManager _manager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_save);
		
		//String name = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		//name += "IMG_" + name + ".jpg";
	}
	
	@Override
	protected void onStart() {
	    super.onStart();
	    _manager = new PhotoManager(this.getApplicationContext());
	}
	 
	@Override
	protected void onStop() {
	    super.onStop();
	    if(_manager != null)
	    	_manager.release();
	}
}
