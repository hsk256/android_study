package com.hsk.mobilesafe.engine;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import com.hsk.mobilesafe.domain.AppInfo;

/**
 * @author heshaokang	
 * 2014-12-18 下午12:32:58
 * 获取手机里所有应用的信息
 */
public class AppInfoProvider {
	public static List<AppInfo> getAppInfos(Context context) {
		PackageManager pm = context.getPackageManager();
		List<PackageInfo> packageInfos = pm.getInstalledPackages(0);
		List<AppInfo> appInfos = new ArrayList<>();
		for(PackageInfo packageInfo:packageInfos) {
			AppInfo appInfo = new AppInfo();
			//packageInfo 相当于一个应用的清单文件
			String packageName = packageInfo.packageName;
			Drawable icon = packageInfo.applicationInfo.loadIcon(pm);
			String name = packageInfo.applicationInfo.loadLabel(pm).toString();
			int flags = packageInfo.applicationInfo.flags;
			if((flags&ApplicationInfo.FLAG_SYSTEM)==0) {
				//用户程序
				appInfo.setUserApp(true);
			}else {
				appInfo.setUserApp(false);
			}
			if((flags&ApplicationInfo.FLAG_EXTERNAL_STORAGE)==0) {
				appInfo.setInRom(true);
			}else {
				//手机外部存储设备
				appInfo.setInRom(false);
			}
			
			appInfo.setPackageName(packageName);
			appInfo.setIcon(icon);
			appInfo.setName(name);
			appInfos.add(appInfo);
		}
		return appInfos;
	}
}
