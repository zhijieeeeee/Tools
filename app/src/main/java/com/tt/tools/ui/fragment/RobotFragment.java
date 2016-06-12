package com.tt.tools.ui.fragment;

import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.tt.tools.R;
import com.tt.tools.common.HttpConfig;
import com.tt.tools.model.bean.RobotAnswer;
import com.tt.tools.model.retrofit.RetrofitUtil;
import com.tt.tools.model.retrofit.api.RobotServiceApi;
import com.tt.tools.ui.dialog.LoadingDialog;
import com.tt.tools.ui.dialog.VoiceDialog;
import com.tzj.frame.base.FrameBaseFragment;
import com.tzj.frame.util.MyTextUtil;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * <p> ProjectName： Tools</p>
 * <p>
 * Description：
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 * @createdate 2016/2/25/025
 */
public class RobotFragment extends FrameBaseFragment {
    @Bind(R.id.til_question)
    TextInputLayout tilQuestion;
    @Bind(R.id.rippleView_commit)
    RippleView rippleViewCommit;
    @Bind(R.id.tv_answer)
    TextView tvAnswer;
    @Bind(R.id.iv_voice)
    ImageView iv_voice;

    private EditText et_question;

    private LoadingDialog loadingDialog;

    private RobotServiceApi robotServiceApi;
    private Call<RobotAnswer> call;
    private Subscription subscription;

    private VoiceDialog voiceDialog;

    @Override
    public int getContentViewLayoutId() {
        return R.layout.fragment_robot;
    }

    @Override
    public void initView() {
        et_question = tilQuestion.getEditText();
        loadingDialog = new LoadingDialog(getActivity());
        voiceDialog = new VoiceDialog(getActivity());
        voiceDialog.setOnVoiceChange(new OnVoiceListener());
    }

    @Override
    public void initData() {
        robotServiceApi = RetrofitUtil.createApi(RobotServiceApi.class);
    }

    @Override
    public View getVaryView() {
        return tvAnswer;
    }

    private void requestCommit(String question) {
        subscription = robotServiceApi.getAnswer(HttpConfig.APIKEY_ROBOT, question)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RobotAnswer>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast(getActivity().getString(com.tzj.frame.R.string.net_disable));
                        tvAnswer.setText("");
                        loadingDialog.dismiss();
                    }

                    @Override
                    public void onNext(RobotAnswer robotAnswer) {
                        tvAnswer.setText("");
                        if (null != robotAnswer) {
                            if (robotAnswer.getErrorCode() == 0) {
                                if (!MyTextUtil.isEmpty(robotAnswer.getResult().getText())) {
                                    tvAnswer.setText(robotAnswer.getResult().getText());
                                }
                            } else {
                                Toast(robotAnswer.getReason());
                            }
                        }
                        loadingDialog.dismiss();
                    }
                });
//        call = robotServiceApi.getAnswer(HttpConfig.APIKEY_ROBOT, question);
//        call.enqueue(new Callback<RobotAnswer>() {
//            @Override
//            public void onResponse(Call<RobotAnswer> call, Response<RobotAnswer> response) {
//                RobotAnswer robotAnswer = response.body();
//                tvAnswer.setText("");
//                if (null != robotAnswer) {
//                    if (robotAnswer.getErrorCode() == 0) {
//                        if (!MyTextUtil.isEmpty(robotAnswer.getResult().getText())) {
//                            tvAnswer.setText(robotAnswer.getResult().getText());
//                        }
//                    } else {
//                        Toast(robotAnswer.getReason());
//                    }
//                }
//                loadingDialog.dismiss();
//            }
//
//            @Override
//            public void onFailure(Call<RobotAnswer> call, Throwable t) {
//                Toast(getActivity().getString(com.tzj.frame.R.string.net_disable));
//                tvAnswer.setText("");
//                loadingDialog.dismiss();
//            }
//        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        subscription.unsubscribe();
    }

    @OnClick({R.id.rippleView_commit, R.id.iv_voice})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rippleView_commit:
                String question = et_question.getText().toString().trim();
                if (MyTextUtil.isEmpty(question)) {
                    tilQuestion.setErrorEnabled(true);
                    tilQuestion.setError("问题不能为空哟");
                    return;
                }
                tilQuestion.setErrorEnabled(false);
                loadingDialog.show();
                requestCommit(question);
                break;
            case R.id.iv_voice:
                voiceDialog.show();
                voiceDialog.startVoice();
                break;
            default:
                break;
        }

    }

    class OnVoiceListener implements VoiceDialog.OnVoiceChange {

        @Override
        public void getText(String text) {
            if (!MyTextUtil.isEmpty(text)) {
                loadingDialog.show();
                et_question.setText(text);
                et_question.setSelection(et_question.length());
                requestCommit(text);
            }
        }
    }
}
