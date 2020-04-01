package com.luck.picture.lib.language;

import android.os.Build;

import com.luck.picture.lib.config.PictureSelectionConfig;

import java.util.Locale;

/**
 * @author：luck
 * @date：2019-11-25 21:58
 * @describe：语言转换
 */
public class LocaleTransform {
    public static Locale getLanguage(int language) {
        switch (language) {
            case LanguageConfig.ENGLISH:
                // 英语-美国
                return Locale.ENGLISH;
            case LanguageConfig.TRADITIONAL_CHINESE:
                // 繁体中文
                return Locale.TRADITIONAL_CHINESE;
            case LanguageConfig.KOREA:
                // 韩语
                return Locale.KOREA;
            case LanguageConfig.GERMANY:
                // 德语
                return Locale.GERMANY;
            case LanguageConfig.FRANCE:
                // 法语
                return Locale.FRANCE;
            case LanguageConfig.JAPAN:
                // 日语
                return Locale.JAPAN;
            case LanguageConfig.VIETNAM:
                // 越南语
                return new Locale("vi");
            case LanguageConfig.CHINESE:
                // 简体中文
                return Locale.CHINESE;
            default:
                // 自动使用app内的语言
                return getAppLocale();
        }
    }

    /**
     * 获取app当前的地区设置
     */
    private static Locale getAppLocale() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return PictureSelectionConfig.application.getResources().getConfiguration().getLocales().get(0);
        } else {
            return PictureSelectionConfig.application.getResources().getConfiguration().locale;
        }
    }
}
