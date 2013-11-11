
package com.smarttiger.demos;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.smarttiger.demos.bean.Demo;

public class DefaultOpenActivity extends BaseActivity implements OnClickListener {

    public static final String PACKAGE_NAME = "com.ijinshan.browser";
    public static final String LIEBAO_OFFICIAL_URL = "http://bbs.liebao.cn";
    public static final Demo DEMO = new Demo();
    static {
        DEMO.setClassName(DefaultOpenActivity.class);
        DEMO.setTitle("默认打开豹浏览器");
        StringBuilder sb = new StringBuilder();
        sb.append("这是一个用默认打开猎豹浏览器的例子\n");
        DEMO.setDescription(sb.toString());

    }
    Button setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.default_open_activity);
        TextView textView = (TextView) findViewById(R.id.text);
        textView.setText(DEMO.getDescription());
        setting = (Button) findViewById(R.id.open);
        setting.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        boolean is = isSelfDefaultBrowser(this);
        if (is) {
            setting.setText("去除默认");
        } else {
            setting.setText("设置默认");
        }
    }

    public static boolean isSelfDefaultBrowser(Context context) {
        boolean flag = false;

        ArrayList<ResolveInfo> arr = getHasDefValueBrowser(context);
        Iterator<ResolveInfo> iter = arr.iterator();
        while (iter.hasNext()) {
            ResolveInfo resolve = iter.next();
            if (resolve.activityInfo.packageName.equals(PACKAGE_NAME)) {
                return true;
            }
        }

        return flag;
    }

    public static ArrayList<ResolveInfo> getHasDefValueBrowser(Context context) {
        ArrayList<ResolveInfo> list_browsers = new ArrayList<ResolveInfo>();
        PackageManager packageManager = context.getPackageManager();

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse("http://www.baidu.com"));

        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        List<ComponentName> list_comp = new ArrayList<ComponentName>();
        List<IntentFilter> list_filter = new ArrayList<IntentFilter>();

        Iterator<ResolveInfo> activity_iter = activities.iterator();
        while (activity_iter.hasNext()) {

            ResolveInfo resolveInfo = activity_iter.next();
            packageManager.getPreferredActivities(list_filter, list_comp,
                    resolveInfo.activityInfo.packageName);

            Iterator<IntentFilter> filter_iter = list_filter.iterator();
            while (filter_iter.hasNext()) {
                IntentFilter fil = filter_iter.next();
                if ((fil.hasCategory(Intent.CATEGORY_BROWSABLE)
                        || fil.hasCategory(Intent.CATEGORY_DEFAULT))
                        && fil.hasDataScheme("http")) {
                    list_browsers.add(resolveInfo);
                }
            }
        }

        return list_browsers;
    }

    public void onClick(View v) {
        boolean is = isSelfDefaultBrowser(this);

        if (is) {
            ResolveInfo resolve = getHasDefValueBrowser(this).get(0);
            startActivity(getPackageDetailsIntent(resolve.activityInfo.packageName));
        } else {
            Intent intent_def = new Intent();
            intent_def.setAction(Intent.ACTION_VIEW);
            intent_def.putExtra("set_browser_congratulation", "set_browser_congratulation");
            intent_def.addCategory(Intent.CATEGORY_DEFAULT);
            intent_def.setData(Uri.parse(LIEBAO_OFFICIAL_URL));
            ComponentName name = new ComponentName("android",
                    "com.android.internal.app.ResolverActivity");
            intent_def.setComponent(name);
            this.startActivity(intent_def);
        }
    }
    
    private static final String SCHEME = "package";
    /**
     * 调用系统InstalledAppDetails界面所需的Extra名称(用于Android 2.1及之前版本)
     */
    private static final String APP_PKG_NAME_21 = "com.android.settings.ApplicationPkgName";
    /**
     * 调用系统InstalledAppDetails界面所需的Extra名称(用于Android 2.2)
     */
    private static final String APP_PKG_NAME_22 = "pkg";

    private static final String ACTION_APPLICATION_DETAILS_SETTINGS_23 = "android.settings.APPLICATION_DETAILS_SETTINGS";
    /**
     * InstalledAppDetails所在包名
     */
    private static final String APP_DETAILS_PACKAGE_NAME = "com.android.settings";
    /**
     * InstalledAppDetails类名
     */
    private static final String APP_DETAILS_CLASS_NAME = "com.android.settings.InstalledAppDetails";
    /**
     * 调用系统InstalledAppDetails界面显示已安装应用程序的详细信息。 对于Android 2.3（Api Level
     * 9）以上，使用SDK提供的接口； 2.3以下，使用非公开的接口（查看InstalledAppDetails源码）。
     * @param packageName
     *            应用程序的包名
     */
    public static Intent getPackageDetailsIntent(String packageName) {
        Intent intent = new Intent();
        int apiLevel = 0;
        try {
            apiLevel = VERSION.SDK_INT;
        } catch (Exception ex) {
        }
        if (apiLevel >= 9) { // 2.3（ApiLevel 9）以上，使用SDK提供的接口
            intent.setAction(ACTION_APPLICATION_DETAILS_SETTINGS_23);
            Uri uri = Uri.fromParts(SCHEME, packageName, null);
            intent.setData(uri);
        } else { // 2.3以下，使用非公开的接口（查看InstalledAppDetails源码）
            // 2.2和2.1中，InstalledAppDetails使用的APP_PKG_NAME不同。
            final String appPkgName = (apiLevel >7 ? APP_PKG_NAME_22 : APP_PKG_NAME_21);
            intent.setAction(Intent.ACTION_VIEW);
            intent.setClassName(APP_DETAILS_PACKAGE_NAME, APP_DETAILS_CLASS_NAME);
            intent.putExtra(appPkgName, packageName);
        }
        return intent;
    }
}
