package com.hsk.swiperefresh;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity implements OnRefreshListener{
	private static final int REFRESH_COMPLETE = 0x110;
	private SwipeRefreshLayout mSwipeRefreshLayout;
	private ListView mListView;
	private ArrayAdapter<String> mAdapter;
	private List<String> mDatas=new ArrayList<String>(Arrays.asList("java","php","c","c++","js"));
	
	
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if(msg.what==REFRESH_COMPLETE) {
				mDatas.addAll(Arrays.asList("你好","我好","大家好"));
				mAdapter.notifyDataSetChanged();
				mSwipeRefreshLayout.setRefreshing(false);
			}
			
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mListView = (ListView) findViewById(R.id.id_listview);
		mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.id_swipe_ly);
		mSwipeRefreshLayout.setOnRefreshListener(this);
		mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_light,
				android.R.color.holo_orange_light, android.R.color.holo_red_light);
		mAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mDatas);
		mListView.setAdapter(mAdapter);
		
	}

	@Override
	public void onRefresh() {
		mHandler.sendEmptyMessageDelayed(REFRESH_COMPLETE, 2000);
	}
}
