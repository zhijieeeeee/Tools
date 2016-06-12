package com.tt.tools.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.tzj.frame.util.FrameSharePreferenceUtil;

/**
 * <p> ProjectName： Tools</p>
 * <p>
 * Description：
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 * @createdate 2016/2/24/024
 */
public class SharePreferenceUtil extends FrameSharePreferenceUtil {

    /**
     * 保存当前滑动的页数
     *
     * @param context
     * @param page
     */
    public static void savePage(Context context, int page) {
        SharedPreferences sp = context.getSharedPreferences("Page", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("page", page);
        editor.commit();
    }

    /**
     * 获得上次滑动的也是
     *
     * @param context
     * @return
     */
    public static int getPage(Context context) {
        SharedPreferences sp = context.getSharedPreferences("Page", Context.MODE_PRIVATE);
        return sp.getInt("page", 1);
    }
}
