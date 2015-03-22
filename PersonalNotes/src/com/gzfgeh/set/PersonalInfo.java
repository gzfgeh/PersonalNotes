package com.gzfgeh.set;

import java.io.File;

import com.gzfgeh.personalnote.R;
import com.gzfgeh.dialog.Effectstype;
import com.gzfgeh.dialog.NiftyDialogBuilder;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

public class PersonalInfo extends Activity implements OnClickListener {
	private static final String FILE_PATH = Environment.getExternalStorageDirectory() + 
			File.separator + "PersonalNote" + File.separator;
	private View titleRightView, titleCenterView, titleLeftView;
	private TextView titleLeftTextView;
	private View headSelect;
	
	private Effectstype effect;
	private NiftyDialogBuilder dialogBuilder;
	private TextView getPhotoText, makePhotoText;
	
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
		getPhotoText = (TextView) findViewById(R.id.get_photo);
		//getPhotoText.setOnClickListener(this);
		makePhotoText = (TextView) findViewById(R.id.make_photo);
		//makePhotoText.setOnClickListener(this);
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
			dialogBuilder.dismiss();
			break;
			
		case R.id.get_photo:
			dialogBuilder.dismiss();
			break;

		default:
			break;
		}
	}
}
