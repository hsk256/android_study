package com.hsk.mobilesafe;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hsk.mobilesafe.utils.MD5Utils;
/**
 * @author heshaokang	
 * 2014-11-29 下午10:49:36
 * 下面这行代码之前一直报错 原来是少写了findViewById前少写了view 导致找不到那个item 所以空指针异常
 * TextView tv_item = (TextView)view.findViewById(R.id.tv_item);
 * 之前没有写这句话 我勒个擦啊 就说Editor editor = sp.edit() 报空指针异常 妈的
 * 看来对SharedPreferences基础不行啊
 * sp = getSharedPreferences("config", MODE_PRIVATE);
 * 
 */
public class HomeActivity extends Activity{
	protected static final String TAG = "HomeActivity";
	private GridView gridView;
	private MyAdapter myAdapter;
	private SharedPreferences sp;
	private static String [] names = {
		"手机防盗","通讯卫士","软件管理",
		"进程管理","流量统计","手机杀毒",
		"缓存清理","高级工具","设置中心"
		
	};
	private static int[] ids = {
		R.drawable.safe,R.drawable.callmsgsafe,R.drawable.app,
		R.drawable.taskmanager,R.drawable.netmanager,R.drawable.trojan,
		R.drawable.sysoptimize,R.drawable.atools,R.drawable.settings
		
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		gridView = (GridView) findViewById(R.id.gridView);
		myAdapter = new MyAdapter();
		gridView.setAdapter(myAdapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent ;
				switch (position) {
				case 0:
					//进入手机防盗页面
					showLostFindDialog();
					break;
				case 1:
					intent = new Intent(HomeActivity.this,CallSmsSafeActivity.class);
					startActivity(intent);
					break;
				case 2:
					intent = new Intent(HomeActivity.this,AppManager.class);
					startActivity(intent);
					break;
				case 3:
					intent = new Intent(HomeActivity.this,TaskManagerActivity.class);
					startActivity(intent);
					break;
				case 5:
					intent = new Intent(HomeActivity.this,AntiVirusActivity.class);
					startActivity(intent);
					break;
				case 6:
					intent = new Intent(HomeActivity.this,CleanCacheActivity.class);
					startActivity(intent);
					break;
				case 7:
					 intent = new Intent(HomeActivity.this,AtoolsActivity.class);
					startActivity(intent);
					break;
				case 8:
					 intent = new Intent(HomeActivity.this,SettingActivity.class);
					startActivity(intent);
					break;
			
				
				default:
					break;
				}
			}
		});
	}
	/**
	 * 判断是否设置过密码
	 */
	private boolean isSetupPwd() {
		String password = sp.getString("password", null);
		return !TextUtils.isEmpty(password);
	}
	protected void showLostFindDialog() {
		//判断是否设置过密码
		if(isSetupPwd()) {
			showEnterDialog();
		}else {
			showSetupPwdDialog();
		}
		
		
	}
	

	private EditText et_setup_pwd;
	private EditText et_setup_confirm;
	private Button ok;
	private Button cancel;
	private AlertDialog dialog;
	/**
	 * 输入密码对话框
	 */
	private void showEnterDialog() {
		AlertDialog.Builder builder = new Builder(HomeActivity.this);
		View view = View.inflate(HomeActivity.this, R.layout.dialog_enter_password, null);
		et_setup_pwd = (EditText) view.findViewById(R.id.et_setup_pwd);
		ok = (Button) view.findViewById(R.id.ok);
		cancel = (Button) view.findViewById(R.id.cancel);
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//得到输入的密码
				String password = MD5Utils.md5Password(et_setup_pwd.getText().toString().trim());
				//取出存储的密码
				String savePassword = sp.getString("password", null);
				if(password.equals(savePassword)) {
					dialog.dismiss();
					Log.i(TAG, "把对话框消掉，进入手机防盗页面");
					Intent intent = new Intent(HomeActivity.this,LostFindActivity.class);
					startActivity(intent);
				}else {
					Toast.makeText(HomeActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
					et_setup_pwd.setText("");
					return;
				}
			}
		});
		dialog = builder.create();
		dialog.setView(view);
		dialog.show();
	}
	/**
	 * 
	 * @author heshaokang	
	 * 2014-12-4 下午10:45:09
	 * 设置密码对话框
	 */
	private void showSetupPwdDialog() {
		AlertDialog.Builder builder = new Builder(HomeActivity.this);
		//自定义一个布局文件
		View view = View.inflate(HomeActivity.this, R.layout.dialog_setup_password, null);
		et_setup_pwd = (EditText) view.findViewById(R.id.et_setup_pwd);
		et_setup_confirm = (EditText) view.findViewById(R.id.et_setup_confirm);
		ok = (Button) view.findViewById(R.id.ok);
		cancel = (Button) view.findViewById(R.id.cancel);
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}

		});
		ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//得到输入的密码
				String password = et_setup_pwd.getText().toString().trim();
				String pwd_confirm = et_setup_confirm.getText().toString().trim();
				if(TextUtils.isEmpty(password)||TextUtils.isEmpty(pwd_confirm)) {
					Toast.makeText(HomeActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
					return ;
				}
				//判断是否一致
				if(password.equals(pwd_confirm)) {
					Editor editor = sp.edit();
					editor.putString("password", MD5Utils.md5Password(password));//保存加密后的
					editor.commit();
					dialog.dismiss();
					Log.i(TAG, "一致的话，就保存密码，把对话框消掉，还要进入手机防盗页面");
					//密码一致的话 进入手机防盗页面
					Intent intent = new Intent(HomeActivity.this,LostFindActivity.class);
					startActivity(intent);
					
				}else {
					Toast.makeText(HomeActivity.this, "密码不一致", Toast.LENGTH_SHORT).show();
					return ;
				}
			}
		});
		dialog = builder.create();
		dialog.setView(view,0,0,0,0);
		dialog.show();
	}
	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			
			return names.length;
		}

		@Override
		public Object getItem(int position) {
			
			return null;
		}

		@Override
		public long getItemId(int position) {
			
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view= View.inflate(HomeActivity.this, R.layout.list_item_home, null);
			TextView tv_item = (TextView)view.findViewById(R.id.tv_item);
			tv_item.setText(names[position]);
			ImageView iv_item = (ImageView) view.findViewById(R.id.iv_item);
			iv_item.setImageResource(ids[position]);
			return view;
		}
		
	}
}
