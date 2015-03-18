package com.gzfgeh.set;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.gzfgeh.personalnote.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

public class TransparentActivity extends Activity implements OnClickListener {
	private static final int GET_PHOTO_RESULT = 1;
	private static final int CROP_PHOTO = 2;
	private View cancleView;
	private View getPhotoView;
	private View makePhotoView;
	private Uri imageUri;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.transparent_activity);
        
        cancleView = findViewById(R.id.transparent_cancle);
        getPhotoView = findViewById(R.id.get_photo);
        makePhotoView = findViewById(R.id.make_photo);
        cancleView.setOnClickListener(this);
        getPhotoView.setOnClickListener(this);
        makePhotoView.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.transparent_cancle:
			finish();
			break;
			
		case R.id.get_photo:
			getPhoto();
			break;
			
		case R.id.make_photo:
			makePhoto();
			break;
			
		default:
			break;
		}
	}

	private void makePhoto() {
		// TODO Auto-generated method stub
		File outputImage = new File(Environment.getExternalStorageDirectory(),
				"output_image.jpg");
		try {
			if (outputImage.exists())
				outputImage.delete();
			outputImage.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		imageUri = Uri.fromFile(outputImage);
		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		startActivityForResult(intent, GET_PHOTO_RESULT);
	}

	private void getPhoto() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch (requestCode) {
		case GET_PHOTO_RESULT:
			if (resultCode == RESULT_OK){
				Intent intent = new Intent("com.android.camera.action.CROP");
				intent.setDataAndType(imageUri, "image/*");
				intent.putExtra("scale", true);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
				startActivityForResult(intent, CROP_PHOTO);
			}
			break;

		case CROP_PHOTO:
			if (resultCode == RESULT_OK){
				try {
					Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
					ByteArrayOutputStream baos=new ByteArrayOutputStream();  
					bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);  
					byte [] bitmapByte =baos.toByteArray(); 
					Intent intent = new Intent();
					intent.putExtra("Bitmap", bitmapByte);
					setResult(RESULT_OK, intent);
					finish();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		default:
			break;
		}
	}
	
	
}
