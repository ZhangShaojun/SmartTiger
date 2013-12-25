
package com.smarttiger.demos;

import android.os.Bundle;

import com.smarttiger.demos.bean.Demo;

public class OverScrollActivity extends BaseActivity {
    public static final Demo DEMO = new Demo();
    static {
        DEMO.setClassName(OverScrollActivity.class);
        DEMO.setTitle("OverScroll");
        StringBuilder sb = new StringBuilder();
        DEMO.setDescription(sb.toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.over_scroll);
    }

}
