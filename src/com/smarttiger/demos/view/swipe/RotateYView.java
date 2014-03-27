
package com.smarttiger.demos.view.swipe;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.ColorFilter;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;

import com.smarttiger.demos.R;

public class RotateYView extends FrameLayout implements Comparable<RotateYView> {

    private View top, mid;
    private CheckBox bottom;
    ObjectAnimator openAnimator, colseAnimator;
    private int index;

    public static final int ANIMATE_TIME = 300;

    public RotateYView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        top = findViewById(R.id.message_icon);
        // top = findViewById(R.id.message_icon);
        bottom = (CheckBox) findViewById(R.id.check_box);
        // rotate
        openAnimator = ObjectAnimator.ofFloat(this, "rotationY", 0f, 180f);
        openAnimator.setDuration(ANIMATE_TIME);
        openAnimator.addUpdateListener(new AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                float xx = Float.parseFloat(animation.getAnimatedValue().toString());
                ColorFilter colorFilter = new ColorFilter();
                if (xx >= 90) {
                    top.setVisibility(View.GONE);
                    bottom.setVisibility(View.VISIBLE);
                }

            }
        });
        colseAnimator = ObjectAnimator.ofFloat(this, "rotationY", 180f, 0f);
        colseAnimator.setDuration(ANIMATE_TIME);
        colseAnimator.addUpdateListener(new AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                float xx = Float.parseFloat(animation.getAnimatedValue().toString());
                if (xx <= 90) {
                    bottom.setVisibility(View.GONE);
                    top.setVisibility(View.VISIBLE);
                }

            }
        });
        colseAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                bottom.setChecked(false);
            }
        });

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        postDelayed(new Runnable() {

            @Override
            public void run() {
                bottom.setRotationY(180f);
            }
        }, 0);

    }

    public void animateState(boolean open) {
        if (open) {
            animateOpen();
        } else {
            animateClose();
        }

    }

    public void animateOpen() {
        openAnimator.start();
    }

    public void animateClose() {
        colseAnimator.start();
    }

    public void setState(boolean open) {
        if (open) {
            open();
        } else {
            close();
        }

    }

    public void open() {
        setRotationY(180f);
        top.setVisibility(View.GONE);
        bottom.setVisibility(View.VISIBLE);
    }

    public void close() {
        setRotationY(0f);
        bottom.setVisibility(View.GONE);
        top.setVisibility(View.VISIBLE);
    }

    public void setChecked(boolean checked) {
        bottom.setChecked(checked);
    }

    @Override
    public int compareTo(RotateYView another) {
        int result = this.index < another.index ? -1 : 1;
        return result;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
