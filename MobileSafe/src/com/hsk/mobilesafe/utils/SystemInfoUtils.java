package com.hsk.mobilesafe.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;

/**
 * @author heshaokang	
 * 2014-12-20 下午8:14:57
 * 系统信息工具类
 */
public class SystemInfoUtils {
	/**
	 * 获得正在运行的进程的个数
	 */
	public static int getRunningProcessCount(Context context) {
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> infos = am.getRunningAppProcesses();
		return infos.size();
	}
	
	/**
	 * 获取手机可用的剩余内存
	 */
	public static long getAvailMem(Context context) {
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		MemoryInfo outInfo = new MemoryInfo();
		am.getMemoryInfo(outInfo);
		return outInfo.availMem;
	}
	
	/**
	 * 获取手机总的内存
	 */
	public static long getTotalMem(Context context) {
		/*ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		MemoryInfo outInfo = new MemoryInfo();
		am.getMemoryInfo(outInfo);
		return outInfo.totalMem;
		4.0版本一下 上述代码不可用
		*/
		File file = new File("/proc/meminfo");
		try {
			FileInputStream fis = new FileInputStream(file);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			String line = br.readLine();
			StringBuilder sb = new StringBuilder();
			for(char c:line.toCharArray()) {
				if(c>='0'&&c<='9') {
					sb.append(c);
				}
			}
			return Long.parseLong(sb.toString())*1024;
		} catch (Exception e) {
			
			e.printStackTrace();
			return 0;
		}
		
	}
}
