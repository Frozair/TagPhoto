package com.newrdev.tagphoto;

import java.io.File;

import android.os.Environment;

public class FileManager {

	public static File getOutputMediaFile(String name){
		 File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "TagPhoto");
		// Create the storage directory if it does not exist
	    if (!mediaStorageDir.exists()){
	        if (!mediaStorageDir.mkdirs()){
	            return null;
	        }
	    }
	    return new File(mediaStorageDir.getPath() + File.separator + name); 
	}
}
