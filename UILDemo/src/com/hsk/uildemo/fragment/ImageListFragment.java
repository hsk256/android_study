package com.hsk.uildemo.fragment;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.hsk.uildemo.Constants;
import com.hsk.uildemo.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * @author heshaokang	
 * 2015-2-16 下午4:29:38
 */
public class ImageListFragment extends AbsListViewBaseFragment {
	public static final int INDEX = 0;
	String[] imageUrls = Constants.IMAGES;
	DisplayImageOptions options;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.ic_stub)  //正在加载时显示的图片
		.showImageForEmptyUri(R.drawable.ic_empty) //地址为空时加载的图片
		.showImageOnFail(R.drawable.ic_error) //加载失败时显示的图片
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.considerExifParams(true)
		.displayer(new RoundedBitmapDisplayer(20)) //图片以圆角方式显示 参数为角度
		.build();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fr_image_list, container,false);
		listview = (AbsListView) rootView.findViewById(android.R.id.list);
		((ListView)listview).setAdapter(new ImageAdapter());
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				startImagePagerActivity(position);
			}
			
		});
		return rootView;
	}
	
	
	class ImageAdapter extends BaseAdapter {
		private LayoutInflater inflater;
		private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
		public ImageAdapter() {
			inflater = LayoutInflater.from(getActivity());
		}
		
		@Override
		public int getCount() {
			
			return imageUrls.length;
		}

		@Override
		public Object getItem(int position) {
			
			return position;
		}

		@Override
		public long getItemId(int position) {
			
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final ViewHolder viewHolder ;
			View view = convertView;
			if(convertView==null) {
				view = inflater.inflate(R.layout.item_list_image, parent, false);
				viewHolder = new ViewHolder();
				viewHolder.image = (ImageView) view.findViewById(R.id.image);
				viewHolder.text = (TextView) view.findViewById(R.id.text);
				view.setTag(viewHolder);
			}else {
				viewHolder = (ViewHolder) view.getTag();
			}
			viewHolder.text.setText("Item " + (position + 1));
			ImageLoader.getInstance().displayImage(imageUrls[position], viewHolder.image, options, animateFirstListener);
			
			return view;
		}
		
	}
	
	private static class ViewHolder {
		ImageView image;
		TextView text;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		AnimateFirstDisplayListener.displayImages.clear();
	}
	
	private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {
		static final List<String> displayImages = Collections.synchronizedList(new LinkedList<String>());
		
		@Override
		public void onLoadingComplete(String imageUri, View view,
				Bitmap loadedImage) {
			
			super.onLoadingComplete(imageUri, view, loadedImage);
			if(loadedImage!=null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayImages.contains(imageUri);
				if(firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayImages.add(imageUri);
				}
			}
		}
	}
	
	

}
