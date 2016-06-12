package com.tzj.frame.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * <p> ProjectName： SecretZone</p>
 * <p>
 * Description：自定义监听滑动的ScrollView
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 * @createdate 2016-01-28 10:17
 */
public class MyScrollView extends ScrollView {

    /**
     * 滑动监听
     */
    private OnScrollChangedListener onScrollChangedListener;

    public void setOnScrollChangedListener(OnScrollChangedListener onScrollChangedListener) {
        this.onScrollChangedListener = onScrollChangedListener;
    }

    public interface OnScrollChangedListener {
        void onScrollChanged(int l, int t, int oldl, int oldt);
    }

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (onScrollChangedListener != null) {
            onScrollChangedListener.onScrollChanged(l, t, oldl, oldt);
        }
    }

}
