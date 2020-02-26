package com.luck.picture.lib;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.config.PictureSelectionConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.style.PictureParameterStyle;
import com.luck.picture.lib.tools.AttrsUtils;
import com.luck.picture.lib.tools.DateUtils;
import com.luck.picture.lib.tools.DoubleUtils;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.luck.picture.lib.tools.SdkVersionUtils;
import com.luck.picture.lib.tools.StringUtils;
import com.luck.picture.lib.tools.ToastUtils;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.model.CutInfo;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * @author：luck
 * @date：2017-5-24 22:30
 * @describe：PictureSelector
 */

public final class PictureSelector {

    private final WeakReference<Activity> mActivity;
    private final WeakReference<Fragment> mFragment;

    private PictureSelector(Activity activity) {
        this(activity, null);
    }

    private PictureSelector(Fragment fragment) {
        this(fragment.getActivity(), fragment);
    }

    private PictureSelector(Activity activity, Fragment fragment) {
        mActivity = new WeakReference<>(activity);
        mFragment = new WeakReference<>(fragment);
    }

    /**
     * Start PictureSelector for Activity.
     *
     * @param activity
     * @return PictureSelector instance.
     */
    public static PictureSelector create(Activity activity) {
        return new PictureSelector(activity);
    }

    /**
     * Start PictureSelector for Fragment.
     *
     * @param fragment
     * @return PictureSelector instance.
     */
    public static PictureSelector create(Fragment fragment) {
        return new PictureSelector(fragment);
    }

    /**
     * @param chooseMode Select the type of picture you want，all or Picture or Video .
     * @return LocalMedia PictureSelectionModel
     */
    public PictureSelectionModel openGallery(int chooseMode) {
        return new PictureSelectionModel(this, chooseMode);
    }

    /**
     * @param chooseMode Select the type of picture you want，Picture or Video.
     * @return LocalMedia PictureSelectionModel
     */
    public PictureSelectionModel openCamera(int chooseMode) {
        return new PictureSelectionModel(this, chooseMode, true);
    }

    /**
     * 外部预览时设置样式
     *
     * @param themeStyle
     * @return
     */
    public PictureSelectionModel themeStyle(int themeStyle) {
        return new PictureSelectionModel(this, PictureMimeType.ofImage())
                .theme(themeStyle);
    }

    /**
     * 外部预览时动态代码设置样式
     *
     * @param style
     * @return
     */
    public PictureSelectionModel setPictureStyle(PictureParameterStyle style) {
        return new PictureSelectionModel(this, PictureMimeType.ofImage())
                .setPictureStyle(style);
    }

    /**
     * @param data
     * @return Selector Multiple LocalMedia
     */
    public static List<LocalMedia> obtainMultipleResult(Intent data) {
        if (data != null) {
            List<LocalMedia> result = data.getParcelableArrayListExtra(PictureConfig.EXTRA_RESULT_SELECTION);
            return result == null ? new ArrayList<LocalMedia>() : result;
        }
        return new ArrayList<>();
    }

    /**
     * @param data
     * @return Put image Intent Data
     */
    public static Intent putIntentResult(List<LocalMedia> data) {
        return new Intent().putParcelableArrayListExtra(PictureConfig.EXTRA_RESULT_SELECTION,
                (ArrayList<? extends Parcelable>) data);
    }

    /**
     * @param bundle
     * @return get Selector  LocalMedia
     */
    public static List<LocalMedia> obtainSelectorList(Bundle bundle) {
        if (bundle != null) {
            List<LocalMedia> selectionMedias = bundle.getParcelableArrayList(PictureConfig.EXTRA_SELECT_LIST);
            return selectionMedias == null ? new ArrayList<LocalMedia>() : selectionMedias;
        }
        return new ArrayList<>();
    }

    /**
     * @param selectedImages
     * @return put Selector  LocalMedia
     */
    public static void saveSelectorList(Bundle outState, List<LocalMedia> selectedImages) {
        outState.putParcelableArrayList(PictureConfig.EXTRA_SELECT_LIST,
                (ArrayList<? extends Parcelable>) selectedImages);
    }

    /**
     * set preview image
     *
     * @param position
     * @param medias
     */
    public void externalPicturePreview(int position, List<LocalMedia> medias, int enterAnimation) {
        if (!DoubleUtils.isFastDoubleClick()) {
            Intent intent = new Intent(getActivity(), PictureExternalPreviewActivity.class);
            intent.putParcelableArrayListExtra(PictureConfig.EXTRA_PREVIEW_SELECT_LIST,
                    (ArrayList<? extends Parcelable>) medias);
            intent.putExtra(PictureConfig.EXTRA_POSITION, position);
            getActivity().startActivity(intent);
            getActivity().overridePendingTransition(enterAnimation != 0
                    ? enterAnimation : R.anim.picture_anim_enter, R.anim.picture_anim_fade_in);
        }
    }

