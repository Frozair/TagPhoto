package com.newrdev.tagphoto;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class DetailsActivity extends Activity implements OnClickListener{
	private PhotoManager _manager;
	private Photo _photo;
	private boolean _saveActivity = false;
	private EditText _title;
	private EditText _desc;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		
		_manager = new PhotoManager(this.getApplicationContext());
		_title = (EditText)findViewById(R.id.title);
		_desc = (EditText)findViewById(R.id.description);
		
		//Get photo id
		Bundle extras = getIntent().getExtras();
		if(extras.getBoolean("SAVED"))
			_saveActivity = true;
		
		this._photo = this._manager.getPhotoById(extras.getLong("PHOTO_ID"));
		_title.setText(_photo.getTitle());
		_desc.setText(_photo.getDescription());

		Bitmap myBitmap = BitmapFactory.decodeFile(this._photo.getPath());
	    ImageView myImage = (ImageView)findViewById(R.id.preview_image);
	    myImage.setImageBitmap(myBitmap);
	    myImage.setRotation(90);
		
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
	public void onClick(View view) {
		switch(view.getId()){
		case R.id.back:
			// Finish the activity and go back
			finish();
			break;
		case R.id.save:
			this._manager.update(this._photo.getId(), MyDB.PHOTOS_TITLE, this._title.getText().toString());
			this._manager.update(this._photo.getId(), MyDB.PHOTOS_DESC, this._desc.getText().toString());

			Toast.makeText(this, "Photo information has been updated.", Toast.LENGTH_SHORT).show();
			
			if(_saveActivity){
				Intent intent = new Intent(this, MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
			break;
		}
		
	}
}
