package com.hsk.mobilesafe;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author heshaokang	
 * 2014-12-5 下午2:31:28
 */
public abstract class BaseSetupActivity extends Activity{
	//定义一个手势识别器
	private GestureDetector detector;
	protected SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		//实例化手势识别器
		detector = new GestureDetector(this, new OnGestureListener() {
			
			@Override
			public boolean onSingleTapUp(MotionEvent e) {
				
				return false;
			}
			
			@Override
			public void onShowPress(MotionEvent e) {
			}
			
			@Override
			public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
					float distanceY) {
				
				return false;
			}
			
			@Override
			public void onLongPress(MotionEvent e) {
			}
			
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
					float velocityY) {
				
				if((e2.getRawX()-e1.getRawX())>100) {
					System.out.println("从左往右滑动，显示上一个页面");
					showPre();
					return true;
				}
				if((e1.getRawX()-e2.getRawX())>100) {
					System.out.println("从右往左滑，显示下一个页面");
					showNext();
					return true;
				}
				return false;
			}
			@Override
			public boolean onDown(MotionEvent e) {
				
				return false;
			}
		});
		
		
	}
	public abstract void showPre() ;
	public abstract void showNext();
	/**
	 * 下一步的点击事件
	 * @param view
	 */
	public void next(View view){
		showNext();
		
	}
	
	/**
	 *   上一步
	 * @param view
	 */
	public void pre(View view){
		showPre();
		
	}
		
	//3.使用手势识别器
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		detector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}
}
