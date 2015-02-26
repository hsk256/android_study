package com.hsk.mobilesafe.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.os.SystemClock;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.TextView;

import com.hsk.mobilesafe.R;
import com.hsk.mobilesafe.db.dao.NumberAddressQueryUtils;
/**
 * 
 * @author heshaokang	
 * 2014-12-14 ����4:44:53
 * �����Զ�����˾ �ƶ���ʱ�� �и�����һֱû�ҵ� ����toast�ǲ������ƶ��� ����Ҫ��Ϊ�ֻ����ȵ�Ȩ��
 * ֮ǰ��绰ʱ ������һֱ����ʾ ԭ����û������Ȩ��
 */
public class AddressService extends Service {
	
	protected static final String TAG = "AddressService";
	/**
	 * ���������
	 */
	private WindowManager wm;
	private View view;

	/**
	 * �绰����
	 */

	private TelephonyManager tm;
	private MyListenerPhone listenerPhone;
	
	private OutCallReceiver receiver;
	private SharedPreferences sp;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	// ����������ڲ���
	//�㲥�����ߵ��������ںͷ���һ��
	class OutCallReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// ����������õ��Ĳ���ȥ�ĵ绰����
			String phone = getResultData();
			// ��ѯ���ݿ�
			String address = NumberAddressQueryUtils.queryNumber(phone);
			
//			Toast.makeText(context, address, 1).show();
			myToast(address);
		}

	}

	private class MyListenerPhone extends PhoneStateListener {

		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			// state��״̬��incomingNumber���������
			super.onCallStateChanged(state, incomingNumber);
			switch (state) {
			case TelephonyManager.CALL_STATE_RINGING:// ������������
				// ��ѯ���ݿ�Ĳ���
				String address = NumberAddressQueryUtils
						.queryNumber(incomingNumber);
				
//				Toast.makeText(getApplicationContext(), address, 1).show();
				myToast(address);

				break;
				
			case TelephonyManager.CALL_STATE_IDLE://�绰�Ŀ���״̬���ҵ绰������ܾ�
				//�����View�Ƴ�
				if(view != null ){
					wm.removeView(view);
				}
			
				
				break;

			default:
				break;
			}
		}

	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);

		// ��������
		listenerPhone = new MyListenerPhone();
		tm.listen(listenerPhone, PhoneStateListener.LISTEN_CALL_STATE);
		
		//�ô���ȥע��㲥������
		receiver = new OutCallReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction("android.intent.action.NEW_OUTGOING_CALL");
		registerReceiver(receiver, filter);
		
		//ʵ��������
		wm = (WindowManager) getSystemService(WINDOW_SERVICE);
	}
	
	private WindowManager.LayoutParams params;
	long[] mHits = new long[2];
	/**
	 * �Զ�����˾
	 * @param address
	 */
	public void myToast(String address) {
	     view =   View.inflate(this, R.layout.address_show, null);
	    TextView textview  = (TextView) view.findViewById(R.id.tv_address);
	    view.setOnClickListener(new OnClickListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
				mHits[mHits.length - 1] = SystemClock.uptimeMillis();
				
                

                
				if (mHits[0] >= (SystemClock.uptimeMillis() - 500)) {
					// ˫�������ˡ�����
					Log.i(TAG, "˫��������");
					params.x = (wm.getDefaultDisplay().getWidth()-view.getWidth())/2;
					wm.updateViewLayout(view, params);
					Editor editor = sp.edit();
					editor.putInt("lastx", params.x);
					editor.commit();
				}
			}
		});
	    //��view ����һ�������ļ�����
	    view.setOnTouchListener(new OnTouchListener() {
			//������ָ�ĳ�ʼ��λ��
	    	int startX;
	    	int startY;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				//��ָ����ʱ
				case MotionEvent.ACTION_DOWN:
					startX = (int) event.getRawX();
					startY = (int) event.getRawY();
					Log.i(TAG, "��ָ�������ؼ�");
					break;
				//��ָ�ƶ�
				case MotionEvent.ACTION_MOVE:
					int newX = (int) event.getRawX();
					int newY = (int) event.getRawY();
					int dx = newX-startX;
					int dy = newY-startY;
					Log.i(TAG,"��ָ�ڿؼ����ƶ�");
					params.x +=dx;
					params.y +=dy;
					
					//�ػ�ؼ�λ��
					wm.updateViewLayout(view, params);
					// ���³�ʼ����ָ�Ŀ�ʼ����λ�á�
					startX = (int) event.getRawX();
					startY = (int) event.getRawY();
					break;
				case MotionEvent.ACTION_UP:
					//v.performClick();
					// ��¼�ؼ�������Ļ���Ͻǵ�����
					Log.i(TAG, "��ָ�뿪�ؼ�");
					Editor editor = sp.edit();
					editor.putInt("lastx", params.x);
					editor.putInt("lasty", params.y);
					editor.commit();
					break;
				default:
					break;
				}
				return false;
			}
		});
	    
	    //"��͸��","������","��ʿ��","������","ƻ����"
	    int [] ids = {R.drawable.call_locate_white,R.drawable.call_locate_orange,R.drawable.call_locate_blue
	    ,R.drawable.call_locate_gray,R.drawable.call_locate_green};
	    sp = getSharedPreferences("config", MODE_PRIVATE);
	    view.setBackgroundResource(ids[sp.getInt("which", 0)]);
	    textview.setText(address);
		//����Ĳ��������ú���
		  params = new WindowManager.LayoutParams();
		 
         params.height = WindowManager.LayoutParams.WRAP_CONTENT;
         params.width = WindowManager.LayoutParams.WRAP_CONTENT;
         params.gravity = Gravity.TOP + Gravity.LEFT;
         params.x = sp.getInt("lastx", 0);
         params.y = sp.getInt("lasty", 0);
         //WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE ȥ�� ������ 
         params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                 
                 | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
         params.format = PixelFormat.TRANSLUCENT;
      // androidϵͳ������е绰���ȼ���һ�ִ������ͣ��ǵ�����Ȩ�ޡ�
         params.type = WindowManager.LayoutParams.TYPE_PRIORITY_PHONE;
		wm.addView(view, params);
		
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		// ȡ����������
		tm.listen(listenerPhone, PhoneStateListener.LISTEN_NONE);
		listenerPhone = null;
		
		//�ô���ȡ��ע��㲥������
		unregisterReceiver(receiver);
		receiver = null;

	}

}