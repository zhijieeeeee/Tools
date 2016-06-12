package com.tt.tools.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.tt.tools.R;
import com.tzj.frame.base.FrameBaseActivity;

import java.lang.ref.WeakReference;


/**
 * <p> ProjectName： Tools</p>
 * <p>
 * Description：
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 * @createdate 2016/2/23/023
 */
public class AboutActivity extends FrameBaseActivity {

    //handler内存泄漏解决方案
    private int count;

    private MyHandler mHandler = new MyHandler(this);

    private static class MyHandler extends Handler {
        private final WeakReference<Activity> mWeakActivity;

        public MyHandler(Activity mWeakActivity) {
            this.mWeakActivity = new WeakReference<>(mWeakActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            AboutActivity aboutActivity = (AboutActivity) mWeakActivity.get();
            if (mWeakActivity == null) {
                return;
            }
            aboutActivity.count = 1;
        }
    }

    private Toolbar toolbar;

    @Override
    public void before(Bundle savedInstanceState) {

    }

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    public void initView() {
        toolbar = getView(R.id.toolbar);
        toolbar.setTitle("关于");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public View getVaryView() {
        return null;
    }

    @Override
    protected void onBuildVersionGT_LOLLIPOP(SystemBarTintManager.SystemBarConfig systemBarConfig) {

    }

}
