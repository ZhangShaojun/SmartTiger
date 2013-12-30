
package com.smarttiger.demos;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.smarttiger.demos.bean.Demo;

public class HideMxSmartBarActivity extends Activity {
    public static final Demo DEMO = new Demo();
    static {
        DEMO.setClassName(HideMxSmartBarActivity.class);
        DEMO.setTitle("隐藏魅族手机的smartbar");
        StringBuilder sb = new StringBuilder();
        DEMO.setDescription(sb.toString());

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        processNavBar(getWindow().getDecorView());

    }

    public static void processNavBar(View view) {
        String manufacturer = Build.MANUFACTURER;
        String hardware = Build.HARDWARE;
        if (manufacturer != null
                && manufacturer.toLowerCase().equals("meizu")
                && hardware != null
                && (hardware.toLowerCase().contains("mx2") || hardware.toLowerCase()
                        .contains("mx3"))) {
            view.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    }
}
