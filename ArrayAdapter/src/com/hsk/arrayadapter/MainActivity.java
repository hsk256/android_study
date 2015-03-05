package com.hsk.arrayadapter;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		ListView list = new ListView(this);
		String[] textArray ={"demo1","demo2","demo3","demo4","demo5","demoo6","demo7"};
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,textArray);
		
		list.setAdapter(adapter);
		setContentView(list);
		
	}
}
