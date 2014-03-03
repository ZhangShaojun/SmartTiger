
package com.smarttiger.demos;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.smarttiger.demos.bean.Demo;

public class VisibleAnimationActivity extends Activity implements OnScrollChangedListener {
    public static final Demo DEMO = new Demo();
    static {
        DEMO.setClassName(VisibleAnimationActivity.class);
        DEMO.setTitle("当滚动出来就播放动画");
        StringBuilder sb = new StringBuilder();
        DEMO.setDescription(sb.toString());

    }

    private LinearLayout parent;
    private ScrollView scroll;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visible_animation_layout);
        parent = (LinearLayout) findViewById(R.id.parent);
        scroll = (ScrollView) findViewById(R.id.scroll);

        for (int i = 0; i < 50; i++)
        {
            Button button = null;
            if (i == 20) {
                button = new MyButton(this);
                mButton = button;
            } else {

                button = new Button(this);

            }
            button.setText(String.valueOf(i));

            parent.addView(button);

        }
    }

    private class MyButton extends Button {

        public MyButton(Context context) {
            super(context);

        }

        @Override
        protected void onAttachedToWindow() {
            super.onAttachedToWindow();
            getViewTreeObserver().addOnScrollChangedListener(VisibleAnimationActivity.this);
        }

        @Override
        protected void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            getViewTreeObserver().removeOnScrollChangedListener(VisibleAnimationActivity.this);
        }

    }

    Rect r = new Rect();

    @Override
    public void onScrollChanged() {

        r.set(0, 0, mButton.getMeasuredWidth(), mButton.getMeasuredHeight());
        boolean b = parent.getChildVisibleRect(mButton, r, new Point(1000, 1000));
        System.out.println("onScrollChanged" + scroll.getScrollY() + b + "r=" + r.toShortString());
    }

}
