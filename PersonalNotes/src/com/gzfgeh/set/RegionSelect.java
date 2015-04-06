package com.gzfgeh.set;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.gzfgeh.personalnote.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

public class RegionSelect extends Activity implements OnClickListener {
	private View titleRightView, titleCenterView, titleLeftView;
	private TextView titleLeftTextView;
	private EditText provinceEditText, cityEditText, countyEditText;
	public LocationClient mLocationClient = null;
	public BDLocationListener myListener = new MyLocationListener();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.region_select);
		
		titleRightView = findViewById(R.id.title_right);
		titleRightView.setVisibility(View.INVISIBLE);
		titleCenterView = findViewById(R.id.title_center);
		titleCenterView.setVisibility(View.INVISIBLE);
		titleLeftTextView = (TextView) findViewById(R.id.title_left_text);
		titleLeftTextView.setText(R.string.back);
		titleLeftView = findViewById(R.id.title_left);
		titleLeftView.setOnClickListener(this);
		
		provinceEditText = (EditText) findViewById(R.id.province);
		cityEditText = (EditText) findViewById(R.id.city);
		countyEditText = (EditText) findViewById(R.id.county);
		
		mLocationClient = new LocationClient(getApplicationContext());     //����LocationClient��
	    mLocationClient.registerLocationListener( myListener );    //ע���������
	    LocationClientOption option = new LocationClientOption();
	    option.setLocationMode(LocationMode.Hight_Accuracy);//���ö�λģʽ
	    option.setCoorType("bd09ll");//���صĶ�λ����ǰٶȾ�γ��,Ĭ��ֵgcj02
	    option.setScanSpan(5000);//���÷���λ����ļ��ʱ��Ϊ5000ms
	    option.setIsNeedAddress(true);//���صĶ�λ���������ַ��Ϣ
	    option.setNeedDeviceDirect(true);//���صĶ�λ��������ֻ���ͷ�ķ���
	    mLocationClient.setLocOption(option);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.putExtra("region", "ddd");
		setResult(4, intent);
		finish();
	}
	
	
	public class MyLocationListener implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
		            return ;
			StringBuffer sb = new StringBuffer(256);
			sb.append("time : ");
			sb.append(location.getTime());
			sb.append("\nerror code : ");
			sb.append(location.getLocType());
			sb.append("\nlatitude : ");
			sb.append(location.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(location.getLongitude());
			sb.append("\nradius : ");
			sb.append(location.getRadius());
			if (location.getLocType() == BDLocation.TypeGpsLocation){
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation){
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
			} 
			provinceEditText.setText(location.getProvince());
			cityEditText.setText(location.getCity());
			countyEditText.setText(location.getDistrict());
		}
	}

	

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		mLocationClient.start();
		mLocationClient.requestLocation();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		mLocationClient.stop();
		mLocationClient.unRegisterLocationListener(myListener);
	}
	
	
}
