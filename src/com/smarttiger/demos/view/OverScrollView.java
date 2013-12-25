
package com.smarttiger.demos.view;

import android.content.Context;
import android.os.StrictMode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
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
                break;
            case MotionEvent.ACTION_MOVE:
                int index = event.findPointerIndex(mActivePointerId);
                // Log.d(TAG, "index=" + index);
                float y = event.getY(index);

                int detaY = (int) (mLastTouchY - y);
                mLastTouchY = y;
                scrollBy(0, detaY);
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
                break;
            case MotionEvent.ACTION_POINTER_UP:
                onSecondaryPointerUp(event);
                mLastTouchY = event.getY(event.findPointerIndex(mActivePointerId));
                break;

        }
        return true;
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

}
