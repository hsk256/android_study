package com.hsk.mobilesafe.utils;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;

/**
 * @author heshaokang	
 * 2014-12-12 下午6:45:08
 */
public class ServiceUtils {
	/**
	 * 检验某个service服务是否还活着
	 * 
	 */
	public static boolean isServiceRunning(Context context,String serviceName) {
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningServiceInfo> infos = am.getRunningServices(100);
		for(RunningServiceInfo info:infos) {
			String name = info.service.getClassName();
			if(serviceName.equals(name)) {
				return true;
			}
		}
		return   false;
	}
}
