
package com.smarttiger.demos;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.smarttiger.demos.bean.Demo;

public class DeskTopActivity extends BaseActivity implements OnClickListener {
    public static final String PACKAGE_NAME = "com.ijinshan.browser";
    public static final String LIEBAO_OFFICIAL_URL = "http://bbs.liebao.cn";
    public static final Demo DEMO = new Demo();
    static {
        DEMO.setClassName(DeskTopActivity.class);
        DEMO.setTitle("快捷方式");
        StringBuilder sb = new StringBuilder();
        DEMO.setDescription(sb.toString());

    }

    private View add, delete, myVideoAdd, myVideoDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.desk_top_activity);
        add = findViewById(R.id.add);
        delete = findViewById(R.id.delete);
        myVideoAdd = findViewById(R.id.my_video_add);
        myVideoDelete = findViewById(R.id.my_video_delete);
        add.setOnClickListener(this);
        delete.setOnClickListener(this);
        myVideoAdd.setOnClickListener(this);
        myVideoDelete.setOnClickListener(this);
    }

    public void add(String name, int imgRes) {
        Intent intentAddShortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        // 添加名称
        intentAddShortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME,
                name);
        // 添加图标
        intentAddShortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
                Intent.ShortcutIconResource.fromContext(this,
                        imgRes));
        /* 设置不允许重复创建 */
        intentAddShortcut.putExtra("duplicate", false);
        // 设置Launcher的Uri数据
        Intent intentLauncher = new Intent();
        intentLauncher.setAction(Intent.ACTION_VIEW);
        intentLauncher.setData(Uri.parse("http://v.m.liebao.cn/?f=android9"));
        intentLauncher.putExtra("from_shortcut", true);
        // 添加快捷方式的启动方法
        intentLauncher.setClassName("com.ijinshan.browser",
                "com.ijinshan.browser.screen.BrowserActivity");
        intentAddShortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intentLauncher);
        sendBroadcast(intentAddShortcut);

    }

    public void delete(String name, int res) {

        Intent intentAddShortcut = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");
        // 添加名称
        intentAddShortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME,
                name);
        // 添加图标
        intentAddShortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
                Intent.ShortcutIconResource.fromContext(this,
                        res));
        /* 设置不允许重复创建 */
        intentAddShortcut.putExtra("duplicate", false);
        // 设置Launcher的Uri数据
        Intent intentLauncher = new Intent();
        intentLauncher.setAction(Intent.ACTION_VIEW);
        intentLauncher.setData(Uri.parse("http://v.m.liebao.cn/?f=android9"));
        intentLauncher.putExtra("from_shortcut", true);
        // 添加快捷方式的启动方法
        intentLauncher.setClassName("com.ijinshan.browser",
                "com.ijinshan.browser.screen.BrowserActivity");
        intentAddShortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intentLauncher);
        sendBroadcast(intentAddShortcut);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.add:
                add("我的视频", R.drawable.ic_launcher);
                break;
            case R.id.delete:
                delete("我的视频", R.drawable.ic_launcher);
                break;
            case R.id.my_video_add:
                add("影视大全", R.drawable.ic_launcher);
                break;
            case R.id.my_video_delete:
                delete("影视大全", R.drawable.ic_launcher);
                break;

            default:
                break;
        }
    }
}
