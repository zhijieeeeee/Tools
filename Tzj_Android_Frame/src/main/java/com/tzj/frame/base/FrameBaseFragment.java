package com.tzj.frame.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.tzj.frame.R;
import com.tzj.frame.util.MyToast;
import com.tzj.frame.widget.VaryViewHelper;

import butterknife.ButterKnife;

/**
 * <p> FileName： Frame</p>
 * <p>
 * Description：基类Fragment
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 * @createdate 2015-12-23 11:58
 */
public abstract class FrameBaseFragment extends Fragment {

    public VaryViewHelper varyViewHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getContentViewLayoutId() != 0) {
            return inflater.inflate(getContentViewLayoutId(), container, false);
        } else {
            throw new IllegalArgumentException("You must return a right contentView layout resource Id");
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        initView();
        initData();

        if (null != getVaryView()) {
            varyViewHelper = new VaryViewHelper(getVaryView());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    /**
     * 初始化控件
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
        return getView(getView(), resId);
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
        MyToast.show(getActivity(), msg);
    }

}
