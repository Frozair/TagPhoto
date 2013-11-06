package com.newrdev.tagphoto;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;


public class PhotoManager {
	private MyDB _myDB;
	
	public PhotoManager(Context context){
		_myDB = new MyDB(context);
		
		Log.i("MyDB", "Created photomanger object");
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
	
	public void update(long id, String column, String value){
		Cursor cursor = this._myDB.query("UPDATE " + MyDB.PHOTOS_TABLE + " SET " + column + 
						" = ? WHERE _id = " + id, new String[] {value});
		
		
		cursor.moveToFirst();
		//cursorToPhoto(cursor);
		this._myDB.close();
	}
	
	public Photo[] getAll(){
		Cursor cursor = this._myDB.query("SELECT * FROM "+MyDB.PHOTOS_TABLE, null);
		cursor.moveToFirst();
		
		ArrayList<Photo> photos = new ArrayList<Photo>();
		while (cursor.isAfterLast() == false){
		    photos.add(cursorToPhoto(cursor));
		    cursor.moveToNext();
		}
		
		return photos.toArray(new Photo[photos.size()]);
	}
	
	public Photo getPhotoById(long id){
		Cursor cursor = this._myDB.query("SELECT * FROM "+MyDB.PHOTOS_TABLE+" WHERE _id = ?", new String[] {Long.toString(id)});
		cursor.moveToFirst();
		Photo photo = cursorToPhoto(cursor);
		this._myDB.close();
		
		return photo;
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
