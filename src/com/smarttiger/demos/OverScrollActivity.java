
package com.smarttiger.demos;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

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
        LinearLayout layout = (LinearLayout) findViewById(R.id.parent);
        for (int i = 0; i < 100; i++) {
            TextView t = new TextView(this);
            t.setText("第" + i + "个");
            layout.addView(t, 400, 100);
        }
    }

}
