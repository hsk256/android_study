package com.hsk.mobilesafe.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author heshaokang	
 * 2014-12-15 上午10:04:02
 */
public class BlackNumberDBOpenHelper extends SQLiteOpenHelper {

	/**
	 * @param context
	 * @param name    数据库名称
	 * @param factory
	 * @param version  数据库版本 初始化一般为1
	 */
	public BlackNumberDBOpenHelper(Context context) {
		super(context, "blacknumber.db",null , 1);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		//初始化数据库表
		db.execSQL("create table blacknumber ( _id integer primary key autoincrement,number varchar(20),mode varchar(2))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}
