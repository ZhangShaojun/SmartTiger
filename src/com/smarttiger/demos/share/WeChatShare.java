
package com.smarttiger.demos.share;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import com.tencent.mm.sdk.openapi.BaseReq;
import com.tencent.mm.sdk.openapi.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXTextObject;

public class WeChatShare implements IShare, IWXAPIEventHandler {
    private final String WX_PACKAGE_NAME = "com.tencent.mm";

    // APP_ID 替换为你的应用从官方网站申请到的合法appId
    public static final String APP_ID = "wx85414d9762a6b9d0";

    private OnShareCallBack mOnShareCallBack;

    private Context mContext;
    private OnShareTrips onShareTrips;
    IWXAPI api;

    public WeChatShare(Context context) {
        mContext = context;
        onShareTrips = new OnShareTrips(context);
        api=WXAPIFactory.createWXAPI(context, WeChatShare.APP_ID);
        api.registerApp(WeChatShare.APP_ID);
        
    }

    public void handIntent(Intent intent) {
        api.handleIntent(intent, this);
    }

    @Override
    public void onResp(BaseResp resp) {
        if (mOnShareCallBack == null) {
            return;
        }
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                mOnShareCallBack.onShareSuc(onShareTrips.getShareSucTrip());
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                mOnShareCallBack.onShareCancel(onShareTrips.getShareCancelTrip());
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                mOnShareCallBack.onShareFail(onShareTrips.getShareFailTrip());
                break;
            default:
                mOnShareCallBack.onShareException(onShareTrips.getShareExceptonTrip());
                break;
        }
    }

    @Override
    public void onReq(BaseReq arg0) {

        System.out.println("onReq");
        if (arg0 instanceof SendMessageToWX.Req) {
            SendMessageToWX.Req req = (SendMessageToWX.Req) arg0;
            System.out.println(req);
        }

    }

    @Override
    public void shareText(String text, String title, String description) {
        // 初始化一个WXTextObject对象
        WXTextObject textObj = new WXTextObject();
        textObj.text = text;

        // 用WXTextObject对象初始化一个WXMediaMessage对象
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;
        // 发送文本类型的消息时，title字段不起作用
        // msg.title = "Will be ignored";
        msg.description = description;

        // 构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("text"); // transaction字段用于唯一标识一个请求
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;

        // 调用api接口发送数据到微信
        api.sendReq(req);
       

    }

    @Override
    public void shareBitmap(Bitmap bitmap, String title, String description) {
    }

    @Override
    public void shareImgURL(String url, String title, String description) {
    }

    @Override
    public void shareWebPage(Bitmap thumb, String webPageUrl, String title, String description) {
    }

    @Override
    public void setOnShareCallBack(OnShareCallBack onShareCallBack) {
        mOnShareCallBack = onShareCallBack;

    }

    private String buildTransaction(final String type) {

        return (type == null) ? String.valueOf(System.currentTimeMillis())
                : type + System.currentTimeMillis();

    }
}
