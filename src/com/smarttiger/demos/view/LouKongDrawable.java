
package com.smarttiger.demos.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.graphics.drawable.StateListDrawable;
import android.view.Gravity;

public class LouKongDrawable extends StateListDrawable {

    private CharSequence text;
    private float textSize;
    private int textColor;
    private Paint mPaint;
    int checked = android.R.attr.state_checked;

    private NinePatchDrawable mCheckedDrawable;

    private NinePatchDrawable mUncheckedDrawable;
    int gravity;
    public static final int CHECKED_STATE = 1;
    public static final int UNCHECKED_STATE = 0;
    int mCurrentState = 0;

    public LouKongDrawable(CharSequence text, float textSize, int textColor, int gravity) {
        super();
        this.text = text;
        this.textSize = textSize;
        this.textColor = textColor;
        this.gravity = gravity;
    }

    public LouKongDrawable() {

        init();
    }

    private void init()
    {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(textSize);
        mPaint.setColor(textColor);
        mPorterDuffXfermode = new PorterDuffXfermode(Mode.XOR);
    }

    public NinePatchDrawable getmCheckedDrawable() {
        return mCheckedDrawable;
    }

    public void setCheckedDrawable(NinePatchDrawable mCheckedDrawable) {
        this.mCheckedDrawable = mCheckedDrawable;
        invalidateSelf();
    }

    public NinePatchDrawable getmUncheckedDrawable() {
        return mUncheckedDrawable;
    }

    public void setUncheckedDrawable(NinePatchDrawable mUncheckedDrawable) {
        this.mUncheckedDrawable = mUncheckedDrawable;
        invalidateSelf();
    }

    public int getGravity() {
        return gravity;
    }

    public void setGravity(int gravity) {
        this.gravity = gravity;
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public CharSequence getText() {
        return text;
    }

    public void setText(CharSequence text) {
        this.text = text;
        invalidateSelf();
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        invalidateSelf();
    }

    @Override
    protected boolean onStateChange(int[] stateSet) {
        for (int state : stateSet) {
            if (state == checked) {
                selectDrawable(CHECKED_STATE);
                return true;
            }
        }
        selectDrawable(UNCHECKED_STATE);
        return true;
    }

    @Override
    public boolean selectDrawable(int idx) {

        if (mCurrentState == idx) {
            return false;
        } else {
            mCurrentState = idx;
            invalidateSelf();
            return true;
        }

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Rect rect = getBounds();

        if (isCheckedState()) {

            int sc = canvas.saveLayer(rect.left, rect.top, rect.right, rect.bottom, null,
                    Canvas.MATRIX_SAVE_FLAG |
                            Canvas.CLIP_SAVE_FLAG |
                            Canvas.HAS_ALPHA_LAYER_SAVE_FLAG |
                            Canvas.FULL_COLOR_LAYER_SAVE_FLAG |
                            Canvas.CLIP_TO_LAYER_SAVE_FLAG);

            Paint paint = mCheckedDrawable.getPaint();
            setPaintStyle(paint);
            paint.setXfermode(mPorterDuffXfermode);
            paint.setColor(Color.BLACK);
            drawBackground(canvas, mCheckedDrawable, rect);
            drawText(canvas, rect, paint);
            resetPaint(paint);
            canvas.restoreToCount(sc);
        } else {

            drawBackground(canvas, mUncheckedDrawable, rect);
            setPaintStyle(mPaint);
            drawText(canvas, rect, mPaint);

        }

    }

    public boolean isCheckedState() {
        return mCurrentState == CHECKED_STATE;
    }

    private void drawBackground(Canvas canvas, Drawable drawable, Rect bounds) {
        drawable.setBounds(bounds);
        drawable.draw(canvas);

    }

    private void drawText(Canvas canvas, Rect rect, Paint paint) {
        // mPaint.setColor(Color.argb(100, 255, 0, 0));
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        float x = rect.left;
        switch (gravity) {
            case Gravity.CENTER:
            case Gravity.CENTER_HORIZONTAL:
                paint.setTextAlign(Align.CENTER);
                x = (rect.left + rect.right) >> 1;
                break;
            case Gravity.RIGHT:
                paint.setTextAlign(Align.RIGHT);
                x = rect.right;
            default:
                paint.setTextAlign(Align.LEFT);
                x = rect.left;
                break;
        }
        FontMetrics fontMetrics = paint.getFontMetrics();

        float y = ((rect.top + rect.bottom) >> 1)
                + (((fontMetrics.bottom - fontMetrics.top) / 2)
                - fontMetrics.bottom);
        canvas.drawText(text.toString(), x, y, paint);
    }

    private void setPaintStyle(Paint paint) {
        paint.setAntiAlias(true);
        paint.setTextSize(textSize);
        paint.setColor(textColor);
    }

    PorterDuffXfermode mPorterDuffXfermode;

    private void resetPaint(Paint paint) {
        paint.setColor(Color.WHITE);
        paint.setXfermode(null);
    }
}
