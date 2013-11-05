package com.newrdev.tagphoto;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PhotosAdapter extends ArrayAdapter<Photo> {
  private final Context _context;
  private final Photo[] _photos;

  public PhotosAdapter(Context context, Photo[] values) {
    super(context, R.layout.row_layout, values);
    this._context = context;
    this._photos = values;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    LayoutInflater inflater = (LayoutInflater) _context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View rowView = inflater.inflate(R.layout.row_layout, parent, false);
    
    TextView textView = (TextView) rowView.findViewById(R.id.title);
    ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
    //Set image
    Bitmap myBitmap = BitmapFactory.decodeFile(this._photos[position].getPath());
    imageView.setImageBitmap(myBitmap);
    imageView.setRotation(90);
    //Set text
    textView.setText(this._photos[position].getTitle());
    
    return rowView;
  }
  
  public Photo getPhoto(int position){
	  return this._photos[position];
  }
} 
