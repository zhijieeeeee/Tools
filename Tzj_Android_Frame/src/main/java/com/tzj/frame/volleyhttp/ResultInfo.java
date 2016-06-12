package com.tzj.frame.volleyhttp;

/**
 * <p> FileName： ResultInfo</p>
 * <p>
 * Description：返回结果类
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 * @createdate 2015-12-24 10:27
 */
public class ResultInfo {

    /**
     * 连接超时,无网
     */
    public static final int REQUEST_TIME_OUT = 1;

    /**
     * 返回结果标识为false
     */
    public static final int REQUEST_FLAG_FALSE = 2;

    /**
     * 无数据
     */
    public static final int REQUEST_NO_DATA = 3;

    /**
     * 请求接口
     */
    private String requestUrl;

    /**
     * 返回结果
     */
    private Object resultObj;

    /**
     * 返回的信息
     */
    private String msg;

    /**
     * 错误类型
     */
    private int errorCode;

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public <T extends Object> T getResultObj() {
        return (T) resultObj;
    }

    public <T extends Object> void setResultObj(T resultObj) {
        this.resultObj = resultObj;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
