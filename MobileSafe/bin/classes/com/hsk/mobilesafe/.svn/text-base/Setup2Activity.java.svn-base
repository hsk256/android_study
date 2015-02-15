package com.hsk.mobilesafe;

import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.hsk.mobilesafe.ui.SettingItemView;

/**
 * @author heshaokang	
 * 2014-12-5 下午2:55:34
 */
public class Setup2Activity extends BaseSetupActivity {
	private SettingItemView siv_setup2_sim;
	private TelephonyManager tm;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup2);
		siv_setup2_sim = (SettingItemView) findViewById(R.id.siv_setup2_sim);
		tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		String sim = sp.getString("sim", null);
		if(TextUtils.isEmpty(sim)) {
			//没有绑定
			siv_setup2_sim.setChecked(false);
		}else {
			siv_setup2_sim.setChecked(true);
		}
		siv_setup2_sim.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Editor editor = sp.edit();
				if(siv_setup2_sim.isChecked()) {
					siv_setup2_sim.setChecked(false);
					editor.putString("sim", null);
				}else {
					siv_setup2_sim.setChecked(true);
					//sim 序列号
					String sim = tm.getSimSerialNumber();
					editor.putString("sim", "sim");
				}
				editor.commit();
			}
		});
	}
	@Override
	public void showPre() {
		Intent intent = new Intent(this,Setup1Activity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
	}

	@Override
	public void showNext() {
		String sim = sp.getString("sim", null);
		
		if(TextUtils.isEmpty(sim)) {
			Toast.makeText(Setup2Activity.this, "sim 卡没有绑定", Toast.LENGTH_SHORT).show();
			return ;
		}
		Intent intent = new Intent(this,Setup3Activity.class);
		startActivity(intent);
		finish();
		//要求在finish或startActivity后执行动画效果
		overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
	}
	
}
