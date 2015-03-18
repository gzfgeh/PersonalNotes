package com.gzfgeh.animation;

import com.nineoldandroids.view.ViewHelper;

import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.view.View;

public class MenuDrawLayout {
	public static void drawerLayoutEvent(final DrawerLayout drawerLayout){
		if (drawerLayout != null){
			drawerLayout.setDrawerListener(new DrawerListener() {
				
				@Override
				public void onDrawerStateChanged(int arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onDrawerSlide(View drawerView, float slideOffset) {
					// TODO Auto-generated method stub
					View mContent = drawerLayout.getChildAt(0);  
	                View mMenu = drawerView;  
	                float scale = 1 - slideOffset;  
	                float rightScale = 0.8f + scale * 0.2f;  
	  
	                if (drawerView.getTag().equals("LEFT"))  
	                {  
	  
	                    float leftScale = 1 - 0.3f * scale;  
	  
	                    ViewHelper.setScaleX(mMenu, leftScale);  
	                    ViewHelper.setScaleY(mMenu, leftScale);  
	                    ViewHelper.setAlpha(mMenu, 0.6f + 0.4f * (1 - scale));  
	                    ViewHelper.setTranslationX(mContent,  
	                            mMenu.getMeasuredWidth() * (1 - scale));  
	                    ViewHelper.setPivotX(mContent, 0);  
	                    ViewHelper.setPivotY(mContent,  
	                            mContent.getMeasuredHeight() / 2);  
	                    mContent.invalidate();  
	                    ViewHelper.setScaleX(mContent, rightScale);  
	                    ViewHelper.setScaleY(mContent, rightScale);  
	                } else{  
	                    ViewHelper.setTranslationX(mContent,  
	                            -mMenu.getMeasuredWidth() * slideOffset);  
	                    ViewHelper.setPivotX(mContent, mContent.getMeasuredWidth());  
	                    ViewHelper.setPivotY(mContent,  
	                            mContent.getMeasuredHeight() / 2);  
	                    mContent.invalidate();  
	                    ViewHelper.setScaleX(mContent, rightScale);  
	                    ViewHelper.setScaleY(mContent, rightScale);  
	                }  
				}
				
				@Override
				public void onDrawerOpened(View arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onDrawerClosed(View drawerView) {
					
				}
			});
		}
	}
}
