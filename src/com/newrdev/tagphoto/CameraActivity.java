package com.newrdev.tagphoto;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceView;
import android.widget.FrameLayout;

public class CameraActivity extends Activity{
	private Preview _preview;
	private Camera _camera;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);
		
		_camera = getCameraInstance();
		
		_preview = new Preview(this, _camera);
		FrameLayout frame = (FrameLayout)findViewById(R.id.preview);
		frame.addView(_preview);
	}

	public static Camera getCameraInstance(){
		Camera c = null;
		try{
			c = Camera.open();
		}catch(Exception e){
		}
		return c;
	}
}
