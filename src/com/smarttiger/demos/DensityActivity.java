
package com.smarttiger.demos;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.smarttiger.demos.bean.Demo;

public class DensityActivity extends BaseActivity {

    public static final Demo DEMO = new Demo();
    static {
        DEMO.setClassName(DensityActivity.class);
        DEMO.setTitle("density");
        StringBuilder sb = new StringBuilder();
        sb.append("这是一个用java代码写dip的实例,如果不想在xml里写dip,但又要实现动态适配的效果可以用这个方法:\n");
        sb.append("\tDisplayMetrics outMetrics=new DisplayMetrics();\n");
        sb.append("\tgetWindowManager().getDefaultDisplay().getMetrics(outMetrics);\n");
        sb.append("\t那么10dip=10* outMetrics.density;\n");
        DEMO.setDescription(sb.toString());

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.density_activity);
        TextView textView = (TextView) findViewById(R.id.text);
        textView.setText(DEMO.getDescription());
        TextView textView2 = (TextView) findViewById(R.id.text2);
        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        int height = (int) (outMetrics.density * 100);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                height);
        textView2.setLayoutParams(lp);

    }
}
