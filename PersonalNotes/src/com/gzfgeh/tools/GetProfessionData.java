package com.gzfgeh.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;

import com.gzfgeh.personalnote.R;
import com.gzfgeh.set.PersonalInfo;

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
		
		List<Map<String, Object>> data = new ArrayList<Map<String,Object>>();
		
		for (int i = 0; i < PersonalInfo.professionText.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("text", PersonalInfo.professionText[i]);
			map.put("image", R.drawable.item_selected);
			data.add(map);
		}
		
		return data;
	}
}
