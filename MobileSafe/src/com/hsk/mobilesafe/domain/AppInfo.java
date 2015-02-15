package com.hsk.mobilesafe.domain;

import android.graphics.drawable.Drawable;

/**
 * @author heshaokang	
 * 2014-12-18 下午12:33:16
 * 应用程序的业务
 */
public class AppInfo {
	private Drawable icon;
	private String name;
	private String packageName;
	private boolean inRom;
	private boolean userApp;
	public Drawable getIcon() {
		return icon;
	}
	public void setIcon(Drawable icon) {
		this.icon = icon;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public boolean isInRom() {
		return inRom;
	}
	public void setInRom(boolean inRom) {
		this.inRom = inRom;
	}
	public boolean isUserApp() {
		return userApp;
	}
	public void setUserApp(boolean userApp) {
		this.userApp = userApp;
	}
	@Override
	public String toString() {
		return "AppInfo [name=" + name + ", packageName=" + packageName
				+ ", inRom=" + inRom + ", userApp=" + userApp + "]";
	}
	
}
