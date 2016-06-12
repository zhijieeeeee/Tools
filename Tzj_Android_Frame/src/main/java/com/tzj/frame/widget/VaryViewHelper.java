package com.tzj.frame.widget;

import android.view.View;
import android.view.ViewGroup;

/**
 * <p> FileName： VaryViewHelper</p>
 * <p>
 * Description：
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 * @createdate 2016-03-14 11:55
 */
public class VaryViewHelper {

    /**
     * 等待被替换的view(原来的view)
     */
    private View mView;

    /**
     * 等待被替换的view的布局参数
     */
    private ViewGroup.LayoutParams mCurViewParam;

    /**
     * 原来的view在父布局中的位置
     */
    private int index;

    /**
     * 父布局
     */
    private ViewGroup parentView;

    public VaryViewHelper(View mView) {
        this.mView = mView;
    }

    /**
     * 初始化
     */
    private void init() {
        mCurViewParam = mView.getLayoutParams();
        if (null != mView.getParent()) {
            parentView = (ViewGroup) mView.getParent();
        } else {
            parentView = (ViewGroup) mView.getRootView().findViewById(android.R.id.content);
        }
        //获取要被替换的View在父视图中的位置
        int count = parentView.getChildCount();
        for (int i = 0; i < count; i++) {
            View childView = parentView.getChildAt(i);
            if (childView == mView) {
                index = i;
            }
        }
    }

    /**
     * 显示指定布局
     *
     * @param showView
     */
    public void showLayout(View showView) {
        if (null == showView) {
            return;
        }
        if (null == parentView) {
            init();
        }
        //如果传入的view和要 被替换的view是同一个，就不做操作
        if (parentView.getChildAt(index) != showView) {
            parentView.removeViewAt(index);
            parentView.addView(showView, index, mCurViewParam);
        }
    }

    /**
     * 重置
     */
    public void resetView() {
        showLayout(mView);
    }
}
