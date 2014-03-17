
package com.smarttiger.demos.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.OverScroller;

public class OverScrollView extends FrameLayout {

    public static final String TAG = "OverScrollView";
    private OverScroller mOverScroller;
    private float mLastTouchY;
    private float mLastTouchX;

    private VelocityTracker mVelocityTracker;
    private int mMinimumVelocity;
    private int mMaximumVelocity;
    private int mActivePointerId = INVALID_POINTER;
    private ViewGroup child;
    public static final int maxOverScrollDistance = 100;
    public static final int scrollTo = 300;
    public static final int scrollTo_1 = scrollTo + 140;

    /**
     * Sentinel value for no current active pointer. Used by
     * {@link #mActivePointerId}.
     */
    private static final int INVALID_POINTER = -1;

    public OverScrollView(Context context) {
        super(context);
        init();
    }

    public OverScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mOverScroller = new OverScroller(getContext());
        mVelocityTracker = VelocityTracker.obtain();
        ViewConfiguration configuration = ViewConfiguration.get(getContext());
        mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
        mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float currentX = event.getX();
        float currentY = event.getY();
        int action = event.getAction() & MotionEvent.ACTION_MASK;
        // Log.d(TAG, "action=" + event.getAction() + "mask=" + action);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastTouchY = event.getY();
                mActivePointerId = event.getPointerId(0);
                mOverScroller.abortAnimation();
                mVelocityTracker.clear();
                break;
            case MotionEvent.ACTION_MOVE:
                int index = event.findPointerIndex(mActivePointerId);
                // Log.d(TAG, "index=" + index);
                float y = event.getY(index);

                int detaY = (int) (mLastTouchY - y);
                mLastTouchY = y;
                childScrollBy(detaY);
                mVelocityTracker.addMovement(event);
                break;
            case MotionEvent.ACTION_POINTER_DOWN: {
                final int actionIndex = event.getActionIndex();
                y = event.getY(actionIndex);
                mLastTouchY = y;
                mActivePointerId = event.getPointerId(actionIndex);
                break;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mVelocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);

                mActivePointerId = INVALID_POINTER;
                checkOverScroll();
                break;
            case MotionEvent.ACTION_POINTER_UP:
                onSecondaryPointerUp(event);
                mLastTouchY = event.getY(event.findPointerIndex(mActivePointerId));
                break;

        }
        return true;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        child = (ViewGroup) getChildAt(0);

    }

    private void childScrollBy(int y) {
        int scrollY = child.getScrollY();
        int target = scrollY + y;
        if (target < -maxOverScrollDistance) {
            child.scrollTo(0, -maxOverScrollDistance);

        } else if (target > getScrollDistance() + maxOverScrollDistance) {
            child.scrollTo(0, getScrollDistance() + maxOverScrollDistance);
        } else {
            child.scrollBy(0, y);
        }

    }

    private void childScrollTo(int y) {

        child.scrollTo(0, y);

    }

    private int getChildScrollY() {
        return child.getScrollY();

    }

    private int getChildHeight() {
        int count = child.getChildCount();
        if (count > 0)
        {
            return child.getChildAt(count - 1).getBottom();
        }
        return child.getMeasuredHeight();
    }

    private int getScrollDistance() {
        int childHeight = getChildHeight();
        int scrollHeight = childHeight - getMeasuredHeight();
        log("getScrollDistance" + scrollHeight);
        return scrollHeight;
    }

    private void postInvalidateChild() {
        child.postInvalidate();

    }

    private void checkOverScroll() {

        int startY = getChildScrollY();

        int yV = (int) mVelocityTracker.getYVelocity();

        if (startY > 0 && startY < scrollTo) {
            int childScroll = getChildScrollY();
            log("yV" + yV + "childScroll" + childScroll);
            if (yV > 0) {
                mOverScroller.startScroll(0, childScroll, 0, -childScroll);
            } else {
                mOverScroller.startScroll(0, childScroll, 0, scrollTo - childScroll);

            }
            postInvalidate();
            return;
        } else if (startY > scrollTo && startY < scrollTo_1) {
            int childScroll = getChildScrollY();
            log("yV" + yV + "childScroll" + childScroll);
            if (yV > 0) {
                mOverScroller.startScroll(0, childScroll, 0, -childScroll + scrollTo);
            } else {
                mOverScroller.startScroll(0, childScroll, 0, scrollTo_1 - childScroll);

            }
            postInvalidate();
            return;
        }
        if (Math.abs(yV) > mMinimumVelocity) {
            log("fling");
            mOverScroller.fling(0, startY, 0, -yV, 0, 0, 0, getScrollDistance(), 0,
                    maxOverScrollDistance);
            postInvalidate();
        } else {
            int childScroll = getChildScrollY();
            if (childScroll < 0) {
                mOverScroller.startScroll(0, childScroll, 0, -childScroll);
                postInvalidate();
            } else if (childScroll > getScrollDistance()) {
                mOverScroller.startScroll(0, childScroll, 0, getScrollDistance() - childScroll);
                postInvalidate();
            }

        }
        Log.d(TAG, "checkOverScroll:" + yV);
    }

    private void onSecondaryPointerUp(MotionEvent ev) {
        Log.d(TAG, "onSecondaryPointerUp");
        final int pointerIndex = (ev.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >>
                MotionEvent.ACTION_POINTER_INDEX_SHIFT;
        final int pointerId = ev.getPointerId(pointerIndex);
        if (pointerId == mActivePointerId) {
            // This was our active pointer going up. Choose a new
            // active pointer and adjust accordingly.
            // TODO: Make this decision more intelligent.
            final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
            mLastTouchY = ev.getY(newPointerIndex);
            mActivePointerId = ev.getPointerId(newPointerIndex);
            if (mVelocityTracker != null) {
                mVelocityTracker.clear();
            }
        }
        Log.d(TAG, "mActivePointerId=" + mActivePointerId);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public void computeScroll() {
        if (mOverScroller.computeScrollOffset()) {

            int y = mOverScroller.getCurrY();

            childScrollTo(y);
            log("scroll:" + y);
            log("scroll-Velocity:" + mOverScroller.getCurrVelocity());
            postInvalidateChild();
        } else {

        }
    }

    private void log(String str) {

        Log.d(TAG, str);
    }
}
