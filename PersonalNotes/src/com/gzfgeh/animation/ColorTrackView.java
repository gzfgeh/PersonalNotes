package com.gzfgeh.animation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class ColorTrackView extends View {
	
	public ColorTrackView(Context context) {
		this(context, null);
	}
	
	public ColorTrackView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public ColorTrackView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
	}
	

}
