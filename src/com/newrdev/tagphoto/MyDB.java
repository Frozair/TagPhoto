package com.newrdev.tagphoto;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDB {
	// Tables
	public static final String PHOTOS_TABLE = "photos";
	
	// Table Columns
	public static final String PHOTOS_ID = "_id";
    public static final String PHOTOS_TITLE = "title";  
    public static final String PHOTOS_DESC = "description";
    public static final String PHOTOS_PATH = "path";
      
    // Database Info
    private static final String DATABASE_NAME = "test.db";    
    private static final int DATABASE_VERSION = 1;

    // Create table statements
    private static final String DATABASE_CREATE_Photos = 
    	"create table " + PHOTOS_TABLE + 
    	"(" + PHOTOS_ID + " integer primary key autoincrement, " + PHOTOS_TITLE + " text, " + PHOTOS_DESC + " text, " + PHOTOS_PATH + ")";

    private Context _context;
    private DatabaseHelper _dbHelper;
    private SQLiteDatabase _db;

    public MyDB(Context context) 
    {
        this._context = context;
        _dbHelper = new DatabaseHelper(_context);
    }

    private MyDB open() throws SQLException 
    {
        _db = _dbHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---    
    public void close() 
    {
        _dbHelper.close();
    }

    //rawQuery doesn't actually execute until a call is made on the returned cursor (example: cursor.moveToFirst()). 
    public Cursor query(String query, String[] args){
    	try{
			open();
		}catch(SQLException e){
			Log.d("MyDB", "Failed to open DB");
		}
    	return this._db.rawQuery(query, args);
    }

    public long insert(ContentValues initialValues,String tablename) 
    {
    	try{
			open();
		}catch(SQLException e){
			Log.d("MyDB", "Failed to open DB");
		}
    	long id = _db.insert(tablename, null, initialValues);
    	close();
    	
        return id;
    }
    
    private static class DatabaseHelper extends SQLiteOpenHelper {
    	
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db){
        	 Log.i("MyDB", "Creating table statement: " + DATABASE_CREATE_Photos);
            db.execSQL(DATABASE_CREATE_Photos);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            Log.i("MyDB", "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + PHOTOS_TABLE);
            onCreate(db);
        }
    }
}
