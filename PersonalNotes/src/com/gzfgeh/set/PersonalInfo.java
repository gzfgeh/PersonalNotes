package com.gzfgeh.set;

import com.gzfgeh.personalnote.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class PersonalInfo extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.personal_info);
	}
	
}
