package com.gzfgeh.tools;

import android.view.View;
import android.widget.LinearLayout;

public class SetViewMargin {
	
	private SetViewMargin(){
		
	}
	
	public static void SetViewMarginLeft(View view, int offset){
		if (view != null){
			LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
			params.setMargins(offset, 0, 0, 0);
			view.setLayoutParams(params);
		}
	}
}
