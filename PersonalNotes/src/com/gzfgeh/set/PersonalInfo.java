package com.gzfgeh.set;

import java.io.File;
import java.io.IOException;

import com.gzfgeh.myapplication.MyApplication;
import com.gzfgeh.personalnote.R;
import com.gzfgeh.tools.GetProfessionData;
import com.gzfgeh.tools.ImageTool;
import com.gzfgeh.dialog.Effectstype;
import com.gzfgeh.dialog.NiftyDialogBuilder;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class PersonalInfo extends Activity implements OnClickListener {
	private static final int FROM_CAPTURE = 1;
	private static final int FROM_GALLERY = 2;
	private static final int CROP_PHOTO = 3;
	
	private View titleRightView, titleCenterView, titleLeftView;
	private TextView titleLeftTextView, professionTextView, signatureTextView;
	private ImageView headImageView;
	private View headSelect, professionSelect, signatureSelect;
	
	private Effectstype effect;
	private NiftyDialogBuilder dialogBuilder;
	
	private MyApplication myApplication;
	private Intent getImageIntent;
	private File outputFile;
	private Uri imageUri;
	
	int professionIndex = 0;
	public static String[] professionText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.personal_info);
		
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
		
		professionSelect = findViewById(R.id.profession_select);
		professionSelect.setOnClickListener(this);
		professionTextView = (TextView)findViewById(R.id.please_select);
		
		signatureSelect = findViewById(R.id.signature_select);
		signatureSelect.setOnClickListener(this);
		signatureTextView = (TextView) findViewById(R.id.signature_text);
		
		myApplication = (MyApplication)getApplication();
		outputFile = myApplication.getOutputFile();
		if (outputFile.length() == 0 || !outputFile.exists())
			headImageView.setImageResource(R.drawable.default_image);
		else 
			headImageView.setImageBitmap(ImageTool.setSDImageView(outputFile.getAbsolutePath()));
		
		professionText = this.getResources().getStringArray(R.array.professions);
		
	}
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.head_select:
			headDialogShow(view);
			break;
			
		case R.id.profession_select:
			professionDialogShow(view);
			break;
			
		case R.id.signature_select:
			signatureDialogShow(view);
			break;
		case R.id.title_left:
			setResult(RESULT_OK);
			finish();
			break;
			
		default:
			break;
		}
	}
	private void signatureDialogShow(View view) {
		LinearLayout linearLayoutMain = new LinearLayout(this);//self define one layout file
        linearLayoutMain.setLayoutParams(new LayoutParams(  
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        final EditText editText = new EditText(this);
        editText.setLayoutParams(new LayoutParams(  
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        linearLayoutMain.addView(editText);
        
		dialogBuilder=NiftyDialogBuilder.getInstance(this);
        effect=Effectstype.Fall;
        dialogBuilder
        		.withMessage(null)
                .withTitle(getString(R.string.signature))
                .withTitleColor("#33CCFF")                                  
                .withDividerColor("#33CCFF")
                .isCancelableOnTouchOutside(true)                          
                .withDuration(300)                                  
                .withEffect(effect)                                 
                .setCustomView(linearLayoutMain,this)
                .withButton1Text(getString(R.string.ok))
                .setButton1Click(new OnClickListener() {
					
					@Override
					public void onClick(View view) {
						signatureTextView.setText(editText.getText().toString());
						dialogBuilder.dismiss();
					}
				})
				.withButton2Text(getString(R.string.cancle))
				.setButton2Click(new OnClickListener() {
					
					@Override
					public void onClick(View view) {
						dialogBuilder.dismiss();
					}
				})
                .show();
	}
	private void professionDialogShow(View view) {
		// TODO Auto-generated method stub
        SimpleAdapter adapter = new SimpleAdapter(this, GetProfessionData.getData(this), R.layout.profession_item,
        			new String[]{"text", "image"}, new int[]{R.id.profession_item_text, R.id.profession_item_image});
        LinearLayout linearLayoutMain = new LinearLayout(this);//self define one layout file
        linearLayoutMain.setLayoutParams(new LayoutParams(  
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));  
        ListView listView = new ListView(this); 
        listView.setFadingEdgeLength(0);	//解决拖拉到上顶下边的阴影
        listView.setAdapter(adapter);
        linearLayoutMain.addView(listView);
        
        dialogBuilder=NiftyDialogBuilder.getInstance(this);
        effect=Effectstype.Slidetop;
        dialogBuilder
        		.withMessage(null)
                .withTitle(getString(R.string.please_select))
                .withTitleColor("#33CCFF")                                  
                .withDividerColor("#33CCFF")
                .isCancelableOnTouchOutside(false)                          
                .withDuration(300)                                  
                .withEffect(effect)                                 
                .setCustomView(linearLayoutMain,this)
                .withButton1Text(getString(R.string.ok))
                .setButton1Click(new OnClickListener() {
					
					@Override
					public void onClick(View view) {
						professionTextView.setText(professionText[professionIndex]);
						dialogBuilder.dismiss();
					}
				})
				.withButton2Text(getString(R.string.cancle))
				.setButton2Click(new OnClickListener() {
					
					@Override
					public void onClick(View view) {
						dialogBuilder.dismiss();
					}
				})
                .show();
        
        listView.setOnItemClickListener(new OnItemClickListener() {
        	ImageView imageView;
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (imageView != null)
					imageView.setVisibility(View.INVISIBLE);
				imageView = (ImageView) view.findViewById(R.id.profession_item_image);
				imageView.setVisibility(View.VISIBLE);
				professionIndex = position;
			}
		});
	}
	private void headDialogShow(View view) {
		// TODO Auto-generated method stub
		dialogBuilder=NiftyDialogBuilder.getInstance(PersonalInfo.this);
        effect=Effectstype.Fadein;

        dialogBuilder
        		.withMessage(null)
                .withTitle(getString(R.string.change_image))                                  //.withTitle(null)  no title
                .withTitleColor("#33CCFF")                                  //def
                .withDividerColor("#33CCFF")
                .isCancelableOnTouchOutside(true)                           //def    | isCancelable(true)
                .withDuration(300)                                          //def
                .withEffect(effect)                                 //def gone
                .setCustomView(R.layout.custom_dialog,view.getContext())         //.setCustomView(View or ResId,context)
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
					ImageTool.saveBitmapToSDCard(outputFile,bitmap);
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
