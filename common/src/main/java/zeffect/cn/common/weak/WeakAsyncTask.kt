package zeffect.cn.common.weak

import android.os.AsyncTask

import java.lang.ref.WeakReference

/**
 * 防止内存泄露的AsyncTask
 * <pre>
 * author  ：zzx
 * e-mail  ：zhengzhixuan18@gmail.com
 * time    ：2017/07/14
 * desc    ：
 * version:：1.0
</pre> *
 *
 * @author zzx
 */

abstract class WeakAsyncTask<Params, Progress, Result, WeakTarget>(pWeakTarget: WeakTarget?) : AsyncTask<Params, Progress, Result>() {
    protected val mTarget: WeakReference<WeakTarget>

    init {
        if (pWeakTarget == null) {
            throw NullPointerException("weak target is null")
        }
        mTarget = WeakReference(pWeakTarget)
    }


    override fun onPreExecute() {
        val target = mTarget.get()
        if (target != null) {
            this.onPreExecute(target)//运行前的准备
        }
    }

    protected fun onPreExecute(pTarget: WeakTarget) {}


    override fun doInBackground(vararg params: Params): Result? {
        val target = mTarget.get()
        return if (target != null) {
            this.doInBackground(target, *params)//后台运行中
        } else {
            null
        }
    }

    protected abstract fun doInBackground(pTarget: WeakTarget, vararg params: Params): Result


    override fun onPostExecute(pResult: Result) {
        val target = mTarget.get()
        if (target != null) {
            this.onPostExecute(target, pResult)
        }
    }


    protected fun onPostExecute(pTarget: WeakTarget, pResult: Result) {}

    override fun onProgressUpdate(vararg values: Progress) {
        val target = mTarget.get()
        if (target != null) {
            this.onProgressUpdate(target, *values)
        }
    }

    protected fun onProgressUpdate(pTarget: WeakTarget, vararg values: Progress) {}

}