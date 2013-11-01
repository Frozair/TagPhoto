package com.newrdev.tagphoto;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;


public class PhotoManager {
	private MyDB _myDB;
	
	public PhotoManager(Context context){
		_myDB = new MyDB(context);
		try{
			_myDB.open();
		}catch(SQLException e){
			Log.d("MyDB", "Failed to open DB");
		}
		Log.i("MyDB", "Created photomanger object");
	}
	
	public void release(){
		Log.i("MyDB", "Releasing photomanger object");
		_myDB.close();
	}
	
	public Photo insert(String title, String desc, String path){
		Photo photo = new Photo(title, desc, path);
		
		ContentValues values = new ContentValues();
		values.put(MyDB.PHOTOS_TITLE, title);
		values.put(MyDB.PHOTOS_DESC, desc);
		values.put(MyDB.PHOTOS_PATH, path);
		
		long id = this._myDB.insert(values, MyDB.PHOTOS_TABLE);
		photo.setId(id);
		
		return photo;
	}
	
	public Photo getPhotoById(long id){
		Cursor cursor = this._myDB.query("SELECT * FROM "+MyDB.PHOTOS_TABLE+" WHERE _id = ?", new String[] {String.valueOf(id)});
		cursor.moveToFirst();
		return cursorToPhoto(cursor);
	}
	
	private Photo cursorToPhoto(Cursor cursor){
		Photo photo = new Photo();
		photo.setId(cursor.getLong(0));
		photo.setTitle(cursor.getString(1));
		photo.setDescription(cursor.getString(2));
		photo.setPath(cursor.getString(3));
		return photo;
	}
}
