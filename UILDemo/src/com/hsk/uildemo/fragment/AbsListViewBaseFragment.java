package com.hsk.uildemo.fragment;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;

import com.hsk.uildemo.Constants;
import com.hsk.uildemo.R;
import com.hsk.uildemo.SimpleImageActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

/**
 * @author heshaokang	
 * 2015-2-16 下午3:57:09
 */
public class AbsListViewBaseFragment extends BaseFragment {
	protected static final String STATE_PAUSE_ON_SCROLL = "STATE_PAUSE_ON_SCROLL";
	protected static final String STATE_PAUSE_ON_FLING = "STATE_PAUSE_ON_FLING";
	protected AbsListView listview;
	
	protected boolean pauseOnScroll = false;
	protected boolean pauseOnFling = true;
	
	public void startImagePagerActivity(int position) {
		Intent intent = new Intent(getActivity(),SimpleImageActivity.class);
		intent.putExtra(Constants.Extra.FRAGMENT_INDEX, ImagePagerFragment.INDEX);
		intent.putExtra(Constants.Extra.IMAGE_POSITION, position);
		startActivity(intent);
	}
	
	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		MenuItem pauseOnScrollItem = menu.findItem(R.id.item_pause_on_scroll);
		pauseOnScrollItem.setVisible(true);
		pauseOnScrollItem.setCheckable(pauseOnScroll);
		
		MenuItem pauseOnFlingItem = menu.findItem(R.id.item_pause_on_fling);
		pauseOnFlingItem.setVisible(true);
		pauseOnFlingItem.setCheckable(pauseOnFling);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.item_pause_on_fling:
			pauseOnFling = !pauseOnFling;
			item.setCheckable(pauseOnFling);
			applyScrollListener();
			return true;
		case R.id.item_pause_on_scroll:
			pauseOnScroll = !pauseOnScroll;
			item.setCheckable(pauseOnScroll);
			applyScrollListener();
			return true;
		default:
			return false;
		}
		
	}
	
	@Override
	public void onResume() {
		
		super.onResume();
		applyScrollListener();
	}
	
	private void applyScrollListener() {
		listview.setOnScrollListener(
				new PauseOnScrollListener(ImageLoader.getInstance(), pauseOnScroll, pauseOnFling));
	}
	
}
