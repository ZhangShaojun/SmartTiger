package com.smarttiger.demos;

import com.smarttiger.demos.bean.Demo;
import com.smarttiger.demos.fragment.Fragment1;
import com.smarttiger.demos.fragment.Fragment2;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

public class FragmentTestActivity extends FragmentActivity {

	public static final Demo DEMO = new Demo();
	
    static {
        DEMO.setClassName(FragmentTestActivity.class);
        DEMO.setTitle("FragmentTest");
        DEMO.setDescription("Fragment的一个小demo");

    }
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_test_activity);
		
		
		getSupportFragmentManager().beginTransaction().replace(R.id.left_layout,new Fragment1()).commit();
		getSupportFragmentManager().beginTransaction().replace(R.id.right_layout,new Fragment2()).commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
