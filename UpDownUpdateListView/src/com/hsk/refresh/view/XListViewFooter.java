package com.hsk.refresh.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hsk.refresh.R;

/**
 * @author heshaokang	
 * 2015-2-10 下午6:35:32
 */
public class XListViewFooter extends LinearLayout {
	public final static int STATE_NORMAL = 0;
	public final static int STATE_READY = 1;
	public final static int STATE_LOADING = 2;

	private Context mContext;
	private View mContentView;
	private View mProgressBar;
	private TextView mHintView;

	/**
	 * @param context
	 * @param attrs
	 * @param defStyleAttr
	 */
	public XListViewFooter(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initView(context);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public XListViewFooter(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	/**
	 * @param context
	 */
	public XListViewFooter(Context context) {
		super(context);
		initView(context);
	}

	private void initView(Context context) {
		mContext = context;
		LinearLayout moreView = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.xlistview_footer, null);
		addView(moreView);
		moreView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
			LayoutParams.WRAP_CONTENT));
		
		mContentView = moreView.findViewById(R.id.xlistview_footer_content);
		mProgressBar = moreView.findViewById(R.id.xlistview_footer_progressbar);
		mHintView = (TextView) moreView.findViewById(R.id.xlistview_footer_hint_textview);
				
	}
	
	
	public void setState(int state) {
		mHintView.setVisibility(View.INVISIBLE);
		mProgressBar.setVisibility(View.INVISIBLE);
		mHintView.setVisibility(View.INVISIBLE);
		if (state == STATE_READY) {
			mHintView.setVisibility(View.VISIBLE);
			mHintView.setText("松开载入更多");
		} else if (state == STATE_LOADING) {
			mProgressBar.setVisibility(View.VISIBLE);
		} else {
			mHintView.setVisibility(View.VISIBLE);
			mHintView.setText("查看更多");
		}
	}
	
	public void setBottomMargin(int height) {
		if(height<0) return;
		LinearLayout.LayoutParams lp = (LayoutParams) mContentView.getLayoutParams();
		lp.bottomMargin = height;
		mContentView.setLayoutParams(lp);
	}
	public int getBottomMargin() {
		LinearLayout.LayoutParams lp = (LayoutParams) mContentView.getLayoutParams();
		return lp.bottomMargin;
	}
	/**
	 * normal status
	 */
	public void normal() {
		mHintView.setVisibility(VISIBLE);
		mProgressBar.setVisibility(GONE);
	}
	
	/**
	 * loading status
	 */
	public void loading() {
		mHintView.setVisibility(GONE);
		mProgressBar.setVisibility(VISIBLE);
	}
	
	/**
	 * 显示 footer view 
	 */
	public void show() {
		LinearLayout.LayoutParams lp = (LayoutParams) mContentView.getLayoutParams();
		lp.height = LayoutParams.WRAP_CONTENT;
		mContentView.setLayoutParams(lp);
	}
	/**
	 * 当没有更多数据加载时 隐藏footer view
	 */
	public void hide() {
		LinearLayout.LayoutParams lp = (LayoutParams) mContentView.getLayoutParams();
		lp.height = 0;
		mContentView.setLayoutParams(lp);
	}
}
