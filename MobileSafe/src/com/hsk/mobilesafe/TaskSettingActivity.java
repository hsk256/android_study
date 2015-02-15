package com.hsk.mobilesafe;

import com.hsk.mobilesafe.service.AutoCleanService;
import com.hsk.mobilesafe.utils.ServiceUtils;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

/**
 * @author heshaokang	
 * 2014-12-21 下午2:20:43
 */
public class TaskSettingActivity extends Activity{
	private CheckBox cb_show_system;
	private CheckBox cb_auto_clean;
	private SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_setting);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		cb_show_system = (CheckBox) findViewById(R.id.cb_show_system);
		cb_auto_clean = (CheckBox) findViewById(R.id.cb_auto_clean);
		cb_show_system.setChecked(sp.getBoolean("show_system", false));
		cb_show_system.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				Editor edit = sp.edit();
				edit.putBoolean("show_system", isChecked);
				edit.commit();
			}
		});
		cb_auto_clean.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				
				//锁屏广播事件只能在代码里注册生效
				Intent intent = new Intent(TaskSettingActivity.this,AutoCleanService.class);
				if(isChecked){
					startService(intent);
				}else{
					stopService(intent);
				}
				
			}
		});
	}
	@Override
	protected void onStart() {
		boolean running = ServiceUtils.isServiceRunning(this, "com.hsk.mobilesafe.service.AutoCleanService");
		System.out.println(running);
		cb_auto_clean.setChecked(running);
		super.onStart();
	}
}
