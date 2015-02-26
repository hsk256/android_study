package com.hsk.mobilesafe;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;
import android.widget.Toast;

import com.hsk.mobilesafe.utils.StreamTools;

public class SplashActivity extends Activity {
	protected static final String TAG = "SplashActivity";
	protected static final int SHOW_UPDATE_DIALOG = 0;
	protected static final int ENTER_HOME = 1;
	protected static final int URL_ERROR = 2;
	protected static final int NETWORK_ERROR = 3;
	protected static final int JSON_ERROR = 4;
	private TextView tv_splash_version;
	private String description;
	private TextView tv_update_info;
	//新版本的下载地址
	private String apkurl;
	private SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		tv_splash_version = (TextView) findViewById(R.id.tv_splash_version);
		tv_splash_version.setText("版本号:"+getVersionName());
		tv_update_info = (TextView) findViewById(R.id.tv_update_info);
		boolean update = sp.getBoolean("update", false);
		//第一次启动时 创建快捷方式 
		installShortCut();
		//拷贝数据库
		copyDB("address.db");
		copyDB("antivirus.db");
		if(update) {
			//检查升级
			checkUpdate();
		}else {
			//自动升级关闭
			handler.postDelayed(new Runnable() {
				
				@Override
				public void run() {
					enterHome();
				}
			}, 2000);
		}
		AlphaAnimation aa = new AlphaAnimation(0.2f, 1.0f);
		aa.setDuration(500);
		findViewById(R.id.rl_root_splash).startAnimation(aa);
	}
	private void installShortCut() {
		boolean shortcut = sp.getBoolean("shortcut", false);
		if(shortcut)
			return;
		Editor editor = sp.edit();
		//发送广播的意图， 大吼一声告诉桌面，要创建快捷图标了
		Intent intent = new Intent();
		intent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
		//快捷方式  要包含3个重要的信息 1，名称 2.图标 3.干什么事情
		intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "手机小卫士");
		intent.putExtra(Intent.EXTRA_SHORTCUT_ICON, BitmapFactory.decodeResource(getResources(), R.drawable.apppic));
		//桌面点击图标对应的意图。
		Intent shortcutIntent = new Intent();
		shortcutIntent.setAction("android.intent.action.MAIN");
		shortcutIntent.addCategory("android.intent.category.LAUNCHER");
		shortcutIntent.setClassName(getPackageName(), "com.hsk.mobilesafe.SplashActivity");


		intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
		sendBroadcast(intent);
		editor.putBoolean("shortcut", true);
		editor.commit();
		
	}
	/**
	 * 拷贝归属地数据库
	 */
	private void copyDB(String filename) {
		
		try {
			File file = new File(getFilesDir(),filename);
			if(file.exists()&& file.length()>0) {
				Log.i("SplasgActivity", "不需要拷贝了");
			}else {
				InputStream is = getAssets().open(filename);
				FileOutputStream fos = new FileOutputStream(file);
				byte[] bytes = new byte[1024];
				int len =0;
				while((len=is.read(bytes))!=-1) {
					fos.write(bytes,0,len);
				}
				is.close();
				fos.close();
			}
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}
	private  Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case ENTER_HOME:  //进入主界面
				enterHome();
				
				break;
			case URL_ERROR:
				enterHome();
				Toast.makeText(getApplicationContext(), "url 出错", Toast.LENGTH_SHORT).show();
				break;
			case JSON_ERROR:
				enterHome();
				Toast.makeText(getApplicationContext(), "json解析出错", Toast.LENGTH_SHORT).show();
				break;
			case NETWORK_ERROR:
				enterHome();
				Toast.makeText(getApplicationContext(), "网络错误", Toast.LENGTH_SHORT).show();
				break;
			case SHOW_UPDATE_DIALOG:
				Log.i(TAG, "显示升级的对话框");
				showUpdateDialog();
				break;
			default:
				break;
			}
			
		}
	};
	/**
	 * 显示升级的对话框
	 */
	private void showUpdateDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
		builder.setTitle("升级提示");
		builder.setMessage(description);
		
		
		builder.setNegativeButton("下次再说",new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				enterHome();
			}
		});
		builder.setPositiveButton("立即升级", new OnClickListener() {
			
			@Override
			public void onClick(final DialogInterface dialog, int which) {
				
						//下载apk 并替换安装 这里用到了开源下载的框架 判断是否有sd卡 
						if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
							FinalHttp finalHttp = new FinalHttp();
							//下载的sd卡地址
							String target = Environment.getExternalStorageDirectory().getAbsolutePath()+"mobilesafe2.0.apk";
							System.out.println(apkurl);
							finalHttp.download(apkurl, target, new AjaxCallBack<File>() {
								//若下载失败 则调用此方法
								public void onFailure(Throwable t, int errorNo, String strMsg) {
									t.printStackTrace();
									Toast.makeText(getApplicationContext(), "下载失败", Toast.LENGTH_SHORT).show();
									super.onFailure(t, errorNo, strMsg);
									
								};
								//下载中
								public void onLoading(long count, long current) {
									super.onLoading(count, current);
									tv_update_info.setVisibility(View.VISIBLE);
									//当前下载的百分比
									int progress = (int) (current*100/count);
									tv_update_info.setText("下载进度:"+progress+"%");
								};
								public void onSuccess(File t) {
									super.onSuccess(t);
									installApk(t);
									
								};
								//安装apk
								private void installApk(File t) {
									//调用系统的意图进行apk的安装
									Intent intent = new Intent();
									intent.setAction("android.intent.action.VIEW");
									intent.addCategory("android.intent.category.DEFAULT");
									intent.setDataAndType(Uri.fromFile(t),"application/vnd.android.package-archive");
									startActivity(intent);
								}
							});
						}else {
							Toast.makeText(getApplicationContext(), "没有sd卡，请确保安装了sd卡", Toast.LENGTH_SHORT).show();
							dialog.dismiss();
							enterHome();
						}
						
					}
		});
		builder.show();
	}
	/**
	 * 进入主界面
	 */
	private void enterHome() {
		Intent intent = new Intent(SplashActivity.this,HomeActivity.class);
		startActivity(intent);
		//关闭该页面 否则回退键会在此显示该页面；
		finish();
	};
	/**
	 * 检查版本升级
	 */
	private void checkUpdate() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				long startTime = System.currentTimeMillis();
				Message mes = Message.obtain();
				try {
					URL url = new URL(getString(R.string.server_url));
					//联网
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					//设置请求方式 一定要大写
					conn.setRequestMethod("GET");
					conn.setConnectTimeout(5000);
					int code = conn.getResponseCode();
					if(code==200) {
						//联网成功
						InputStream is = conn.getInputStream();
						//把流对象 装换为String
						String result = StreamTools.readFromStream(is);
						//Log.i(TAG, "联网成功了："+result);
						//json解析
						//这里之前没有传json字符串对象  导致一直有错 太坑了
						JSONObject obj = new JSONObject(result);
						//得到服务器的版本信息
						String version =  obj.getString("version");
						description = (String) obj.get("description");
						apkurl = (String) obj.get("apkurl");
						//检验是否有新版本
						if(getVersionName().equals(version)) {
							mes.what = ENTER_HOME;
						}else {
							mes.what = SHOW_UPDATE_DIALOG;
						}
					}
				} catch (MalformedURLException e) {
					mes.what = URL_ERROR;
					e.printStackTrace();
				} catch (IOException e) {
					mes.what = NETWORK_ERROR;
					e.printStackTrace();
				} catch (JSONException e) {
					mes.what = JSON_ERROR;
					e.printStackTrace();
				}finally {
					long endTime = System.currentTimeMillis();
					long dTime = endTime-startTime;
					if(dTime<2000) {
						try {
							Thread.sleep(2000-dTime);
						} catch (InterruptedException e) {
							
							e.printStackTrace();
						}
					}
					handler.sendMessage(mes);
				}
				
				
			}
		}).start();
		
	}
	/**
	 * 得到应用程序的版本名称
	 */
	private String getVersionName() {
		PackageManager pm = getPackageManager();
		try {
			PackageInfo pi = pm.getPackageInfo(getPackageName(), 0);
			return pi.versionName;
		}catch(NameNotFoundException e) {
			e.printStackTrace();
			return "";
		}
	}
}
