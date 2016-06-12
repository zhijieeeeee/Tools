package com.tzj.frame.util;

import android.util.Log;

/**
 * <p> ProjectName： Frame</p>
 * <p>
 * Description：日志输入工具类
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 * @createdate 2015-12-21 10:58
 */
public class MyLog {

    private final static String TAG = "MyLog";

    public static void i(String msg) {
        Log.i(TAG, msg);
    }

    public static void i(String tag, String msg) {
        Log.i(tag, msg);
    }
}
