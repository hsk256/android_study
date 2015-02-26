package com.hsk.mobilesafe;

import android.content.Intent;
import android.os.Bundle;

/**
 * @author heshaokang	
 * 2014-12-5 下午2:30:55
 */
public class Setup1Activity extends BaseSetupActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup1);
	}
	@Override
	public void showPre() {
		
	}

	@Override
	public void showNext() {
		Intent intent = new Intent(this,Setup2Activity.class);
		startActivity(intent);
		finish();
		//要求在finish或startActivity后执行动画效果
		overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
	}
	
}
