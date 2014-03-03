package com.smarttiger.demos.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView {

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
    }
}
