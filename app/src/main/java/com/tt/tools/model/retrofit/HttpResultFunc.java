package com.tt.tools.model.retrofit;

import rx.functions.Func1;

/**
 * <p> FileName： HttpResultFunc</p>
 * <p>
 * Description： 统一处理Http的resultCode
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 */
public class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {

    @Override
    public T call(HttpResult<T> httpResult) {
        if (httpResult.getErrorCode() != 0) {//请求失败
            throw new ApiException(httpResult.getReason());
        }
        return httpResult.getResult();
    }
}