    /**
     * set preview image
     *
     * @param position
     * @param medias
     * @param directory_path
     */
    public void externalPicturePreview(int position, String directory_path, List<LocalMedia> medias, int enterAnimation) {
        if (!DoubleUtils.isFastDoubleClick()) {
            Intent intent = new Intent(getActivity(), PictureExternalPreviewActivity.class);
            intent.putParcelableArrayListExtra(PictureConfig.EXTRA_PREVIEW_SELECT_LIST, (ArrayList<? extends Parcelable>) medias);
            intent.putExtra(PictureConfig.EXTRA_POSITION, position);
            intent.putExtra(PictureConfig.EXTRA_DIRECTORY_PATH, directory_path);
            getActivity().startActivity(intent);
            getActivity().overridePendingTransition(enterAnimation != 0
                    ? enterAnimation : R.anim.picture_anim_enter, R.anim.picture_anim_fade_in);
        }
    }

    /**
     * set preview video
     *
     * @param path
     */
    public void externalPictureVideo(String path) {
        Activity activity = getActivity();
        if (activity == null)
            return;
        if (!DoubleUtils.isFastDoubleClick()) {
            Intent intent = new Intent(activity, PictureVideoPlayActivity.class);
            intent.putExtra(PictureConfig.EXTRA_VIDEO_PATH, path);
            intent.putExtra(PictureConfig.EXTRA_PREVIEW_VIDEO, true);
            activity.startActivity(intent);
        }
    }

    /**
     * set preview audio
     *
     * @param path
     */
    @Nullable
    public PicturePlayAudioDialog externalPictureAudio(String path, boolean playNow) {
        Activity activity = getActivity();
        if (activity == null)
            return null;
        if (!DoubleUtils.isFastDoubleClick()) {
            //Intent intent = new Intent(getActivity(), PicturePlayAudioActivity.class);
            //intent.putExtra(PictureConfig.EXTRA_AUDIO_PATH, path);
            //getActivity().startActivity(intent);
            //getActivity().overridePendingTransition(R.anim.picture_anim_enter, 0);
            //改为弹窗样式
            return new PicturePlayAudioDialog(activity, R.style.Picture_Theme_Dialog_AudioStyle)
                    .init(path, playNow);
        }
        return null;
    }

    /**
     * 压缩图片,线程阻塞
     */
    public List<File> compressImage(Context context, PictureSelectionConfig config, String... paths) throws IOException {
        ArrayList<LocalMedia> list = new ArrayList<>(paths.length);
        for (String path : paths) {
            LocalMedia media = new LocalMedia();
            media.setPath(path);
            list.add(media);
        }
        return Luban.with(context)
                .loadMediaData(list)
                .isCamera(false)
                .setTargetDir(config.compressSavePath)
                .setCompressQuality(config.compressQuality)
                .setFocusAlpha(config.focusAlpha)
                .setNewCompressFileName(config.renameCompressFileName)
                .ignoreBy(config.minimumCompressSize).get();
    }


