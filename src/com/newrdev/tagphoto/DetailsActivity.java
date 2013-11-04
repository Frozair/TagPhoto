package com.newrdev.tagphoto;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.FrameLayout.LayoutParams;

public class DetailsActivity extends Activity implements OnClickListener{
	private PhotoManager _manager;
	private Photo _photo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		
		_manager = new PhotoManager(this.getApplicationContext());
		//Get photo id
		Bundle extras = getIntent().getExtras();
		this._photo = this._manager.getPhotoById(extras.getLong("PHOTO_ID"));

		Bitmap myBitmap = BitmapFactory.decodeFile(this._photo.getPath());
	    ImageView myImage = new ImageView(this);
	    myImage.setImageBitmap(myBitmap);
	    myImage.setRotation(90);
	    myImage.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	    
		FrameLayout frame = (FrameLayout)findViewById(R.id.preview);
		frame.addView(myImage);	
		
		// Set onclick for buttons
		Button b1 = (Button)findViewById(R.id.back);
		Button b2 = (Button)findViewById(R.id.save);
		
		b1.setOnClickListener(this);
		b2.setOnClickListener(this);
	}
	
	@Override
	protected void onStart() {
	    super.onStart();
	    if(_manager == null)
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
		case R.id.back:
			// Finish the activity and go back
			finish();
			break;
		case R.id.save:
			EditText title = (EditText)findViewById(R.id.title);
			EditText desc = (EditText)findViewById(R.id.description);
			
			long id = this._photo.getId();
			
			_photo = this._manager.update(id, MyDB.PHOTOS_TITLE, title.getText().toString());
			_photo = this._manager.update(id, MyDB.PHOTOS_DESC, desc.getText().toString());

			
			System.out.println("Photo info -- " + this._photo.toString());
			Toast.makeText(this, "Photo information has been updated.", Toast.LENGTH_SHORT).show();
			break;
		}
		
	}
}
