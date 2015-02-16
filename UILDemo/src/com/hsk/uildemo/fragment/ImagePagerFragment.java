package com.hsk.uildemo.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hsk.uildemo.Constants;
import com.hsk.uildemo.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * @author heshaokang	
 * 2015-2-16 下午4:25:33
 * 显示点击对应图片item后放大的图片
 */
public class ImagePagerFragment extends BaseFragment {
	public static final int INDEX = 2;
	String[] imageUrls = Constants.IMAGES;
	DisplayImageOptions options;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		options = new DisplayImageOptions.Builder()
		.showImageForEmptyUri(R.drawable.ic_empty)
		.showImageOnFail(R.drawable.ic_error)
		.resetViewBeforeLoading(true)
		.cacheOnDisk(true)
		.imageScaleType(ImageScaleType.EXACTLY)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.considerExifParams(true)
		.displayer(new FadeInBitmapDisplayer(300))
		.build();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fr_image_pager, container, false);
		ViewPager pager = (ViewPager) rootView.findViewById(R.id.pager);
		pager.setAdapter(new ImageAdapter());
		pager.setCurrentItem(getArguments().getInt(Constants.Extra.IMAGE_POSITION, 0));
		
		return rootView;
	}
	
	private class ImageAdapter extends PagerAdapter {
		private LayoutInflater inflater;
		
		public ImageAdapter() {
			inflater = LayoutInflater.from(getActivity());
		}
		@Override
		public int getCount() {
			
			return imageUrls.length;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			
			return arg0.equals(arg1);
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			
			container.removeView((View)object);
		}
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View imageLayout = inflater.inflate(R.layout.item_pager_image, container, false);
			assert imageLayout!=null;
			ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image);
			final ProgressBar spinner = (ProgressBar) imageLayout.findViewById(R.id.loading);
			ImageLoader.getInstance().displayImage(imageUrls[position], imageView, options, new SimpleImageLoadingListener() {

				@Override
				public void onLoadingStarted(String imageUri, View view) {
					
					spinner.setVisibility(View.VISIBLE);
				}

				@Override
				public void onLoadingFailed(String imageUri, View view,
						FailReason failReason) {
					
					String message = null;
					switch (failReason.getType()) {
						case IO_ERROR:
							message = "Input/Output error";
							break;
						case DECODING_ERROR:
							message = "Image can't be decoded";
							break;
						case NETWORK_DENIED:
							message = "Downloads are denied";
							break;
						case OUT_OF_MEMORY:
							message = "Out Of Memory error";
							break;
						case UNKNOWN:
							message = "Unknown error";
							break;
					}
					
					Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
					spinner.setVisibility(View.GONE);
				}

				@Override
				public void onLoadingComplete(String imageUri, View view,
						Bitmap loadedImage) {
					
					spinner.setVisibility(View.GONE);
				}

				@Override
				public void onLoadingCancelled(String imageUri, View view) {
					
					super.onLoadingCancelled(imageUri, view);
				}
				
			});
			container.addView(imageLayout,0);
			return imageLayout;
		}
		
		
		@Override
		public void restoreState(Parcelable state, ClassLoader loader) {
			
			super.restoreState(state, loader);
		}
		@Override
		public Parcelable saveState() {
			
			return null;
		}
		
		
	}
}
