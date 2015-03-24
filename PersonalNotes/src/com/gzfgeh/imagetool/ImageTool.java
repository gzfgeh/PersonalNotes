package com.gzfgeh.imagetool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.gzfgeh.set.PersonalInfo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

public class ImageTool {
	
	private ImageTool(){
		throw new AssertionError();
	};
	
	public static Bitmap setSDImageView(String filePath){
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 1;
		Bitmap bm = BitmapFactory.decodeFile(filePath, options);
		return bm;
	}
	
	public static String getPhotoPathByLocalUri(Context context, Intent data) {
        Uri selectedImage = data.getData();
        String[] filePathColumn = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        return picturePath;
    }
	
	public static void startCrop(Activity activity, String imagePath,
			int requestCode) {
		File f = new File(imagePath);
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(Uri.fromFile(f), "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 200);
		intent.putExtra("outputY", 150);
		intent.putExtra("scale", true);
		intent.putExtra("return-data", true); // 设置true才能有返回
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imagePath);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true); // no face detection
		activity.startActivityForResult(intent, requestCode);
	}
	
	public static void saveBitmapToSDCard(Bitmap bitmap){
		File outputFile = new File(PersonalInfo.FILE_PATH + PersonalInfo.FILE_NAME);
		try {
			if (outputFile.exists())
				outputFile.delete();
			outputFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
        FileOutputStream fOut = null;
        try {
                fOut = new FileOutputStream(outputFile);
        } catch (FileNotFoundException e) {
                e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
        try {
                fOut.flush();
        } catch (IOException e) {
                e.printStackTrace();
        }
        try {
                fOut.close();
        } catch (IOException e) {
                e.printStackTrace();
        }
	}
}
