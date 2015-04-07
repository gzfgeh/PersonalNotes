package com.gzfgeh.personalnote;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.framework.ShareSDK;

import com.gzfgeh.animation.MenuDrawLayout;
import com.gzfgeh.animation.RoundImageView;
import com.gzfgeh.myapplication.MyApplication;
import com.gzfgeh.tools.ImageTool;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity implements OnClickListener, OnPageChangeListener {
	private View textLayout, soundsLayout, photoLayout, movieLayout;
	private TextView textView, soundsView, photoView, movieView;
	private TextView titleText, titleLeftText, titleRightText;
	private RoundImageView titleLeftImageView;
	private TextFragment textFragment;
	private SoundsFragment soundsFragment;
	private PhotoFragment photoFragment;
	private MovieFragment movieFragment;
	private FragmentManager fragmentManager;
	private ViewPager viewPager;
	private FragmentPagerAdapter fragmentPagerAdapter;
	private List<Fragment> fragments = new ArrayList<Fragment>();
	private ImageView cursorView, titleRightImageView;
	private int cursorWidth;
	private int cursorOffset;
	private int pageWidth;
	private float animationStart, animationEnd;
	private DrawerLayout drawerLayout;
	private RoundImageView imageView;
	
	private MyApplication myApplication;
	private File outputFile;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        
        if (savedInstanceState == null) {
            initViews();
            fragmentManager = getSupportFragmentManager();
            fragmentPagerAdapter = new FragmentPagerAdapter(fragmentManager) {
				
				@Override
				public int getCount() {
					// TODO Auto-generated method stub
					return fragments.size();
				}
				
				@Override
				public Fragment getItem(int position) {
					// TODO Auto-generated method stub
					return fragments.get(position);
				}
			};
            
			viewPager.setAdapter(fragmentPagerAdapter);
			viewPager.setOnPageChangeListener(this);
        }
    }
    
    @SuppressLint("ResourceAsColor") private void resetSelected(){
		textView.setTextColor(R.color.bottom_bg);
		soundsView.setTextColor(R.color.bottom_bg);
		photoView.setTextColor(R.color.bottom_bg);
		movieView.setTextColor(R.color.bottom_bg);
	}

	private void initViews() {
		// TODO Auto-generated method stub
		textLayout = findViewById(R.id.text_layout);
		soundsLayout = findViewById(R.id.sounds_layout);
		photoLayout = findViewById(R.id.photo_layout);
		movieLayout = findViewById(R.id.movie_layout);
		textLayout.setOnClickListener(this);
		soundsLayout.setOnClickListener(this);
		photoLayout.setOnClickListener(this);
		movieLayout.setOnClickListener(this);
		
		textView = (TextView) findViewById(R.id.text_msg);
		soundsView = (TextView) findViewById(R.id.sounds_msg);
		photoView = (TextView) findViewById(R.id.photo_msg);
		movieView = (TextView) findViewById(R.id.movie_msg);
		
		titleRightImageView = (ImageView) findViewById(R.id.title_right_image);
		titleRightImageView.setVisibility(View.VISIBLE);
		titleRightText = (TextView) findViewById(R.id.title_right_text);
		titleRightText.setVisibility(View.GONE);
		titleText = (TextView) findViewById(R.id.title_center_text);
		titleLeftText = (TextView) findViewById(R.id.title_left_text);
		titleLeftText.setVisibility(View.GONE);
		titleLeftImageView = (RoundImageView) findViewById(R.id.title_left_image);
		titleLeftImageView.setOnClickListener(this);
		myApplication = (MyApplication)getApplication();
		outputFile = myApplication.getOutputFile();
		if (outputFile.length() == 0 || !outputFile.exists())
			titleLeftImageView.setImageResource(R.drawable.default_image);
		else 
			titleLeftImageView.setImageBitmap(ImageTool.setSDImageView(outputFile.getAbsolutePath()));
		
		cursorView = (ImageView) findViewById(R.id.cursor);
		viewPager = (ViewPager) findViewById(R.id.container);
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		imageView = (RoundImageView) getSupportFragmentManager().
				findFragmentById(R.id.left_menu).getView().findViewById(R.id.self_view);
		
		textFragment = new TextFragment();
		soundsFragment = new SoundsFragment();
		photoFragment = new PhotoFragment();
		movieFragment = new MovieFragment();
		fragments.add(textFragment);
		fragments.add(photoFragment);
		fragments.add(soundsFragment);
		fragments.add(movieFragment);
		
		cursorWidth = BitmapFactory.decodeResource(getResources(), R.drawable.cursor).getWidth();
		DisplayMetrics displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		int screenWidth = displayMetrics.widthPixels;
		cursorOffset = (screenWidth/4 - cursorWidth) / 2;
		Matrix matrix = new Matrix();
		matrix.postTranslate(cursorOffset, 0);
		cursorView.setImageMatrix(matrix);
		pageWidth = cursorOffset * 2 + cursorWidth;
		
		titleText.setText(R.string.text);
		textView.setTextColor(getResources().getColor(R.color.title_bg));
		MenuDrawLayout.drawerLayoutEvent(drawerLayout);
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.text_layout:
			viewPager.setCurrentItem(0);
			break;

		case R.id.photo_layout:
			viewPager.setCurrentItem(1);
			break;
			
		case R.id.sounds_layout:
			viewPager.setCurrentItem(2);
			break;
			
		case R.id.movie_layout:
			viewPager.setCurrentItem(3);
			break;
			
		case R.id.title_left_image:
			drawerLayout.openDrawer(Gravity.LEFT);
			drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, Gravity.LEFT);
			break;
			
		case R.id.title_right_image:
			
			break;
			
		default:
			break;
		}
	}


	@Override
	public void onPageScrollStateChanged(int position) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onPageScrolled(int currentPage, float percentage, int percentagePix) {
		Animation animation = null;
		if (percentage == 0){
			animationStart = currentPage * pageWidth;
			animationEnd = animationStart;
			animation = new TranslateAnimation(animationStart, animationEnd, 0, 0);
			
		}else{
			animationStart = animationEnd;
			animationEnd = (currentPage + percentage) * pageWidth;
			animation = new TranslateAnimation(animationStart, animationEnd, 0, 0);
		}
		animation.setFillAfter(true);
		cursorView.startAnimation(animation);
	}


	@Override
	public void onPageSelected(int position) {
		resetSelected();
		
		switch (position) {
		case 0:
			titleText.setText(R.string.text);
			textView.setTextColor(getResources().getColor(R.color.title_bg));
			break;
			
		case 1:
			titleText.setText(R.string.photo);
			photoView.setTextColor(getResources().getColor(R.color.title_bg));
			break;
			
		case 2:
			titleText.setText(R.string.sounds);
			soundsView.setTextColor(getResources().getColor(R.color.title_bg));
			break;
		case 3:
			titleText.setText(R.string.movie);
			movieView.setTextColor(getResources().getColor(R.color.title_bg));
			break;
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (requestCode == LeftMenu.REQUEST_CODE){
			if (resultCode == RESULT_OK){
				
				Intent intent = getIntent();
				if (intent != null){
					byte [] bis=intent.getByteArrayExtra("Bitmap");  
		            Bitmap bitmap=BitmapFactory.decodeByteArray(bis, 0, bis.length);  
		            imageView.setImageBitmap(bitmap);
				}
			}
		}
	}
	
	private void showShare() {
		 ShareSDK.initSDK(this);
//		 OnekeyShare oks = new OnekeyShare();
//		 //关闭sso授权
//		 oks.disableSSOWhenAuthorize(); 
//
//		// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
//		 //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
//		 // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
//		 oks.setTitle(getString(R.string.share));
//		 // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
//		 oks.setTitleUrl("http://sharesdk.cn");
//		 // text是分享文本，所有平台都需要这个字段
//		 oks.setText("我是分享文本");
//		 // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//		 oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
//		 // url仅在微信（包括好友和朋友圈）中使用
//		 oks.setUrl("http://sharesdk.cn");
//		 // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//		 oks.setComment("我是测试评论文本");
//		 // site是分享此内容的网站名称，仅在QQ空间使用
//		 oks.setSite(getString(R.string.app_name));
//		 // siteUrl是分享此内容的网站地址，仅在QQ空间使用
//		 oks.setSiteUrl("http://sharesdk.cn");
//
//		// 启动分享GUI
//		 oks.show(this);
		 }
}
