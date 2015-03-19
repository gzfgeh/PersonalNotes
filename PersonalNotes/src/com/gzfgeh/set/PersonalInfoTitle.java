package com.gzfgeh.set;

import com.gzfgeh.personalnote.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class PersonalInfoTitle extends Fragment implements OnClickListener {
	private View titleRightView;
	private TextView titleCenterText;
	private TextView titleLeftText;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.main_title, container, false);
		titleRightView = view.findViewById(R.id.title_right);
		titleRightView.setVisibility(View.GONE);
		titleCenterText = (TextView) view.findViewById(R.id.title_center_text);
		titleCenterText.setText(R.string.personal_info);
		titleLeftText = (TextView) view.findViewById(R.id.title_left_text);
		titleLeftText.setText(R.string.back);
		titleLeftText.setOnClickListener(this);
		return view;
	}
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		getActivity().finish();
	}
	
}
