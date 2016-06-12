package com.tzj.frame.app;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * <p> FileName： ActivityController</p>
 * <p>
 * Description：Activity管理器
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 * @CreateDate 2016/3/1
 */
public class ActivityController {

    private static List<Activity> activityList = new ArrayList<>();

    /**
     * 添加Activity
     *
     * @param activity
     */
    public static void addActivity(Activity activity) {
        if (null != activity) {
            activityList.add(activity);
        }
    }

    /**
     * 移除activity
     *
     * @param activity
     */
    public static void removeActivity(Activity activity) {
        if (null != activity) {
            if (activityList.contains(activity)) {
                activityList.remove(activity);
            }
        }
    }

    /**
     * 关闭所有Activity
     */
    public static void finishAllActivity() {
        for (Activity activity : activityList) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
        activityList.clear();
    }
}
