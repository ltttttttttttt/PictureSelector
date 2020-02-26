package com.luck.picture.lib;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.luck.picture.lib.tools.DateUtils;

/**
 * creator: lt  2020/2/25  lt.dygzs@qq.com
 * effect : 播放音频的弹窗
 * warning:
 */
public class PicturePlayAudioDialog extends Dialog {
    public MediaPlayer mediaPlayer = new MediaPlayer();
    private SeekBar musicSeekBar;
    private TextView mTvPlayPause;
    private Handler handler = new Handler();
    private boolean isPlayAudio = false;
    private TextView mTvMusicTime;
    private TextView mTvMusicTotal;
    private boolean isLoop = false;

    public PicturePlayAudioDialog(@NonNull Context context) {
        super(context);
    }

    public PicturePlayAudioDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected PicturePlayAudioDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    /**
     * 设置是否循环播放
     */
    public PicturePlayAudioDialog setLoop(boolean loop) {
        isLoop = loop;
        return this;
    }

    /**
     * 初始化
     *
     * @param path    音频路径
     * @param playNow 是否立即播放
     * @return
     */
    public PicturePlayAudioDialog init(final String path, final boolean playNow) {
        setContentView(R.layout.picture_audio_dialog);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        mTvMusicTime = findViewById(R.id.tv_musicTime);
        musicSeekBar = findViewById(R.id.musicSeekBar);
        mTvMusicTotal = findViewById(R.id.tv_musicTotal);
        mTvPlayPause = findViewById(R.id.tv_PlayPause);
        TextView mTvStop = findViewById(R.id.tv_Stop);
        TextView mTvQuit = findViewById(R.id.tv_Quit);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                initPlayer(path, playNow);
            }
        }, 30);
        mTvPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAudio();
            }
        });
        mTvStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTvPlayPause.setText(getContext().getString(R.string.picture_play_audio));
                stop(path);
            }
        });
        mTvQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        stop(path);
                    }
                }, 30);
                try {
                    if (isShowing()) {
                        dismiss();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                handler.removeCallbacks(mRunnable);
            }
        });
        musicSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                handler.removeCallbacks(mRunnable);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        stop(path);
                    }
                }, 30);
                try {
                    if (isShowing()) {
                        dismiss();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        if (playNow)
            handler.post(mRunnable);
        show();
        return this;
    }

    /**
     * 初始化音频播放组件
     */
    private void initPlayer(String path, boolean playNow) {
        try {
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
            mediaPlayer.setLooping(isLoop);
            if (playNow)
                playAudio();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void playAudio() {
        musicSeekBar.setProgress(mediaPlayer.getCurrentPosition());
        musicSeekBar.setMax(mediaPlayer.getDuration());
        String ppStr = mTvPlayPause.getText().toString();
        if (ppStr.equals(getContext().getString(R.string.picture_play_audio))) {
            mTvPlayPause.setText(getContext().getString(R.string.picture_pause_audio));
            playOrPause();
        } else {
            mTvPlayPause.setText(getContext().getString(R.string.picture_play_audio));
            playOrPause();
        }
        if (!isPlayAudio) {
            if (handler != null) {
                handler.post(mRunnable);
            }
            isPlayAudio = true;
        }
    }

    /**
     * 暂停播放
     */
    private void playOrPause() {
        try {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            } else {
                mediaPlayer.start();
                handler.post(mRunnable);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 停止播放
     *
     * @param path
     */
    private void stop(String path) {
        try {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
            mediaPlayer.seekTo(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //  通过 Handler 更新 UI 上的组件状态
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                if (!isShowing()) {
                    return;
                }
                if (!mediaPlayer.isPlaying() && !isLoop) {
                    mTvPlayPause.setText(getContext().getString(R.string.picture_play_audio));
                }
                mTvMusicTime.setText(DateUtils.formatDurationTime(mediaPlayer.getCurrentPosition()));
                musicSeekBar.setProgress(mediaPlayer.getCurrentPosition());
                musicSeekBar.setMax(mediaPlayer.getDuration());
                mTvMusicTotal.setText(DateUtils.formatDurationTime(mediaPlayer.getDuration()));
                if (mediaPlayer.isPlaying()) {
                    handler.postDelayed(mRunnable, 16);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
}
