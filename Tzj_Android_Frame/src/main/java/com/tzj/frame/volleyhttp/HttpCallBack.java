package com.tzj.frame.volleyhttp;

/**
 * <p> FileName： HttpCallBack</p>
 * <p>
 * Description：http请求回调接口
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 * @createdate 2015-12-24 9:30
 */
public interface HttpCallBack {

    /**
     * 成功请求(有数据)
     *
     * @param resultInfo 返回结果
     */
    void requestSuccessCallBack(ResultInfo resultInfo);

//    /**
//     * 请求无数据
//     *
//     * @param resultInfo 返回结果
//     */
//    void requestNoDataCallBack(ResultInfo resultInfo);

    /**
     * 失败请求(包括请求错误和超时)
     *
     * @param resultInfo 返回结果
     */
    void requestFailCallBack(ResultInfo resultInfo);

//    /**
//     * 无网
//     */
//    void noNetCallBack();

}
