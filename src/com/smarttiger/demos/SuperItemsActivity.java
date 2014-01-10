
package com.smarttiger.demos;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;

import com.smarttiger.demos.bean.Demo;
import com.smarttiger.demos.view.SimpleMutiWindowLayout;
import com.smarttiger.demos.view.SimpleMutiWindowLayout.OnSampleMutiWindowLayoutListener;

public class SuperItemsActivity extends BaseActivity implements OnClickListener,
        OnSampleMutiWindowLayoutListener {
    public static final Demo DEMO = new Demo();
    static {
        DEMO.setClassName(SuperItemsActivity.class);
        DEMO.setTitle("listItems");
        StringBuilder sb = new StringBuilder();
        DEMO.setDescription(sb.toString());
    }
    SimpleMutiWindowLayout mMutiLayout;

    List<String> items = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.muti_layout);

        mMutiLayout = (SimpleMutiWindowLayout) findViewById(R.id.muti);
        mMutiLayout.setOnSampleMutiWindowLayoutListener(this);
        Button closeAll = (Button) findViewById(R.id.closeAll);
        closeAll.setOnClickListener(this);
        initItems();
        setItems();
    }

    private void initItems() {

        for (int i = 0; i < 20; i++) {
            items.add("第" + i + "个");
        }
    }

    private void setItems() {
        mMutiLayout.removeAllViews();
        for (int i = 0; i < items.size(); i++) {
            mMutiLayout.addItem(items.get(i).toString());
        }

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.closeAll) {
            mMutiLayout.closeAll();
        }
    }

    @Override
    public void onRemoveAllAniEnd() {

        finish();
    }

    @Override
    public void onRemoveAllAniStart() {

    }
}
