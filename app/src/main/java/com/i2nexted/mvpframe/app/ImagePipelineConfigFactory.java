package com.i2nexted.mvpframe.app;

import android.content.Context;
import android.graphics.Bitmap;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.internal.Supplier;
import com.facebook.common.util.ByteConstants;
import com.facebook.imagepipeline.cache.MemoryCacheParams;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.decoder.ProgressiveJpegConfig;
import com.facebook.imagepipeline.image.ImmutableQualityInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.facebook.imagepipeline.listener.RequestListener;
import com.facebook.imagepipeline.listener.RequestLoggingListener;

import java.util.HashSet;
import java.util.Set;


/**
 * Created by Administrator on 2017/3/23 0023.
 * 对fresco进行全局配置
 */

public class ImagePipelineConfigFactory {

    private static final int MAX_HEAP_SIZE = (int) Runtime.getRuntime().maxMemory();
    public static final int MAX_DISK_CACHE_SIZE = 100 * ByteConstants.MB;
    public static final int MAX_MEMORY_CACHE_SIZE = MAX_HEAP_SIZE / 4;
    private static final String IMAGE_PIPELINE_CACHE_DIR = "dub_image_pipeline_cache";
    private static ImagePipelineConfig sImagePipelineConfig;

    /**
     * 使用Android自带的网络加载图片
     */
    public static ImagePipelineConfig getImagePipelineConfig(Context context) {

        if (sImagePipelineConfig == null) {
            ImagePipelineConfig.Builder configBuilder = ImagePipelineConfig.newBuilder(context);
            configBuilder.setProgressiveJpegConfig(mProgressiveJpegConfig);
            configBuilder.setBitmapsConfig(Bitmap.Config.ARGB_4444);
            configureCaches(configBuilder, context);  //配置缓存
            configureLoggingListeners(configBuilder);
            configureOptions(configBuilder);
            sImagePipelineConfig = configBuilder.build();
        }
        return sImagePipelineConfig;
    }


    /**
     * 配置内存缓存和磁盘缓存
     */
    private static void configureCaches(ImagePipelineConfig.Builder configBuilder, Context context) {
        final MemoryCacheParams bitmapCacheParams = new MemoryCacheParams(
                MAX_MEMORY_CACHE_SIZE, // Max total size of elements in the cache
                Integer.MAX_VALUE,                     // Max entries in the cache
                MAX_MEMORY_CACHE_SIZE, // Max total size of elements in eviction queue
                Integer.MAX_VALUE,                     // Max length of eviction queue
                Integer.MAX_VALUE);                    // Max cache entry size

        configBuilder .setBitmapMemoryCacheParamsSupplier(   //配置内存缓存
                        new Supplier<MemoryCacheParams>() {
                            public MemoryCacheParams get() {
                                return bitmapCacheParams;
                            }
                        })
                      .setMainDiskCacheConfig(DiskCacheConfig.newBuilder(context)   //配置硬盘缓存
                                               .setBaseDirectoryPath(context.getApplicationContext().getCacheDir())
                                               .setBaseDirectoryName(IMAGE_PIPELINE_CACHE_DIR)  //硬盘缓存路径
                                               .setMaxCacheSize(MAX_DISK_CACHE_SIZE)  //硬盘缓存最大size
                                               .build());
    }

    private static void configureLoggingListeners(ImagePipelineConfig.Builder configBuilder) {
        Set<RequestListener> requestListeners = new HashSet<>();
        requestListeners.add(new RequestLoggingListener());
        configBuilder.setRequestListeners(requestListeners);
    }

    private static void configureOptions(ImagePipelineConfig.Builder configBuilder) {
        configBuilder.setDownsampleEnabled(true);
    }

    //渐进式图片
    static ProgressiveJpegConfig mProgressiveJpegConfig = new ProgressiveJpegConfig() {
        @Override
        public int getNextScanNumberToDecode(int scanNumber) {
            return scanNumber + 2;
        }

        public QualityInfo getQualityInfo(int scanNumber) {
            boolean isGoodEnough = (scanNumber >= 5);
            return ImmutableQualityInfo.of(scanNumber, isGoodEnough, false);
        }
    };
}
