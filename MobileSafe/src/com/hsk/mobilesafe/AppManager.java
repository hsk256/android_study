package com.hsk.mobilesafe;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hsk.mobilesafe.db.dao.ApplockDao;
import com.hsk.mobilesafe.domain.AppInfo;
import com.hsk.mobilesafe.engine.AppInfoProvider;
import com.hsk.mobilesafe.utils.DensityUtil;

/**
 * @author heshaokang	
 * 2014-12-18 上午11:52:33
 * app 程序管理
 */
public class AppManager extends Activity implements OnClickListener {
	private static final String TAG = "AppManager";
	private TextView tv_avaiable_rom;
	private TextView tv_avaiable_sd;
	private ListView lv_app_manager;
	private LinearLayout ll_loading;
	private List<AppInfo> appInfos;
	//用户应用程序的集合
	private List<AppInfo> userAppInfos;
	//系统应用程序的集合
	private List<AppInfo> systemAppInfos;
	private AppManagerAdapter adapter;
	private AppInfo appInfo;
	
	private TextView tv_status;
	//弹出悬浮窗体
	private PopupWindow popupWindow;
	private LinearLayout ll_uninstall;
	private LinearLayout ll_start;
	private LinearLayout ll_share;
	private ApplockDao dao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_manager);
		tv_avaiable_rom = (TextView) findViewById(R.id.tv_avaiable_rom);
		tv_avaiable_sd = (TextView) findViewById(R.id.tv_avaiable_sd);
		tv_status = (TextView) findViewById(R.id.tv_status);
		long sdSize = getAvaiableSpace(Environment.getExternalStorageDirectory().getAbsolutePath());
		long romSize = getAvaiableSpace(Environment.getDataDirectory().getAbsolutePath());
		tv_avaiable_rom.setText("SD卡可用:"+Formatter.formatFileSize(this, sdSize));
		tv_avaiable_sd.setText("存储空间可用:"+Formatter.formatFileSize(this, romSize));
		
		lv_app_manager = (ListView) findViewById(R.id.lv_app_manager);
		ll_loading = (LinearLayout) findViewById(R.id.ll_loading);
		dao = new ApplockDao(this);
		
		fillData();
		
		//滚动事件的监听
		lv_app_manager.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				dismissPopupWindow();
				//firstVisibleItem 第一个课件条目的position
				if(userAppInfos!=null && systemAppInfos!=null) {
					if(firstVisibleItem>userAppInfos.size()) {
						tv_status.setText("系统程序:"+systemAppInfos.size()+"个");
					}else {
						tv_status.setText("用户程序:"+userAppInfos.size()+"个");
					}
				}
				
			}
		});
		
		//listView 点击事件
		lv_app_manager.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//当点击textView 时 return 
				if(position==0) {
					
					return ;
				}else if(position==(userAppInfos.size()+1)){
					return ;
				}else if(position<=userAppInfos.size()) {
					int newposition = position-1;
					appInfo = userAppInfos.get(newposition);
				}else {
					int newposition = position-userAppInfos.size()-2;
					appInfo = systemAppInfos.get(newposition);
				}
				//System.out.println(appInfo.getPackageName());
				dismissPopupWindow();
				View contentView= View.inflate(getBaseContext(), R.layout.popup_app_item, null);
				ll_uninstall = (LinearLayout) contentView.findViewById(R.id.ll_uninstall);
				ll_start = (LinearLayout) contentView.findViewById(R.id.ll_start);
				ll_share = (LinearLayout) contentView.findViewById(R.id.ll_share);
				ll_uninstall.setOnClickListener(AppManager.this);
				ll_start.setOnClickListener(AppManager.this);
				ll_share.setOnClickListener(AppManager.this);
				//将dp转换为px 以设配不同分辨率
				int dp = 60;
				int px = DensityUtil.dip2px(getApplicationContext(), dp);
				//弹出气泡窗体
			    popupWindow = new PopupWindow(contentView,-2,-2);
			    //动画效果的播放 必须 设置背景颜色  这里设置为透明
				popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
				int[] location = new int[2];
				view.getLocationInWindow(location);
				popupWindow.showAtLocation(parent, Gravity.LEFT|Gravity.TOP, px, location[1]);
				//动画效果
				ScaleAnimation sa = 
						new ScaleAnimation(0.3f,1.0f,0.3f,1.0f,
						Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,0.5f);
				sa.setDuration(350);
				AlphaAnimation aa = new AlphaAnimation(0.5f, 1.0f);
				aa.setDuration(350);
				AnimationSet set = new AnimationSet(false);
				set.addAnimation(aa);
				set.addAnimation(sa);
				contentView.startAnimation(set);
				
			}
		});
		
		//listView 长按事件
		lv_app_manager.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				//当点击textView 时 return 
				if(position==0) {
					
					return true;
				}else if(position==(userAppInfos.size()+1)){
					return true;
				}else if(position<=userAppInfos.size()) {
					int newposition = position-1;
					appInfo = userAppInfos.get(newposition);
				}else {
					int newposition = position-userAppInfos.size()-2;
					appInfo = systemAppInfos.get(newposition);
				}
				System.out.println("长按监听:"+appInfo.getPackageName());
				ViewHolder viewHolder = (ViewHolder) view.getTag();
				//判断该条目的程序是否在程序锁的数据库里
				if(dao.find(appInfo.getPackageName())) {
					dao.delete(appInfo.getPackageName());
					viewHolder.iv_status.setImageResource(R.drawable.unlock);
				}else {
					dao.add(appInfo.getPackageName());
					viewHolder.iv_status.setImageResource(R.drawable.lock);
				}
				return true;
			}
		});
		
	}
	private void fillData() {
		ll_loading.setVisibility(View.VISIBLE);	
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				appInfos = AppInfoProvider.getAppInfos(AppManager.this);
				userAppInfos = new ArrayList<AppInfo>();
				systemAppInfos = new ArrayList<AppInfo>();
				for(AppInfo appInfo:appInfos) {
					if(appInfo.isUserApp()) {
						userAppInfos.add(appInfo);
					}else {
						systemAppInfos.add(appInfo);
					}
				}
				//加载listView 的数据适配器
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						
						if(adapter==null) {
							adapter = new AppManagerAdapter();
							lv_app_manager.setAdapter(adapter);
						}else {
							adapter.notifyDataSetChanged();
						}
						ll_loading.setVisibility(View.INVISIBLE);
					}
				});
			}
		}).start();
	}
	/**
	 * 
	 * @author heshaokang	
	 * 2014-12-18 下午8:02:57
	 * 关掉窗体
	 */
	private void dismissPopupWindow() {
		if(popupWindow!=null && popupWindow.isShowing()) {
			popupWindow.dismiss();
			popupWindow = null;
		}
	}
	
	
	private class  AppManagerAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			
			return userAppInfos.size()+systemAppInfos.size()+2;
		}

		@Override
		public Object getItem(int position) {
			
			return null;
		}

		@Override
		public long getItemId(int position) {
			
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(position==0) {
				TextView textView = new TextView(getApplicationContext());
				textView.setTextColor(Color.WHITE);
				textView.setBackgroundColor(Color.GRAY);
				textView.setText("用户程序:"+userAppInfos.size()+"个");
				return textView;
			}else if(position<=userAppInfos.size()) {  //用户程序
				int newPosition = position-1;
				System.out.println("用户程序--"+newPosition);
				appInfo = userAppInfos.get(newPosition);
			}else if(position==(userAppInfos.size()+1)){
				TextView textView = new TextView(getApplicationContext());
				textView.setTextColor(Color.WHITE);
				textView.setBackgroundColor(Color.GRAY);
				textView.setText("系统程序:"+systemAppInfos.size()+"个");
				return textView;
			}else {
				int newposition = position-userAppInfos.size()-2;
				System.out.println("系统程序--"+newposition);
				appInfo = systemAppInfos.get(newposition);
			}
			View view;
			ViewHolder viewHolder;
			//不仅要检查convertView 是否为空 还要检查他的类型 否则可能会抛异常
			if(convertView!=null && convertView instanceof RelativeLayout ) {
				view = convertView;
				viewHolder = (ViewHolder) view.getTag();
				
			}else {
				view = View.inflate(getApplicationContext(), R.layout.list_item_appinfo, null);
				viewHolder = new ViewHolder();
				viewHolder.tv_app_name = (TextView) view.findViewById(R.id.tv_app_name);
				viewHolder.tv_location = (TextView) view.findViewById(R.id.tv_app_location);
				viewHolder.iv_app_icon = (ImageView) view.findViewById(R.id.iv_app_icon);
				viewHolder.iv_status = (ImageView) view.findViewById(R.id.iv_status);
				view.setTag(viewHolder);
			}
			
			viewHolder.iv_app_icon.setImageDrawable(appInfo.getIcon());
			viewHolder.tv_app_name.setText(appInfo.getName());
			
			if(appInfo.isInRom()) {
				viewHolder.tv_location.setText("手机内存");
			}else {
				viewHolder.tv_location.setText("外部存储");
			}
			if(dao.find(appInfo.getPackageName())) {
				viewHolder.iv_status.setImageResource(R.drawable.lock);
			}else {
				viewHolder.iv_status.setImageResource(R.drawable.unlock);
			}
			//程序锁图标的点击事件
			viewHolder.iv_status.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Toast.makeText(AppManager.this, "长按列表项进行程序锁定或解锁", Toast.LENGTH_SHORT).show();
				}
			});
			return view;
		}
		
	}
	static class ViewHolder {
		TextView tv_app_name;
		TextView tv_location;
		ImageView iv_app_icon;
		ImageView iv_status;
	}
	/**
	 * 获取某个目录的可用空间 
	 * @param path 传递路径 内部存储空间的 或sd卡的
	 */
	@SuppressWarnings("deprecation")
	private long getAvaiableSpace(String path) {
		StatFs statf = new StatFs(path);
		statf.getBlockCount(); //获取分区的个数
		long size = statf.getBlockSize(); //获取分区的大小
		long count = statf.getAvailableBlocks();
		return count*size;
	}
	@Override
	protected void onDestroy() {
		
		super.onDestroy();
		dismissPopupWindow();
	}
	/**
	 * popupWindow窗体上的点击事件
	 */
	@Override
	public void onClick(View v) {
		dismissPopupWindow();
		switch (v.getId()) {
		case R.id.ll_start:
			
			startApp();
			break;
		case R.id.ll_uninstall:
			if(appInfo.isUserApp()) {
				unInstallApp();
			}else {
				Toast.makeText(this, "Root后才能卸载", Toast.LENGTH_SHORT).show();
			}
		
			break;
		case R.id.ll_share:
			shareApplication();
			break;
		default:
			break;
		}
		
	}
	/**
	 * 分享应用信息
	 */
	private void shareApplication() {
		// Intent { act=android.intent.action.SEND typ=text/plain flg=0x3000000 cmp=com.android.mms/.ui.ComposeMessageActivity (has extras) } from pid 256
		Intent intent = new Intent();
		intent.setAction("android.intent.action.SEND");
		intent.addCategory(Intent.CATEGORY_DEFAULT);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_TEXT, "推荐您使用一款软件,名称叫："+appInfo.getName());
		startActivity(intent);
	}
	
	/**
	 * 卸载应用
	 */
	private void unInstallApp() {
		Intent intent = new Intent();
		intent.setAction("android.intent.action.VIEW");
		intent.setAction("android.intent.action.DELETE");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.setData(Uri.parse("package:"+appInfo.getPackageName()));
		startActivityForResult(intent, 0);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		super.onActivityResult(requestCode, resultCode, data);
		//刷新界面
		fillData();
	}
	/**
	 * 开启应用程序
	 */
	private void startApp() {
		PackageManager pm = getPackageManager();
		Intent intent = pm.getLaunchIntentForPackage(appInfo.getPackageName());
		if(intent!=null) {
			startActivity(intent);
		}else {
			Toast.makeText(this,"无法启动当前应用",0).show();
		}
	}
	
}
