
package com.smarttiger.demos.view;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

public class AnimateListView extends ListView implements AnimatorListener, AnimatorUpdateListener {

    ValueAnimator valueAnimator;

    private static final int ANIMATOR_TIME = 1000;
    private static final float END_FRAME = 100;
    private static final float overAni = 0.1f;
    private int mCurrentbackAni = -1;

    public AnimateListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setChildrenDrawingOrderEnabled(true);
        valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(ANIMATOR_TIME);
        valueAnimator.addListener(this);
        valueAnimator.addUpdateListener(this);

    }

    @Override
    protected void measureChildren(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        super.measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
        System.out.println("onFocusChanged");
    }

    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
        int result = childCount - i - 1;
        return result;
    }

   
    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        if (valueAnimator.isRunning()) {
            valueAnimator.cancel();
        }
        valueAnimator.start();
        // for (int i = 0; i < getChildCount(); i++) {
        // View child = getChildAt(i);
        // System.out.println("child height" + child.getHeight());
        // }
    }

    @Override
    public void onAnimationStart(Animator animation) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onAnimationCancel(Animator animation) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onAnimationRepeat(Animator animation) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        int aniCount = getChildCount() + 1;
        int mItemHeight = 200;
        float y = Float.valueOf(animation.getAnimatedValue().toString());
        int index = (int) (y * aniCount / END_FRAME);

        if (index < aniCount) {
            float childAnimatorY = (y - index * END_FRAME / aniCount)
                    / (END_FRAME / aniCount);
            float currentTranslateY = (childAnimatorY - 1) * mItemHeight;
            if (index > 0) {
                int preIndex = index - 1;
                View pre = getChildAt(preIndex);
                if (childAnimatorY < overAni) {
                    float preTranslateY = childAnimatorY * mItemHeight;
                    pre.setTranslationY(preTranslateY);
                } else {
                    if (mCurrentbackAni < preIndex) {
                        mCurrentbackAni = preIndex;
                        // backAnimator(pre);
                    }
                }

            }
            if (index < getChildCount()) {
                View current = getChildAt(index);
                current.setVisibility(View.VISIBLE);
                translateY(current, currentTranslateY);
            }

        }

    }

    private void translateY(View target, float y) {
        if (target != null) {
            target.setTranslationY(y);
        }

    }

}
