package com.tzj.frame.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * <p> ProjectName： Frame</p>
 * <p>
 * Description：Toast工具类
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 * @createdate 2015-12-23 13:49
 */
public class MyToast {

    /**
     * 短吐司
     */
    public static void show(Context context, String msg) {
        if (MyTextUtil.isEmpty(msg)) {
            return;
        }
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 居中的短吐司
     */
    public static void showCenter(Context context, String msg) {
        if (MyTextUtil.isEmpty(msg)) {
            return;
        }
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

}
