package com.newrdev.tagphoto;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class SaveActivity extends Activity implements OnClickListener{
	private PhotoManager _manager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_save);
		
		File imgFile = FileManager.getOutputMediaFile("temp.jpg");
		if(imgFile.exists()){
		    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

		    ImageView myImage = (ImageView)findViewById(R.id.preview_image);
		    myImage.setImageBitmap(myBitmap);
		    myImage.setRotation(90);
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
	public void onClick(View view) {
		switch(view.getId()){
		case R.id.cancel:
			// Finish the activity and go back
			finish();
			break;
		case R.id.use:
			String name = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
			name += "IMG_" + name + ".jpg";
			File pictureFile = FileManager.getOutputMediaFile("temp.jpg");
			File renameTo = FileManager.getOutputMediaFile(name);
			pictureFile.renameTo(renameTo);

			Photo photo = _manager.insert(null, null, Uri.fromFile(renameTo).getPath());
			
			Intent intent = new Intent(this, DetailsActivity.class);
			intent.putExtra("PHOTO_ID", photo.getId());
			intent.putExtra("SAVED", true);
			startActivity(intent);
			break;
		}
		
	}
}
