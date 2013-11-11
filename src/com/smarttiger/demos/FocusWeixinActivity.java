package com.smarttiger.demos;

import com.smarttiger.demos.bean.Demo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

public class FocusWeixinActivity extends BaseActivity {

    public static final Demo DEMO=new Demo();
    static {
        DEMO.setClassName(FocusWeixinActivity.class);
        DEMO.setTitle("关注微信");
        StringBuilder sb = new StringBuilder();
        sb.append("这是一个关注微信的例子的例子\n");
        DEMO.setDescription(sb.toString());

    }
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        mContext=this;
        
        invokeWeChat();
    }
    private boolean isPackageInstalled(String packageName) {
        boolean installed = true;
        try {
            mContext.getPackageManager().getPackageInfo(packageName, 0);
        } catch (Exception e) {
            installed = false;
        }
        return installed;
    }
    
    public void invokeWeChat() {
        if (!isPackageInstalled("com.tencent.mm")) {
//            UIUtils.ShowToast(mContext, mContext.getString(R.string.no_weixin), true);
            Toast.makeText(mContext, "没有微信", Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent();
        intent.setPackage("com.tencent.mm");                                        // 微信包名
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://weixin.qq.com/r/wHXT3GbEUlgth3e-nyDk"));   // 手机毒霸二维码数据内容
        try {
            mContext.startActivity(intent);
        } catch (Exception e) {
            
            try {
                
                //
                //字符串加密，用字节表示 LauncherUI_From_Biz_Shortcut字符串
                //
                byte buffer[] = {
                        76, 97, 117, 110, 99, 104, 101, 114, 85, 73, 95, 70, 
                        114, 111, 109, 95, 66, 105, 122, 95, 83, 104, 111, 114, 116, 99, 117, 116};
                
                //com.tencent.mm.ui.LauncherUI
                byte buffer2[] = {
                        99, 111, 109, 46, 116, 101, 110, 99, 101, 110, 116, 46, 109, 109, 46, 117, 
                        105, 46, 76, 97, 117, 110, 99, 104, 101, 114, 85, 73};
                
                Intent localIntent = new Intent("liebaocn");
                localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                localIntent.setClassName("com.tencent.mm", new String(buffer2));
                localIntent.putExtra(new String(buffer), true);
                mContext.startActivity(localIntent);
                
            } catch(Exception e2) {
//                UIUtils.ShowToast(mContext, mContext.getString(R.string.weixin_unsupport), true);
                Toast.makeText(mContext, "微信不支持", Toast.LENGTH_LONG).show();
            }
        }
    }
}
