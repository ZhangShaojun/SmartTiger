
package com.smarttiger.demos.share;

import android.content.Context;

import com.smarttiger.demos.R;

public class OnShareTrips {

    private Context mContext;

    public OnShareTrips(Context context) {
        super();
        this.mContext = context;
    }

    public String getShareSucTrip() {
        return mContext.getString(R.string.share_suc);
    }

    public String getShareFailTrip() {
        return mContext.getString(R.string.share_fail);
    }

    public String getShareCancelTrip() {
        return mContext.getString(R.string.share_cancel);
    }

    public String getShareExceptonTrip() {
        return mContext.getString(R.string.share_exceptoin);
    }
}
