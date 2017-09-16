package zeffect.cn.common.view

import android.view.View

/**
 * Created by Administrator on 2017/9/16.
 */
object ViewUtils{
    /***
     * 测量的默认处理
     * 1. EXACTLY返回定值
     * 2. UNSPECIFIED返回默认值
     * 3.AT_MOST返回最小值
     * @param measureSpace 值
     * @param default 默认值
     * @return 大小
     */
    fun getMeasureSize(measureSpace: Int, default: Int): Int {
        var retuSize = 0
        val model = View.MeasureSpec.getMode(measureSpace)
        val size = View.MeasureSpec.getSize(measureSpace)
        when (model) {
            View.MeasureSpec.UNSPECIFIED -> retuSize = default
            View.MeasureSpec.AT_MOST -> retuSize = Math.min(default, size)
            View.MeasureSpec.EXACTLY -> retuSize = size
        }
        return retuSize
    }
}