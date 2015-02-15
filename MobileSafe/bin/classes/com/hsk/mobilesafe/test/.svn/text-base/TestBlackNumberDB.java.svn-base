package com.hsk.mobilesafe.test;

import java.util.List;
import java.util.Random;

import android.test.AndroidTestCase;

import com.hsk.mobilesafe.db.BlackNumberDBOpenHelper;
import com.hsk.mobilesafe.db.dao.BlackNumberDao;
import com.hsk.mobilesafe.domain.BlackNumberInfo;

/**
 * @author heshaokang	
 * 2014-12-15 ÉÏÎç10:09:30
 */
public class TestBlackNumberDB extends AndroidTestCase {
	public void testCreateDB() throws Exception{
		BlackNumberDBOpenHelper helper = new BlackNumberDBOpenHelper(getContext());
		helper.getWritableDatabase();
	}
	public void testAdd() throws Exception {
		BlackNumberDao dao = new BlackNumberDao(getContext());
		long basenumber = 1350000000l;
		Random random = new Random();
		for (int i = 0; i < 100; i++) {
			dao.add(String.valueOf(basenumber+i), String.valueOf(random.nextInt(3)+1));
		}
	}
	public void testDelete() throws Exception {
		BlackNumberDao dao = new BlackNumberDao(getContext());
		dao.delete("110");
	}
	public void testUpdate() throws Exception {
		BlackNumberDao dao = new BlackNumberDao(getContext());
		dao.update("110", "2");
	}
	public void testFindAll() throws Exception{
		BlackNumberDao dao = new BlackNumberDao(getContext());
		List<BlackNumberInfo> infos = dao.findAll();
		for(BlackNumberInfo info:infos){
			System.out.println(info.toString());
		}
	}

}
