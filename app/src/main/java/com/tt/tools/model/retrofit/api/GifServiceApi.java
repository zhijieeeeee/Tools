package com.tt.tools.model.retrofit.api;

import com.tt.tools.model.bean.GifBean;
import com.tt.tools.common.HttpConfig;
import com.tt.tools.model.retrofit.HttpResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * <p> FileName： GifServiceApi</p>
 * <p>
 * Description：gif接口api
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 * @CreateDate 2016/3/9
 */
public interface GifServiceApi {

//    /**
//     * 获取gif列表
//     *
//     * @param key
//     * @param page
//     * @param pagesize
//     * @return
//     */
//    @GET(HttpConfig.HTTP_GIF)
//    Call<GifBean> getGifList(@Query("key") String key,
//                             @Query("page") int page,
//                             @Query("pagesize") int pagesize);

    @GET(HttpConfig.HTTP_GIF)
    Observable<HttpResult<GifBean>> getGifList(@Query("key") String key,
                                               @Query("page") int page,
                                               @Query("pagesize") int pagesize);
}
