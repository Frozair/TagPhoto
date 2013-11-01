package com.newrdev.tagphoto;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Preview extends SurfaceView implements SurfaceHolder.Callback {
	private SurfaceHolder _holder;
	private Camera _camera;

	public Preview(Context context, Camera camera) {
		super(context);
		_camera = camera;
		
		_holder = getHolder();
		_holder.addCallback(this);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		try{
			_camera.setPreviewDisplay(_holder);	
			_camera.setDisplayOrientation(90);
			_camera.startPreview();
		}catch(IOException e){
			Log.d("MyDB", "Error setting camera preview: " + e.getMessage());
		}		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {		
	}

}
