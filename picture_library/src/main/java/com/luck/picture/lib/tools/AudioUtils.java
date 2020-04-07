package com.luck.picture.lib.tools;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;

/**
 * creator: lt  2020/4/1  lt.dygzs@qq.com
 * effect : 音频处理工具
 * warning:
 */
public class AudioUtils {
    /**
     * 获取专辑图,id暂不知道怎么获取
     * @param context
     * @param album_id
     * @return
     */
    public static String getAlbumArt(Context context, int album_id) {
        String mUriAlbums = "content://media/external/audio/albums";
        String[] projection = new String[]{"album_art"};
        Cursor cur = context.getContentResolver().query(Uri.parse(mUriAlbums + "/" + Integer.toString(album_id)), projection, null, null, null);
        String album_art = null;
        if (cur != null && cur.getCount() > 0 && cur.getColumnCount() > 0) {
            cur.moveToNext();
            album_art = cur.getString(0);
            cur.close();
        }
        return album_art;
    }

    /**
     * 获取专辑图
     * @param context
     * @param url
     * @return
     */
    public static Bitmap getArtwork(Context context, String url) {
        MediaMetadataRetriever myRetriever = getMediaMetadataRetriever(context, url);
        byte[] artwork;

        artwork = myRetriever.getEmbeddedPicture();

        if (artwork != null) {
            return BitmapFactory.decodeByteArray(artwork, 0, artwork.length);
        }
        return null;
    }

    /**
     * 获取音频名称
     */
    public static String getAudioName(Context context, String url) {
        return getMediaMetadataRetriever(context, url)
                .extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
    }


    public static MediaMetadataRetriever getMediaMetadataRetriever(Context context, String url) {
        Uri selectedAudio = Uri.parse(url);
        MediaMetadataRetriever myRetriever = new MediaMetadataRetriever();
        myRetriever.setDataSource(context, selectedAudio); // the URI of audio file
        return myRetriever;
    }

}
