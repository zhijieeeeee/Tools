package com.tzj.frame.base;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;

import com.tzj.frame.R;
import com.tzj.frame.util.ScreenUtil;

/**
 * <p> FileName： Frame</p>
 * <p>
 * Description：Base对话框
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 * @createdate 2015-12-21 11:12
 */
public abstract class FrameBaseDialog extends Dialog implements View.OnClickListener {

    public Context mContext;

    /**
     * dialog对应的布局view
     */
    public View view;

    public FrameBaseDialog(Context context,int layoutId) {
        super(context, R.style.style_dialog_without_anim);
        this.mContext = context;
        view = getLayoutInflater().inflate(layoutId, null);
        setContentView(view);
        initView();
        initData();
    }

    /**
     * 初始化控件
     */
    public abstract void initView();

    /**
     * 初始化数据
     */
    public abstract void initData();

    /**
     * 控件点击监听
     *
     * @param view
     */
    public abstract void onViewClick(View view);

    /**
     * 设置宽高和对齐方式
     *
     * @param width       宽度
     * @param height      高度
     * @param gravity     对齐方式
     * @param animStyleId 动画style,传入0代表没有动画
     */
    public void setWindowParam(float width, float height, int gravity, int animStyleId) {
        WindowManager.LayoutParams p = getWindow().getAttributes(); // 获取对话框当前的参数值

        if (width == WindowManager.LayoutParams.WRAP_CONTENT) {//宽度小于0，默认为WRAP_CONTENT
            p.width = WindowManager.LayoutParams.WRAP_CONTENT;
        } else if (width > 0 && width < 1) {//宽度介于0与1之间（0.x），设置为屏幕高的百分比
            p.width = (int) (ScreenUtil.getScreenWidth(mContext) * width);
        } else if (width >= ScreenUtil.getScreenWidth(mContext) ||
                width == WindowManager.LayoutParams.MATCH_PARENT) {//宽度大于屏幕宽度，设置为屏幕宽度
            p.width = WindowManager.LayoutParams.MATCH_PARENT;
        } else {
            p.width = (int) width;
        }

        if (height == WindowManager.LayoutParams.WRAP_CONTENT) {//高度小于0，默认为WRAP_CONTENT
            p.height = WindowManager.LayoutParams.WRAP_CONTENT;
        } else if (height > 0 && height < 1) {//高度介于0与1之间（0.x），设置为屏幕高的百分比
            p.height = (int) (ScreenUtil.getScreenHeight(mContext) * height);
        } else if (height >= ScreenUtil.getScreenHeight(mContext)
                || height == WindowManager.LayoutParams.MATCH_PARENT) {//高度大于屏幕宽度，设置为屏幕宽度
            p.height = WindowManager.LayoutParams.MATCH_PARENT;
        } else {
            p.height = (int) height;
        }
        //设置对齐方式
        p.gravity = gravity;
        getWindow().setAttributes(p);
        if (animStyleId != 0)
            getWindow().setWindowAnimations(animStyleId);
    }

    /**
     * 设置不可取消
     */
    public void setCannotCancel() {
        setCancelable(false);
    }

    /**
     * 获取控件，封装了findViewById
     *
     * @param res 资源
     * @return<T> 控件
     */
    public <T extends View> T getView(int res) {
        View v = view.findViewById(res);
        return (T) v;
    }

    /**
     * 获取控件，封装了findViewById和onClickListener
     *
     * @param res 资源
     * @return <T> 控件
     */
    public <T extends View> T getViewAndClick(int res) {
        View v = view.findViewById(res);
        v.setOnClickListener(this);
        return (T) v;
    }

    /**
     * 获取控件，封装了findViewById
     *
     * @param res 资源
     * @param v   布局
     * @return<T> 控件
     */
    public <T extends View> T getView(View v, int res) {
        View view = v.findViewById(res);
        return (T) view;
    }

    /**
     * 获取控件，封装了findViewById和onClickListener
     *
     * @param res 资源
     * @param v   布局
     * @return <T> 控件
     */
    public <T extends View> T getViewAndClick(View v, int res) {
        View view = v.findViewById(res);
        view.setOnClickListener(this);
        return (T) view;
    }

    @Override
    public void onClick(View v) {
        onViewClick(v);
    }
}
