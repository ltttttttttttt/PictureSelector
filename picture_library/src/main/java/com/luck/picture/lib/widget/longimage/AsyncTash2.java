package com.luck.picture.lib.widget.longimage;

import com.luck.picture.lib.config.PictureSelectionConfig;

/**
 * creator: lt  2020/3/10  lt.dygzs@qq.com
 * effect :
 * warning:
 */
abstract class AsyncTask2 {
    public void executeOnExecutor() {
        PictureSelectionConfig.resourcesConfig.runIoThread(new Runnable() {
            @Override
            public void run() {
                final Object o = doInBackground();
                PictureSelectionConfig.resourcesConfig.runMainThread(new Runnable() {
                    @Override
                    public void run() {
                        onPostExecute(o);
                    }
                });
            }
        });
    }

    public void execute() {
        PictureSelectionConfig.resourcesConfig.runSingleIoThread(new Runnable() {
            @Override
            public void run() {
                final Object o = doInBackground();
                PictureSelectionConfig.resourcesConfig.runMainThread(new Runnable() {
                    @Override
                    public void run() {
                        onPostExecute(o);
                    }
                });
            }
        });
    }

    protected abstract Object doInBackground(Object... objects);

    protected abstract void onPostExecute(Object object);
}
