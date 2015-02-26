package com.hsk.uildemo.fragment;

import com.hsk.uildemo.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * @author heshaokang	
 * 2015-2-16 下午3:37:18
 */
public abstract class BaseFragment extends Fragment {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		//在fragment里设置此为true menu才会生效
		setHasOptionsMenu(true);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.main, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.item_clear_memory_cache:
			ImageLoader.getInstance().clearMemoryCache();
			return true;
		case R.id.item_clear_disc_cache:
			ImageLoader.getInstance().clearDiskCache();
			return true;
		default:
			return false;
		}
		
	}
}
