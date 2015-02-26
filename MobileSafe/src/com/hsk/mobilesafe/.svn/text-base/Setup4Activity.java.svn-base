package com.hsk.mobilesafe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

/**
 * @author heshaokang	
 * 2014-12-5 下午2:55:34
 */
public class Setup4Activity extends BaseSetupActivity {
	private SharedPreferences sp;
	private CheckBox cb_protecting;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup4);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		cb_protecting = (CheckBox) findViewById(R.id.cb_protecting);
		boolean protecting = sp.getBoolean("protecting", false);
		if(protecting) {
			//手机防盗已开启
			cb_protecting.setText("手机防盗已开启");
			cb_protecting.setChecked(true);
		}else {
			cb_protecting.setText("手机防盗未开启");
			cb_protecting.setChecked(false);
		}
		cb_protecting.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked) {
					cb_protecting.setText("手机防盗已经开启");
				}else {
					cb_protecting.setText("手机防盗没有开启");
				}
				//保存选择的状态
				Editor editor = sp.edit();
				editor.putBoolean("protecting", isChecked);
				editor.commit();
			}
		});
	}
	@Override
	public void showPre() {
		Intent intent = new Intent(this,Setup3Activity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
	}

	@Override
	public void showNext() {
		Editor editor = sp.edit();
		editor.putBoolean("configed", true);
		editor.commit();
		//设置完成 回到防盗页面
		Intent intent = new Intent(this,LostFindActivity.class);
		startActivity(intent);
		finish();
		//要求在finish或startActivity后执行动画效果
		overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
	}
	
}
