package zeffect.cn.common.media;

import android.media.MediaPlayer;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <pre>
 *      author  ：zzx
 *      e-mail  ：zhengzhixuan18@gmail.com
 *      time    ：2017/08/17
 *      desc    ：
 *      version:：1.0
 * </pre>
 *
 * @author zzx
 */

public class MediaUtils {
    private static MediaUtils instance;
    private MediaPlayer mPlayer;

    public static MediaUtils getInstance() {
        if (instance == null) {
            synchronized (MediaUtils.class) {
                if (instance == null) {
                    instance = new MediaUtils();
                }
            }
        }
        return instance;
    }

    /**
     * 播放
     *
     * @param paths 本地路径的集合，这里没有考虑其它路径，只有本地路径。因为我用这个路径判断了这个文件在不在。
     **/
    public void play(String[] paths) {
        play(paths, null);
    }

    public void play(String[] paths, OnPlayer pOnPlayer) {
        if (pOnPlayer != null) addPlayListener(pOnPlayer);
        mPaths.clear();
        mPaths.addAll(Arrays.asList(paths));
        if (!mPaths.isEmpty()) {
            play(0);
        }
    }

    private List<String> mPaths = new ArrayList<>();

    private void play(final int position) {
        if (mPaths.size() - 1 < position || position < 0) {
            stop();
            return;
        }
        String nowPath = mPaths.get(position);
        if (TextUtils.isEmpty(nowPath)) {
            stop();
            return;
        }
        File tempFile = new File(nowPath);
        if (!tempFile.exists()) {
            stop();
            return;
        }
        if (mPlayer == null) {
            mPlayer = new MediaPlayer();
        }
        try {
//            if (mPlayer.isPlaying()) {
            mPlayer.reset();
//            }
        } catch (IllegalStateException e) {
            mPlayer = null;
            mPlayer = new MediaPlayer();
        }
        try {
            mPlayer.reset();
            mPlayer.setDataSource(nowPath);
            mPlayer.prepareAsync();
            mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });
            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer arg0) {
                    int next = position + 1;
                    if (mPaths.size() - 1 < next || next < 0) stop();
                    else play(next);
                }

            });
            mPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer player, int arg1, int arg2) {
                    stop();
                    return true;
                }
            });
        } catch (IOException e) {
            stop();
        }
    }

    /***
     * 关闭播放
     */
    public void stop() {
        if (mPlayer != null) {
            try {
                mPlayer.reset();
                mPlayer.release();
                mPlayer.stop();
            } catch (IllegalStateException E) {
                E.printStackTrace();
            }
        }
        removeListener();
    }


    private void removeListener() {
        if (mOnPlayerWeakReference != null && mOnPlayerWeakReference.get() != null)
            mOnPlayerWeakReference.get().onStop();
    }


    private WeakReference<OnPlayer> mOnPlayerWeakReference;

    public interface OnPlayer {
        void onStop();
    }


    private void addPlayListener(OnPlayer pOnPlayer) {
        if (pOnPlayer == null) return;
        mOnPlayerWeakReference = new WeakReference<OnPlayer>(pOnPlayer);
    }

}
