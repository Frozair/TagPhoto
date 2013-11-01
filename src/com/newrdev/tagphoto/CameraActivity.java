package com.newrdev.tagphoto;

import android.app.Activity;
import android.os.Bundle;
import android.view.SurfaceView;

public class CameraActivity extends Activity{
	private SurfaceView view;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);
		
	}

}
