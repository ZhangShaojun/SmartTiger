package com.smarttiger.demos;

import com.smarttiger.demos.bean.Demo;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.TextView;

public class ClickAppActivity extends Activity{
    public static final Demo DEMO = new Demo();
    static {
        DEMO.setClassName(ClickAppActivity.class);
        DEMO.setTitle("点击文本打开一个已知包名的应用");
        StringBuilder sb = new StringBuilder();
        DEMO.setDescription(sb.toString());

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        TextView text = new TextView(this);
        setContentView(text);
        
        SpannableString ss = new SpannableString("text4: Click here to open app.");   
        ss.setSpan(new StyleSpan(Typeface.BOLD), 0, 6,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);   
        ss.setSpan(new MyClickableSpan("com.android.settings"), 13, 17,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);   
        
        text.setText(ss);   
        MovementMethod movementMethod=LinkMovementMethod.getInstance();
        text.setMovementMethod(movementMethod);  


    }
    
    
    class MyClickableSpan extends ClickableSpan{
    	
    	String packageName;
    	public MyClickableSpan(String packageName) {
			// TODO Auto-generated constructor stub
    		this.packageName=packageName;
		}
    	
		@Override
		public void onClick(View widget) {
			// TODO Auto-generated method stub
			openAppByPackage(packageName);
		}
    	
    }
    
    private void openAppByPackage(String packageName){
    	PackageManager packageManager = this.getPackageManager(); 
    	Intent intent=new Intent(); 
    	    try { 
    	    intent =packageManager.getLaunchIntentForPackage(packageName); 
    	} catch (Exception e) { 
    		
    	} 
    	startActivity(intent); 
    }
}
