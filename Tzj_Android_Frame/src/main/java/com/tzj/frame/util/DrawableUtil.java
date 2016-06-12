package com.tzj.frame.util;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;

/**
 * <p> FileName： DrawableUtil</p>
 * <p>
 * Description：动态选择器工具类
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 * @createdate 2015-12-17 11:11
 */
public class DrawableUtil {

    /**
     * 设置图片选择器
     *
     * @param normal  未选中时图片
     * @param checked 选中时图片
     * @return
     */
    public static StateListDrawable newDrawableSelector(Drawable normal, Drawable checked) {
        StateListDrawable bg = new StateListDrawable();
        bg.addState(new int[]{android.R.attr.state_checked}, checked);
        bg.addState(new int[]{}, normal);
        return bg;
    }

    /**
     * 设置图片选择器
     *
     * @param normal  未选中时图片
     * @param checked 选中时图片
     * @return
     */
    public static StateListDrawable newDrawableSelector(Context context, int normal, int checked) {
        Bitmap checkedBitmap = BitmapFactory.decodeResource(context.getResources(), checked);
        Bitmap normalBitmap = BitmapFactory.decodeResource(context.getResources(), normal);
        Drawable checkedDrawable = new BitmapDrawable(checkedBitmap);
        Drawable normalDrawable = new BitmapDrawable(normalBitmap);
        StateListDrawable bg = new StateListDrawable();
        bg.addState(new int[]{android.R.attr.state_checked}, checkedDrawable);
        bg.addState(new int[]{}, normalDrawable);
        return bg;
    }

    /**
     * 设置颜色选择器
     *
     * @param normal  未选中时颜色
     * @param checked 选中时颜色
     * @return
     */
    public static ColorStateList newColorSelector(Context context, int normal, int checked) {
        int[] colors = new int[]{checked, normal};
        int[][] states = new int[2][];
        states[0] = new int[]{android.R.attr.state_checked};
        states[1] = new int[]{};
        ColorStateList colorList = new ColorStateList(states, colors);
        return colorList;
    }
}
