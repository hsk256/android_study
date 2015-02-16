package com.example.filemanager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author hsk
 *
 */
public class EditTxtActivity extends Activity implements OnClickListener{
	//显示打开的文本内容
	private EditText txtEditText;
	private TextView txtTextTitle;
	private Button txtSaveButton;
	private Button txtCancleButton;
	private String txtData;
	private String txtPath;
	private String txtTitle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_txt);
		initContentView();
		//获得文件路径
		txtPath = getIntent().getStringExtra("path");
		txtData = getIntent().getStringExtra("data");
		txtTitle = getIntent().getStringExtra("title");
		
//		try {
//			txtData = new String(txtData.getBytes("ISO-8859-1"),"GBK");//转码
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
		txtTextTitle.setText(txtTitle);
		txtEditText.setText(txtData);
	}
	
	//组件初始化
	private void initContentView() {
		txtEditText = (EditText)findViewById(R.id.EditTextDetail);
		txtTextTitle = (TextView)findViewById(R.id.TextViewTitle);
		txtSaveButton = (Button)findViewById(R.id.ButtonRefer);
		txtCancleButton = (Button)findViewById(R.id.ButtonBack);
		txtSaveButton.setOnClickListener(this);
		txtCancleButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==txtSaveButton.getId()) {
			saveTxt();
		}else if(v.getId()==txtCancleButton.getId()){
			EditTxtActivity.this.finish();
		}
	}
	//保存编辑后的文本信息
	private void saveTxt() {
		try {
			String newData = txtEditText.getText().toString();
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(txtPath)));
			//写入文件
			bw.write(newData,0,newData.length());
			bw.newLine();
			bw.close();
			Toast.makeText(EditTxtActivity.this, "成功保存", Toast.LENGTH_SHORT).show();
		}catch(IOException e) {
			Toast.makeText(EditTxtActivity.this, "文件存储失败", Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
		this.finish();
	}
}
