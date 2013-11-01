package com.newrdev.tagphoto;

import java.io.IOException;
import android.content.Context;
import android.hardware.Camera;
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
		System.out.println("format is: " + format + " (w, h): (" + w + ", " + h + ")");
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		try{
			System.out.println("Camera null? " + (_camera == null));
			_camera.setPreviewDisplay(_holder);	
			_camera.setDisplayOrientation(90);
			_camera.startPreview();
		}catch(IOException e){
			e.printStackTrace();
		}		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {	
		if(_camera != null){
			_camera.stopPreview();
		}
	}

	public void setCamera(Camera camera) {
		System.out.println("Setting camera, is null? " + (camera == null));
		if(_camera != null) _camera.release();
		_camera = camera;
	}
}
