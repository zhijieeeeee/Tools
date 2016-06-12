package com.tt.tools.model.retrofit.api;

import com.tt.tools.model.bean.MyJoke;
import com.tt.tools.model.retrofit.HttpResult;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * <p> FileName： JokeServiceApi</p>
 * <p>
 * Description：笑话接口api
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 * @createdate 2016-03-09 15:05
 */
public interface JokeServiceApi {

//    /**
//     * 获取笑话列表
//     *
//     * @param param
//     * @return
//     */
//    @GET("http://japi.juhe.cn/joke/content/text.from")
//    Call<MyJoke> getJokeList(@QueryMap Map<String, String> param);

//    @GET("http://japi.juhe.cn/joke/content/text.from")
//    Call<String> getJokeList(@Query("key") String key, @Query("page") String page, @Query("pagesize") String pageSize);

    //Rxjava模式的
    @GET("http://japi.juhe.cn/joke/content/text.from")
    Observable<HttpResult<MyJoke>> getJokeList(@QueryMap Map<String, String> param);

}
