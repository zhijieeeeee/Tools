package com.tzj.frame.base;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.tzj.frame.R;
import com.tzj.frame.app.ActivityController;
import com.tzj.frame.util.MyToast;
import com.tzj.frame.widget.VaryViewHelper;

import butterknife.ButterKnife;

/**
 * <p> FileName： FrameBaseActivity</p>
 * <p>
 * Description：基类Activity
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 * @createdate 2015-12-22 10:55
 */
public abstract class FrameBaseActivity extends AppCompatActivity {

    public VaryViewHelper varyViewHelper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityController.addActivity(this);
        before(savedInstanceState);

        if (getContentViewLayoutId() != 0) {
            setContentView(getContentViewLayoutId());
        } else {
            throw new IllegalArgumentException("You must return a right contentView layout resource Id");
        }

        initView();
        initData();

        if (null != getVaryView()) {
            varyViewHelper = new VaryViewHelper(getVaryView());
        }

        //版本在5.0以上使用沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setTranslucentStatus(true);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            //状态栏颜色
            tintManager.setStatusBarTintResource(R.color.statusBarColor);
            onBuildVersionGT_LOLLIPOP(tintManager.getConfig());
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        ActivityController.removeActivity(this);
    }

    /**
     * 设置布局之前的逻辑
     *
     * @param savedInstanceState 被销毁之前保存的数据
     */
    public abstract void before(Bundle savedInstanceState);

    /**
     * 设置布局,必须返回layout的id
     */
    public abstract int getContentViewLayoutId();

    /**
     * 初始化控件
     */
    public abstract void initView();

    /**
     * 初始化数据
     */
    public abstract void initData();

    /**
     * 设置要放置加载view或者错误显示的view
     *
     * @return
     */
    public abstract View getVaryView();

    /**
     * 获取控件，封装了findViewById
     *
     * @param resId 资源
     * @return <T> 控件
     */
    public <T extends View> T getView(int resId) {
        View v = findViewById(resId);
        return (T) v;
    }

    /**
     * 获取控件，封装了findViewById
     *
     * @param view  布局
     * @param resId 资源
     * @return <T> 控件
     */
    public <T extends View> T getView(View view, int resId) {
        View v = view.findViewById(resId);
        return (T) v;
    }

    /**
     * 吐司
     *
     * @param msg
     */
    public void Toast(String msg) {
        MyToast.show(this, msg);
    }

    /**
     * 当android版本大于5.0的额外处理
     * android 5.0 以上启用沉浸式状态栏  会导致view被顶到顶部
     * 留这个函数是为了让每个界面自己处理view的paddingtop来解决
     *
     * @param systemBarConfig
     */
    protected abstract void onBuildVersionGT_LOLLIPOP(SystemBarTintManager.SystemBarConfig systemBarConfig);

    /**
     * 是否使用沉浸式状态栏
     *
     * @param on
     */
    public void setTranslucentStatus(boolean on) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if (on) {
                winParams.flags |= bits;
            } else {
                winParams.flags &= ~bits;
            }
            win.setAttributes(winParams);
        }
    }

}
