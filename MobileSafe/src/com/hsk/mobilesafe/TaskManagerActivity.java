package com.hsk.mobilesafe;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hsk.mobilesafe.domain.TaskInfo;
import com.hsk.mobilesafe.engine.TaskInfoProvider;
import com.hsk.mobilesafe.utils.SystemInfoUtils;

/**
 * @author heshaokang	
 * 2014-12-20 下午8:05:45
 */
public class TaskManagerActivity extends Activity {
	//进程个数 
	private TextView tv_process_count;
	//剩余信息
	private TextView tv_mem_info;
	
	private int processCount;
	private long availMem;
	private long totalMem;
	private ListView lv_task_manager;
	private TaskManagerAdapter adapter;
	private List<TaskInfo> allTaskInfos;
	private List<TaskInfo> userTaskInfos;
	private List<TaskInfo> systemTaskInfos;
	private LinearLayout ll_loading;
	private TextView tv_status;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_manager);
		tv_process_count = (TextView) findViewById(R.id.tv_process_count);
		tv_mem_info = (TextView) findViewById(R.id.tv_mem_info);
		setTitle();
		lv_task_manager = (ListView) findViewById(R.id.lv_task_manager);
		ll_loading = (LinearLayout) findViewById(R.id.ll_loading);
		tv_status = (TextView) findViewById(R.id.tv_status);
		fiiData();
		
		lv_task_manager.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if(userTaskInfos!=null & systemTaskInfos!=null) {
					if(firstVisibleItem>userTaskInfos.size()) {
						tv_status.setText("系统进程:"+systemTaskInfos.size()+"个");
					}else {
						tv_status.setText("用户进程:"+userTaskInfos.size()+"个");
					}
				}
			}
		});
		lv_task_manager.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				TaskInfo taskInfo;
				if(position==0) {
					return ;
				}else if(position==(userTaskInfos.size()+1)) {
					return ;
				}else if(position<=userTaskInfos.size()) {
					taskInfo = userTaskInfos.get(position-1);
				}else {
					taskInfo = systemTaskInfos.get(position-userTaskInfos.size()-2);
				}
				//不让点自己当前的进程
				if(getPackageName().equals(taskInfo.getPackname())) {
					return ;
				}
				ViewHolder viewHolder = (ViewHolder) view.getTag();
				if (taskInfo.isChecked()) {
					taskInfo.setChecked(false);
					viewHolder.cb_status.setChecked(false);
				} else {
					taskInfo.setChecked(true);
					viewHolder.cb_status.setChecked(true);
				}
				
			}
		});
	}
	private void setTitle() {
		processCount = SystemInfoUtils.getRunningProcessCount(this);
		tv_process_count.setText("运行中的进程:"+processCount+"个");
		availMem = SystemInfoUtils.getAvailMem(this);
		totalMem = SystemInfoUtils.getTotalMem(this);
		tv_mem_info.setText("剩余/总内存:"+Formatter.formatFileSize(this, availMem)+"/"+
		Formatter.formatFileSize(this, totalMem));
		
	}
	private void fiiData() {
		ll_loading.setVisibility(View.VISIBLE);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				allTaskInfos = TaskInfoProvider.getTaskInfos(getApplicationContext());
				userTaskInfos = new ArrayList<TaskInfo>();
				systemTaskInfos = new ArrayList<TaskInfo>();
				for(TaskInfo infos:allTaskInfos) {
					if(infos.isUserTask()) {
						userTaskInfos.add(infos);
					}else {
						systemTaskInfos.add(infos);
					}
				}
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						ll_loading.setVisibility(View.INVISIBLE);
						if (adapter == null) {
							adapter = new TaskManagerAdapter();
							lv_task_manager.setAdapter(adapter);
						} else {
							adapter.notifyDataSetChanged();
						}
						lv_task_manager.setAdapter(adapter);
						setTitle();
					}
				});
			}
		}).start();
	}
	private class TaskManagerAdapter extends BaseAdapter {

		private static final String TAG = " MyTaskManagerAdapter";

		@Override
		public int getCount() {
			SharedPreferences sp = getSharedPreferences("config",MODE_PRIVATE);
			if(sp.getBoolean("show_system", false)) {
				return userTaskInfos.size()+systemTaskInfos.size()+2;
			}else {
				return userTaskInfos.size()+1;
			}
			
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
			TaskInfo taskInfo;
			if(position==0) {
				TextView tv = new TextView(getApplicationContext());
				tv.setBackgroundColor(Color.GRAY);
				tv.setTextColor(Color.WHITE);
				tv.setText("用户进程：" + userTaskInfos.size() + "个");
				return tv;
			}else if(position==(userTaskInfos.size()+1)) {
				TextView tv = new TextView(getApplicationContext());
				tv.setBackgroundColor(Color.GRAY);
				tv.setTextColor(Color.WHITE);
				tv.setText("系统进程：" + systemTaskInfos.size() + "个");
				return tv;
			}else if(position<=userTaskInfos.size()){
				taskInfo = userTaskInfos.get(position-1);
			}else {
				taskInfo = systemTaskInfos.get(position-userTaskInfos.size()-2);
			}
			ViewHolder viewHolder;
			View view;
			if(convertView!=null && convertView instanceof RelativeLayout) {
				view = convertView;
				viewHolder = (ViewHolder) view.getTag();
				Log.i(TAG, "复用缓存。。" +taskInfo.getName()+"--"+ position);
			}else {
				 view = View.inflate(getApplicationContext(), R.layout.list_item_taskinfo, null);
				viewHolder = new ViewHolder();
				viewHolder.iv_icon = (ImageView) view.findViewById(R.id.iv_task_icon);
				viewHolder.tv_name = (TextView) view.findViewById(R.id.tv_task_name);
				viewHolder.tv_memsize = (TextView) view.findViewById(R.id.tv_task_memsize);
				viewHolder.cb_status = (CheckBox) view.findViewById(R.id.cb_status);
				view.setTag(viewHolder);
				Log.i(TAG, "创建对象。。" + taskInfo.getName()+"--"+position);
			}
			viewHolder.iv_icon.setImageDrawable(taskInfo.getIcon());
			viewHolder.tv_memsize.setText("内存占用："
					+ Formatter.formatFileSize(getApplicationContext(),
							taskInfo.getMemsize()));
			viewHolder.tv_name.setText(taskInfo.getName());
			//这个错误 找了好久啊 妈的 少写了这一句
			viewHolder.cb_status.setChecked(taskInfo.isChecked());
			if (getPackageName().equals(taskInfo.getPackname())) {
				viewHolder.cb_status.setVisibility(View.INVISIBLE);
			} else {
				viewHolder.cb_status.setVisibility(View.VISIBLE);
			}
			return view;
		}
		
	}
	
	static class ViewHolder {
		ImageView iv_icon;
		TextView tv_name;
		TextView tv_memsize;
		CheckBox cb_status;
	}
	/**
	 * xml定义的点击事件 这里必须是public  否则会出错
	 */
	public void selectAll(View view) {
		for(TaskInfo info:allTaskInfos) {
			if(getPackageName().equals(info.getPackname())) {
				continue;
			}
			info.setChecked(true);
		}
		adapter.notifyDataSetChanged();
	}
	public void selectOppo(View view) {
		for(TaskInfo info:allTaskInfos) {
			if(getPackageName().equals(info.getPackname())) {
				continue;
			}
			info.setChecked(!info.isChecked());
		}
		adapter.notifyDataSetChanged();
	}
	public void killAll(View view) {
		ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		int count = 0;
		int saveMem =0;
		List<TaskInfo> killedTaskInfo = new ArrayList<TaskInfo>();
		for(TaskInfo info:allTaskInfos) {
			if(info.isChecked()) { //杀死勾选的进程
				am.killBackgroundProcesses(info.getPackname());
				//将杀死的进程从集合中移除
				if(info.isUserTask()) {
					userTaskInfos.remove(info);
				}else {
					systemTaskInfos.remove(info);
				}
				killedTaskInfo.add(info);
				count++;
				saveMem+=info.getMemsize();
			}
		}
		allTaskInfos.remove(killedTaskInfo);
		adapter.notifyDataSetChanged();
		Toast.makeText(
				this,
				"杀死了" + count + "个进程，释放了"
						+ Formatter.formatFileSize(this, saveMem) + "的内存", 1)
				.show();
		processCount -= count;
		availMem += saveMem;
		tv_process_count.setText("运行中的进程：" + processCount + "个");
		tv_mem_info.setText("剩余/总内存："
				+ Formatter.formatFileSize(this, availMem) + "/"
				+ Formatter.formatFileSize(this, totalMem));
		
	}
	
	public void enterSetting(View view) {
		Intent intent = new Intent(this, TaskSettingActivity.class);
		startActivityForResult(intent, 0);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		adapter.notifyDataSetChanged();
		super.onActivityResult(requestCode, resultCode, data);
	}
}
