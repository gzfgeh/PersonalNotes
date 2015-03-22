package com.gzfgeh.set;

import java.io.File;

import com.gzfgeh.personalnote.R;

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
	}
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.head_select:
			
			break;
			
		case R.id.title_left:
			finish();
			break;
			
		default:
			break;
		}
	}
	
}
