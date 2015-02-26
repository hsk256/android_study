package com.hsk.mobilesafe.ui;

import com.hsk.mobilesafe.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author heshaokang	
 * 2014-12-13 下午7:39:14
 */
public class SettingClickView extends RelativeLayout {
	private TextView tv_desc;
	private TextView tv_title;
	
	private  String desc_on;
	private String desc_off;
	/*
	 * 初始化布局文件
	 */
	private void initView(Context context) {
		View.inflate(context, R.layout.setting_click_view, this);
		tv_desc = (TextView) findViewById(R.id.tv_desc);
		tv_title = (TextView) findViewById(R.id.tv_title);
		
	}
	

	/**
	 * @param context
	 * @param attrs
	 * @param defStyleAttr
	 */
	public SettingClickView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initView(context);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public SettingClickView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
//		String title = attrs.getAttributeValue("http://schemas.android.com/apk/res/com.hsk.mobilesafe", "title");
//		desc_on = attrs.getAttributeValue("http://schemas.android.com/apk/res/com.hsk.mobilesafe", "desc_on");
//		desc_off = attrs.getAttributeValue("http://schemas.android.com/apk/res/com.hsk.mobilesafe", "desc_off");
//		tv_title.setText(title);
//		setDesc(desc_off);
	}

	/**
	 * @param context
	 */
	public SettingClickView(Context context) {
		super(context);
		initView(context);
	}

	
	public void setDesc(String text) {
		tv_desc.setText(text);
	}
	/**
	 * 设置组合控件的标题
	 */
	
	public void setTitle(String title){
		tv_title.setText(title);
	}
	/**
	 * 设置组合控件的状态
	 */
	
	public void setChecked(boolean checked){
		if(checked){
			setDesc(desc_on);
		}else{
			setDesc(desc_off);
		}
	}
	
}
