
package com.smarttiger.demos;

import android.graphics.Color;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;

import com.smarttiger.demos.bean.Demo;
import com.smarttiger.demos.view.LouKongDrawable;

public class RouKongDrawableActivity extends BaseActivity implements OnClickListener {

    public static final String PACKAGE_NAME = "com.ijinshan.browser";
    public static final String LIEBAO_OFFICIAL_URL = "http://bbs.liebao.cn";
    public static final Demo DEMO = new Demo();
    static {
        DEMO.setClassName(RouKongDrawableActivity.class);
        DEMO.setTitle("镂空文字的RadioButton");
        StringBuilder sb = new StringBuilder();
        sb.append("");
        DEMO.setDescription(sb.toString());

    }

    private RadioButton search_web, search_video, taobao, search_book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.roukong_drawable_activity);
        search_web = (RadioButton) findViewById(R.id.search_web);
        search_video = (RadioButton) findViewById(R.id.search_video);
        taobao = (RadioButton) findViewById(R.id.taobao);
        search_book = (RadioButton) findViewById(R.id.search_book);
        setDrawable(search_web);
        setDrawable(search_video);
        setDrawable(taobao);
        setDrawable(search_book);
    }

    private void setDrawable(RadioButton radioButton) {
        LouKongDrawable rouKongDrawable = new LouKongDrawable();
        rouKongDrawable.setText(radioButton.getText());
        rouKongDrawable.setTextSize(radioButton.getTextSize());
        rouKongDrawable.setTextColor(Color.WHITE);
        rouKongDrawable.setGravity(radioButton.getGravity());
        radioButton.setBackgroundDrawable(rouKongDrawable);
        radioButton.setTextColor(Color.TRANSPARENT);
        NinePatchDrawable checkDrawable = (NinePatchDrawable) getResources().getDrawable(
                R.drawable.home_search_radio_checked);
        NinePatchDrawable unCheckDrawable = (NinePatchDrawable) getResources().getDrawable(
                R.drawable.home_search_radio_unchecked);
        // radioButton.setButtonDrawable(rouKongDrawable);
        rouKongDrawable.setUncheckedDrawable(unCheckDrawable);
        rouKongDrawable.setCheckedDrawable(checkDrawable);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

    }

}
