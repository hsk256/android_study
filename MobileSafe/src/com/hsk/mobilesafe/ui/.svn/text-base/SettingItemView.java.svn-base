package com.hsk.mobilesafe.ui;

import com.hsk.mobilesafe.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author heshaokang	
 * 2014-12-1 下午4:23:01
 * 自定义组合控件 里面有两个TextView 一个CheckBox 一个View
 */
public class SettingItemView extends RelativeLayout {
	
	private CheckBox cb_status;
	private TextView tv_desc;
	private TextView tv_title;
	private  String desc_on;
	private String desc_off;
	/**
	 * 初始化控件
	 * @param context
	 * 
	 */
	private void iniView(Context context) {
		View.inflate(context, R.layout.setting_item_view,this);
		cb_status = (CheckBox) this.findViewById(R.id.cb_status);
		tv_desc = (TextView) this.findViewById(R.id.tv_desc);
		tv_title = (TextView) this.findViewById(R.id.tv_title);
		
	}
	/**
	 * 检验控件 是否选中
	 */
	public boolean isChecked() {
		return cb_status.isChecked();
	}
	/**
	 * 设置组合控件的状态
	 */
	public void setChecked(boolean checked) {
		if(checked){
			setDesc(desc_on);
		}else{
			setDesc(desc_off);
		}
		cb_status.setChecked(checked);
	}
	/**
	 * 设置 组合控件的描述信息
	 */
	
	public void setDesc(String text){
		tv_desc.setText(text);
	}
	/**
	 * @param context
	 * @param attrs
	 * @param defStyleAttr
	 * @param defStyleRes
	 */
	@SuppressLint("NewApi")
	public SettingItemView(Context context, AttributeSet attrs,
			int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		iniView(context);
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyleAttr
	 */
	public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		iniView(context);
	}

	/**
	 * @param context
	 * @param attrs
	 * 带有两个参数的布局文件
	 */
	public SettingItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		iniView(context);
		String title = attrs.getAttributeValue("http://schemas.android.com/apk/res/com.hsk.mobilesafe", "my_title");
		desc_on = attrs.getAttributeValue("http://schemas.android.com/apk/res/com.hsk.mobilesafe","desc_on");
		desc_off = attrs.getAttributeValue("http://schemas.android.com/apk/res/com.hsk.mobilesafe", "desc_off");
		tv_title.setText(title);
		setDesc(desc_off);
	}

	/**
	 * @param context
	 */
	public SettingItemView(Context context) {
		super(context);
		iniView(context);
	}
	
}
