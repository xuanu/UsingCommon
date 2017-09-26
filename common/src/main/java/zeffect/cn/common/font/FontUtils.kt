package zeffect.cn.common.font

import android.graphics.Paint

/**
 * <pre>
 *      author  ：zzx
 *      e-mail  ：zhengzhixuan18@gmail.com
 *      time    ：2017/09/26
 *      desc    ：
 *      version:：1.0
 * </pre>
 * @author zzx
 */
object FontUtils {
    /***
     * 字体高度
     * @param fontSize 字体大小
     */
    fun fontHeight(fontSize: Float): Int {
        val paint = Paint()
        paint.textSize = fontSize
        val fm = paint.fontMetrics
        return Math.ceil((fm.descent - fm.top).toDouble()).toInt()
    }
}