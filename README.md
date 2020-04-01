# PictureSelector 2.0
   一款针对Android平台下的图片选择器，支持从相册获取图片、视频、音频&拍照，支持裁剪(单图or多图裁剪)、压缩、主题自定义配置等功能，支持动态获取权限&适配Android 5.0+系统的开源图片选择框架。<br>
  
  <br>如果有bug请描述清楚并可以提Issues   ,是fork过来的,原项目地址 https://github.com/LuckSiege/PictureSelector <br>


## 目录
-[用前需知](https://github.com/LuckSiege/PictureSelector/wiki/%E7%94%A8%E5%89%8D%E9%9C%80%E7%9F%A5)<br>
-[如何提Issues?](https://github.com/LuckSiege/PictureSelector/wiki/%E5%A6%82%E4%BD%95%E6%8F%90Issues%3F)<br>
-[功能特点](https://github.com/LuckSiege/PictureSelector/wiki/%E5%8A%9F%E8%83%BD%E7%89%B9%E7%82%B9)<br>
-[最新版本 v2.4.6](#最新版本)<br>
-[更新日志](https://github.com/LuckSiege/PictureSelector/releases/tag/v2.4.6)<br>
-[主题配置-Xml方式](https://github.com/LuckSiege/PictureSelector/wiki/%E8%87%AA%E5%AE%9A%E4%B9%89%E4%B8%BB%E9%A2%98-Xml%E6%96%B9%E5%BC%8F)<br>
-[主题配置-Code方式](https://github.com/LuckSiege/PictureSelector/wiki/%E8%87%AA%E5%AE%9A%E4%B9%89%E5%8A%A8%E6%80%81%E4%B8%BB%E9%A2%98(%E5%8C%85%E5%90%AB%E8%A3%81%E5%89%AA%E3%80%81%E7%9B%B8%E5%86%8C%E5%90%AF%E5%8A%A8%E5%8A%A8%E7%94%BB)-Code%E6%96%B9%E5%BC%8F)<br>
-[演示效果](#演示效果)<br>
-[集成方式](https://github.com/LuckSiege/PictureSelector/wiki/%E9%9B%86%E6%88%90%E6%96%B9%E5%BC%8F)<br>
-[启动相册](#启动相册)<br>
-[单独拍照](#单独拍照)<br>
-[结果回调](https://github.com/LuckSiege/PictureSelector/wiki/%E7%BB%93%E6%9E%9C%E5%9B%9E%E8%B0%83)<br>
-[常见错误](https://github.com/LuckSiege/PictureSelector/wiki/%E5%B8%B8%E8%A7%81%E9%94%99%E8%AF%AF)<br>
-[缓存清除](#缓存清除)<br>
-[混淆配置](#混淆配置)<br>
-[LICENSE](#LICENSE)<br>
-[兼容性测试](#兼容性测试)<br>
-[打赏](#打赏)<br> 


## 最新版本
```sh
maven { url 'https://jitpack.io' }
implementation 'com.github.ltttttttttttt:PictureSelector:2.7.2'
```

## 初始化
```
PictureSelectionConfig.init(this, new ResourcesConfig());//初始化图片选择
```

## 启动相册
```sh
 PictureSelector.create(this)
   .openGallery(PictureMimeType.ofImage())
   .loadImageEngine(GlideEngine.createGlideEngine()) // 请参考Demo GlideEngine.java
   .forResult(PictureConfig.CHOOSE_REQUEST);   
```

更多功能
```
val builder = PictureSelector.create(activity)

                            //主要可变更设置
                            .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                    builder.queryMaxFileSize(10)// 只查多少M以内的图片、视频、音频  单位M
                            //.setLanguage(LanguageConfig.CHINESE)// 设置语言，默认自动识别
//                            .querySpecifiedFormatSuffix(PictureMimeType.ofPNG())// 查询指定后缀格式资源
                            .isGif(false)// 是否显示gif图片 true or false
//                            .selectionMedia()// 是否传入已选图片 List<LocalMedia> list
                            .minimumCompressSize(30)// 小于100kb的图片不压缩
                            .cutOutQuality(90)// 裁剪输出质量 默认100
                            .videoQuality(1)// 视频录制质量 0 or 1 int
                            .videoMaxSecond(60)// 显示多少秒以内的视频or音频也可适用 int
                            .videoMinSecond(1)// 显示多少秒以上的视频or音频也可适用 int
                            .recordVideoSecond(60)//视频最大秒数录制 默认60s int
                            .theme(R.style.picture_WeChat_style)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                            .isSingleDirectReturn(false)// 单选模式下是否直接返回，PictureConfig.SINGLE模式下有效


                            //次要可变更设置
//                            .cropImageWideHigh()// 裁剪宽高值，设置如果大于图片本身宽高则无效
                            .synOrAsy(false)//同步true或异步false 压缩 默认同步
                            .compressSavePath(FileConfig.CACHE_DIR)//压缩图片和裁切图片保存地址
                            .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示 true or false
                            .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)// 设置相册Activity方向，不设置默认使用系统,之前用的 ActivityInfo.SCREEN_ORIENTATION_PORTRAIT as3.6报错,所以改了一下
//                            .cameraFileName("test.png") // 重命名拍照文件名、注意这个只在使用相机时可以使用
//                            .renameCompressFile("test.png")// 重命名压缩文件名、 注意这个不要重复，只适用于单张图压缩使用
//                            .renameCropFileName("test.png")// 重命名裁剪文件名、 注意这个不要重复，只适用于单张图裁剪使用
//                            .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                            .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                            .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                            .circleDimmedLayer(false)// 是否圆形裁剪 true or false
                            .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                            .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                            .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                            .openClickSound(false)// 是否开启点击声音 true or false
                            .previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false  true有bug,但是false的效果正好,所以不改了
                            .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
                            .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                            .isDragFrame(true)// 是否可拖动裁剪框(固定)
//                            .bindCustomPlayVideoCallback(callback)// 自定义播放回调控制，用户可以使用自己的视频播放界面


                            //一般不变设置
                            .compress(isCompress)// 是否压缩 true or false
                            .enableCrop(isCorp)// 是否裁剪 true or false
//                            .setPictureStyle()// 动态自定义相册主题  注意：此方法最好不要与.theme();同时存在， 二选一
//                            .setPictureCropStyle()// 动态自定义裁剪主题
                            .setPictureWindowAnimationStyle(PictureWindowAnimationStyle(R.anim.in_from_bottom, R.anim.out_to_bottom))// 自定义相册启动退出动画
//                            .loadImageEngine(PictureResourcesConfig())// 外部传入图片加载引擎，必传项   现在再初始化时设置了,所以不需要在设置
                            .isWithVideoImage(true)// 图片和视频是否可以同选,只在ofAll模式下有效
//                            .isUseCustomCamera(false)// 是否使用自定义相机，5.0以下请不要使用，可能会出现兼容性问题,被弃用
                            .isOriginalImageControl(false)// 是否显示原图控制按钮，如果用户勾选了 压缩、裁剪功能将会失效
                            .isWeChatStyle(true)// 是否开启微信图片选择风格，此开关开启了才可使用微信主题！！！
                            .isAndroidQTransform(false)// 是否需要处理Android Q 拷贝至应用沙盒的操作，只针对compress(false); && enableCrop(false);有效
                            .isMultipleSkipCrop(true)// 多图裁剪时是否支持跳过，默认支持
                            .isMultipleRecyclerAnimation(true)// 多图裁剪底部列表显示动画效果
                            .maxSelectNum(num)// 最大图片选择数量 int
                            .minSelectNum(1)// 最小选择数量 int
                            .minVideoSelectNum(1)// 视频最小选择数量，如果没有单独设置的需求则可以不设置，同用minSelectNum字段
                            .maxVideoSelectNum(num) // 视频最大选择数量，如果没有单独设置的需求则可以不设置，同用maxSelectNum字段
                            .imageSpanCount(4)// 每行显示个数 int
                            .isReturnEmpty(false)// 未选择数据时点击按钮是否可以返回
                            .isNotPreviewDownload(true)// 预览图片长按是否可以下载
//                            .setTitleBarBackgroundColor()//相册标题栏背景色
//                            .isChangeStatusBarFontColor()// 是否关闭白色状态栏字体颜色
//                            .setStatusBarColorPrimaryDark()// 状态栏背景色
//                            .setUpArrowDrawable()// 设置标题栏右侧箭头图标
//                            .setDownArrowDrawable()// 设置标题栏右侧箭头图标
//                            .isOpenStyleCheckNumMode()// 是否开启数字选择模式 类似QQ相册
                            .selectionMode(if (num == 1) PictureConfig.SINGLE else PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                            .previewImage(true)// 是否可预览图片 true or false
                            .previewVideo(true)// 是否可预览视频 true or false
                            .enablePreviewAudio(true) // 是否可播放音频 true or false
//                            .setCircleDimmedColor()// 设置圆形裁剪背景色值
//                            .setCircleDimmedBorderColor()// 设置圆形裁剪边框色值
//                            .setCircleStrokeWidth(3)// 设置圆形裁剪边框粗细
//                            .glideOverride()// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                    if (corpRatio != null) {
                        builder.withAspectRatio(corpRatio.first, corpRatio.last)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                    }
                    builder.isCamera(isShowCamera)// 是否显示拍照按钮 true or false
//                            .cropCompressQuality(90)// 废弃 改用cutOutQuality()
                            .forResult(188)//结果回调onActivityResult code
                    //.forResult(OnResultCallbackListener listener);//Callback回调方式
                    //.forIntent(activity);//返回intent,自行调用startActivityForResult
```

## 单独拍照
快捷调用，单独启动拍照或视频 根据PictureMimeType自动识别 更多功能 [请查看](https://github.com/LuckSiege/PictureSelector/wiki/PictureSelector-%E5%8A%9F%E8%83%BD%E9%85%8D%E5%88%B6%E9%A1%B9)
```sh
 PictureSelector.create(this)
   .openCamera(PictureMimeType.ofImage())
   .forResult(PictureConfig.REQUEST_CAMERA);   
```

## 缓存清除
```sh
 //包括裁剪和压缩后的缓存，要在上传成功后调用，type 指的是图片or视频缓存取决于你设置的ofImage或ofVideo 注意：需要系统sd卡权限  
 PictureFileUtils.deleteCacheDirFile(this,type);
 // 清除所有缓存 例如：压缩、裁剪、视频、音频所生成的临时文件
 PictureFileUtils.deleteAllCacheDirFile(this);
```
 
## 预览图片 
```
// 预览图片 可自定长按保存路径
*注意 .themeStyle(R.style.theme)；里面的参数不可删，否则闪退...

PictureSelector.create(AppManager.currentActivity())
                .themeStyle(R.style.picture_WeChat_style)
                .isWeChatStyle(true)
                .isNotPreviewDownload(true)
                .openExternalPreview(position, selectList)
```

## 预览视频
```sh
PictureSelector.create(AppManager.currentActivity()).externalPictureVideo(videoPath)
```

## 预览音频
```
PictureSelector.create(AppManager.currentActivity())
                .externalPictureAudio(audioPath, true)
                ?.setLoop(false)//是否循环播放
```

## 项目使用第三方库：

* PhotoView
* luban
* ucrop

## 混淆配置 
```sh
#PictureSelector 2.0
-keep class com.luck.picture.lib.** { *; }

-dontwarn com.yalantis.ucrop**
-keep class com.yalantis.ucrop** { *; }
-keep interface com.yalantis.ucrop** { *; }
```
## LICENSE
```sh
   Copyright 2017 Luck

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```

## 兼容性测试
******腾讯优测-深度测试-通过率达到100%******

![image](https://github.com/LuckSiege/PictureSelector/blob/master/image/test.png)


## 演示效果

| 单一模式 | 混选模式 |
|:-----------:|:-----------:|
|![](image/home.jpg)|![](image/home_mixed.jpg)| 

| 默认风格 | 预览 | 多图裁剪 |
|:-----------:|:--------:|:---------:|
|![](image/picture_default_style_1.jpg) | <img src="image/picture_default_style_2.jpg"/> | ![](image/picture_default_style_new_3.jpg)|  

| 数字风格 | 预览 | 多图裁剪 |
|:-----------:|:--------:|:---------:|
|![](image/picture_num_style_new_1.jpg) | ![](image/picture_num_style_new_2.jpg) | ![](image/picture_num_style_new_3.jpg)| 

| 白色风格 | 预览 | 单图裁剪 |
|:-----------:|:--------:|:---------:|
|![](image/picture_sina_style_1.jpg) | ![](image/picture_sina_style_new_2.jpg) | ![](image/picture_sina_style_new_3.jpg)| 

| 全新风格 | 预览 | 多图裁剪 |
|:-----------:|:--------:|:---------:|
|![](image/picture_wechat_style_1.jpg) | ![](image/picture_wechat_style_2.jpg) | ![](image/picture_wechat_style_new_3.jpg)| 

| 相册目录 | 单选模式 | 头像裁剪|
|:-----------:|:--------:|:--------:|
|![](image/picture_wechat_album_style.jpg) |![](image/picture_wechat_single_style_3.jpg) | ![](image/picture_circular_crop_new_style.jpg)| 

| 白色风格 | 视频 | 音频 |
|:-----------:|:-----------:|:--------:|
|![](image/picture_white_style.png) |![](image/picture_video.jpg) | ![](image/picture_audio.jpg)| 

