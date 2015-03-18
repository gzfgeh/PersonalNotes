package com.gzfgeh.personalnote;

import com.gzfgeh.set.Setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class LeftMenu extends Fragment implements OnClickListener {
	private View setView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.left_menu, container, false);
		setView = view.findViewById(R.id.left_menu_setting);
		setView.setOnClickListener(this);
		return view;
	}
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.left_menu_setting:
			Intent intent = new Intent(getActivity(), Setting.class);
			getActivity().startActivity(intent);
			break;

		default:
			break;
		}
	}
	
	
}
