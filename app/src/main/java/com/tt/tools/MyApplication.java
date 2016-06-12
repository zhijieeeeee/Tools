package com.tt.tools;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.tzj.frame.app.FrameBaseApplication;
import com.tzj.frame.util.FunctionUtil;

import cn.sharesdk.framework.ShareSDK;

/**
 * <p> FileName： MyApplication</p>
 * <p>
 * Description：
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 * @createdate 2016-02-18 16:45
 */
public class MyApplication extends FrameBaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        ShareSDK.initSDK(this);
        Fresco.initialize(this, createFrescoConfig());
    }

    /**
     * 配置fresco参数
     *
     * @return
     */
    private ImagePipelineConfig createFrescoConfig() {
        DiskCacheConfig mainDiskCacheConfig = DiskCacheConfig.newBuilder(this)
                .setBaseDirectoryPath(FunctionUtil.getCacheFile(getApplicationContext()))
                .setBaseDirectoryName("fresco")
                .setMaxCacheSize(100 * 1024 * 1024)
                .setMaxCacheSizeOnLowDiskSpace(10 * 1024 * 1024)
                .setMaxCacheSizeOnVeryLowDiskSpace(5 * 1024 * 1024)
                .setVersion(1)
                .build();
        return ImagePipelineConfig.newBuilder(this)
//                .setBitmapMemoryCacheParamsSupplier(bitmapCacheParamsSupplier)
//                .setCacheKeyFactory(cacheKeyFactory)
//                .setEncodedMemoryCacheParamsSupplier(encodedCacheParamsSupplier)
//                .setExecutorSupplier(executorSupplier)
//                .setImageCacheStatsTracker(imageCacheStatsTracker)
                .setMainDiskCacheConfig(mainDiskCacheConfig)
//                .setMemoryTrimmableRegistry(memoryTrimmableRegistry)
//                .setNetworkFetchProducer(networkFetchProducer)
//                .setPoolFactory(poolFactory)
//                .setProgressiveJpegConfig(progressiveJpegConfig)
//                .setRequestListeners(requestListeners)
//                .setSmallImageDiskCacheConfig(smallImageDiskCacheConfig)
                .build();
    }
}
