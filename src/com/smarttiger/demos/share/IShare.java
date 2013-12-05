
package com.smarttiger.demos.share;

import android.graphics.Bitmap;

public interface IShare {

    public void shareText(String text, String title, String description);

    public void shareBitmap(Bitmap bitmap, String title, String description);

    public void shareImgURL(String url, String title, String description);

    public void shareWebPage(Bitmap thumb, String webPageUrl, String title, String description);

    public void setOnShareCallBack(OnShareCallBack onShareCallBack);

}
