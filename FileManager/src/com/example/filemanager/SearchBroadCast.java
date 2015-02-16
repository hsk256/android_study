package com.example.filemanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @author hsk
 *
 */
public class SearchBroadCast extends BroadcastReceiver{
	public static String mServiceKeyword = "";  //接收搜索关键字的静态变量
	public static String mServiceSearchPath = ""; //接收搜索路径的静态变量
	
	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if(MainActivity.KEYWORD_BROADCAST.equals(action)) {
			mServiceKeyword = intent.getStringExtra("keyword");
			mServiceSearchPath = intent.getStringExtra("searchpath");
		}
	}

}
