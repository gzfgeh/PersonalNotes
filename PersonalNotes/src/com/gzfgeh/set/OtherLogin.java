package com.gzfgeh.set;


import java.util.HashMap;

import com.gzfgeh.personalnote.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import cn.sharesdk.framework.FakeActivity;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;

@SuppressLint("HandlerLeak") public class OtherLogin extends FakeActivity implements Callback, OnClickListener, PlatformActionListener {
	@SuppressWarnings("unused")
	private static final int MSG_SMSSDK_CALLBACK = 1;
	@SuppressWarnings("unused")
	private static final int MSG_AUTH_CANCEL = 2;
	@SuppressWarnings("unused")
	private static final int MSG_AUTH_ERROR= 3;
	private static final int MSG_AUTH_COMPLETE = 4;
	@SuppressWarnings("unused")
	private static final int MSG_USERID_FOUND = 5;
	private Context context;
	
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case MSG_AUTH_COMPLETE:
				Toast.makeText(getContext(), msg.obj.toString(), Toast.LENGTH_SHORT).show();
				break;

			default:
				break;
			}
		}
		
	};
	@Override
	public void onCreate() {
		activity.setContentView(R.layout.other_login);
		(activity.findViewById(R.id.tv_login_msg)).setOnClickListener(this);
		(activity.findViewById(R.id.tv_login_qq)).setOnClickListener(this);
		(activity.findViewById(R.id.tv_login_weibo)).setOnClickListener(this);
		(activity.findViewById(R.id.tv_login_quit)).setOnClickListener(this);
	}
	
	public void show(Context context){
		ShareSDK.initSDK(context);
		this.context = context;
		super.show(context, null);
		
	}

	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onClick(View v) {
		Platform platform;
		switch (v.getId()) {
		case R.id.tv_login_weibo:
			platform = ShareSDK.getPlatform(context, SinaWeibo.NAME);
			authorize(platform);
			break;
			
		case R.id.tv_login_qq:
			platform = ShareSDK.getPlatform(context, QQ.NAME);
			authorize(platform);
			break;
			
		case R.id.tv_login_quit:
			finish();
			break;

		default:
			break;
		}
	}

	private void authorize(Platform plat) {
		// TODO Auto-generated method stub
		if (plat == null) {
			//popupOthers();
			return;
		}
		plat.setPlatformActionListener(this);
		plat.authorize();
		//关闭SSO授权
		//plat.SSOSetting(true);
		//plat.showUser(null);
	}

	@Override
	public void onCancel(Platform platform, int action) {
		Toast.makeText(getContext(), "cancle", Toast.LENGTH_SHORT).show();
		
	}

	@Override
	public void onComplete(Platform platform, int action, HashMap<String, Object> res) {
		// TODO Auto-generated method stub
		if (action == Platform.ACTION_USER_INFOR) {
			Message msg = handler.obtainMessage();
			msg.what = MSG_AUTH_COMPLETE;
			msg.obj = new Object[] {platform.getName(), res};
			handler.sendMessage(msg);
		}
	}

	@Override
	public void onError(Platform plat, int action, Throwable arg2) {
		// TODO Auto-generated method stub
		Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
	}
}
