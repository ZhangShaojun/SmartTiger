
package com.smarttiger.demos.share;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler.Response;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;

public class SinaWBShare implements IShare, Response {
    /** 微博微博分享接口实例 */
    private IWeiboShareAPI mWeiboShareAPI = null;

    public static final String APP_KEY = "1582441314";
    /**
     * 当前 DEMO 应用的回调页，第三方应用可以使用自己的回调页。
     * <p>
     * 注：关于授权回调页对移动客户端应用来说对用户是不可见的，所以定义为何种形式都将不影响， 但是没有定义将无法使用 SDK 认证登录。
     * 建议使用默认回调页：https://api.weibo.com/oauth2/default.html
     * </p>
     */
    public static final String REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";

    /**
     * Scope 是 OAuth2.0 授权机制中 authorize 接口的一个参数。通过 Scope，平台将开放更多的微博
     * 核心功能给开发者，同时也加强用户隐私保护，提升了用户体验，用户在新 OAuth2.0 授权页中有权利 选择赋予应用的功能。
     * 我们通过新浪微博开放平台-->管理中心-->我的应用-->接口管理处，能看到我们目前已有哪些接口的 使用权限，高级权限需要进行申请。 目前
     * Scope 支持传入多个 Scope 权限，用逗号分隔。 有关哪些 OpenAPI
     * 需要权限申请，请查看：http://open.weibo.com/wiki/%E5%BE%AE%E5%8D%9AAPI 关于 Scope
     * 概念及注意事项，请查看：http://open.weibo.com/wiki/Scope
     */
    public static final String SCOPE =
            "email,direct_messages_read,direct_messages_write,"
                    + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
                    + "follow_app_official_microblog," + "invitation_write";

    SinaShareActivity activity;

    public SinaWBShare(Context context) {

        mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(context, APP_KEY);
        mWeiboShareAPI.registerApp();
        activity = (SinaShareActivity) context;
    }

    public void handleIntent(Intent intent) {
        mWeiboShareAPI.handleWeiboResponse(intent,this);
    }

    @Override
    public void shareText(String text, String title, String description) {
     
        if (mWeiboShareAPI.isWeiboAppSupportAPI()) {
            // 1. 初始化微博的分享消息
            WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
            TextObject textObject = new TextObject();
            textObject.text = text;
            weiboMessage.textObject = textObject;
            SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
            // 用transaction唯一标识一个请求
            request.transaction = String.valueOf(System.currentTimeMillis());
            request.multiMessage = weiboMessage;
            // 3. 发送请求消息到微博，唤起微博分享界面
            mWeiboShareAPI.sendRequest(request);
        }

    }

    @Override
    public void shareBitmap(Bitmap bitmap, String title, String description) {
        // TODO Auto-generated method stub

    }

    @Override
    public void shareImgURL(String url, String title, String description) {
        // TODO Auto-generated method stub

    }

    @Override
    public void shareWebPage(Bitmap thumb, String webPageUrl, String title, String description) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setOnShareCallBack(OnShareCallBack onShareCallBack) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onResponse(BaseResponse baseResponse) {
        // TODO Auto-generated method stub

        System.out.println(baseResponse.errCode);

    }

}
