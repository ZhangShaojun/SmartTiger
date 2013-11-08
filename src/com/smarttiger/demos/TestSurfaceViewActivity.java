package com.smarttiger.demos;

import com.smarttiger.demos.bean.Demo;
import com.smarttiger.demos.view.MySurfaceView;

import android.app.Activity;  
import android.os.Bundle;  
  
public class TestSurfaceViewActivity extends Activity  
{  
	
	public static final Demo DEMO = new Demo();
	static{
		DEMO.setClassName(TestSurfaceViewActivity.class);
        DEMO.setTitle("ServiceViewTest");
        DEMO.setDescription("ServiceView的一个小demo。");
	}
	
    @Override  
    public void onCreate(Bundle savedInstanceState)  
    {  
        super.onCreate(savedInstanceState);  
        //setContentView(R.layout.main);   
        setContentView(new MySurfaceView(this));  
        
    }
}  
