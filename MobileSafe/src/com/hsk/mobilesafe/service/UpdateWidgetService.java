package com.hsk.mobilesafe.service;

import java.util.Timer;
import java.util.TimerTask;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.RemoteViews;

import com.hsk.mobilesafe.R;
import com.hsk.mobilesafe.receiver.MyWidget;
import com.hsk.mobilesafe.utils.SystemInfoUtils;

/**
 * @author heshaokang	
 * 2014-12-22 下午5:00:25
 */
public class UpdateWidgetService extends Service {
	protected static final String TAG = "UpdateWidgetService";
	private ScreenOffReceiver offreceiver;
	private ScreenOnReceiver onreceiver;
	private Timer timer;
	private TimerTask timerTask;
	private AppWidgetManager awm;
	@Override
	public IBinder onBind(Intent intent) {

		return null;
	}
	@Override
	public void onCreate() {
		offreceiver = new ScreenOffReceiver();
		onreceiver = new ScreenOnReceiver();
		registerReceiver(offreceiver, new IntentFilter(Intent.ACTION_SCREEN_OFF));
		registerReceiver(onreceiver, new IntentFilter(Intent.ACTION_SCREEN_ON));
		awm = AppWidgetManager.getInstance(this);
		startTimer();
		super.onCreate();
	}
	@Override
	public void onDestroy() {
		unregisterReceiver(offreceiver);
		unregisterReceiver(onreceiver);
		offreceiver = null;
		onreceiver = null;
		stopTimer();
		super.onDestroy();
	}
	private class ScreenOffReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.i(TAG,"屏幕锁住了....");
			stopTimer();
		}
	}
	private class ScreenOnReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.i(TAG,"屏幕解锁了....");
			startTimer();
		}
	}
	
	private void startTimer() {
		if(timer==null && timerTask==null) {
			timer = new Timer();
			timerTask = new TimerTask() {
				
				@Override
				public void run() {
					Log.i(TAG, "更新widget");
					ComponentName provider = new ComponentName(UpdateWidgetService.this, MyWidget.class);
					RemoteViews views = new RemoteViews(getPackageName(), R.layout.process_widget);
					views.setTextViewText(R.id.process_count, 
							"正在运行的进程:"+SystemInfoUtils.getRunningProcessCount(getApplicationContext()));
					
					long size = SystemInfoUtils.getAvailMem(getApplicationContext());
					views.setTextViewText(R.id.process_memory, 
							"可用内存:"+Formatter.formatFileSize(getApplicationContext(), size));
					//自定一个广播杀死后台进程
					Intent intent = new Intent();
					intent.setAction("com.hsk.mobilesafe.killall");
					PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
					views.setOnClickPendingIntent(R.id.btn_clear, pi);
					awm.updateAppWidget(provider,views);
					
				}
			};
			timer.schedule(timerTask, 0, 3000);
		}
	}
	private void stopTimer() {
		if(timer!=null && timerTask!=null) {
				timer.cancel();
				timerTask.cancel();
				timer = null;
				timerTask = null;
		}
	}
}
