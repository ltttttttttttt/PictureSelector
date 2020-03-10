package com.luck.pictureselector;

import android.app.Application;
import android.content.Context;

import com.luck.picture.lib.app.IApp;
import com.luck.picture.lib.app.PictureAppMaster;
import com.luck.picture.lib.config.PictureSelectionConfig;
import com.luck.picture.lib.crash.PictureSelectorCrashUtils;


/**
 * @author：luck
 * @date：2019-12-03 22:53
 * @describe：Application
 */

public class App extends Application implements IApp {
    @Override
    public void onCreate() {
        super.onCreate();

        /** PictureSelector日志管理配制开始 **/
        // PictureSelector 绑定监听用户获取全局上下文或其他...
        PictureAppMaster.getInstance().setApp(this);
        // PictureSelector Crash日志监听
        PictureSelectorCrashUtils.init((t, e) -> {
            // Crash之后的一些操作可再此处理，没有就忽略...

        });
        /** PictureSelector日志管理配制结束 **/

        //初始化图片选择库
        PictureSelectionConfig.init(this, GlideEngine.createGlideEngine());

    }

    @Override
    public Context getAppContext() {
        return this;
    }
}
