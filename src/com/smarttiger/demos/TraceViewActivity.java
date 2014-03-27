
package com.smarttiger.demos;

import android.os.Bundle;
import android.os.Debug;
import android.os.SystemClock;

import com.smarttiger.demos.bean.Demo;

public class TraceViewActivity extends BaseActivity {
    public static final Demo DEMO = new Demo();
    static {
        DEMO.setClassName(TraceViewActivity.class);
        DEMO.setTitle("TraceView,TextureView");
        StringBuilder sb = new StringBuilder();
        DEMO.setDescription(sb.toString());

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ViewServer.get(this).addWindow(this);

        Debug.startMethodTracing("test");
        
        
        Debug.stopMethodTracing();
    }

}
