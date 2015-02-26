package com.hsk.mobilesafe.receiver;

import com.hsk.mobilesafe.R;
import com.hsk.mobilesafe.service.GPSService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.telephony.SmsMessage;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;

/**
 * @author heshaokang	
 * 2014-12-9 下午12:49:57
 */
public class SMSReceiver extends BroadcastReceiver{
	private static final String TAG = "SMSReceiver";
	private SharedPreferences sp;
	@Override
	public void onReceive(Context context, Intent intent) {
		//接收短信的代码
		Object[] objs = (Object[]) intent.getExtras().get("pdus");
		sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		for(Object b:objs) {
			//具体的某一条短信
			SmsMessage sms = SmsMessage.createFromPdu((byte[])b);
			//发送者 
			String sender = sms.getOriginatingAddress();
			String safeNumber = sp.getString("safeNumber", "");
			String body = sms.getMessageBody();
			if(sender.contains(safeNumber)) {
				if("#*location*#".equals(body)) {
					//得到手机的GPS
					Log.i(TAG, "得到手机GPS");
					Intent intent1 = new Intent(context,GPSService.class);
					context.startService(intent1);
					SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
					String lastLocation = sp.getString("location", null);
					if(TextUtils.isEmpty(lastLocation)) {
						SmsManager.getDefault().sendTextMessage(sender, null, "getting location...", null, null);
					}else {
						SmsManager.getDefault().sendTextMessage(sender, null, lastLocation, null, null);
						//终止广播
						abortBroadcast();
					}
				}else if("#*alarm*#".equals(body)) {
					//播放报警影音
					Log.i(TAG,"播放报警影音");
					//注意 这里导的包不是android.R 而是自己工程下的.R
					MediaPlayer player = MediaPlayer.create(context, R.raw.ylzs);
					player.setLooping(false);
					//设置左右声道
					player.setVolume(1.0f, 1.0f);
					player.start();
					abortBroadcast();
				}
			}
		}
	}

}
