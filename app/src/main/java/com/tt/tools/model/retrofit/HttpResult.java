package com.tt.tools.model.retrofit;

import com.google.gson.annotations.SerializedName;

/**
 * <p> FileName： HttpResult</p>
 * <p>
 * Description：为了处理统一格式的json字符串
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 */
public class HttpResult<T> {

    @SerializedName("error_code")
    private int errorCode;

    private String reason;

    private T result;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
