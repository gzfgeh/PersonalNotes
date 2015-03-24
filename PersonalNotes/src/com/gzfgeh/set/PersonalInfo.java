package com.gzfgeh.set;

import java.io.File;
import java.io.IOException;

import com.gzfgeh.personalnote.R;
import com.gzfgeh.dialog.Effectstype;
import com.gzfgeh.dialog.NiftyDialogBuilder;
import com.gzfgeh.imagetool.ImageTool;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class PersonalInfo extends Activity implements OnClickListener {
	public static final String FILE_PATH = Environment.getExternalStorageDirectory() + 
			File.separator + "PersonalNote" + File.separator;
	public static final String FILE_NAME = "image.jpg";
	private static final int FROM_CAPTURE = 1;
	private static final int FROM_GALLERY = 2;
	private static final int CROP_PHOTO = 3;
	
	private View titleRightView, titleCenterView, titleLeftView;
	private TextView titleLeftTextView;
	private ImageView headImageView;
	private View headSelect;
	
	private Effectstype effect;
	private NiftyDialogBuilder dialogBuilder;
	
	private Intent getImageIntent;
	private File outputFile;
	private Uri imageUri;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.personal_info);
		
		File fold = new File(FILE_PATH);
		if (!fold.exists())
			fold.mkdirs();
		
		titleRightView = findViewById(R.id.title_right);
		titleRightView.setVisibility(View.INVISIBLE);
		titleCenterView = findViewById(R.id.title_center);
		titleCenterView.setVisibility(View.INVISIBLE);
		titleLeftTextView = (TextView) findViewById(R.id.title_left_text);
		titleLeftTextView.setText(R.string.personal_info);
		titleLeftView = findViewById(R.id.title_left);
		titleLeftView.setOnClickListener(this);
		headSelect = findViewById(R.id.head_select);
		headSelect.setOnClickListener(this);
		headImageView = (ImageView) findViewById(R.id.head_image_view);
		
		outputFile = new File(FILE_PATH + FILE_NAME);
		if (outputFile.length() == 0 || !outputFile.exists())
			headImageView.setImageResource(R.drawable.default_image);
		else 
			headImageView.setImageBitmap(ImageTool.setSDImageView(FILE_PATH + FILE_NAME));
	}
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.head_select:
			dialogShow();
			break;
			
		case R.id.title_left:
			finish();
			break;
			
		
			
		default:
			break;
		}
	}
	private void dialogShow() {
		// TODO Auto-generated method stub
		dialogBuilder=NiftyDialogBuilder.getInstance(this);
        effect=Effectstype.Slidetop;

        dialogBuilder
        		.withMessage(null)
                .withTitle(getString(R.string.change_image))                                  //.withTitle(null)  no title
                .withTitleColor("#33CCFF")                                  //def
                .withDividerColor("#33CCFF")
                .isCancelableOnTouchOutside(true)                           //def    | isCancelable(true)
                .withDuration(300)                                          //def
                .withEffect(effect)                                 //def gone
                .setCustomView(R.layout.custom_dialog,this)         //.setCustomView(View or ResId,context)
                .show();
	}
	
	public void onSelectPhoto(View view){
		switch (view.getId()) {
		case R.id.make_photo:
			try {
				if (outputFile.exists())
					outputFile.delete();
				outputFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			imageUri = Uri.fromFile(outputFile);
			getImageIntent = new Intent("android.media.action.IMAGE_CAPTURE");
			getImageIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
			startActivityForResult(getImageIntent, FROM_CAPTURE);
			break;
			
		case R.id.get_photo:
			getImageIntent = new Intent(Intent.ACTION_PICK, null);
			getImageIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
					"image/*");
			startActivityForResult(getImageIntent, FROM_GALLERY);
			break;

		default:
			break;
		}
		dialogBuilder.dismiss();
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK){
			switch (requestCode) {
			case FROM_CAPTURE:
				ImageTool.startCrop(PersonalInfo.this, outputFile.getAbsolutePath(), CROP_PHOTO);
				break;
				
			case FROM_GALLERY:
				if (data != null) {
					ImageTool.startCrop(PersonalInfo.this, ImageTool.getPhotoPathByLocalUri(this, data), CROP_PHOTO);
				}
				
				break;
				
			case CROP_PHOTO:
				if (data != null) {
					Bitmap bitmap = data.getParcelableExtra("data");
					ImageTool.saveBitmapToSDCard(bitmap);
					headImageView.setImageBitmap(bitmap);
				}
				break;
				
			default:
				headImageView.setImageResource(R.drawable.default_image);
				break;
			}
		}
	}
	
	
}
