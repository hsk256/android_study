package com.hsk.mobilesafe.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author heshaokang	
 * 2014-12-11 下午5:26:05
 */
public class NumberAddressQueryUtils {
	private static String path = "data/data/com.hsk.mobilesafe/files/address.db";
	public static String queryNumber(String number) {
		String address = number;
		SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
		if(number.matches("^1[345678]\\d{9}$")) {
			Cursor cursor = db.rawQuery("select location from data2 where id = (select outkey from data1 where id = ?)",
					new String[]{number.substring(0,7)});
			while(cursor.moveToNext()) {
				String location = cursor.getString(0);
				address = location;
			}
			cursor.close();
		}else {
			switch (number.length()) {
			case 3:
				address="匪警电话";
				break;
			case 4:
				address="服务号";
				break;
			case 5:
				address="客服电话";
				break;
			case 7:
				address="本地号码";
				break;
			case 8:
				address="本地号码";
			default:
				//固定电话
				if(number.length()>10&&number.startsWith("0")) {
					Cursor cursor = db.rawQuery("select location from data2 where area=?", new String[]{number.substring(1, 3)});
					while(cursor.moveToNext()) {
						String location = cursor.getString(0);
						address = location;
					}
					cursor.close();
					cursor = db.rawQuery(
							"select location from data2 where area = ?",
							new String[] { number.substring(1, 4) });
					while (cursor.moveToNext()) {
						String location = cursor.getString(0);
						address = location.substring(0, location.length() - 2);

					}
					cursor.close();
				}
				break;
			}
		}
		db.close();
		return address;
	}
}
