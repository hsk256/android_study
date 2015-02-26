package com.hsk.mobilesafe;

import com.hsk.mobilesafe.db.dao.NumberAddressQueryUtils;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

/**
 * @author heshaokang	
 * 2014-12-11 下午4:50:41
 */
public class NumberAddressQueryActivity extends Activity{
	private EditText ed_phone;
	private TextView result;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_number_addres_query);
		ed_phone = (EditText) findViewById(R.id.ed_phone);
		result = (TextView) findViewById(R.id.result);
		ed_phone.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				String address = NumberAddressQueryUtils.queryNumber(s.toString());
				result.setText(address);
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
			}
		});
	}
	/*
	 * 
	 * 点击查询按钮
	 */
	public void numberAddressQuery(View view) {
		String phone = ed_phone.getText().toString().trim();
		if(TextUtils.isEmpty(phone)) {
			//当输入为架空时  editText抖动
			Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
			ed_phone.startAnimation(shake);
			return ;
		}else {
			//工具类 用来查询数据库
			
			String address = NumberAddressQueryUtils.queryNumber(phone);
			result.setText(address);
		}
	}
}
