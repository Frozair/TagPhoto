package com.newrdev.tagphoto;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.app.Activity;
import android.content.Intent;

public class MainActivity extends Activity {
	private PhotoManager _manager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button button = (Button) findViewById(R.id.camera);
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), CameraPreviewActivity.class);
				startActivity(intent);
			}
		});
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
