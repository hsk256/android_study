package com.hsk.qq_back_dialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener{
	//退出Button
	private Button exit_button;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		exit_button = (Button) findViewById(R.id.exit_button);
		exit_button.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.exit_button:
			CustomDialog dialog = new CustomDialog(this, R.layout.customdialog, R.style.myStyle);
			dialog.show();
			break;

		default:
			break;
		}
	}
	
}
