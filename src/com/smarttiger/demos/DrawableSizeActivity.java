
package com.smarttiger.demos;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.smarttiger.demos.bean.Demo;

public class DrawableSizeActivity extends BaseActivity {

    public static final Demo DEMO = new Demo();
    static {
        DEMO.setClassName(DrawableSizeActivity.class);
        DEMO.setTitle("drawable-size");
        StringBuilder sb = new StringBuilder();
        sb.append("如果在xxhdpi里用了一张1px的图,在小手机里,这张图的大小会是多少?");
        sb.append("");
        sb.append("\n");
        DEMO.setDescription(sb.toString());

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawable_size_activity);
        final ImageView img = (ImageView) findViewById(R.id.img);
        
        img.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {

            boolean isMeasure = false;

            @Override
            public boolean onPreDraw() {
                if (!isMeasure) {
                    Log.d("xx","size=" + img.getMeasuredHeight() + "," + img.getMeasuredWidth());
                    isMeasure = true;
                }
                return true;
            }
        });

    }
}
