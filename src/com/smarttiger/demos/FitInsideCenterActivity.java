
package com.smarttiger.demos;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.smarttiger.demos.bean.Demo;

public class FitInsideCenterActivity extends BaseActivity {

    public static final Demo DEMO = new Demo();
    static {
        DEMO.setClassName(FitInsideCenterActivity.class);
        DEMO.setTitle("fitCenter与centerInside");
        StringBuilder sb = new StringBuilder();
        DEMO.setDescription(sb.toString());

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fit_inside_center);

    }
}
