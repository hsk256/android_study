package com.hsk.mobilesafe.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author heshaokang	
 * 2014-12-23 下午6:37
 */
public class ApplockDBOpenHelper extends SQLiteOpenHelper {

	/**
	 * @param context
	 * @param name    数据库名称
	 * @param factory
	 * @param version  数据库版本 初始化一般为1
	 */
	public ApplockDBOpenHelper(Context context) {
		super(context, "applock.db",null , 1);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		//初始化数据库表
		db.execSQL("create table applock ( _id integer primary key autoincrement,packname varchar(20))");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}
