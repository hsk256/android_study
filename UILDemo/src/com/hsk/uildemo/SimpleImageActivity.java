package com.hsk.uildemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.hsk.uildemo.fragment.ImageListFragment;
import com.hsk.uildemo.fragment.ImagePagerFragment;
/**
 * @author heshaokang	
 * 2015-2-16 下午3:26:12
 */
public class SimpleImageActivity extends FragmentActivity {
	@Override
	protected void onCreate(Bundle arg0) {
		
		super.onCreate(arg0);
		int frIndex = getIntent().getIntExtra(Constants.Extra.FRAGMENT_INDEX, 0);
		Fragment fr = null;
		String tag = null;
		int titleRes = 0;
		switch (frIndex) {
		case ImageListFragment.INDEX:
			tag = ImageListFragment.class.getSimpleName();
			fr = getSupportFragmentManager().findFragmentByTag(tag);
			if(fr==null) {
				fr = new ImageListFragment();
			}
			titleRes = R.string.ac_name_image_list;
			break;
		case ImagePagerFragment.INDEX:
			tag = ImagePagerFragment.class.getSimpleName();
			fr = getSupportFragmentManager().findFragmentByTag(tag);
			if (fr == null) {
				fr = new ImagePagerFragment();
				fr.setArguments(getIntent().getExtras());
			}
			titleRes = R.string.ac_name_image_pager;
			break;
		}
		setTitle(titleRes);
		getSupportFragmentManager().beginTransaction().replace(android.R.id.content, fr, tag).commit();
	}
}
