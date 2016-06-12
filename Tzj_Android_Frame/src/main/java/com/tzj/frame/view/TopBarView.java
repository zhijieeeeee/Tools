package com.tzj.frame.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tzj.frame.R;

/**
 * <p> ProjectName： Frame</p>
 * <p>
 * Description：自定义TopBar
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 * @createdate 2015/11/13/013
 */
public class TopBarView extends LinearLayout implements View.OnClickListener {

    private Activity activity;
    private View view;

    /**
     * 容器
     */
    private LinearLayout ll_top_bar;

    /**
     * 标题
     */
    private TextView tv_title;

    /**
     * 左侧图片
     */
    private ImageView iv_left;

    /**
     * 左侧文字
     */
    private TextView tv_left;

    /**
     * 右侧图片
     */
    private ImageView iv_right;

    /**
     * 右侧文字
     */
    private TextView tv_right;

    /**
     * 左侧图片资源
     */
    private int left_iv_res;

    /**
     * 右侧图片资源
     */
    private int right_iv_res;

    /**
     * 标题显示文字
     */
    private String title_tv_string;

    /**
     * 右侧TextView显示文字
     */
    private String right_tv_string;

    /**
     * 左侧TextView显示文字
     */
    private String left_tv_string;

    /**
     * 背景颜色
     */
    private int bgColor;

    /**
     * 点击左侧图片监听
     */
    private OnLeftIvClickListener onLeftIvClickListener;

    /**
     * 点击左侧文本监听
     */
    private OnLeftTvClickListener onLeftTvClickListener;

    /**
     * 点击右侧图片监听
     */
    private OnRightIvClickListener onRightIvClickListener;

    /**
     * 点击右侧文本监听
     */
    private OnRightTvClickListener onRightTvClickListener;

    public void setOnLeftIvClickListener(OnLeftIvClickListener onLeftIvClickListener) {
        this.onLeftIvClickListener = onLeftIvClickListener;
    }

    public void setOnRightTvClickListener(OnRightTvClickListener onRightTvClickListener) {
        this.onRightTvClickListener = onRightTvClickListener;
    }

    public void setOnLeftTvClickListener(OnLeftTvClickListener onLeftTvClickListener) {
        this.onLeftTvClickListener = onLeftTvClickListener;
    }

    public void setOnRightIvClickListener(OnRightIvClickListener onRightIvClickListener) {
        this.onRightIvClickListener = onRightIvClickListener;
    }

    /**
     * 设置标题
     */
    public void setTitle(String title) {
        tv_title.setText(title);
    }

    /**
     * 设置右侧文本
     *
     * @param rightString
     */
    public void setRightTvText(String rightString) {
        tv_right.setText(rightString);
    }

    /**
     * 设置左侧文本
     *
     * @param leftString
     */
    public void setLeftTvText(String leftString) {
        tv_left.setText(leftString);
    }

    /**
     * 设置右侧图片资源
     *
     * @param res
     */
    public void setRightIvRes(int res) {
        iv_right.setImageResource(res);
    }

    /**
     * 设置左侧图片资源
     *
     * @param res
     */
    public void setLeftIvRes(int res) {
        iv_left.setImageResource(res);
    }

    public TopBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TopBar);
        left_iv_res = a.getResourceId(R.styleable.TopBar_left_iv_drawable, 0);
        right_iv_res = a.getResourceId(R.styleable.TopBar_right_iv_drawable, 0);
        right_tv_string = a.getString(R.styleable.TopBar_right_tv_string);
        left_tv_string = a.getString(R.styleable.TopBar_left_tv_string);
        title_tv_string = a.getString(R.styleable.TopBar_title_string);
        bgColor = a.getColor(R.styleable.TopBar_bg_color, 0);
        a.recycle();
        init(context);
    }

    private void init(Context context) {
        this.activity = (Activity) context;
        view = LayoutInflater.from(context).inflate(R.layout.frame_top_bar, null);
        ll_top_bar = (LinearLayout) view.findViewById(R.id.ll_top_bar);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_left = (TextView) view.findViewById(R.id.tv_left);
        tv_right = (TextView) view.findViewById(R.id.tv_right);
        iv_left = (ImageView) view.findViewById(R.id.iv_left);
        iv_right = (ImageView) view.findViewById(R.id.iv_right);
        tv_right.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        iv_left.setOnClickListener(this);
        iv_right.setOnClickListener(this);

        //设置左侧图片资源，如果没有设置默认为返回键
        if (left_iv_res != 0) {
            iv_left.setImageResource(left_iv_res);
        } else {
            iv_left.setImageResource(R.drawable.btn_back);
        }

        //设置右侧图片资源
        iv_right.setImageResource(right_iv_res);
        //设置右侧文本
        tv_right.setText(right_tv_string);
        //设置标题
        tv_title.setText(title_tv_string);

        addView(view);
    }

    @Override
    public void onClick(View view) {

        if (view == iv_left) {//左侧图片点击事件，默认是返回
            if (onLeftIvClickListener != null) {
                onLeftIvClickListener.leftIvClick();
            } else {
                activity.finish();
            }
        }
        if (view == tv_left) {//左侧文字点击事件
            if (onLeftTvClickListener != null) {
                onLeftTvClickListener.leftTvClick();
            }
        }
        if (view == iv_right) {//右侧图片点击事件
            if (onRightIvClickListener != null) {
                onRightIvClickListener.rightIvClick();
            }
        }
        if (view == tv_right) {//右侧文字点击事件
            if (onRightTvClickListener != null) {
                onRightTvClickListener.rightTvClick();
            }
        }

    }

    public interface OnLeftIvClickListener {
        /**
         * 左侧图片点击事件
         */
        void leftIvClick();
    }

    public interface OnLeftTvClickListener {
        /**
         * 左侧TextView点击事件
         */
        void leftTvClick();
    }

    public interface OnRightIvClickListener {
        /**
         * 右侧图片点击事件
         */
        void rightIvClick();
    }

    public interface OnRightTvClickListener {
        /**
         * 右侧TextView点击事件
         */
        void rightTvClick();
    }

}
