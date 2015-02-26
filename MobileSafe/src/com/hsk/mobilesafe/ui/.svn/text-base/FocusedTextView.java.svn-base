package com.hsk.mobilesafe.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewDebug.ExportedProperty;
import android.widget.TextView;

/**
 * @author heshaokang	
 * 2014-12-1 下午4:14:52
 * 自定义TextView 使其具有焦点
 */
public class FocusedTextView extends TextView {

	/**
	 * @param context
	 * @param attrs
	 * @param defStyleAttr
	 * @param defStyleRes
	 */
	@SuppressLint("NewApi")
	public FocusedTextView(Context context, AttributeSet attrs,
			int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyleAttr
	 */
	public FocusedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public FocusedTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
	}

	/**
	 * @param context
	 */
	public FocusedTextView(Context context) {
		super(context);
		
	}
	@Override
	@ExportedProperty(category = "focus")
	public boolean isFocused() {
		return true;
	}

}
