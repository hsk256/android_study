package com.hsk.qq_back_dialog;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

/**
 * @author heshaokang	
 * 2015-2-7 下午7:21:19
 */
public class CustomDialog extends Dialog implements android.view.View.OnClickListener{
    Context context;
	//自定义布局文件
    int layoutRes;
	//确定按钮
	private Button confirmButton;
	private Button cancelButton;
	private RadioButton radioButton;
	//点击次数
	private int check_count=0;
	//Toast持续时间 1000
	public static final int TOAST_TIME = 1000;
	/**
	 * @param context
	 */
	public CustomDialog(Context context) {
		super(context);
		this.context = context;
	}

	/**
	 * @param context
	 * @param theme
	 */
	public CustomDialog(Context context, int resLayout) {
		super(context);
		this.context = context;
		this.layoutRes = resLayout;
	}

	public CustomDialog(Context context,int resLayout,int theme) {
		super(context,theme);
		this.context = context;
		this.layoutRes = resLayout;
	}
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		//指定布局
		this.setContentView(layoutRes);
		confirmButton = (Button) findViewById(R.id.confirm_btn);
		cancelButton = (Button) findViewById(R.id.cancel_btn);
		radioButton = (RadioButton) findViewById(R.id.my_rbtn);
		confirmButton.setTextColor(0xff1E90FF);
		cancelButton.setTextColor(0xff1E90FF);
		
		confirmButton.setOnClickListener(this);
		cancelButton.setOnClickListener(this);
		radioButton.setOnClickListener(this);
		
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.confirm_btn:
			Toast.makeText(context, "您点击了确定按钮", TOAST_TIME).show();
			break;
		case R.id.cancel_btn:
			Toast.makeText(context, "您点击了取消按钮", TOAST_TIME).show();
			break;
		case R.id.my_rbtn:
			// 点击了离线消息按钮
						check_count = check_count + 1;
						if (check_count % 2 == 0) {
							// no checked
							radioButton.setButtonDrawable(context.getResources()
									.getDrawable(R.drawable.radio));
						} else {
							// checked
							radioButton.setButtonDrawable(context.getResources()
									.getDrawable(R.drawable.radio_check));
						}
			break;
		default:
			break;
		}
		
	}

}
