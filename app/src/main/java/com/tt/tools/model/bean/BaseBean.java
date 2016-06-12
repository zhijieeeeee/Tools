package com.tt.tools.model.bean;

import com.google.gson.annotations.SerializedName;

/**
 * <p> FileName： BaseBean</p>
 * <p>
 * Description：实体类基类
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 * @CreateDate 2016/3/9
 */
public class BaseBean {

    @SerializedName("error_code")
    private int errorCode;

    private String reason;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
