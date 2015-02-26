package com.hsk.mobilesafe.receiver;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.hsk.mobilesafe.service.UpdateWidgetService;

/**
 * @author heshaokang	
 * 2014-12-22 下午3:40:00
 * 在真机上测试时 没有显示出来 后来才知道 原来必须安装在手机内存上才行 之前在清单文件里写的是外部安装 preferExternal
 */
public class MyWidget extends AppWidgetProvider {

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i("MyWidget","onReceive....");
		Intent i = new Intent(context,UpdateWidgetService.class);
		context.startService(i);
		super.onReceive(context, intent);
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		Log.i("MyWidget","OnUdate....");
		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}
	/**
	 * 当控件拖拽到屏幕上时 调用 只要在屏幕上显示 则只调用一次
	 */
	@Override
	public void onEnabled(Context context) {
		Log.i("MyWidget","开启服务....");
		Intent intent = new Intent(context,UpdateWidgetService.class);
		context.startService(intent);
		super.onEnabled(context);
	}
	/**
	 * 当控件删除时调用
	 */
	@Override
	public void onDisabled(Context context) {
		Log.i("MyWidget","关闭服务....");
		Intent intent = new Intent(context,UpdateWidgetService.class);
		context.stopService(intent);
		super.onDisabled(context);
	}
	
}
