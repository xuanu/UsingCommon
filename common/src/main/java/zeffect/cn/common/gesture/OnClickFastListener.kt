package zeffect.cn.common.gesture

import android.view.View

/**
 * 方法按钮重复点击的监听类源码
 *
 *
 * 指定时间内的点击操作不可用。
 */
abstract class OnClickFastListener : View.OnClickListener {

    constructor() {}

    constructor(time: Long) {
        var time = time
        if (time < 0) {
            time = 0
        }
        this.DELAY_TIME = time
    }

    private var DELAY_TIME: Long = 900

    private val isFastDoubleClick: Boolean
        get() {
            val time = System.currentTimeMillis()
            val timeD = time - lastClickTime
            if (0 < timeD && timeD < DELAY_TIME) {
                return true
            }
            lastClickTime = time
            return false
        }

    override fun onClick(v: View) {
        if (isFastDoubleClick) {
            return
        }
        onFastClick(v)
    }

    /**
     * 快速点击事件回调方法
     *
     * @param v
     */
    abstract fun onFastClick(v: View)

    companion object {
        private var lastClickTime: Long = 0
    }
}