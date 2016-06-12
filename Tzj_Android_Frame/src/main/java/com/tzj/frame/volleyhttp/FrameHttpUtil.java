package com.tzj.frame.volleyhttp;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.tzj.frame.R;
import com.tzj.frame.app.FrameBaseApplication;
import com.tzj.frame.util.CheckUtil;
import com.tzj.frame.util.MyLog;
import com.tzj.frame.util.MyTextUtil;
import com.tzj.frame.util.MyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p> FileName： FrameHttpUtil</p>
 * <p>
 * Description：http请求工具类
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 * @createdate 2015-12-24 9:26
 */
public class FrameHttpUtil {

    private Context mContext;

    /**
     * 连接超时时间
     */
    private final int REQUEST_TIMEOUT_TIME = 60 * 1000;

    /**
     * 域名前缀
     */
    private String host;

    /**
     * 请求回调接口
     */
    private HttpCallBack mHttpCallBack;

    private Gson mGson;

    public FrameHttpUtil(Context mContext, String host, HttpCallBack httpCallBack) {
        this.mContext = mContext;
        this.host = host;
        this.mHttpCallBack = httpCallBack;
        mGson = new Gson();
    }

//    /**
//     * 发送一个网络请求（Xutils）
//     *
//     * @param url    地址
//     * @param params 参数
//     */
//    public void post(final String url, RequestParams params) {
//        if (!CheckUtil.isNetEnable(mContext)) {//网络不可用
//            MyToast.show(mContext, mContext.getString(R.string.net_disable));
//            return;
//        }
//
//        final ResultInfo resultInfo = new ResultInfo();
//        resultInfo.setRequestUrl(url);
//
//        mHttpUtils.send(HttpRequest.HttpMethod.POST, host + url, params,
//                new RequestCallBack<String>() {
//
//                    @Override
//                    public void onSuccess(ResponseInfo<String> responseInfo) {
//                        JSONObject jsonObject;
//                        try {
//                            //请求是否成功
//                            boolean flag;
//                            jsonObject = new JSONObject(responseInfo.result);
//                            flag = jsonObject.optBoolean("flag");
//                            String msg = jsonObject.optString("msg");
//                            resultInfo.setMsg(msg);
//                            if (flag) {//请求成功
//                                String data = jsonObject.optString("data");
//                                resultInfo.setResultObj(data);
//                                if (mHttpCallBack != null) {
//                                    mHttpCallBack.requestSuccessCallBack(resultInfo);
//                                }
//                            } else {//请求失败
//                                resultInfo.setErrorCode(ResultInfo.REQUEST_FLAG_FALSE);
//                                if (mHttpCallBack != null) {
//                                    mHttpCallBack.requestFailCallBack(resultInfo);
//                                }
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(HttpException e, String s) {
//                        String msg = mContext.getString(R.string.http_request_time_out);
//                        resultInfo.setMsg(msg);
//                        resultInfo.setErrorCode(ResultInfo.REQUEST_TIME_OUT);
//                        if (mHttpCallBack != null) {
//                            mHttpCallBack.requestFailCallBack(resultInfo);
//                        }
//                    }
//                });
//    }

    /**
     * 发送一个网络请求(Volley)
     *
     * @param url    地址
     * @param params 参数
     */
    public void post(final String url, final RequestParam params) {

        final ResultInfo resultInfo = new ResultInfo();
        resultInfo.setRequestUrl(url);

        if (!CheckUtil.isNetEnable(mContext)) {//网络不可用
            MyToast.show(mContext, mContext.getString(R.string.net_disable));
            resultInfo.setErrorCode(ResultInfo.REQUEST_TIME_OUT);
            mHttpCallBack.requestFailCallBack(resultInfo);
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, host + url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(response);
                    boolean flag = jsonObject.optBoolean("flag");
                    String msg = jsonObject.optString("msg");
                    resultInfo.setMsg(msg);
                    if (flag) {//请求成功
                        String data = jsonObject.optString("data");
                        resultInfo.setResultObj(data);
                        if (mHttpCallBack != null) {
                            mHttpCallBack.requestSuccessCallBack(resultInfo);
                        }
                    } else {//请求失败
                        resultInfo.setErrorCode(ResultInfo.REQUEST_FLAG_FALSE);
                        if (mHttpCallBack != null) {
                            mHttpCallBack.requestFailCallBack(resultInfo);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String msg = mContext.getString(R.string.http_request_time_out);
                resultInfo.setMsg(msg);
                resultInfo.setErrorCode(ResultInfo.REQUEST_TIME_OUT);
                if (mHttpCallBack != null) {
                    mHttpCallBack.requestFailCallBack(resultInfo);
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params.getMap();
            }
        };
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        //要确保最大重试次数为1，以保证超时后不重新请求
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(REQUEST_TIMEOUT_TIME, 1, 1.0f));
        //将请求添加到队列
        FrameBaseApplication.addToRequestQueue(stringRequest, url);
    }

    /**
     * 发送一个网络请求,使用了Gson解析
     *
     * @param url    地址
     * @param params 参数
     * @param clazz  对象类
     * @param isList 是否是列表
     */
    public <T> void post(final String url, final RequestParam params, final Class<T> clazz, final boolean isList) {
        if (!CheckUtil.isNetEnable(mContext)) {//网络不可用
            MyToast.show(mContext, mContext.getString(R.string.net_disable));
            return;
        }

        final ResultInfo resultInfo = new ResultInfo();
        resultInfo.setRequestUrl(url);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, host + url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(response);
                    boolean flag = jsonObject.optBoolean("flag");
                    String msg = jsonObject.optString("msg");
                    resultInfo.setMsg(msg);
                    if (flag) {//请求成功
                        String data = jsonObject.optString("data");
                        if (!MyTextUtil.isEmpty(data)) {
                            if (isList) {//返回是列表
                                JSONArray dataArray = new JSONArray(data);
                                if (dataArray != null && dataArray.length() > 0) {
                                    List<T> beanList = new ArrayList<>();
                                    for (int i = 0; i < dataArray.length(); i++) {
                                        String itemJsonString = dataArray.optString(i);
                                        T bean = mGson.fromJson(itemJsonString, clazz);
                                        beanList.add(bean);
                                    }
                                    resultInfo.setResultObj(beanList);
                                }
                            } else {//返回不是列表
                                T bean = mGson.fromJson(data, clazz);
                                resultInfo.setResultObj(bean);
                            }
                        }
                        if (mHttpCallBack != null) {
                            mHttpCallBack.requestSuccessCallBack(resultInfo);
                        }
                    } else {//请求失败
                        resultInfo.setErrorCode(ResultInfo.REQUEST_FLAG_FALSE);
                        if (mHttpCallBack != null) {
                            mHttpCallBack.requestFailCallBack(resultInfo);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String msg = mContext.getString(R.string.http_request_time_out);
                resultInfo.setMsg(msg);
                resultInfo.setErrorCode(ResultInfo.REQUEST_TIME_OUT);
                if (mHttpCallBack != null) {
                    mHttpCallBack.requestFailCallBack(resultInfo);
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params.getMap();
            }
        };
        //要确保最大重试次数为1，以保证超时后不重新请求
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(REQUEST_TIMEOUT_TIME, 1, 1.0f));
        //将请求添加到队列
        FrameBaseApplication.addToRequestQueue(stringRequest, url);
    }

    /**
     * 发送一个网络请求(Volley)（此方法是针对聚合数据改造的）
     *
     * @param url    地址
     * @param params 参数
     */
    public void get(final String url, final RequestParam params) {
        final ResultInfo resultInfo = new ResultInfo();

        StringBuffer sb = new StringBuffer();
        sb.append(host + url);
        //拼接请求url
        if (params != null) {
            sb.append("?");
            try {
                for (Map.Entry<String, String> entry : params.getMap().entrySet()) {
                    sb.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), "UTF-8")).append("&");
                }
                //删除最后一个&
                sb.deleteCharAt(sb.length() - 1);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        //打印请求接口
        MyLog.i(sb.toString());

        resultInfo.setRequestUrl(url);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, sb.toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(response);
                    int error_code = jsonObject.optInt("error_code");
                    String msg = jsonObject.optString("reason");
                    resultInfo.setMsg(msg);
                    if (error_code == 0) {//请求成功
                        JSONObject resultObj = jsonObject.optJSONObject("result");
//                        //获取resultObj中数据
//                        String data = resultObj.optString(key);
//                        if (!MyTextUtil.isEmpty(data)) {//有数据
//                            resultInfo.setResultObj(data);
//                            if (mHttpCallBack != null) {
//                                mHttpCallBack.requestSuccessCallBack(resultInfo);
//                            }
//                        } else {//无数据
//                            if (mHttpCallBack != null) {
//                                resultInfo.setErrorCode(ResultInfo.REQUEST_NO_DATA);
//                                mHttpCallBack.requestFailCallBack(resultInfo);
//                            }
//                        }
                        if (resultObj != null) {
                            resultInfo.setResultObj(resultObj);
                            if (mHttpCallBack != null) {
                                mHttpCallBack.requestSuccessCallBack(resultInfo);
                            }
                        } else {//无数据
                            if (mHttpCallBack != null) {
                                resultInfo.setErrorCode(ResultInfo.REQUEST_NO_DATA);
                                mHttpCallBack.requestFailCallBack(resultInfo);
                            }
                        }

                    } else {//请求失败
                        resultInfo.setErrorCode(ResultInfo.REQUEST_FLAG_FALSE);
                        if (mHttpCallBack != null) {
                            mHttpCallBack.requestFailCallBack(resultInfo);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                String msg = mContext.getString(R.string.net_disable);
//                resultInfo.setMsg(msg);
                MyToast.show(mContext, mContext.getString(R.string.net_disable));
                resultInfo.setErrorCode(ResultInfo.REQUEST_TIME_OUT);
                if (mHttpCallBack != null) {
                    mHttpCallBack.requestFailCallBack(resultInfo);
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params.getMap();
            }
        };
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        //要确保最大重试次数为1，以保证超时后不重新请求
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(REQUEST_TIMEOUT_TIME, 1, 1.0f));
        //将请求添加到队列
        FrameBaseApplication.addToRequestQueue(stringRequest, url);
    }

    /**
     * 发送一个网络请求,使用了Gson解析（此方法是针对聚合数据改造的）
     *
     * @param url    地址
     * @param params 参数
     * @param clazz  对象类
     * @param isList 是否是列表
     * @param key    列表在Json中对应的key（聚合数据的key有些接口不一样）
     */
    public <T> void get(final String url, final RequestParam params, final Class<T> clazz, final boolean isList, final String key) {
        final ResultInfo resultInfo = new ResultInfo();

        StringBuffer sb = new StringBuffer();
        sb.append(host + url);
        //拼接请求url
        if (params != null) {
            sb.append("?");
            try {
                for (Map.Entry<String, String> entry : params.getMap().entrySet()) {
                    sb.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), "UTF-8")).append("&");
                }
                //删除最后一个&
                sb.deleteCharAt(sb.length() - 1);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        //打印请求接口
        MyLog.i(sb.toString());

        resultInfo.setRequestUrl(url);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, sb.toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(response);
                    int error_code = jsonObject.optInt("error_code");
                    String msg = jsonObject.optString("reason");
                    resultInfo.setMsg(msg);
                    if (error_code == 0) {//请求成功
                        JSONObject resultObj = jsonObject.optJSONObject("result");
                        //获取resultObj中数据
                        String data = resultObj.optString(key);
                        if (!MyTextUtil.isEmpty(data) && !data.equals("null")) {//有数据
                            if (isList) {//返回是列表
                                JSONArray dataArray = new JSONArray(data);
                                if (dataArray != null && dataArray.length() > 0) {
                                    List<T> beanList = new ArrayList<>();
                                    for (int i = 0; i < dataArray.length(); i++) {
                                        String itemJsonString = dataArray.optString(i);
                                        T bean = mGson.fromJson(itemJsonString, clazz);
                                        beanList.add(bean);
                                    }
                                    resultInfo.setResultObj(beanList);
                                }
                            } else {//返回不是列表
                                T bean = mGson.fromJson(data, clazz);
                                resultInfo.setResultObj(bean);
                            }
                            if (mHttpCallBack != null) {
                                mHttpCallBack.requestSuccessCallBack(resultInfo);
                            }
                        } else {//请求成功，但无数据
                            if (mHttpCallBack != null) {
                                resultInfo.setErrorCode(ResultInfo.REQUEST_NO_DATA);
                                mHttpCallBack.requestFailCallBack(resultInfo);
                            }
                        }
                    } else {//请求失败
                        resultInfo.setErrorCode(ResultInfo.REQUEST_FLAG_FALSE);
                        if (mHttpCallBack != null) {
                            mHttpCallBack.requestFailCallBack(resultInfo);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                String msg = mContext.getString(R.string.http_request_time_out);
//                resultInfo.setMsg(msg);
                MyToast.show(mContext, mContext.getString(R.string.net_disable));
                resultInfo.setErrorCode(ResultInfo.REQUEST_TIME_OUT);
                if (mHttpCallBack != null) {
                    mHttpCallBack.requestFailCallBack(resultInfo);
                }
            }
        });
        //要确保最大重试次数为1，以保证超时后不重新请求
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(REQUEST_TIMEOUT_TIME, 1, 1.0f));
        //将请求添加到队列
        FrameBaseApplication.addToRequestQueue(stringRequest, url);
    }

}
