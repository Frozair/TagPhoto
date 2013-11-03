package com.newrdev.tagphoto;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;

public class SaveActivity extends Activity implements OnClickListener{
	private PhotoManager _manager;
	private FrameLayout _frame;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_save);
		
		File imgFile = FileManager.getOutputMediaFile("temp.jpg");
		if(imgFile.exists()){
		    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

		    ImageView myImage = new ImageView(this);
		    myImage.setImageBitmap(myBitmap);
		    myImage.setRotation(90);
		    myImage.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		    
		    _frame = (FrameLayout)findViewById(R.id.preview);
			_frame.addView(myImage);	
		}
		
		Button b1 = (Button)findViewById(R.id.cancel);
		Button b2 = (Button)findViewById(R.id.use);
		
		b1.setOnClickListener(this);
		b2.setOnClickListener(this);
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

	@Override
	public void onClick(View view) {
		switch(view.getId()){
		case R.id.cancel:
			// Finish the activity and go back
			finish();
			break;
		case R.id.use:
			String name = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
			name += "IMG_" + name + ".jpg";
			File pictureFile = FileManager.getOutputMediaFile("temp.jpg");
			File renameTo = FileManager.getOutputMediaFile(name);
			pictureFile.renameTo(renameTo);

			Photo photo = _manager.insert(null, null, Uri.fromFile(renameTo).getPath());
			System.out.println("Photo info -- " + photo.toString());
			
			Intent intent = new Intent(this, DetailsActivity.class);
			intent.putExtra("PHOTO_ID", photo.getId());
			startActivity(intent);
			break;
		}
		
	}
}
