package com.newrdev.tagphoto;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

public class CameraActivity extends Activity{
	private Preview _preview;
	private Camera _camera;
	private static File _mediaStorageDir;
	private Context _context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);
		_context = this;
		
		if(safeCameraOpen()){
			_preview = new Preview(this, _camera);
			FrameLayout frame = (FrameLayout)findViewById(R.id.preview);
			frame.addView(_preview);
			
			Button button = (Button)findViewById(R.id.capture);
			button.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View v) {
					_camera.takePicture(null, null, MyJPGCallback);			
				}
				
			});
		}
	}
	
	private PictureCallback MyJPGCallback = new PictureCallback(){

		@Override
		public void onPictureTaken(byte[] data, Camera camera) {		
			
			
			File pictureFile = getOutputMediaFile("temp.jpg");
			if(pictureFile == null){
				Toast.makeText(getApplicationContext(), "Failed to save file.", Toast.LENGTH_SHORT).show();
				return;
			}
			
			try{
				FileOutputStream fos = new FileOutputStream(pictureFile);
				fos.write(data);
				fos.close();
			} catch (FileNotFoundException e) {
	            System.out.println("File not found: " + e.getMessage());
	        } catch (IOException e) {
	            System.out.println("Error accessing file: " + e.getMessage());
	        }
			
			Intent intent = new Intent(_context, SaveActivity.class);
			startActivity(intent);
			
			//Uri path = Uri.fromFile(pictureFile);
			//Photo photo = _manager.insert("My new photo", "", path.toString());
			//Photo other = _manager.getPhotoById(photo.getId());
			
			//System.out.println("My photo id is: " + other.getId() + " path: " + other.getPath() + " and title: " + other.getTitle());
		}
		
	};
	
	private static File getOutputMediaFile(String name){
		 _mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "TagPhoto");
		// Create the storage directory if it does not exist
	    if (!_mediaStorageDir.exists()){
	        if (!_mediaStorageDir.mkdirs()){
	            return null;
	        }
	    }
	    return new File(_mediaStorageDir.getPath() + File.separator + name); 
	}

	private boolean safeCameraOpen(){
		boolean open = false;
		try{	
			System.out.println("Opening camera");
			releaseCameraAndPreview();
			_camera = Camera.open();
			open = (_camera != null);
		}catch(Exception e){
			Log.i("MyDB", "Exception thrown: " + e.getMessage());
		}
		Log.i("MyDB", "Camera safe open? " + open);
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
    protected void onResume() {
        super.onResume();
        if(_camera == null && safeCameraOpen())
			_preview.setCamera(_camera);
    }
	
	@Override
    protected void onPause() {
        super.onPause();
        releaseCameraAndPreview();
    }
}
