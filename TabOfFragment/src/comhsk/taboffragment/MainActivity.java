package comhsk.taboffragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import comhsk.taboffragment.fragment.AddressFragment;
import comhsk.taboffragment.fragment.FrdFragment;
import comhsk.taboffragment.fragment.SettingFragment;
import comhsk.taboffragment.fragment.WeixinFragment;

public class MainActivity extends FragmentActivity implements OnClickListener{
	private LinearLayout mTabWeixin;
	private LinearLayout mTabFrd;
	private LinearLayout mTabAddress;
	private LinearLayout mTabSetting;
	
	private ImageButton mImgWeixin;
	private ImageButton mImgFrd;
	private ImageButton mImgAddress;
	private ImageButton mImgSettings;
	
	private Fragment mTab01;
	private Fragment mTab02;
	private Fragment mTab03;
	private Fragment mTab04;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initEvent();
		setSelect(0);
	}
	
	public void initView() {
		mTabWeixin = (LinearLayout) findViewById(R.id.id_tab_weixin);
		mTabFrd = (LinearLayout) findViewById(R.id.id_tab_frd);
		mTabAddress = (LinearLayout) findViewById(R.id.id_tab_address);
		mTabSetting = (LinearLayout) findViewById(R.id.id_tab_settings);
		
		mImgAddress = (ImageButton) findViewById(R.id.id_tab_address_img);
		mImgFrd = (ImageButton) findViewById(R.id.id_tab_frd_img);
		mImgSettings = (ImageButton) findViewById(R.id.id_tab_settings_img);
		mImgWeixin = (ImageButton) findViewById(R.id.id_tab_weixin_img);
	}
	
	public void initEvent() {
		mTabWeixin.setOnClickListener(this);
		mTabFrd.setOnClickListener(this);
		mTabAddress.setOnClickListener(this);
		mTabSetting.setOnClickListener(this);
	}
	
	public void setSelect(int i) {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();
		hideFragment(transaction);
		// 把图片设置为亮的
		// 设置内容区域
		switch (i)
		{
		case 0:
			if (mTab01 == null)
			{
				mTab01 = new WeixinFragment();
				transaction.add(R.id.id_content, mTab01);
			} else
			{
				transaction.show(mTab01);
			}
			mImgWeixin.setImageResource(R.drawable.tab_weixin_pressed);
			break;
		case 1:
			if (mTab02 == null)
			{
				mTab02 = new FrdFragment();transaction.add(R.id.id_content, mTab02);
			} else
			{
				transaction.show(mTab02);
				
			}
			mImgFrd.setImageResource(R.drawable.tab_find_frd_pressed);
			break;
		case 2:
			if (mTab03 == null)
			{
				mTab03 = new AddressFragment();
				transaction.add(R.id.id_content, mTab03);
			} else
			{
				transaction.show(mTab03);
			}
			mImgAddress.setImageResource(R.drawable.tab_address_pressed);
			break;
		case 3:
			if (mTab04 == null)
			{
				mTab04 = new SettingFragment();
				transaction.add(R.id.id_content, mTab04);
			} else
			{
				transaction.show(mTab04);
			}
			mImgSettings.setImageResource(R.drawable.tab_settings_pressed);
			break;

		default:
			break;
		}

		transaction.commit();
	}
	
	public void hideFragment(FragmentTransaction transtion) {
		if(mTab01!=null) {
			transtion.hide(mTab01);
		}
		
		if(mTab02!=null) {
			transtion.hide(mTab02);
		}
		
		if(mTab03!=null) {
			transtion.hide(mTab03);
		}
		
		if(mTab04!=null) {
			transtion.hide(mTab04);
		}
	}
	@Override
	public void onClick(View v) {
		resetImgs();
		switch (v.getId())
		{
		case R.id.id_tab_weixin:
			setSelect(0);
			break;
		case R.id.id_tab_frd:
			setSelect(1);
			break;
		case R.id.id_tab_address:
			setSelect(2);
			break;
		case R.id.id_tab_settings:
			setSelect(3);
			break;

		default:
			break;
		}
	}
	private void resetImgs() {
		mImgWeixin.setImageResource(R.drawable.tab_weixin_normal);
		mImgFrd.setImageResource(R.drawable.tab_find_frd_normal);
		mImgAddress.setImageResource(R.drawable.tab_address_normal);
		mImgSettings.setImageResource(R.drawable.tab_settings_normal);
	}
	
}
