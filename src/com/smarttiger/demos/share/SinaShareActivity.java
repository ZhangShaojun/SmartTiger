
package com.smarttiger.demos.share;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler.Response;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuth;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.constant.WBConstants;
import com.sina.weibo.sdk.exception.WeiboException;
import com.smarttiger.demos.R;

public class SinaShareActivity extends Activity implements OnClickListener, Response {
    ShareManager mShareManager;

    Button author, share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mShareManager = new ShareManager(this);

        setContentView(R.layout.sina_share_activity);
        author = (Button) findViewById(R.id.author);
        share = (Button) findViewById(R.id.share);
        author.setOnClickListener(this);
        share.setOnClickListener(this);
        mShareManager.handIntent(getIntent());

    }

    @Override
    protected void onNewIntent(Intent intent) {
        // TODO Auto-generated method stub
        super.onNewIntent(intent);
        mShareManager.handIntent(intent);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.share:
                mShareManager.getSinaWBShare().shareText("xx", "xxxx",
                        "xxfdlaskjfsa");
                break;

            case R.id.author: {
                WeiboAuth mWeiboAuth = new WeiboAuth(this, SinaWBShare.APP_KEY,
                        SinaWBShare.REDIRECT_URL,
                        SinaWBShare.SCOPE);
                SsoHandler mSsoHandler = new SsoHandler(SinaShareActivity.this,
                        mWeiboAuth);
                mSsoHandler.authorize(new AuthListener());
                break;
            }
            default:
                break;
        }

    }

    private Oauth2AccessToken mAccessToken;

    /**
     * 微博认证授权回调类。 1. SSO 授权时，需要在 {@link #onActivityResult} 中调用
     * {@link SsoHandler#authorizeCallBack} 后， 该回调才会被执行。 2. 非 SSO
     * 授权时，当授权结束后，该回调就会被执行。 当授权成功后，请保存该 access_token、expires_in、uid 等信息到
     * SharedPreferences 中。
     */
    class AuthListener implements WeiboAuthListener {

        @Override
        public void onComplete(Bundle values) {
            // 从 Bundle 中解析 Token
            mAccessToken = Oauth2AccessToken.parseAccessToken(values);
            if (mAccessToken.isSessionValid()) {
                // 显示 Token
                // updateTokenView(false);

                // 保存 Token 到 SharedPreferences
                AccessTokenKeeper.writeAccessToken(SinaShareActivity.this, mAccessToken);
                Toast.makeText(SinaShareActivity.this,
                        "成功",
                        Toast.LENGTH_SHORT).show();
            } else {
                // 当您注册的应用程序签名不正确时，就会收到 Code，请确保签名正确
                String code = values.getString("code");
                // String message =
                // getString(R.string.weibosdk_demo_toast_auth_failed);
                // if (!TextUtils.isEmpty(code)) {
                // message = message + "\nObtained the code: " + code;
                // }
                Toast.makeText(SinaShareActivity.this, code,
                        Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onCancel() {
            Toast.makeText(SinaShareActivity.this,
                    "取消",
                    Toast.LENGTH_LONG).show();
        }

        @Override
        public void onWeiboException(WeiboException e) {
            // Toast.makeText(WBAuthActivity.this,
            // "Auth exception : " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onResponse(BaseResponse baseResponse) {

        switch (baseResponse.errCode) {
            case WBConstants.ErrorCode.ERR_OK:
                Toast.makeText(this, "成功", Toast.LENGTH_LONG).show();
                break;
            case WBConstants.ErrorCode.ERR_CANCEL:
                Toast.makeText(this, "取消", Toast.LENGTH_LONG).show();
                break;
            case WBConstants.ErrorCode.ERR_FAIL:
                Toast.makeText(this,
                        "失败" + "Error Message: " + baseResponse.errMsg,
                        Toast.LENGTH_LONG).show();
                break;
        }
    }
}
