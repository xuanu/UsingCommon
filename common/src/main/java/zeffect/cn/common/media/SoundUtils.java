package zeffect.cn.common.media;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

/**
 * <pre>
 *      author  ：zzx
 *      e-mail  ：zhengzhixuan18@gmail.com
 *      time    ：2017/09/04
 *      desc    ：
 *      version:：1.0
 * </pre>
 *
 * @author zzx
 */

public class SoundUtils {
    private static SoundUtils instance;
    private SoundPool mSoundPool;


    public static SoundUtils getInstance() {
        if (instance == null) {
            synchronized (SoundUtils.class) {
                if (instance == null) instance = new SoundUtils();
            }
        }
        return instance;
    }

    /**
     * 默认最多10个音频
     **/
    private void init() {
        init(10);
    }

    public void init(int max) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            mSoundPool = new SoundPool.Builder().setMaxStreams(max).build();
        else
            mSoundPool = new SoundPool(max, AudioManager.STREAM_MUSIC, 0);
    }


    private int load(Context pContext, int id, int priority) {
        if (pContext == null) throw new NullPointerException("context is null");
        if (mSoundPool == null) init();
        return mSoundPool.load(pContext, id, priority);
    }


    public void play(Context pContext, int id, int priority) {
        if (pContext == null) throw new NullPointerException("context is null");
        if (mSoundPool == null) init();
        mSoundPool.play(load(pContext, id, priority), 1, 1, 0, 0, 1);
    }


}
