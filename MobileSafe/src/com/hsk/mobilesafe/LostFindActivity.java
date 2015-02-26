package com.hsk.mobilesafe;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author heshaokang	
 * 2014-12-4 下午11:09:25
 */
public class LostFindActivity extends Activity{
	private SharedPreferences sp;
	private TextView tv_safeNumber ;
	private ImageView iv_protecting;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		Boolean configed = sp.getBoolean("configed", false);
		if(configed) {
			//设置过则停在此页面
			setContentView(R.layout.activity_lost_find);
			tv_safeNumber = (TextView) findViewById(R.id.tv_safeNumber);
			iv_protecting = (ImageView) findViewById(R.id.iv_protecting);
			String safeNumber = sp.getString("safeNumber", "");
			tv_safeNumber.setText(safeNumber);
			boolean protecting = sp.getBoolean("protecting", false);
			if(protecting) {
				iv_protecting.setImageResource(R.drawable.lock);
			}else {
				iv_protecting.setImageResource(R.drawable.unlock);
			}
		}else {
			//还没有做过设置向导
			Intent intent = new Intent(this,Setup1Activity.class);
			startActivity(intent);
			//关闭当前页面
			finish();
		}
	}
	/**
	 * 重新进入手机防盗设置向导页面
	 * @param view
	 */
	public void reEnterSetup(View view){
		Intent intent = new Intent(this,Setup1Activity.class);
		startActivity(intent);
		//关闭当前页面
		finish();
	}

}
