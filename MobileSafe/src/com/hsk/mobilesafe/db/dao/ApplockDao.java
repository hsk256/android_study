package com.hsk.mobilesafe.db.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hsk.mobilesafe.db.ApplockDBOpenHelper;

/**
 * @author heshaokang	
 * 2014-12-23 下午6:39:46
 * 程序锁的增删该查的业务逻辑
 */
public class ApplockDao {
	private ApplockDBOpenHelper helper;
	private Context context;
	/**
	 * 构造方法
	 * @param context
	 */
	public ApplockDao(Context context) {
		helper = new ApplockDBOpenHelper(context);
		this.context = context;
	}
	/**
	 * 添加要锁定的程序的包名到数据库中
	 */
	public void add(String packname) {
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("packname", packname);
		db.insert("applock", null, values);
		db.close();
		Intent intent = new Intent();
		intent.setAction("com.hsk.mobilesafe.applockchange");
		context.sendBroadcast(intent);
	}
	/**
	 * 解锁 即删除数据库中应用程序的包名
	 */
	public void delete(String packname) {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.delete("applock", "packname=?", new String[]{packname});
		db.close();
		
		Intent intent = new Intent();
		intent.setAction("com.hsk.mobilesafe.applockchange");
		context.sendBroadcast(intent);
	}
	/**
	 * 查询一条程序锁 包名是否存在
	 */
	public boolean find(String packname) {
		boolean flag = false;
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.query("applock", null, "packname=?", new String[]{packname}, null, null, null);
		if(cursor.moveToNext()) {
			flag = true;
		}
		cursor.close();
		db.close();
		return flag;
	}
	public List<String> findAll(){
		List<String> protectPacknames = new ArrayList<String>();
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.query("applock", new String[]{"packname"}, null,null, null, null, null);
		while(cursor.moveToNext()){
			protectPacknames.add(cursor.getString(0));
		}
		cursor.close();
		db.close();
		return protectPacknames;
	}
	
} 
