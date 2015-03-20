package com.gzfgeh.personalnote;

import com.gzfgeh.set.PersonalInfo;
import com.gzfgeh.set.Setting;
import com.gzfgeh.set.TransparentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class LeftMenu extends Fragment implements OnClickListener {
	private View setView, personalSet;
	public static final int REQUEST_CODE = 1;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.left_menu, container, false);
		setView = view.findViewById(R.id.left_menu_setting);
		setView.setOnClickListener(this);
		personalSet = view.findViewById(R.id.personal_set);
		personalSet.setOnClickListener(this);
		return view;
	}
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		Intent intent = null;
		switch (view.getId()) {
		case R.id.left_menu_setting:
			intent = new Intent(getActivity(), Setting.class);
			getActivity().startActivity(intent);
			break;
		
		case R.id.personal_set:
			intent = new Intent(getActivity(), PersonalInfo.class);
			getActivity().startActivityForResult(intent, REQUEST_CODE);
		default:
			break;
		}
	}
	
}
