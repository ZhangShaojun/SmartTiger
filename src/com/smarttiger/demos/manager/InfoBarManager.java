
package com.smarttiger.demos.manager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import com.smarttiger.demos.bean.InfoBar;
import com.smarttiger.demos.utils.LOGManager;

public class InfoBarManager {

    public static InfoBarManager instance;

    Vector<InfoBar> infoBars;

    Thread mThread;
    Object lock;
    InfoBar currentInfoBar = null;
    Comparator<InfoBar> mComparator;

    public static InfoBarManager getInstance() {

        if (instance == null) {
            instance = new InfoBarManager();

        }
        return instance;
    }

    private InfoBarManager() {
        infoBars = new Vector<InfoBar>();
        lock = new Object();
        mComparator = new InfoBarComparator();
        mThread = new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    LOGManager.d("InfoBarManager.newThread run");
                    synchronized (lock) {
                        LOGManager.d("InfoBarManager.newThread lock");
                        InfoBar infoBar;
                        try {
                            if (infoBars.size() == 0) {
                                currentInfoBar = null;
                            } else {
                                infoBar = infoBars.get(0);
                                if (infoBar != null) {
                                    currentInfoBar = infoBar;
                                    currentInfoBar.show();
                                    infoBars.remove(infoBar);
                                } else {
                                    currentInfoBar = null;
                                }
                            }

                            LOGManager.d("InfoBarManager.newThread waited");
                            lock.wait();
                            LOGManager.d("InfoBarManager.newThread waked!");

                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    if (currentInfoBar != null) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                }

            }
        });
        mThread.start();
    }

    public void addInfoBar(InfoBar infoBar) {
        infoBars.add(infoBar);
        Collections.sort(infoBars, mComparator);

    }

    public void show() {
        if (infoBars != null) {
            synchronized (lock) {
                lock.notify();
                LOGManager.d("InfoBarManager.show notify");
            }
        }
    }

    public void show(InfoBar infoBar) {

        addInfoBar(infoBar);
        if (currentInfoBar == null) {
            synchronized (lock) {
                lock.notify();
                LOGManager.d("InfoBarManager.show notify");
            }
        }
    }

    public void onHide(InfoBar infoBar) {
        synchronized (lock) {
            if (currentInfoBar == infoBar) {
                lock.notify();
            }

        }
    }

    public void clear() {
        infoBars.clear();
        currentInfoBar = null;
    }

    public static class InfoBarComparator implements Comparator<InfoBar> {

        @Override
        public int compare(InfoBar lhs, InfoBar rhs) {
            int leftWeight = lhs.getWeight();
            int rightWeight = rhs.getWeight();
            if (leftWeight > rightWeight) {
                return -1;
            } else if (leftWeight == rightWeight) {
                return 0;
            } else {
                return 1;
            }
        }
    }
}
