package com.newrdev.tagphoto;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.app.Activity;
import android.content.Intent;

public class MainActivity extends Activity implements OnClickListener {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button b1 = (Button) findViewById(R.id.camera);
		Button b2 = (Button) findViewById(R.id.view);
		b1.setOnClickListener(this);
		b2.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		Intent intent = null;
		switch(view.getId()){
		case R.id.camera:
			intent = new Intent(getApplicationContext(), CameraActivity.class);
			break;
		case R.id.view:
			intent = new Intent(getApplicationContext(), BrowseActivity.class);
			break;
		}
		startActivity(intent);
	}
}
