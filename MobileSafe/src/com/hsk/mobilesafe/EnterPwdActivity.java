package com.hsk.mobilesafe;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author heshaokang	
 * 2014-12-23 下午9:39:48
 */
public class EnterPwdActivity extends Activity {
	private EditText et_password;
	private String packname;
	private TextView tv_name;
	private ImageView iv_icon;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_enter_pwd);
		et_password = (EditText) findViewById(R.id.et_password);
		tv_name = (TextView) findViewById(R.id.tv_name);
		iv_icon = (ImageView) findViewById(R.id.iv_icon);
		Intent intent = getIntent();
		packname = intent.getStringExtra("packname");
		PackageManager pm = getPackageManager();
		try {
			ApplicationInfo info = pm.getApplicationInfo(packname, 0);
			tv_name.setText(info.loadLabel(pm));
			iv_icon.setImageDrawable(info.loadIcon(pm));
		} catch (NameNotFoundException e) {
			
			e.printStackTrace();
		}
		
		
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		finish();
	}
	
	@Override
	public void onBackPressed() {
		//回桌面。
//        <action android:name="android.intent.action.MAIN" />
//        <category android:name="android.intent.category.HOME" />
//        <category android:name="android.intent.category.DEFAULT" />
//        <category android:name="android.intent.category.MONKEY"/>
		Intent intent = new Intent();
		intent.setAction("android.intent.action.MAIN");
		intent.addCategory("android.intent.category.HOME");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addCategory("android.intent.category.MONKEY");
		startActivity(intent);
		//所有的activity最小化 不会执行ondestory 只执行 onstop方法。
		
	}
	
	public void click(View view) {
		String pwd = et_password.getText().toString().trim();
		if(TextUtils.isEmpty(pwd)) {
			Toast.makeText(EnterPwdActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
			return;
		}
		if(pwd.equals("123")) {
			Intent intent = new Intent();
			intent.setAction("com.hsk.mobilesafe.tempstop");
			intent.putExtra("packname",packname );
			sendBroadcast(intent);
			finish();
		}else {
			Toast.makeText(EnterPwdActivity.this, "密码不正确", Toast.LENGTH_SHORT).show();
		}
	}
}
