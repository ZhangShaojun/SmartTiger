
package com.smarttiger.demos.share;

public interface OnShareCallBack {

    public void onShareSuc(String message);

    public void onShareFail(String message);

    public void onShareCancel(String message);
    public void onShareException(String message);

}
