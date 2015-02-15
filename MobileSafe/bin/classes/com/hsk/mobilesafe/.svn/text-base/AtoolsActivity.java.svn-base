package com.hsk.mobilesafe;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.hsk.mobilesafe.utils.SmsUtils;
import com.hsk.mobilesafe.utils.SmsUtils.BackUpCallBack;

/**
 * @author heshaokang	
 * 2014-12-11 下午4:42:09
 */
public class AtoolsActivity extends Activity {
	private ProgressDialog pd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_atools);
	}
	public void numberQuery(View view) {
		Intent intent = new Intent(AtoolsActivity.this,NumberAddressQueryActivity.class);
		startActivity(intent);
	}
	
	public void smsBackup(View view) {
		pd = new ProgressDialog(this);
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.setMessage("正在备份短信");
		pd.show();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				try {
					SmsUtils.backupSms(getApplicationContext(), new BackUpCallBack() {
						
						@Override
						public void onSmsBackup(int progress) {
							
							pd.setProgress(progress);
						}
						
						@Override
						public void beforeBackup(int max) {
							pd.setMax(max);
						}
						
					});
					runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							Toast.makeText(AtoolsActivity.this, "备份成功", Toast.LENGTH_SHORT).show();
							
						}
					});
					
				} catch (Exception e) {
					
					e.printStackTrace();
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(AtoolsActivity.this, "备份失败", 0).show();
						}
					});
				}finally {
					pd.dismiss();
				}
			}
		}).start();
		
	}
	public void smsRestore(View view) {
		
	}
}
