package com.gzfgeh.imagetool;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageTool {
	public static Bitmap setSDImageView(String filePath){
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 1;
		Bitmap bm = BitmapFactory.decodeFile(filePath, options);
		return bm;
	}
}
