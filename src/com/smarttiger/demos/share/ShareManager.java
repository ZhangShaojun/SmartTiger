
package com.smarttiger.demos.share;

import android.app.Activity;
import android.content.Intent;

public class ShareManager {

    Activity mActivity;
    private WeChatShare mWeChatShare;
    private SinaWBShare sinaWBShare;

    public SinaWBShare getSinaWBShare() {
        return sinaWBShare;
    }

    public WeChatShare getWeChatShare() {
        return mWeChatShare;
    }

    public ShareManager(Activity activity) {

        mActivity = activity;
        mWeChatShare = new WeChatShare(activity);
        sinaWBShare = new SinaWBShare(activity);

    }

    public void handIntent(Intent intent) {
        mWeChatShare.handIntent(intent);
        sinaWBShare.handleIntent(intent);
    }
}
