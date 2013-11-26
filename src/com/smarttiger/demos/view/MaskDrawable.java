
package com.smarttiger.demos.view;

import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;

public class MaskDrawable extends StateListDrawable {

    Drawable mNinePatchDrawable;
    ColorDrawable mColorDrawable;
    int color;
    int pressed = android.R.attr.state_pressed;
    int window_focused = android.R.attr.state_window_focused;
    int focused = android.R.attr.state_focused;
    int selected = android.R.attr.state_selected;

    int[] defaultState = new int[] {};
    int[] pressState = new int[] {
            pressed, window_focused
    };

    Drawable currentDrawable;
    PorterDuffXfermode mPorterDuffXfermode;

    public MaskDrawable(Drawable drawable, int color) {
        setDrawable(drawable);
        setMaskColor(color);
    }

    public MaskDrawable() {
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);
        mColorDrawable.setBounds(left, top, right, bottom);
        mNinePatchDrawable.setBounds(left, top, right, bottom);

        System.out.println("setBounds:");
    }

    @Override
    protected boolean onStateChange(int[] stateSet) {
        // TODO Auto-generated method stub
        for (int state : stateSet) {
            System.out.println(state);
            if (state == pressed || state == focused) {
                System.out.println("有了!");
                selectDrawable(1);
                return true;
            }
        }
        selectDrawable(0);
        return true;
    }

    @Override
    public boolean selectDrawable(int idx) {

        if (idx == 0) {
            currentDrawable = mNinePatchDrawable;
        } else {
            currentDrawable = mColorDrawable;
        }

        invalidateSelf();
        return true;
    }

    public void setDrawable(Drawable drawable) {
        mNinePatchDrawable = drawable;
        addState(defaultState, drawable);

    }

    public void setMaskColor(int color) {
        this.color = color;
        mColorDrawable = new ColorDrawable(color);
        addState(pressState, mColorDrawable);
    }

    @Override
    public void draw(Canvas canvas) {
        // mDrawable.draw(canvas);

        if (currentDrawable == mNinePatchDrawable)
        {
            mNinePatchDrawable.draw(canvas);
        } else {
            drawClick(canvas);
        }

    }

    private void drawClick(Canvas canvas) {
        mNinePatchDrawable.draw(canvas);
        mNinePatchDrawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        mNinePatchDrawable.draw(canvas);
        mNinePatchDrawable.setColorFilter(null);
    }
}
