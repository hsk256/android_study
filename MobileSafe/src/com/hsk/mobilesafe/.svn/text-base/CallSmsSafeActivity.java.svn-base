package com.hsk.mobilesafe;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Path.FillType;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hsk.mobilesafe.db.dao.BlackNumberDao;
import com.hsk.mobilesafe.domain.BlackNumberInfo;

/**
 * @author heshaokang	
 * 2014-12-15 上午9:51:12
 */
public class CallSmsSafeActivity extends Activity {
	private ListView lv_callsms_safe;
	private List<BlackNumberInfo> infos;
	private BlackNumberDao dao;
	private CallSmsSafeAdapter adapter;
	private LinearLayout ll_loading;
	
	private int offset = 0;
	private int maxNumber = 20;
	//数据库总记录
	private int count;
	private TextView loading_text;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_call_sms_safe);
		lv_callsms_safe = (ListView) findViewById(R.id.lv_callsms_safe);
		dao = new BlackNumberDao(this);
	
		ll_loading = (LinearLayout) findViewById(R.id.ll_loading);
		 loading_text = (TextView) findViewById(R.id.loading_text);
		//ll_loading.setVisibility(View.VISIBLE);
		fillData();
		lv_callsms_safe.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				switch (scrollState) {
				case OnScrollListener.SCROLL_STATE_IDLE:  //空闲状态
					//获取最后一个可见条目在集合里的位置
					int lastPosition = lv_callsms_safe.getLastVisiblePosition();
					if(lastPosition==(infos.size()-1)) {
						System.out.println("滑行到了最后一列");
						offset+=maxNumber;
						
						count = dao.getCount();
						System.out.println(count);
						if(offset<=count) {
							fillData();
						}else {
							loading_text.setText("没有数据了哦");
						}
						
					}
					break;
				case OnScrollListener.SCROLL_STATE_FLING:  //惯性滑行状态
					break;
				case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL: //手指触摸滚定
					
					break;
				default:
					break;
				}
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
			}
		});
		
		
		
		
		
	}
	private void fillData() {
		ll_loading.setVisibility(View.VISIBLE);
		//在子线程中执行数据的查找
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						if(infos==null) {
							infos = dao.findPart(offset, maxNumber);
						}else {
							infos.addAll(dao.findPart(offset, maxNumber));
						}
						
						runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								ll_loading.setVisibility(View.INVISIBLE);
								if(adapter==null) {
									adapter = new CallSmsSafeAdapter();
									lv_callsms_safe.setAdapter(adapter);
								}else {
									adapter.notifyDataSetChanged();
								}
							
								
							}
						});
					}
					
					
				}).start();
	}
	private class CallSmsSafeAdapter extends BaseAdapter {
		
		@Override
		public int getCount() {
			
			return infos.size();
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
		public View getView(final int position, View convertView, ViewGroup parent) {
			View view;
			ViewHolder holder;
			//复用view 对象
			if(convertView==null) {
				view = View.inflate(getApplicationContext()
							,R.layout.list_item_callsms , null);
				
				holder = new ViewHolder();
				holder.tv_number = (TextView) view.findViewById(R.id.tv_black_number);
				holder.tv_mode = (TextView) view.findViewById(R.id.tv_block_mode);
				holder.iv_delete = (ImageView) view.findViewById(R.id.iv_delete);
				view.setTag(holder);
			}else {
				view = convertView;
				holder = (ViewHolder) view.getTag();
			}
			holder.tv_number.setText(infos.get(position).getNumber());
			String mode = infos.get(position).getMode();
			if(mode.equals("1")) {
				holder.tv_mode.setText("电话拦截");
			}else if("2".equals(mode)){
				holder.tv_mode.setText("短信拦截");
			}else if("3".equals(mode)) {
				holder.tv_mode.setText("全部拦截");
			}
			
			holder.iv_delete.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					AlertDialog.Builder builder = new AlertDialog.Builder(CallSmsSafeActivity.this);
					builder.setTitle("提示");
					builder.setMessage("真的要删除这条黑名单吗");
					builder.setNegativeButton("取消", null);
					builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dao.delete(infos.get(position).getNumber());
							//更新界面
							infos.remove(position);
							adapter.notifyDataSetChanged();
						}
					});
					builder.show();
				}
			});
			
			return view;
		}
		
	}
	/**
	 * view对象的容器
	 *记录孩子的内存地址。
	 *相当于一个记事本
	 */
	static class ViewHolder {
		TextView tv_number;
		TextView tv_mode;
		ImageView iv_delete;
	}
	private EditText et_blacknumber;
	private CheckBox cb_phone;
	private CheckBox cb_sms;
	private Button bt_ok;
	private Button bt_cancel;
	
	public void addBlackNumber(View view) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		final AlertDialog dialog = builder.create();
		View contentView = View.inflate(this, R.layout.dialog_add_blacknumber, null);
		et_blacknumber = (EditText) contentView.findViewById(R.id.et_blacknumber);
		cb_phone = (CheckBox) contentView.findViewById(R.id.cb_phone);
		cb_sms = (CheckBox) contentView.findViewById(R.id.cb_sms);
		bt_ok = (Button) contentView.findViewById(R.id.ok);
		bt_cancel = (Button) contentView.findViewById(R.id.cancel);
		dialog.setView(contentView,0,0,0,0);
		dialog.show();
		bt_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}		
		});
		bt_ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				String blacknumber = et_blacknumber.getText().toString().trim();
				if(TextUtils.isEmpty(blacknumber)) {
					Toast.makeText(CallSmsSafeActivity.this, "号码不能为空", Toast.LENGTH_SHORT).show();
					return;
				}
				String mode;
				if(cb_phone.isChecked()&&cb_sms.isChecked()){
					//全部拦截
					mode = "3";
				}else if(cb_phone.isChecked()){
					//电话拦截
					mode = "1";
				}else if(cb_sms.isChecked()){
					//短信拦截
					mode = "2";
				}else{
					Toast.makeText(getApplicationContext(), "请选择拦截模式", 0).show();
					return;
				}
				//将数据添加到数据库
				dao.add(blacknumber, mode);
				//更新list列表
				BlackNumberInfo info = new BlackNumberInfo();
				info.setMode(mode);
				info.setNumber(blacknumber);
				infos.add(0,info);
				//通知listview数据适配器更新数据了
				adapter.notifyDataSetChanged();
				dialog.dismiss();
			}
		});
	}
}
