package com.tt.tools.model.volley;

import android.content.Context;

import com.tzj.frame.volleyhttp.FrameHttpUtil;
import com.tzj.frame.volleyhttp.HttpCallBack;

/**
 * <p> FileName： HttpUtil</p>
 * <p>
 * Description：http访问工具类
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 * @createdate 2016-02-18 17:09
 */
public class HttpUtil extends FrameHttpUtil {

    public HttpUtil(Context mContext, HttpCallBack httpCallBack) {
        super(mContext, "", httpCallBack);
    }
}
