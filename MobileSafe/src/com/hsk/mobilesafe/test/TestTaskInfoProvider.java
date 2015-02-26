package com.hsk.mobilesafe.test;

import java.util.List;

import android.test.AndroidTestCase;

import com.hsk.mobilesafe.domain.TaskInfo;
import com.hsk.mobilesafe.engine.TaskInfoProvider;

/**
 * @author heshaokang	
 * 2014-12-20 обнГ9:11:41
 */
public class TestTaskInfoProvider extends AndroidTestCase {
	public void testGetTaskInfo() throws Exception{
		List<TaskInfo> infos = TaskInfoProvider.getTaskInfos(getContext());
		System.out.println("tMd.......");
		for(TaskInfo info:infos){
			System.out.println(info.toString());
		}
	}
}
