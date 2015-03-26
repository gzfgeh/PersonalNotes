package com.gzfgeh.myapplication;

import java.io.File;

import android.app.Application;
import android.os.Environment;

public class MyApplication extends Application {
	private static final String FILE_PATH = Environment.getExternalStorageDirectory() + 
			File.separator + "PersonalNote" + File.separator;
	private static final String FILE_NAME = "image.jpg";
	private File outputFile;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		File fold = new File(FILE_PATH);
		if (!fold.exists())
			fold.mkdirs();
		
		outputFile = new File(FILE_PATH + FILE_NAME);
	}
	
	public File getOutputFile() {
		return outputFile;
	}
}
