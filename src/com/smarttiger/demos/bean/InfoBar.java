
package com.smarttiger.demos.bean;

import com.smarttiger.demos.manager.InfoBarManager;
import com.smarttiger.demos.utils.LOGManager;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public abstract class InfoBar {

    public Context mContext;

    public ViewGroup parent;
    public View view;

    private int weight = 0;

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public InfoBar(Context context, ViewGroup parent) {
        this.mContext = context;
        this.parent = parent;
        view = onCreateView();
    }

    public void show() {
        parent.post(new Runnable() {

            @Override
            public void run() {
                if (parent != null && view != null) {
                    parent.removeAllViews();
                    parent.addView(view);
                    LOGManager.d("InfoBar addView");
                    onShow();
                }
            }
        });

    }

    public abstract View onCreateView();

    /**
     * ui 线程
     */
    public abstract void onShow();

    public void hide() {
        parent.post(new Runnable() {

            @Override
            public void run() {

                parent.removeAllViews();
                InfoBarManager.getInstance().onHide(InfoBar.this);
            }
        });
    };
}
