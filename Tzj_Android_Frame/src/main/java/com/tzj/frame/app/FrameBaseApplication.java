package com.tzj.frame.app;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.squareup.leakcanary.LeakCanary;
import com.tzj.frame.util.MyTextUtil;

/**
 * <p> FileName： FrameBaseApplication</p>
 * <p>
 * Description：Application基类
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 * @createdate 2016-01-12 10:38
 */
public class FrameBaseApplication extends Application {

    /**
     * volley请求队列
     */
    public static RequestQueue mRequestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化Volley请求
        initRequestQueue();
        //内存泄漏检测
        LeakCanary.install(this);
    }

    /**
     * 初始化请求队列
     *
     * @return
     */
    private void initRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
    }

    /**
     * 添加请求到mRequestQueue中，tag为空则默认为volley
     */
    public static <T> void addToRequestQueue(Request<T> req, String tag) {
        if (mRequestQueue != null) {
            req.setTag(MyTextUtil.isEmpty(tag) ? "volley" : tag);
            mRequestQueue.add(req);
        }
    }

    /**
     * 添加请求到mRequestQueue中，tag默认为volley
     */
    public static <T> void addToRequestQueue(Request<T> req) {
        if (mRequestQueue != null) {
            req.setTag("volley");
            mRequestQueue.add(req);
        }
    }

    /**
     * 取消请求
     */
    public static void cancelRequests(Object tag) {
        if (mRequestQueue != null && tag != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

}
