package com.gzfgeh.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;

import com.gzfgeh.personalnote.R;

/**
 * can not extends and all functions are final
 * @author Administrator
 *
 */
public final class GetProfessionData {
	/**
	 * can not instantiation
	 */
	private GetProfessionData(){
		
	}
	
	public static List<Map<String, Object>> getData(Context context){
		String[] text = {
				context.getResources().getString(R.string.exchange_design),
				context.getResources().getString(R.string.productor_manager),
				context.getResources().getString(R.string.ui_design),
				context.getResources().getString(R.string.js_design),
				context.getResources().getString(R.string.web_design),
				context.getResources().getString(R.string.mobile_design),
				context.getResources().getString(R.string.java_design),
				context.getResources().getString(R.string.php_design),
				context.getResources().getString(R.string.linux_design),
				context.getResources().getString(R.string.software_test),
				context.getResources().getString(R.string.others),
				
		};
		
		List<Map<String, Object>> data = new ArrayList<Map<String,Object>>();
		for (int i = 0; i < text.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("text", text[i]);
			map.put("image", context.getResources().getDrawable(R.drawable.item_selected));
			data.add(map);
		}
		
		return data;
	}
}
