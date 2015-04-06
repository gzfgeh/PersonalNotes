package com.gzfgeh.set;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
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
	private MapView mapView;
	private BaiduMap baiduMap;
	private boolean isFirstIn = true;
	public LocationClient mLocationClient = null;
	public BDLocationListener myListener = new MyLocationListener();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		SDKInitializer.initialize(getApplicationContext()); 
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
		
		mapView = (MapView) findViewById(R.id.map);
		MapStatusUpdate msu = MapStatusUpdateFactory.zoomBy(1000.0f);
		baiduMap = mapView.getMap();
		baiduMap.setMapStatus(msu);
		baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
		
		mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
	    mLocationClient.registerLocationListener( myListener );    //注册监听函数
	    LocationClientOption option = new LocationClientOption();
	    option.setLocationMode(LocationMode.Hight_Accuracy);//设置定位模式
	    option.setCoorType("bd09ll");//返回的定位结果是百度经纬度,默认值gcj02
	    option.setScanSpan(1000);//设置发起定位请求的间隔时间为5000ms
	    option.setOpenGps(true);
	    option.setIsNeedAddress(true);//返回的定位结果包含地址信息
	    option.setNeedDeviceDirect(true);//返回的定位结果包含手机机头的方向
	    mLocationClient.setLocOption(option);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.putExtra("region", provinceEditText.getText() + 
								cityEditText.getText().toString() + 
								countyEditText.getText().toString());
		setResult(RESULT_OK, intent);
		finish();
	}
	
	
	public class MyLocationListener implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
		            return ;
			MyLocationData data = new MyLocationData.Builder()
									.accuracy(location.getRadius())
									.latitude(location.getLatitude())
									.longitude(location.getLongitude())
									.build();
			baiduMap.setMyLocationData(data);
			
			if (isFirstIn){
				LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
				MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
				baiduMap.animateMapStatus(msu);
				isFirstIn = false;
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
		baiduMap.setMyLocationEnabled(true);
		if (!mLocationClient.isStarted())
			mLocationClient.start();
		mLocationClient.requestLocation();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		baiduMap.setMyLocationEnabled(false);
		mLocationClient.stop();
		mLocationClient.unRegisterLocationListener(myListener);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mapView.onResume();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mapView.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mapView.onPause();
	}
	
	
}
