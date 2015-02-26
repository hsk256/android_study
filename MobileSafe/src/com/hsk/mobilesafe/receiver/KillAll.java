package com.hsk.mobilesafe.receiver;

import java.util.List;

import com.hsk.mobilesafe.utils.SystemInfoUtils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * @author heshaokang	
 * 2014-12-22 下午5:24:05
 */
public class KillAll extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		System.out.println("自定义广播接收到了....");
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> infos = am.getRunningAppProcesses();
		for(RunningAppProcessInfo info:infos) {
			am.killBackgroundProcesses(info.processName);
		}
		Toast.makeText(context, "清理完毕^∨^", Toast.LENGTH_SHORT).show();
	}

}
