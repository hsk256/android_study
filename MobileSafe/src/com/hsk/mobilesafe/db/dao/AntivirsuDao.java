package com.hsk.mobilesafe.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author heshaokang	
 * 2014-12-27 下午9:45:28
 */
public class AntivirsuDao {
	/**
	 * 查询一个md5是否在病毒字符串里
	 */
	public static boolean isVirus(String md5) {
		String path = "/data/data/com.hsk.mobilesafe/files/antivirus.db";
		boolean result = false;
		//打开病毒数据库文件
		SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
		Cursor cursor = db.rawQuery("select * from datable where md5=?", new String[]{md5});
		if(cursor.moveToNext()){
			result = true;
		}
		cursor.close();
		db.close();
		return result;
	}
}
