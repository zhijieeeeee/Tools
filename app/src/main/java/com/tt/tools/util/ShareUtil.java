package com.tt.tools.util;

import java.util.HashMap;

import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.exception.WeiboException;
import com.tt.tools.R;
import com.tt.tools.util.sina.AccessTokenKeeper;
import com.tt.tools.util.sina.Constants;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * <p>
 * ProjectName： Haomama
 * </p>
 * <p>
 * Description：分享工具类
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 * @CreateDate 2015/8/13
 */
public class ShareUtil {

    /**
     * 微博分享的接口实例
     */
    private IWeiboShareAPI mWeiboShareAPI;

    private Message message;

    private Context context;

    /**
     * 要分享的图片
     */
    private int iconId = R.mipmap.logo;

    /**
     * 要分享的链接
     */
    private String shareLink;

    /**
     * 要分享的文本
     */
    private String shareText;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1001:
                    switch (msg.arg1) {
                        case 1:// 分享成功
                            Toast.makeText(context, "分享成功", Toast.LENGTH_SHORT).show();
                            break;
                        case 2:// 分享失败
                            Toast.makeText(context, "分享失败", Toast.LENGTH_SHORT).show();
                            break;
                        case 3:// 取消分享
                            Toast.makeText(context, "取消分享", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            break;
                    }
                    break;
                default:
                    break;
            }
        }
    };

    public ShareUtil(Context context) {
        this.context = context;
        message = handler.obtainMessage();
        message.what = 1001;
        // 创建微博 SDK 接口实例
        mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(context, Constants.APP_KEY);
        // 注册到新浪微博
        mWeiboShareAPI.registerApp();
        this.shareLink = context.getString(R.string.share_url);
        this.shareText = context.getString(R.string.share_txt);
    }

    /**
     * 设置要分享的连接和文本
     *
     * @param shareLink 分享的链接
     * @param shareText 分享的文本
     */
    public void setShareParam(String shareLink, String shareText) {
        this.shareLink = shareLink;
        this.shareText = shareText;
    }

    // 分享到新浪微博
    public void shareToSina() {
        // 1. 初始化微博的分享消息
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
        // 文本信息
        TextObject textObject = new TextObject();
        textObject.text = shareText + shareLink;
        // 图片
        ImageObject imageObject = new ImageObject();
        // 设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), iconId);
        imageObject.setImageObject(bitmap);

        weiboMessage.textObject = textObject;
        weiboMessage.imageObject = imageObject;

        // 初始化从第三方到微博的消息请求
        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
        // 用transaction唯一标识一个请求
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.multiMessage = weiboMessage;

        AuthInfo authInfo = new AuthInfo(context, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE);
        Oauth2AccessToken accessToken = AccessTokenKeeper.readAccessToken(context);
        String token = "";
        if (accessToken != null) {
            token = accessToken.getToken();
        }
        mWeiboShareAPI.sendRequest((Activity) context, request, authInfo, token, new WeiboAuthListener() {

            @Override
            public void onWeiboException(WeiboException arg0) {
                message.arg1 = 2;
                handler.sendMessage(message);
            }

            @Override
            public void onComplete(Bundle bundle) {
                // TODO Auto-generated method stub
                Oauth2AccessToken newToken = Oauth2AccessToken.parseAccessToken(bundle);
                AccessTokenKeeper.writeAccessToken(context.getApplicationContext(), newToken);
                message.arg1 = 1;
                handler.sendMessage(message);
            }

            @Override
            public void onCancel() {
                message.arg1 = 3;
                handler.sendMessage(message);
            }
        });
    }

    // 分享到qq空间
    public void shareToQzone() {
        QZone.ShareParams sp = new QZone.ShareParams();
        sp.setTitle(context.getString(R.string.app_name));
        sp.setTitleUrl(shareLink); // 标题的超链接
        sp.setText(shareText);
        sp.setImageUrl(context.getString(R.string.share_icon));
        sp.setSite(context.getString(R.string.app_name));
        sp.setSiteUrl(shareLink);
        Platform qzone = ShareSDK.getPlatform(QZone.NAME);
        // 分享回调
        qzone.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                message.arg1 = 1;
                handler.sendMessage(message);
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                message.arg1 = 2;
                handler.sendMessage(message);
            }

            @Override
            public void onCancel(Platform platform, int i) {
                message.arg1 = 3;
                handler.sendMessage(message);
            }
        });
        // 执行分享
        qzone.share(sp);
    }

    // 分享到QQ好友
    public void shareToQQ() {
        QQ.ShareParams sp = new QQ.ShareParams();
        // sp.setTitle("快点来用好妈妈吧");
        sp.setTitleUrl(shareLink); // 标题的超链接
        sp.setText(shareText);
        sp.setImageUrl(context.getString(R.string.share_icon));
        Platform qq = ShareSDK.getPlatform(QQ.NAME);
        // 分享回调
        qq.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                message.arg1 = 1;
                handler.sendMessage(message);
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                message.arg1 = 2;
                handler.sendMessage(message);
            }

            @Override
            public void onCancel(Platform platform, int i) {
                message.arg1 = 3;
                handler.sendMessage(message);
            }
        });
        // 执行分享
        qq.share(sp);
    }

    // 分享到微信好友
    public void shareToWeChatFriend() {
        Wechat.ShareParams sp = new Wechat.ShareParams();
        // 任何分享类型都需要title和text
        sp.shareType = Platform.SHARE_WEBPAGE;
        sp.title = context.getString(R.string.app_name);
        sp.text = shareText;
        sp.url = shareLink;
        sp.imageData = BitmapFactory.decodeResource(context.getResources(), iconId);
        Platform weChat = ShareSDK.getPlatform(Wechat.NAME);
        // 分享回调
        weChat.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                message.arg1 = 1;
                handler.sendMessage(message);
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                message.arg1 = 2;
                handler.sendMessage(message);
            }

            @Override
            public void onCancel(Platform platform, int i) {
                message.arg1 = 3;
                handler.sendMessage(message);
            }
        });
        // 执行分享
        weChat.share(sp);
    }

    // 分享到微信朋友圈
    public void shareToWeChatCircle() {
        WechatMoments.ShareParams sp = new WechatMoments.ShareParams();
        // 任何分享类型都需要title和text
        sp.shareType = Platform.SHARE_WEBPAGE;
        sp.title = shareText;
        sp.text = shareText;
        sp.url = shareLink;
        sp.imageData = BitmapFactory.decodeResource(context.getResources(), iconId);

        Platform weChatMoments = ShareSDK.getPlatform(WechatMoments.NAME);

        message = handler.obtainMessage(1001);

        // 分享回调
        weChatMoments.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                message.arg1 = 1;
                handler.sendMessage(message);
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                message.arg1 = 2;
                handler.sendMessage(message);
            }

            @Override
            public void onCancel(Platform platform, int i) {
                message.arg1 = 3;
                handler.sendMessage(message);
            }
        });
        // 执行分享
        weChatMoments.share(sp);
    }

    /**
     * 发送短信
     *
     * @param msg 短信内容
     */
    public void shareByMessage(String msg) {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"));
        intent.putExtra("sms_body", msg);
        context.startActivity(intent);
    }
}
