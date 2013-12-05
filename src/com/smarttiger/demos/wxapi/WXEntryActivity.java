
package com.smarttiger.demos.wxapi;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.smarttiger.demos.BaseActivity;
import com.smarttiger.demos.R;
import com.smarttiger.demos.bean.Demo;
import com.smarttiger.demos.share.ShareManager;

public class WXEntryActivity extends BaseActivity implements OnClickListener {
    ShareManager mShareManager;
    public static final Demo DEMO = new Demo();
    static {
        DEMO.setClassName(WXEntryActivity.class);
        DEMO.setTitle("各种分享");
        StringBuilder sb = new StringBuilder();
        DEMO.setDescription(sb.toString());
    }
    public static final String WECHAT_PACKAGE_NAME="com.tencent.mm";
    public static final String SINA_WEIBO_PACKAGE_NAME="com.sina.weibo";
    private View mTranslateView;

    private GridView mShareToGridView, mSaveToGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_layout);
        mTranslateView = findViewById(R.id.share_translate_view);
        mShareToGridView = (GridView) findViewById(R.id.share_to_frends_grid_view);
        mSaveToGridView = (GridView) findViewById(R.id.save_to_grid_view);
        mSaveToGridView.setAdapter(new ShareAdapter(this, generateShareList()));
        mShareToGridView.setAdapter(new ShareAdapter(this, generateSaveToList()));
        mTranslateView.setOnClickListener(this);
    }

    /**
     * 生成分享列表
     * @return
     */
    private List<ShareItem> generateShareList() {

        List<ShareItem> shareItems = new ArrayList<WXEntryActivity.ShareItem>();
        ShareItem sinaWeibo=new ShareItem();
        sinaWeibo.setName(getString(R.string.share_sina_weibo));
        sinaWeibo.setPackName(SINA_WEIBO_PACKAGE_NAME);
        shareItems.add(sinaWeibo);
        
        ShareItem weChat=new ShareItem();
        weChat.setPackName(SINA_WEIBO_PACKAGE_NAME);
        shareItems.add(weChat);
        return shareItems;
    }
 
    private List<ShareItem> generateSaveToList() {
        List<ShareItem> saveItems = new ArrayList<WXEntryActivity.ShareItem>();
        return saveItems;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        mShareManager.handIntent(intent);

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.share_translate_view) {
            finish();
        }
        // mShareManager.getWeChatShare().shareText("x", "x", "xxx");
    }

    private static class ShareAdapter extends BaseAdapter {

        private Context mContext;

        private List<ShareItem> mItems;
        private LayoutInflater inflater;

        public ShareAdapter(Context context, List<ShareItem> items) {
            super();
            inflater = LayoutInflater.from(context);
            mContext = context;
            mItems = items;
        }

        @Override
        public int getCount() {
            return mItems.size();
        }

        @Override
        public Object getItem(int position) {
            return mItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {

                convertView = inflater.inflate(R.layout.share_item_layout, null);
                viewHolder = new ViewHolder();
                viewHolder.shareIconImageView = (ImageView) convertView
                        .findViewById(R.id.share_icon);
                viewHolder.shareNameTextView = (TextView) convertView.findViewById(R.id.share_name);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            ShareItem shareItem = mItems.get(position);
            viewHolder.shareIconImageView.setImageResource(shareItem.getIconRes());
            viewHolder.shareNameTextView.setText(shareItem.getName());

            return convertView;
        }

    }

    private static class ViewHolder {
        TextView shareNameTextView;
        ImageView shareIconImageView;
    }

    private static class ShareItem {
        private String name;
        private int iconRes;
        private String packName;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getIconRes() {
            return iconRes;
        }

        public void setIconRes(int iconRes) {
            this.iconRes = iconRes;
        }

        public String getPackName() {
            return packName;
        }

        public void setPackName(String packName) {
            this.packName = packName;
        }

    }
    /** 是否安装微信 */
    public boolean isInstallWx(String packageName) {
        try {

            PackageManager manager = getPackageManager();

            PackageInfo info = manager.getPackageInfo(packageName,
                    PackageManager.GET_ACTIVITIES);

            if (info != null) {

                return true;
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return false;
    }
}
