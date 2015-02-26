package com.hsk.mobilesafe;

import java.lang.reflect.Method;
import java.util.List;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageDataObserver;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.PackageStats;
import android.os.Bundle;
import android.os.RemoteException;
import android.text.format.Formatter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * @author heshaokang	
 * 2014-12-27 下午10:03:54
 */
public class CleanCacheActivity extends Activity {
	private ProgressBar pb;
	private TextView tv_scan_status;
	private PackageManager pm;
	private LinearLayout ll_container;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clean_cache);
		pb = (ProgressBar) findViewById(R.id.pb);
		tv_scan_status = (TextView) findViewById(R.id.tv_scan_status);
		ll_container = (LinearLayout) findViewById(R.id.ll_container);
		scanCache();
	}
	private void scanCache() {
		pm = getPackageManager();
		new Thread(new Runnable() {
			@Override
			public void run() {
				Method getPackageSizeInfoMethod = null;
				Method[] methods = PackageManager.class.getMethods();
				for(Method method:methods) {
					if ("getPackageSizeInfo".equals(method.getName())) {
						getPackageSizeInfoMethod = method;
					}
				}
				List<PackageInfo> infos = pm.getInstalledPackages(0);
				pb.setMax(infos.size());
				int progress = 0;
				
				for(PackageInfo packInfo:infos) {
					try {
						getPackageSizeInfoMethod.invoke(pm,
								packInfo.packageName,new MyDataObserver());
						Thread.sleep(50);
					} catch (Exception e) {		
						e.printStackTrace();
					}
					progress++;
					pb.setProgress(progress);
				}
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						tv_scan_status.setText("扫描完毕");
					}
				});
			}
		}).start();
	}
	private class MyDataObserver extends IPackageStatsObserver.Stub {

		@Override
		public void onGetStatsCompleted(PackageStats pStats, boolean succeeded)
				throws RemoteException {
			final long cache = pStats.cacheSize;
			final String packname = pStats.packageName;
			final ApplicationInfo appInfo;
			try {
				appInfo = pm.getApplicationInfo(packname, 0);
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						tv_scan_status.setText("正在扫描:"+appInfo.loadLabel(pm));
						if(cache>0) {
							View view = View.inflate(getApplicationContext(), R.layout.list_item_cacheinfo, null);
							TextView tv_cache = (TextView) view.findViewById(R.id.tv_cache_size);
							tv_cache.setText("缓存大小:"+Formatter.formatFileSize(getApplicationContext(), cache));
							TextView tv_name = (TextView) view.findViewById(R.id.tv_app_name);
							tv_name.setText(appInfo.loadLabel(pm));
							ImageView iv_delete = (ImageView) view.findViewById(R.id.iv_delete);
							iv_delete.setOnClickListener(new OnClickListener() {
								@Override
								public void onClick(View v) {
									//deleteApplicationCacheFiles
									try {
										Method method = PackageManager.class.getMethod("deleteApplicationCacheFiles", String.class,
												IPackageDataObserver.class
												);
										method.invoke(pm, packname,new MypackDataObserver());
									} catch (Exception e) {
										e.printStackTrace();
									}
									
								}
							});
							ll_container.addView(tv_cache,0);
						}
					}
				});
				
			} catch (NameNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	private class MypackDataObserver extends IPackageDataObserver.Stub{
		@Override
		public void onRemoveCompleted(String packageName, boolean succeeded)
				throws RemoteException {
			System.out.println(packageName+succeeded);
		}
	}
	/**
	 * 清理手机的全部缓存.
	 * @param view
	 */
	public void clearAll(View view){
		Method[] methods = PackageManager.class.getMethods();
		for(Method method:methods){
			if("freeStorageAndNotify".equals(method.getName())){
				try {
					method.invoke(pm, Integer.MAX_VALUE,new MypackDataObserver());
				} catch (Exception e) {
					e.printStackTrace();
				}
				return;
			}
		}
	}
}
