package com.hsk.refresh;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ArrayAdapter;

import com.hsk.refresh.view.XListView;
import com.hsk.refresh.view.XListView.IXListViewListener;

public class MainActivity extends Activity implements IXListViewListener{
	private XListView mListView;
	private ArrayAdapter<String> mAdapter;
	private ArrayList<String> items = new ArrayList<String>();
	private Handler mHandler;
	private int start = 0;
	private static int refreshCnt = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		geneItems();
		mListView = (XListView) findViewById(R.id.xListView);
		mListView.setPullLoadEnable(true);
		mAdapter = new ArrayAdapter<String>(this, R.layout.list_item,items);
		mListView.setAdapter(mAdapter);
		//mListView.setPullLoadEnable(false); 隐藏footer view
		mListView.setXListViewListener(this);
		mHandler = new Handler();
	}
	
	private void geneItems() {
		for (int i = 0; i != 20; ++i) {
			items.add("refresh count----> " + (++start));
		}
	}

	@Override
	public void onRefresh() {
		mHandler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				start = ++refreshCnt;
				items.clear();
				geneItems();
				// mAdapter.notifyDataSetChanged();
				mAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.list_item, items);
				mListView.setAdapter(mAdapter);
				onLoad();
			}
		}, 2000);
		
	}

	protected void onLoad() {
		mListView.stopRefresh();
		mListView.stopLoadMore();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		mListView.setRefreshTime(df.format(new Date()));
		
	}

	@Override
	public void onLoadMore() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				geneItems();
				mAdapter.notifyDataSetChanged();
				onLoad();
			}
		}, 2000);
		
	}
	
}
