package com.tt.tools.ui.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.tt.tools.R;
import com.tzj.frame.base.FrameBaseDialog;

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
public class LoadingDialog extends FrameBaseDialog {

//    private ProgressBar progressBar;

    public LoadingDialog(Context context) {
        super(context, R.layout.dialog_loading);
        setWindowParam(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER, 0);
    }

    @Override
    public void initView() {
//        progressBar = getView(R.id.progressBar);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onViewClick(View view) {

    }
}
