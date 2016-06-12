package com.tzj.frame.volleyhttp;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

/**
 * <p> FileName： ByteRequest</p>
 * <p>
 * Description：自定义返回字节数组的Volley请求
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 * @CreateDate 2016/2/21
 */
public class ByteRequest extends Request<byte[]> {

    private final Response.Listener<byte[]> mListener;

    public ByteRequest(int method, String url, Response.Listener<byte[]> listener,
                       Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        mListener = listener;
    }

    public ByteRequest(String url, Response.Listener<byte[]> listener, Response.ErrorListener errorListener) {
        this(Method.GET, url, listener, errorListener);
    }

    @Override
    protected Response<byte[]> parseNetworkResponse(NetworkResponse response) {
        byte[] data = response.data;
        return Response.success(data, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(byte[] response) {
        mListener.onResponse(response);
    }
}
