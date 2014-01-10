
package com.smarttiger.demos.view;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.smarttiger.demos.R;

public class SimpleMutiWindowLayout extends LinearLayout implements
        OnClickListener {
    private float mLastTouchX;
    private float mLastTouchY;
    private View mTouchedChild = null;
    private static final int TOUCH_STATE_UNTOUCH = 0;
    private static final int TOUCH_STATE_ALLOW_SCROLL = 1;
    private static final int TOUCH_STATE_DRAG_ITEM = 2;
    private int mTouchState = TOUCH_STATE_UNTOUCH;
    /**
     * 判定是横向还是纵向的值
     */
    private static final float X_Y = 0.8f;
    private static final float REMOVE_X = 0.25f;
    public static final int ANIMATOR_TIME_REMOVE = 200;
    /**
     * 关闭所有的时候每个子view的动画延时时间
     */
    public static final int ANIMATOR_DELAYED_TIEM = 40;
    /**
     * 子view加进来显示的动画时间
     */
    public static final int ANIMATOR_TIME_APPEARING = 500;
    /**
     * 子view被移除,重新布局动画的等待时间表
     */
    public static final int ANIMATOR_TIME_CHANGE_DISAPPEARING = 50;

    private LayoutTransition mLayoutTransition;
    private LayoutInflater mInflater;
    private OnSampleMutiWindowLayoutListener mOnSampleMutiWindowLayoutListener;
    /**
     * 是否正在删除所有子view
     */
    private boolean mAllRemoveing = false;

    public SimpleMutiWindowLayout(Context context) {
        super(context);

    }

    public SimpleMutiWindowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mLayoutTransition = new LayoutTransition();
        mLayoutTransition.setDuration(LayoutTransition.APPEARING, ANIMATOR_TIME_APPEARING);
        mLayoutTransition.setStartDelay(LayoutTransition.CHANGE_DISAPPEARING,
                ANIMATOR_TIME_CHANGE_DISAPPEARING);
        setLayoutTransition(mLayoutTransition);
        mInflater = LayoutInflater.from(getContext());
    }

    public void addItem(String title) {
        View item = mInflater.inflate(R.layout.muti_item, null);
        View close = item.findViewById(R.id.close);
        close.setOnClickListener(this);
        close.setTag(item);
        TextView titleText = (TextView) item.findViewById(R.id.title);
        titleText.setText(title);
        addView(item);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();
        float y = event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastTouchX = event.getX();
                mLastTouchY = event.getY();
                mTouchedChild = findTouchChildView(mLastTouchY);
                // 如果它正在走动画就无视这个事件
                if (mTouchedChild == null) {
                    return false;
                } else if (mTouchedChild.getTranslationX() != 0) {
                    mTouchedChild = null;
                    return false;
                }

                return true;
            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float deleX = x - mLastTouchX;
                float deleY = y - mLastTouchY;
                if (mTouchState == TOUCH_STATE_DRAG_ITEM) {
                    setChildTranslatX(deleX);
                } else {
                    if (Math.abs(deleX) > Math.abs(deleY) * X_Y && mTouchedChild != null) {
                        requestDisallowInterceptTouchEvent(true);
                        mTouchState = TOUCH_STATE_DRAG_ITEM;
                        setChildTranslatX(deleX);
                    } else {
                        requestDisallowInterceptTouchEvent(false);
                        mTouchState = TOUCH_STATE_ALLOW_SCROLL;
                        return super.onTouchEvent(event);
                    }
                }
                return true;
            case MotionEvent.ACTION_CANCEL:
                if (mTouchState == TOUCH_STATE_DRAG_ITEM) {
                    startRemoveOrResume();
                }
                mTouchState = TOUCH_STATE_UNTOUCH;
                requestDisallowInterceptTouchEvent(false);
                break;
            case MotionEvent.ACTION_UP:
                if (mTouchState == TOUCH_STATE_DRAG_ITEM) {
                    startRemoveOrResume();
                }
                requestDisallowInterceptTouchEvent(false);
                mTouchState = TOUCH_STATE_UNTOUCH;
                break;
            default:
                break;
        }
        return true;

    }

    private void startRemoveOrResume() {
        if (mTouchedChild == null) {
            return;
        }
        float translateX = mTouchedChild.getTranslationX();
        int width = getMeasuredWidth();
        float startX = translateX;
        float endX = 0;
        boolean needRemove = false;
        if (translateX > 0) {
            if (translateX > (width * REMOVE_X)) {
                // 删除
                endX = width;
                needRemove = true;

            } else {
                // 恢复
                endX = 0f;
                needRemove = false;
            }
        } else {
            if (Math.abs(translateX) > (width * REMOVE_X)) {
                // 删除
                endX = -width;
                needRemove = true;
            } else {
                // 恢复
                endX = 0;
                needRemove = false;
            }

        }
        animatRemoveOrResume(startX, endX, needRemove, mTouchedChild, false);
    }

    private void setChildTranslatX(float x) {
        if (mTouchedChild != null) {
            mTouchedChild.setTranslationX(x);
        }
    }

    private View findTouchChildView(float y) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child.getBottom() > y) {
                return child;
            }

        }
        return null;
    }

    public void closeAll() {
        if (mAllRemoveing) {
            return;
        } else {
            mAllRemoveing = true;
        }
        if (mOnSampleMutiWindowLayoutListener != null) {
            mOnSampleMutiWindowLayoutListener.onRemoveAllAniStart();
        }
        if (getChildCount() == 0) {
            if (mOnSampleMutiWindowLayoutListener != null) {
                mAllRemoveing = false;
                mOnSampleMutiWindowLayoutListener.onRemoveAllAniEnd();

            }
        }
        setLayoutTransition(null);
        final int size = getChildCount();
        View[] childs = new View[size];
        for (int i = 0; i < size; i++) {
            childs[i] = getChildAt(i);
        }

        for (int i = 0; i < size; i++) {
            final View view = childs[i];
            final int index = i;
            postDelayed((new Runnable() {

                @Override
                public void run() {

                    animatRemoveOrResume(0, view.getMeasuredWidth(), false, view, index == size - 1);
                }
            }), ANIMATOR_DELAYED_TIEM * index);
        }

    }

    private void animatRemoveOrResume(float begin, float end, final boolean removeChild,
            final View child, final boolean isFinishRemoveAll) {
        ValueAnimator removeAnimator = ObjectAnimator.ofFloat(child, "translationX", begin, end);
        removeAnimator.setDuration(ANIMATOR_TIME_REMOVE);
        removeAnimator.start();
        removeAnimator.addListener(new AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (removeChild) {
                    removeViewIfContains(child);
                }

                if (isFinishRemoveAll) {
                    if (mOnSampleMutiWindowLayoutListener != null) {
                        mAllRemoveing = false;
                        mOnSampleMutiWindowLayoutListener.onRemoveAllAniEnd();
                    }
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }
        });
    }

    public void closeItem(View view) {

        if (view != null && indexOfChild(view) > -1) {
            animatRemoveOrResume(0, view.getMeasuredWidth(), true, view, false);
        }
    }

    private void removeViewIfContains(View v) {
        if (indexOfChild(v) > -1) {
            removeView(v);
        }
        if (getChildCount() == 0) {
            if (mOnSampleMutiWindowLayoutListener != null) {
                mOnSampleMutiWindowLayoutListener.onRemoveAllAniEnd();
            }
        }
    }

    @Override
    public void onClick(View v) {
        v.setClickable(false);
        closeItem((View) v.getTag());
    }

    public OnSampleMutiWindowLayoutListener getOnSampleMutiWindowLayoutListener() {
        return mOnSampleMutiWindowLayoutListener;
    }

    public void setOnSampleMutiWindowLayoutListener(
            OnSampleMutiWindowLayoutListener onSampleMutiWindowLayoutListener) {
        this.mOnSampleMutiWindowLayoutListener = onSampleMutiWindowLayoutListener;
    }

    public interface OnSampleMutiWindowLayoutListener {
        public void onRemoveAllAniStart();

        public void onRemoveAllAniEnd();
    }

}
