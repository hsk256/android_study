package com.hsk.mobilesafe;

import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @author heshaokang	
 * 2014-12-5 下午2:55:34
 */
public class Setup3Activity extends BaseSetupActivity {
	private EditText et_setup3_phone;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup3);
		et_setup3_phone = (EditText) findViewById(R.id.et_setup3_number);
		et_setup3_phone.setText(sp.getString("safeNumber", ""));
	}
	@Override
	public void showPre() {
		Intent intent = new Intent(this,Setup2Activity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
	}

	@Override
	public void showNext() {
		String phone = et_setup3_phone.getText().toString().trim();
		if(TextUtils.isEmpty(phone)) {
			Toast.makeText(Setup3Activity.this, "安全号码还没有设置", Toast.LENGTH_SHORT).show();
			return;
		}
		
		Editor editor = sp.edit();
		editor.putString("safeNumber", phone);
		editor.commit();
		
		Intent intent = new Intent(this,Setup4Activity.class);
		startActivity(intent);
		finish();
		//要求在finish或startActivity后执行动画效果
		overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
	}
	/**
	 * 选择联系人
	 */
	public void selectContact(View view) {
		Intent intent = new Intent(Setup3Activity.this,SelectContactActivity.class);
		//startActivity(intent);
		startActivityForResult(intent, 0);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		super.onActivityResult(requestCode, resultCode, data);
		
		
		if(data==null) {
			return ;
		}
		String phone = data.getStringExtra("phone");
		System.out.println("得到:"+phone);
		et_setup3_phone.setText(phone);
	}
}
