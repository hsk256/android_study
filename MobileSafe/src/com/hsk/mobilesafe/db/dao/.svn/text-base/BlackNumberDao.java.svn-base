package com.hsk.mobilesafe.db.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hsk.mobilesafe.db.BlackNumberDBOpenHelper;
import com.hsk.mobilesafe.domain.BlackNumberInfo;

/**
 * @author heshaokang	
 * 2014-12-15 上午10:14:43
 * 黑名单增删改查业务
 */
public class BlackNumberDao {
	private BlackNumberDBOpenHelper helper;
	/**
	 * 构造方法 
	 * @param context  上下文
	 */
	public BlackNumberDao(Context context) {
		helper = new BlackNumberDBOpenHelper(context);
	}
	/**
	 * 查询和名单号码是否存在
	 */
	public boolean find(String number) {
		boolean result = false;
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from blacknumber where number=?", new String[]{number});
		if(cursor.moveToNext()) {
			result = true;
		}
		cursor.close();
		db.close();
		return result;
		
	}
	/**
	 * 查询黑名单的拦截模式 
	 * @return 返回号码的拦截模式 不是黑名单 返回null
	 */
	public String findMode(String number) {
		String result = null;
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select mode from blacknumber where number=?", new String[]{number});
		if(cursor.moveToNext()) {
			result = cursor.getString(0);
		}
		cursor.close();
		db.close();
		return result;
	}
	
	/**
	 * 查询全部黑名单号码
	 */
	public List<BlackNumberInfo> findAll() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		List<BlackNumberInfo> result = new ArrayList<BlackNumberInfo>();
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select number,mode from blacknumber order by _id desc",null );
		while(cursor.moveToNext()) {
			BlackNumberInfo info = new BlackNumberInfo();
			String number = cursor.getString(0);
			String mode  =cursor.getString(1);
			info.setNumber(number);
			info.setMode(mode);
			result.add(info);
		}
		cursor.close();
		db.close();
		return result;
	}
	
	public List<BlackNumberInfo> findPart(int offset,int maxNumber) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		List<BlackNumberInfo> result = new ArrayList<BlackNumberInfo>();
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select number,mode from blacknumber  order by _id desc limit ? offset ?",
				new String[]{String.valueOf(maxNumber),String.valueOf(offset)});
		while(cursor.moveToNext()) {
			BlackNumberInfo info = new BlackNumberInfo();
			String number = cursor.getString(0);
			String mode  =cursor.getString(1);
			info.setNumber(number);
			info.setMode(mode);
			result.add(info);
		}
		cursor.close();
		db.close();
		return result;
	}
	
	
	/**
	 * 添加黑名单 
	 * @param mode 1 代表电话拦截 2 短信拦截 3全部拦截
	 *
	 */
	public void add(String number,String mode) {
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("number", number);
		values.put("mode", mode);
		db.insert("blacknumber", null, values);
		db.close();
	}
	/**
	 * 更新 黑名单的拦截模式
	 */
	public void update(String number,String newMode) {
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("mode", newMode);
		db.update("blacknumber", values, "number=?", new String[]{number});
		db.close();
	}
	/**
	 * 删除黑名单号码
	 */
	public void delete(String  number) {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.delete("blacknumber", "number=?", new String[]{number});
		db.close();
	}
	/**
	 * 得到数据库总的记录数
	 */
	public int getCount() {
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from blacknumber", null);
		if(cursor!=null) {
			return cursor.getCount();
		}
		return 0;
	}
}
