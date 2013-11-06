package com.newrdev.tagphoto;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

public class CameraFragment extends Fragment{
	private Preview _preview;
	private Camera _camera;
	private Context _context;
	
	public CameraFragment(){
		//For fragments, we do nothing in constructor
	}
	
	 @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_camera, container, false); // False so that the container can handle the fragment layout correctly
		 
		_context = this.getActivity();
		if(safeCameraOpen()){
			_preview = new Preview(_context, _camera);
			FrameLayout frame = (FrameLayout)view.findViewById(R.id.preview);
			frame.addView(_preview);
			
			Button button = (Button)view.findViewById(R.id.capture);
			button.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View v) {
					_camera.takePicture(null, null, MyJPGCallback);			
				}
				
			});
		}
		
		return view; 
	 }
	
	
	private PictureCallback MyJPGCallback = new PictureCallback(){

		@Override
		public void onPictureTaken(byte[] data, Camera camera) {	
			File pictureFile = FileManager.getOutputMediaFile("temp.jpg");
			if(pictureFile == null){
				Toast.makeText(_context, "Failed to save file.", Toast.LENGTH_SHORT).show();
				return;
			}
			
			try{
				FileOutputStream fos = new FileOutputStream(pictureFile);
				fos.write(data);
				fos.close();
			} catch (FileNotFoundException e) {
	        } catch (IOException e) {
	        }
			
			Intent intent = new Intent(_context, SaveActivity.class);
			startActivity(intent);
		}
		
	};

	private boolean safeCameraOpen(){
		boolean open = false;
		try{	
			releaseCameraAndPreview();
			_camera = Camera.open();
			open = (_camera != null);
		}catch(Exception e){
		}
		return open;
	}
	
	private void releaseCameraAndPreview(){
		if(_preview != null)
			_preview.setCamera(null);
		if (_camera != null) {
	        _camera.release();
	        _camera = null;
		}
	}
	
	@Override
	public void onResume() {
        super.onResume();
        if(_camera == null && safeCameraOpen())
			_preview.setCamera(_camera);
    }
	
	@Override
	public void onPause() {
        super.onPause();
        releaseCameraAndPreview();
    }
}
