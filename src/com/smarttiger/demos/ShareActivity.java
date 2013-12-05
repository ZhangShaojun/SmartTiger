
package com.smarttiger.demos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.smarttiger.demos.bean.Demo;
import com.smarttiger.demos.share.ShareManager;
import com.smarttiger.demos.share.SinaShareActivity;
import com.smarttiger.demos.wxapi.WXEntryActivity;

public class ShareActivity extends BaseActivity implements OnClickListener {
    public static final Demo DEMO = new Demo();
    static {
        DEMO.setClassName(ShareActivity.class);
        DEMO.setTitle("各种分享");
        StringBuilder sb = new StringBuilder();
        DEMO.setDescription(sb.toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_layout);
    }

    @Override
    public void onClick(View v) {
       

    }

}
