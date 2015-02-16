package com.hsk.uildemo;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;

/**
 * @author heshaokang	
 * 2015-2-16 下午2:11:42
 * 初始化 ImageLoader
 */
public class UILApplication extends Application {
	@SuppressWarnings("unused")
	@Override
	public void onCreate() {
		
		super.onCreate();
		if (Constants.Config.DEVELOPER_MODE && Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyDialog().build());
			StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyDeath().build());
		}
		initImageLoader(getApplicationContext());
	}
	public void initImageLoader(Context context) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
		.threadPriority(Thread.NORM_PRIORITY-2)
		.denyCacheImageMultipleSizesInMemory()
		.diskCacheFileNameGenerator(new Md5FileNameGenerator())
		.diskCacheSize(50*1024*1024)
		.tasksProcessingOrder(QueueProcessingType.LIFO)
		.writeDebugLogs()
		.build();
		ImageLoader.getInstance().init(config);
	}
}