    /**
     * goto crop image
     */
    public void gotoCropImage(PictureSelectionConfig config, String... paths) {
        Activity activity = getActivity();
        if (activity == null)
            return;
        if (paths == null || paths.length == 0) {
            ToastUtils.s(activity, activity.getString(R.string.picture_not_crop_data));
            return;
        }
        int toolbarColor = 0, statusColor = 0, titleColor = 0;
        boolean isChangeStatusBarFontColor;
        if (config.cropStyle != null) {
            if (config.cropStyle.cropTitleBarBackgroundColor != 0) {
                toolbarColor = config.cropStyle.cropTitleBarBackgroundColor;
            }
            if (config.cropStyle.cropStatusBarColorPrimaryDark != 0) {
                statusColor = config.cropStyle.cropStatusBarColorPrimaryDark;
            }
            if (config.cropStyle.cropTitleColor != 0) {
                titleColor = config.cropStyle.cropTitleColor;
            }
            isChangeStatusBarFontColor = config.cropStyle.isChangeStatusBarFontColor;
        } else {
            if (config.cropTitleBarBackgroundColor != 0) {
                toolbarColor = config.cropTitleBarBackgroundColor;
            } else {
                // 兼容老的Theme方式
                toolbarColor = AttrsUtils.getTypeValueColor(activity, R.attr.picture_crop_toolbar_bg);
            }
            if (config.cropStatusBarColorPrimaryDark != 0) {
                statusColor = config.cropStatusBarColorPrimaryDark;
            } else {
                // 兼容老的Theme方式
                statusColor = AttrsUtils.getTypeValueColor(activity, R.attr.picture_crop_status_color);
            }
            if (config.cropTitleColor != 0) {
                titleColor = config.cropTitleColor;
            } else {
                // 兼容老的Theme方式
                titleColor = AttrsUtils.getTypeValueColor(activity, R.attr.picture_crop_title_color);
            }

            // 兼容单独动态设置主题方式
            isChangeStatusBarFontColor = config.isChangeStatusBarFontColor;
            if (!isChangeStatusBarFontColor) {
                // 是否改变裁剪页状态栏字体颜色 黑白切换
                isChangeStatusBarFontColor = AttrsUtils.getTypeValueBoolean(activity, R.attr.picture_statusFontColor);
            }
        }
        UCrop.Options options = config.uCropOptions == null ? new UCrop.Options() : config.uCropOptions;
        options.isOpenWhiteStatusBar(isChangeStatusBarFontColor);
        options.setToolbarColor(toolbarColor);
        options.setStatusBarColor(statusColor);
        options.setToolbarWidgetColor(titleColor);
        options.setCircleDimmedLayer(config.circleDimmedLayer);
        options.setDimmedLayerColor(config.circleDimmedColor);
        options.setDimmedLayerBorderColor(config.circleDimmedBorderColor);
        options.setCircleStrokeWidth(config.circleStrokeWidth);
        options.setShowCropFrame(config.showCropFrame);
        options.setDragFrameEnabled(config.isDragFrame);
        options.setShowCropGrid(config.showCropGrid);
        options.setScaleEnabled(config.scaleEnabled);
        options.setRotateEnabled(config.rotateEnabled);
        options.isMultipleSkipCrop(config.isMultipleSkipCrop);
        options.setHideBottomControls(config.hideBottomControls);
        options.setCompressionQuality(config.cropCompressQuality);
        options.setRenameCropFileName(config.renameCropFileName);
        options.isCamera(config.camera);
        ArrayList<CutInfo> cutInfos = new ArrayList<>(paths.length);
        for (String path : paths)
            cutInfos.add(new CutInfo(path, true));
        options.setCutListData(cutInfos);
        options.isWithVideoImage(config.isWithVideoImage);
        options.setFreeStyleCropEnabled(config.freeStyleCropEnabled);
        options.setCropExitAnimation(config.windowAnimationStyle != null
                ? config.windowAnimationStyle.activityCropExitAnimation : 0);
        options.setNavBarColor(config.cropStyle != null ? config.cropStyle.cropNavBarColor : 0);
        options.withAspectRatio(config.aspect_ratio_x, config.aspect_ratio_y);
        options.isMultipleRecyclerAnimation(config.isMultipleRecyclerAnimation);
        if (config.cropWidth > 0 && config.cropHeight > 0) {
            options.withMaxResultSize(config.cropWidth, config.cropHeight);
        }
        String path = paths[0];
        boolean isAndroidQ = SdkVersionUtils.checkedAndroid_Q();
        boolean isHttp = PictureMimeType.isHttp(path);
        Uri uri = isHttp || isAndroidQ ? Uri.parse(path) : Uri.fromFile(new File(path));
        String mimeType = PictureMimeType.getMimeTypeFromMediaContentUri(activity, uri);
        String suffix = mimeType.replace("image/", ".");
        File file = new File(PictureFileUtils.getDiskCacheDir(activity),
                TextUtils.isEmpty(config.renameCropFileName) ? DateUtils.getCreateFileName("IMG_")
                        + suffix : config.camera ? config.renameCropFileName : StringUtils.rename(config.renameCropFileName));
        UCrop.of(uri, Uri.fromFile(file))
                .withOptions(options)
                .startAnimationMultipleCropActivity(activity, config.windowAnimationStyle != null
                        ? config.windowAnimationStyle.activityCropEnterAnimation : R.anim.picture_anim_enter);

    }

    /**
     * @return Activity.
     */
    @Nullable
    Activity getActivity() {
        return mActivity.get();
    }

    /**
     * @return Fragment.
     */
    @Nullable
    Fragment getFragment() {
        return mFragment != null ? mFragment.get() : null;
    }

}
