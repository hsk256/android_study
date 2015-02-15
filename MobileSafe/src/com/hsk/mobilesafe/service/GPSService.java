package com.hsk.mobilesafe.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

/**
 * @author heshaokang	
 * 2014-12-9 下午1:06:34
 * 
 */
public class GPSService extends Service{
	//用到位置服务
	private LocationManager lm;
	private MyLocationListener listener;
	@Override
	public IBinder onBind(Intent intent) {
		
		return null;
	}
	@Override
	public void onCreate() {
		
		super.onCreate();
		lm = (LocationManager) getSystemService(LOCATION_SERVICE);
		listener = new MyLocationListener();
		//注册监听位置服务
		Criteria criteria = new Criteria();
		String provider = lm.getBestProvider(criteria, true);
		lm.requestLocationUpdates(provider,0, 0, listener);
	}
	@Override
	public void onDestroy() {
		
		super.onDestroy();
		//取消监听服务
		lm.removeUpdates(listener);
		listener = null;
	}
	class MyLocationListener implements LocationListener {
		
		@Override
		public void onLocationChanged(Location location) {
			String longitude = "j:"+location.getLongitude()+"\n";
			String latitude = "w:"+location.getLatitude()+"\n";
			String accuracy = "a:"+location.getAccuracy()+"\n";
			
			SharedPreferences sp = getSharedPreferences("config", MODE_PRIVATE);
			Editor editor = sp.edit();
			editor.putString("location", longitude + latitude + accuracy);
			editor.commit();
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}

		@Override
		public void onProviderEnabled(String provider) {
		}

		@Override
		public void onProviderDisabled(String provider) {
		}
		
	}
}
