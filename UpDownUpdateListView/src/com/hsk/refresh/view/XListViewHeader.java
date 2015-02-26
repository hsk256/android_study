package com.hsk.refresh.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hsk.refresh.R;

/**
 * @author heshaokang	
 * 2015-2-10 下午5:46:50
 */
public class XListViewHeader extends LinearLayout {

	private static final long ROTATE_ANIM_DURATION = 180;
	private LinearLayout mContainer;
	private ImageView mArrowImageView;
	private TextView mHintTextView;
	private ProgressBar mProgressBar;
	private RotateAnimation mRotateUpAnim;
	private RotateAnimation mRotateDownAnim;
	
	public final static int STATE_NORMAL = 0;
	public final static int STATE_READY = 1;
	public final static int STATE_REFRESHING = 2;
	
	private int mState = STATE_NORMAL;

	/**
	 * @param context
	 * @param attrs
	 * 
	 * 
	 * @param defStyleAttr
	 */
	public XListViewHeader(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initView(context);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public XListViewHeader(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	/**
	 * @param context
	 */
	public XListViewHeader(Context context) {
		super(context);
		initView(context);
	}

	private void initView(Context context) {
		
		//初始情况 设置下拉刷新高度为0
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT,0);
		mContainer = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.xlistview_header, null);
		addView(mContainer,lp);
		setGravity(Gravity.BOTTOM);
		
		mArrowImageView = (ImageView) findViewById(R.id.xlistview_header_arrow);
		mHintTextView = (TextView) findViewById(R.id.xlistview_header_hint_textview);
		mProgressBar = (ProgressBar) findViewById(R.id.xlistview_header_progressbar);
		
		mRotateUpAnim = new RotateAnimation(0.0f, -180.0f,Animation.RELATIVE_TO_SELF,
				0.5f,Animation.RELATIVE_TO_SELF,0.5f);
		mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
		mRotateUpAnim.setFillAfter(true);
		
		mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
		mRotateDownAnim.setFillAfter(true);
	}
	public void setState(int state) {
		if(state==mState) return;
		
		if(state==STATE_REFRESHING) {   //显示progress
			mArrowImageView.clearAnimation();
			mArrowImageView.setVisibility(INVISIBLE);
			mProgressBar.setVisibility(VISIBLE);
		}else {
			mArrowImageView.setVisibility(VISIBLE);
			mProgressBar.setVisibility(INVISIBLE);
		}
		
		switch (state) {
		case STATE_NORMAL:
			if(mState==STATE_READY) {
				mArrowImageView.setAnimation(mRotateDownAnim);
			}
			if(mState==STATE_REFRESHING) {
				mArrowImageView.clearAnimation();
			}
			mHintTextView.setText("下拉刷新");
			break;
			//准备刷新
		case STATE_READY:
			if(mState!=STATE_READY) {
				mArrowImageView.clearAnimation();
				mArrowImageView.setAnimation(mRotateUpAnim);
				mHintTextView.setText("松开刷新数据");
			}
			break;
		case STATE_REFRESHING:
			mHintTextView.setText("正在加载...");
		default:
			break;
		}
		mState = state;
	}
	public void setVisableHeight(int height) {
		if(height<0)
			height = 0;
		//获取header view 的宽高 参数
		LinearLayout.LayoutParams lp = (LayoutParams) mContainer.getLayoutParams();
		lp.height = height;
		mContainer.setLayoutParams(lp);
	}
	/**
	 * 获取容器的header view 高度
	 */
	public int getVisiableHeight() {
		return mContainer.getHeight();
	}
}
