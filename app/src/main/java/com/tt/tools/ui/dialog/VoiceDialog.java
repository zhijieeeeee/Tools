package com.tt.tools.ui.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tt.tools.R;
import com.tt.tools.common.Config;
import com.tzj.frame.base.FrameBaseDialog;

import java.util.List;

import cn.yunzhisheng.common.USCError;
import cn.yunzhisheng.pro.USCRecognizer;
import cn.yunzhisheng.pro.USCRecognizerListener;

/**
 * <p> FileName： VoiceDialog</p>
 * <p>
 * Description：语音识别对话框
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 * @createdate 2016-03-17 10:48
 */
public class VoiceDialog extends FrameBaseDialog {

    private TextView tv_title;
    private TextView tv_left;
    private TextView tv_right;

    private ImageView iv_voice;
    private ProgressBar progressBar;

    private USCRecognizer mRecognizer;

    /**
     * 转化后的文本
     */
    private StringBuffer voiceText = new StringBuffer();

    /**
     * 是否重新说
     */
    private boolean isNeedAgain;

    /**
     * 转换监听
     */
    private OnVoiceChange onVoiceChange;

    public void setOnVoiceChange(OnVoiceChange onVoiceChange) {
        this.onVoiceChange = onVoiceChange;
    }

    public VoiceDialog(Context context) {
        super(context, R.layout.dialog_voice);
        setWindowParam(0.9f, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER, 0);
        initRecognizer();
    }

    @Override
    public void initView() {
        tv_title = getView(R.id.tv_title);
        tv_left = getViewAndClick(R.id.tv_left);
        tv_right = getViewAndClick(R.id.tv_right);
        progressBar = getView(R.id.progressBar);
        iv_voice = getView(R.id.iv_voice);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_right:
                if (isNeedAgain) {//再说一遍
                    startVoice();
                } else {//说完了
                    mRecognizer.stop();
                }
                break;
            case R.id.tv_left:
                dismiss();
                break;
        }
    }

    /**
     * 初始化云知声
     */
    private void initRecognizer() {
        mRecognizer = new USCRecognizer(getContext(), Config.VOICE_KEY);
        mRecognizer.setListener(new USCRecognizerListener() {
            /**
             * 识别结果实时返回
             */
            @Override
            public void onResult(String result, boolean isLast) {
                // 通常onResult接口多次返回结果，保留识别结果组成完整的识别内容。
                voiceText.append(result);
            }

            /**
             * 识别结束
             */
            @Override
            public void onEnd(USCError error) {
                progressBar.setVisibility(View.GONE);
                iv_voice.setVisibility(View.VISIBLE);
                if (error != null) {
                    sayAgain();
                } else {
                    if ("".equals(voiceText.toString())) {//没有返回
                        sayAgain();
                    } else {
                        if (isShowing()) {
                            if (onVoiceChange != null) {
                                onVoiceChange.getText(voiceText.toString());
                            }
                            dismiss();
                        }
                    }
                }
            }

            /**
             * 检测用户停止说话回调
             */
            @Override
            public void onVADTimeout() {
                progressBar.setVisibility(View.VISIBLE);
                iv_voice.setVisibility(View.GONE);
                tv_title.setText("正在识别...");
                mRecognizer.stop();
            }

            /**
             * 实时返回说话音量 0~100
             */
            @Override
            public void onUpdateVolume(int volume) {

            }

            /**
             * 上传用户数据状态返回
             */
            @Override
            public void onUploadUserData(USCError error) {
            }

            /**
             * 录音设备打开，开始识别，用户可以开始说话
             */
            @Override
            public void onRecognizerStart() {
                tv_title.setText("说点什么呗");
            }

            /**
             * 停止录音，请等待识别结果回调
             */
            @Override
            public void onRecordingStop(List<byte[]> arg0) {
                progressBar.setVisibility(View.VISIBLE);
                iv_voice.setVisibility(View.GONE);
                tv_title.setText("正在识别...");
            }

            /**
             * 用户开始说话
             */
            @Override
            public void onSpeechStart() {

            }
        });
    }

    /**
     * 开始
     */
    public void startVoice() {
        progressBar.setVisibility(View.GONE);
        iv_voice.setVisibility(View.VISIBLE);
        voiceText.delete(0, voiceText.length());
        mRecognizer.start();
        tv_right.setText("说完了");
    }

    /**
     * 再说一遍
     */
    private void sayAgain() {
        progressBar.setVisibility(View.GONE);
        iv_voice.setVisibility(View.VISIBLE);
        tv_title.setText("没听清楚");
        isNeedAgain = true;
        tv_right.setText("再说一遍");
    }

    public interface OnVoiceChange {
        void getText(String text);
    }

}
