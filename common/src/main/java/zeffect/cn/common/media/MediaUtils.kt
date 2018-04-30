package zeffect.cn.common.media

import android.media.MediaPlayer
import android.text.TextUtils

import java.io.File
import java.io.IOException
import java.lang.ref.WeakReference
import java.util.ArrayList
import java.util.Arrays

/**
 * <pre>
 * author  ：zzx
 * e-mail  ：zhengzhixuan18@gmail.com
 * time    ：2017/08/17
 * desc    ：
 * version:：1.0
</pre> *
 *
 * @author zzx
 */

class MediaUtils {
    private var mPlayer: MediaPlayer? = null

    @JvmOverloads
    fun play(paths: Array<String>, pOnPlayer: OnPlayer? = null) {
        if (pOnPlayer != null) addPlayListener(pOnPlayer)
        mPaths.clear()
        mPaths.addAll(Arrays.asList(*paths))
        if (!mPaths.isEmpty()) {
            play(0)
        }
    }

    private val mPaths = ArrayList<String>()

    private fun play(position: Int) {
        if (mPaths.size - 1 < position || position < 0) {
            stop()
            return
        }
        val nowPath = mPaths[position]
        if (TextUtils.isEmpty(nowPath)) {
            stop()
            return
        }
//        val tempFile = File(nowPath)
//        if (!tempFile.exists()) {
//            stop()
//            return
//        }
        if (mPlayer == null) {
            mPlayer = MediaPlayer()
        }
        try {
            //            if (mPlayer.isPlaying()) {
            mPlayer?.reset()
            //            }
        } catch (e: IllegalStateException) {
            mPlayer = null
            mPlayer = MediaPlayer()
        }

        try {
            mPlayer?.reset()
            mPlayer?.setDataSource(nowPath)
            mPlayer?.prepareAsync()
            mPlayer?.setOnPreparedListener { mp -> mp.start() }
            mPlayer?.setOnCompletionListener {
                val next = position + 1
                if (mPaths.size - 1 < next || next < 0)
                    stop()
                else
                    play(next)
            }
            mPlayer?.setOnErrorListener { player, arg1, arg2 ->
                stop()
                true
            }
        } catch (e: IOException) {
            stop()
        }

    }

    /***
     * 关闭播放
     */
    fun stop() {
        if (mPlayer != null) {
            try {
                mPlayer?.reset()
                mPlayer?.release()
                mPlayer?.stop()
            } catch (E: IllegalStateException) {
                E.printStackTrace()
            }

        }
        removeListener()
    }


    private fun removeListener() {
        if (mOnPlayerWeakReference != null && mOnPlayerWeakReference?.get() != null)
            mOnPlayerWeakReference?.get()?.onStop()
    }


    private var mOnPlayerWeakReference: WeakReference<OnPlayer>? = null

    interface OnPlayer {
        fun onStop()
    }


    private fun addPlayListener(pOnPlayer: OnPlayer?) {
        if (pOnPlayer == null) return
        mOnPlayerWeakReference = WeakReference(pOnPlayer)
    }

    companion object {
        private var instance: MediaUtils? = null
    }

}
/**
 * 播放
 *
 * @param paths 本地路径的集合，这里没有考虑其它路径，只有本地路径。因为我用这个路径判断了这个文件在不在。
 */
