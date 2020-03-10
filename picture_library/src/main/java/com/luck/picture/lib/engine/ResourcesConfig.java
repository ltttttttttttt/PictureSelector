package com.luck.picture.lib.engine;

import android.app.Dialog;
import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.luck.picture.lib.listener.ImageCompleteCallback;
import com.luck.picture.lib.widget.longimage.SubsamplingScaleImageView;

/**
 * @author :lt
 * @date : 2020-3-6
 * @describe :全局重资源配置
 */
public interface ResourcesConfig {
    /**
     * 加载图片
     *
     * @param context
     * @param url
     * @param imageView
     */
    void loadImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView);

    /**
     * 加载图片
     *
     * @param context
     * @param url
     * @param imageView
     */
    void loadImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView, SubsamplingScaleImageView longImageView, ImageCompleteCallback callback);

    /**
     * 加载网络长图适配
     *
     * @param context
     * @param url
     * @param imageView
     */
    @Deprecated
    void loadImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView, SubsamplingScaleImageView longImageView);


    /**
     * 加载相册目录图片
     *
     * @param context   上下文
     * @param url       图片路径
     * @param imageView 承载图片ImageView
     */
    void loadFolderImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView);

    /**
     * 加载gif图片
     *
     * @param context   上下文
     * @param url       图片路径
     * @param imageView 承载图片ImageView
     */
    void loadAsGifImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView);

    /**
     * 加载图片列表图片
     *
     * @param context   上下文
     * @param url       图片路径
     * @param imageView 承载图片ImageView
     */
    void loadGridImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView);

    /**
     * 发送任务到子线程
     *
     * @param runnable
     */
    void runIoThread(@NonNull Runnable runnable);

    /**
     * 发送任务到单例队列子线程
     *
     * @param runnable
     */
    void runSingleIoThread(@NonNull Runnable runnable);

    /**
     * 发送任务到主线程
     *
     * @param runnable
     */
    void runMainThread(@NonNull Runnable runnable);

    void runMainThread(@NonNull Runnable runnable, long time);

    /**
     * show出加载中的dialog,如果返回null就使用默认的
     */
    Dialog showLoadingDialog(Context context);
}
