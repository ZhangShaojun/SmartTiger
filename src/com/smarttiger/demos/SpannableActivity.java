
package com.smarttiger.demos;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.widget.EditText;
import android.widget.TextView;

import com.smarttiger.demos.bean.Demo;

public class SpannableActivity extends BaseActivity {
    public static final Demo DEMO = new Demo();
    static {
        DEMO.setClassName(SpannableActivity.class);
        DEMO.setTitle("Spannable研究");
        StringBuilder sb = new StringBuilder();
        // sb.append("这是一个用默认打开猎豹浏览器的例子\n");
        DEMO.setDescription(sb.toString());

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        float density = getResources().getDisplayMetrics().density;
        EditText text = new EditText(this);
        setContentView(text);

        SpannableString spannableString = new SpannableString("大家好,电话是:010-8978564修改文字内容可以知道EXCLUSIVE_EXCLUSIVE和SPAN_INCLUSIVE_INCLUSIVE的区别");
        ColorStateList colorStateList=ColorStateList.valueOf(Color.RED);
        TextAppearanceSpan textAppearanceSpan1 = new TextAppearanceSpan(null,
                android.graphics.Typeface.BOLD, (int) (32 * density),
                colorStateList, null);
        TextAppearanceSpan textAppearanceSpan2 = new TextAppearanceSpan(null,
                android.graphics.Typeface.ITALIC, (int) (32 * density),
                colorStateList, null);
        spannableString.setSpan(textAppearanceSpan1, 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        
        spannableString.setSpan(textAppearanceSpan2, 8,19, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        text.setText(spannableString);

    }
}
