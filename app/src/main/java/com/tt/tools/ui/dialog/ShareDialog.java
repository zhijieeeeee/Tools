package com.tt.tools.ui.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.tt.tools.R;
import com.tt.tools.util.ShareUtil;
import com.tzj.frame.base.FrameBaseDialog;

/**
 * <p> ProjectName： Haomama</p>
 * <p>
 * Description：分享的dialog
 * </p>
 *
 * @author liyuanhong
 * @version 1.0
 * @createdate 2015/11/20/020
 */
public class ShareDialog extends FrameBaseDialog {
    private TextView share_weibo;
    private TextView share_wxfriend;
    private TextView share_wxspace;
    private TextView share_message;
    private TextView share_qqfriend;
    private TextView share_qqspace;
    private Button share_cancel;

    private ShareUtil shareUtil;

    public ShareDialog(Context context) {
        super(context, R.layout.dialog_share);
        setWindowParam(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM, R.style.style_dialog_anim);
        shareUtil = new ShareUtil(context);
    }

    @Override
    public void initView() {
        share_weibo = getViewAndClick(R.id.share_weibo);
        share_wxfriend = getViewAndClick(R.id.share_wxfriend);
        share_wxspace = getViewAndClick(R.id.share_wxspace);
        share_message = getViewAndClick(R.id.share_message);
        share_qqfriend = getViewAndClick(R.id.share_qqfriend);
        share_qqspace = getViewAndClick(R.id.share_qqspace);
        share_cancel = getViewAndClick(R.id.share_cancel);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.share_weibo:
                shareUtil.shareToSina();
                dismiss();
                break;
            case R.id.share_wxfriend:
                shareUtil.shareToWeChatFriend();
                dismiss();
                break;
            case R.id.share_wxspace:
                shareUtil.shareToWeChatCircle();
                dismiss();
                break;
            case R.id.share_qqfriend:
                shareUtil.shareToQQ();
                dismiss();
                break;
            case R.id.share_qqspace:
                shareUtil.shareToQzone();
                dismiss();
                break;
            case R.id.share_message:
//                shareUtil.shareByMessage(getContext().getString(R.string.share_txt));
//                dismiss();
                break;
            case R.id.share_cancel:
                dismiss();
                break;
            default:
                break;
        }
    }

}
