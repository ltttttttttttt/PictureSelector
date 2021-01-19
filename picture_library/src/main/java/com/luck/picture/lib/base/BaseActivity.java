package com.luck.picture.lib.base;

import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.luck.picture.lib.config.PictureSelectionConfig;

/**
 * creator: lt  2021/1/19  lt.dygzs@qq.com
 * effect : 父类Activity
 * warning:
 */
public class BaseActivity extends AppCompatActivity {
    private boolean isSetResources = false;
    private Resources mResources;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        PictureSelectionConfig.resourcesConfig.inFrontOfActivitySuperOnCreate(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isSetResources = false;
    }

    @Override
    public Resources getResources() {
        if (!isSetResources) {
            mResources = PictureSelectionConfig.resourcesConfig.hookResources(super.getResources());
            isSetResources = true;
        }
        return mResources == null ? super.getResources() : mResources;
    }
}
