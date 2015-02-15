package com.hsk.mobilesafe.utils;

import java.io.File;
import java.io.FileOutputStream;

import org.xmlpull.v1.XmlSerializer;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.util.Xml;

/**
 * @author heshaokang	
 * 2014-12-17 下午12:01:12
 * 短信的工具类
 */
public class SmsUtils {
	public interface BackUpCallBack {
		//总进度
		public void beforeBackup(int max);
		//当前进度
		public void onSmsBackup(int progress);
	}
	/**
	 * 备份用户的短信
	 * 
	 */
	public static void backupSms(Context context,BackUpCallBack callBack) throws Exception{
		ContentResolver resolver = context.getContentResolver();
		File file = new File(Environment.getExternalStorageDirectory(),"backup.xml");
		FileOutputStream fos = new FileOutputStream(file);
		//获取xml文件的生成器
		XmlSerializer serializer = Xml.newSerializer();
		//初始化生成器
		serializer.setOutput(fos,"utf-8");
		serializer.startDocument("utf-8", true);
			serializer.startTag(null, "smss");
				Uri uri = Uri.parse("content://sms/");
				Cursor cursor = resolver.query(uri, new String[]{"body","address","type","date"}, null, null, null);
				
				//开始的时候设置进度条的最大值
				int max = cursor.getCount();
				callBack.beforeBackup(max);
				serializer.attribute(null, "max", max+"");
				int process = 0;
				while(cursor.moveToNext()) {
					String body = cursor.getString(0);
					String address = cursor.getString(1);
					String type = cursor.getString(2);
					String date = cursor.getString(3);
					
					serializer.startTag(null, "sms");
					serializer.startTag(null, "body");
					serializer.text(body);
					serializer.endTag(null, "body");
					
					serializer.startTag(null, "address");
					serializer.text(address);
					serializer.endTag(null, "address");
					
					serializer.startTag(null, "type");
					serializer.text(type);
					serializer.endTag(null, "type");
					
					serializer.startTag(null, "date");
					serializer.text(date);
					serializer.endTag(null, "date");
					serializer.endTag(null, "sms");
					
					process++;
					callBack.onSmsBackup(process);
				}
				
			cursor.close();
			serializer.endTag(null, "smss");
		serializer.endDocument();
		fos.close();
	}
	/**
	 * flag 是否清理原来的短信
	 */
	public static void restoreSms(Context context,boolean flag) {
		Uri uri = Uri.parse("content://sms/");
		if(flag) {
			context.getContentResolver().delete(uri, null, null);
		}
		// 1.读取sd卡上的xml文件
		// Xml.newPullParser();

		// 2.读取max

		// 3.读取每一条短信信息，body date type address

		// 4.把短信插入到系统短息应用。

		ContentValues values = new ContentValues();
		values.put("body", "woshi duanxin de neirong");
		values.put("date", "1395045035573");
		values.put("type", "1");
		values.put("address", "5558");
		context.getContentResolver().insert(uri, values);
	}
}
