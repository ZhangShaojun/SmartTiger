
package com.smarttiger.demos;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Bundle;
import android.widget.Button;

import com.smarttiger.demos.bean.Demo;
import com.smarttiger.demos.view.MaskDrawable;

public class MaskDrawableActivity extends BaseActivity {

    public static final Demo DEMO = new Demo();
    static {
        DEMO.setClassName(MaskDrawableActivity.class);
        DEMO.setTitle("仿ios默认点击图");
        StringBuilder sb = new StringBuilder();
        DEMO.setDescription(sb.toString());

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.mask_layout);
        Button button = (Button) findViewById(R.id.btn);
        Button button1 = (Button) findViewById(R.id.btn1);
        Button button2 = (Button) findViewById(R.id.btn2);
        button.setText("xxx");
        MaskDrawable maskDrawable = new MaskDrawable();
        Drawable ninePatchDrawable =  getResources()
                .getDrawable(R.drawable.bg_1);
        maskDrawable.setDrawable(ninePatchDrawable);
        maskDrawable.setMaskColor(Color.argb(20, 0, 0, 0));
        button.setBackgroundDrawable(maskDrawable);

        button1.setBackgroundDrawable(new MaskDrawable(ninePatchDrawable, Color.argb(20, 255, 0, 0)));
        button2.setBackgroundDrawable(new MaskDrawable(getResources()
                .getDrawable(R.drawable.ic_launcher), Color.argb(20, 0, 0, 0)));

    }
}
