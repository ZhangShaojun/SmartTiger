
package com.smarttiger.demos;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.smarttiger.demos.bean.Demo;
import com.smarttiger.demos.bean.InfoBar;
import com.smarttiger.demos.manager.InfoBarManager;

public class SquenceInfoBarActivity extends BaseActivity implements OnClickListener {

    public static final Demo DEMO = new Demo();
    static {
        DEMO.setClassName(SquenceInfoBarActivity.class);
        DEMO.setTitle("弹出infoBar的各种优先级");
        StringBuilder sb = new StringBuilder();
        sb.append("这是一个InfoBar弹出的各种例子");
        DEMO.setDescription(sb.toString());

    }

    ViewGroup info_bar_parent;
    Button show, addNomal, addSuper, addNomalNotShow, addSuperNotShow, randomAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.squence_info_activity);
        info_bar_parent = (ViewGroup) findViewById(R.id.info_bar_parent);

        addNomal = (Button) findViewById(R.id.add_nomal);
        show = (Button) findViewById(R.id.show);
        addSuper = (Button) findViewById(R.id.add_super);
        addNomalNotShow = (Button) findViewById(R.id.add_nomal_not_show);
        addSuperNotShow = (Button) findViewById(R.id.add_super_not_show);
        randomAdd = (Button) findViewById(R.id.random_add);
        addNomal.setOnClickListener(this);
        addSuper.setOnClickListener(this);
        addNomalNotShow.setOnClickListener(this);
        addSuperNotShow.setOnClickListener(this);
        show.setOnClickListener(this);
        randomAdd.setOnClickListener(this);

    }

    public static class NomalInfoBar extends InfoBar {

        public NomalInfoBar(Context context, ViewGroup parent, String text) {
            super(context, parent);
            setText(text);
        }

        String text;

        public void setText(String text) {
            button.setText(text);
        }

        Button button;

        @Override
        public View onCreateView() {
            // TODO Auto-generated method stub
            button = new Button(mContext);
            button.setText(text);
            button.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {

                    hide();
                }
            });
            return button;
        }

        @Override
        public void onShow() {
            // TODO Auto-generated method stub

        }

    }

    int nomal = 0;
    int jsuper = 0;
    boolean go;

    @Override
    public void onClick(View v) {

        if (v == addNomal) {
            addNomalAndShow();

        } else if (v == addSuper) {
            addSuperAndShow();

        } else if (v == addNomalNotShow) {
            addNomal();

        } else if (v == addSuperNotShow) {
            addSuper();

        } else if (v == show) {
            InfoBarManager.getInstance().show();
        } else if (v == randomAdd) {
            go = true;
            new Thread(new Runnable() {

                @Override
                public void run() {
                    while (go) {
                        randomAdd();
                        int time = (int) (Math.random() * 3000 + 1000);
                        try {
                            Thread.sleep(time);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }

            }).start();
            new Thread(new Runnable() {

                @Override
                public void run() {
                    while (go) {
                        randomAdd();
                        int time = (int) (Math.random() * 3000 + 1000);
                        try {
                            Thread.sleep(time);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
    }

    private void randomAdd() {
        int what = (int) (Math.random() * 4);

        switch (what) {
            case 0:
                addNomal();
                break;
            case 1:
                addSuper();
                break;
            case 2:

                addNomalAndShow();
                break;
            case 3:
                addSuperAndShow();

                break;

            default:
                break;
        }
    }

    private void addSuper() {
        NomalInfoBar nomalInfoBar = new NomalInfoBar(this, info_bar_parent, "super"
                + String.valueOf(jsuper));
        jsuper++;
        nomalInfoBar.setWeight(3);
        InfoBarManager.getInstance().addInfoBar(nomalInfoBar);
    }

    private void addNomal() {
        NomalInfoBar nomalInfoBar = new NomalInfoBar(this, info_bar_parent,
                String.valueOf(nomal));
        nomal++;
        InfoBarManager.getInstance().addInfoBar(nomalInfoBar);
    }

    private void addSuperAndShow() {
        NomalInfoBar nomalInfoBar = new NomalInfoBar(this, info_bar_parent, "super"
                + String.valueOf(jsuper));
        jsuper++;
        nomalInfoBar.setWeight(3);
        InfoBarManager.getInstance().show(nomalInfoBar);
    }

    private void addNomalAndShow() {
        NomalInfoBar nomalInfoBar = new NomalInfoBar(this, info_bar_parent,
                String.valueOf(nomal));
        nomal++;
        InfoBarManager.getInstance().show(nomalInfoBar);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        go = false;
        InfoBarManager.getInstance().clear();
    }
}
