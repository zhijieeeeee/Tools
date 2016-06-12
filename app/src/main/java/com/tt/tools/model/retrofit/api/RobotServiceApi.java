package com.tt.tools.model.retrofit.api;

import com.tt.tools.model.bean.RobotAnswer;
import com.tt.tools.common.HttpConfig;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * <p> FileName： RobotServiceApi</p>
 * <p>
 * Description：机器人问答接口api
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 * @createdate 2016-03-10 9:55
 */
public interface RobotServiceApi {

//    /**
//     * 获取问答
//     *
//     * @param key
//     * @param info
//     * @return
//     */
//    @GET(HttpConfig.HTTP_ROBOT)
//    Call<RobotAnswer> getAnswer(@Query("key") String key, @Query("info") String info);

    @GET(HttpConfig.HTTP_ROBOT)
    Observable<RobotAnswer> getAnswer(@Query("key") String key, @Query("info") String info);
}
