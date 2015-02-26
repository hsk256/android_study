package com.hsk.mobilesafe.service;

import java.lang.reflect.Method;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.internal.telephony.ITelephony;
import com.hsk.mobilesafe.db.dao.BlackNumberDao;

/**
 * @author heshaokang	
 * 2014-12-15 下午6:37:09
 * 黑名单拦截服务
 */
public class CallSmsSafeService extends Service {
	public static final String TAG = "CallSmsSafeService";
	private BlackNumberDao dao;
	private InnerSmsReceiver receiver;
	private TelephonyManager tm;
	private MyListener listener;
	@Override
	public IBinder onBind(Intent intent) {

		return null;
	}
	private class InnerSmsReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			Log.i(TAG,"短信来了");
			//检查短信发件人 是否被设置为短信拦截的黑名单
			Object[] objs = (Object[]) intent.getExtras().get("pdus");
			for(Object obj:objs) {
				SmsMessage smsmessage = SmsMessage.createFromPdu((byte[])obj);
				//得到短信的发件人
				String sender = smsmessage.getOriginatingAddress();
				String result = dao.findMode(sender);
				if("2".equals(result)||"3".equals(result)) {
					Log.i(TAG,"拦截短息");
					abortBroadcast();
				}
			}
			
			
		}
		
		
	}
	@Override
	public void onCreate() {
		dao  = new BlackNumberDao(this);
		tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		listener = new MyListener();
		tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
		
		receiver = new InnerSmsReceiver();
		IntentFilter filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
		registerReceiver(receiver, filter);
		super.onCreate();
	}
	@Override
	public void onDestroy() {
		unregisterReceiver(receiver);
		receiver = null;
		tm.listen(listener, PhoneStateListener.LISTEN_NONE);
		super.onDestroy();
	}
	private class MyListener extends PhoneStateListener {
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			switch (state) {
			case TelephonyManager.CALL_STATE_RINGING:
				String result = dao.findMode(incomingNumber);
				if("1".equals(result)||"3".equals(result)) {
					Log.i(TAG,"挂断电话");
					//观察呼叫记录数据库内容的变化
					Uri uri = Uri.parse("content://call_log/calls");
					getContentResolver().registerContentObserver(uri, true, new CallLogObserver(incomingNumber, new Handler()));
					endCall();
				}
				break;

			default:
				break;
			}
			super.onCallStateChanged(state, incomingNumber);
			
		}
	}
	
	private class CallLogObserver extends ContentObserver {
		private String incommingNumber;
		/**
		 * @param handler
		 */
		public CallLogObserver(String incommingNumber,Handler handler) {
			super(handler);
			this.incommingNumber = incommingNumber;
		}
		@Override
		public void onChange(boolean selfChange) {
			
			super.onChange(selfChange);
			Log.i(TAG,"数据库的内容变化了 ，产生了呼叫记录");
			getContentResolver().unregisterContentObserver(this);
			deleteCallLog(incommingNumber);
		}
		
	}
	@SuppressWarnings("unchecked")
	public void endCall() {
		//IBinder iBinder = ServiceManager.getService(TELEPHONY_SERVICE);
		try {
			//加载servicemanager的字节码
			Class clazz = CallSmsSafeService.class.getClassLoader().loadClass("android.os.ServiceManager");
			Method method = clazz.getDeclaredMethod("getService", String.class);
			IBinder ibinder = (IBinder) method.invoke(null, TELEPHONY_SERVICE);
			ITelephony.Stub.asInterface(ibinder).endCall();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 删除 通话记录
	 */
	public void deleteCallLog(String incommingNumber) {
		ContentResolver resolver = getContentResolver();
		Uri uri = Uri.parse("content://call_log/calls");
		resolver.delete(uri, "number=?", new String[]{incommingNumber});
	}
}
