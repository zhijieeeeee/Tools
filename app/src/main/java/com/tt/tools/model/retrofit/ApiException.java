package com.tt.tools.model.retrofit;

/**
 * <p> FileName： ApiException</p>
 * <p>
 * Description：携带json中错误信息的Exception
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 */
public class ApiException extends RuntimeException {

    public ApiException(String msg) {
        super(msg);
    }
}
