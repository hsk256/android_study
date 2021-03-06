package com.example.handler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	private Button  start;
	private Button end;
	private Handler myHandler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		start = (Button)findViewById(R.id.start);
		end = (Button)findViewById(R.id.end);
		start.setOnClickListener( new startButtonListener());
		end.setOnClickListener(new endButtonListener());
	}
	class startButtonListener implements OnClickListener {

		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			handler.post(updateThread);
		}
		
	}
	class endButtonListener implements OnClickListener {

		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			handler.removeCallbacks(updateThread);
		}
		
	}
	
		//创建handler对象
	Handler handler = new Handler();
	Runnable updateThread = new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			System.out.println("update thread");
			handler.postDelayed(updateThread, 3000);
		}
	};

}
