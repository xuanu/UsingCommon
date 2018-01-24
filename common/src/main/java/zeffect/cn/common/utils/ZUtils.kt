package zeffect.cn.common.utils

import java.util.Collections.replaceAll


/**
 * Created by Administrator on 2018/1/24.
 */

object ZUtils {
    /***
     * 去除标点
     * @param input
     * @return
     */
    fun removePunctuation(input: String): String {
        return input.replace("[\\p{P}+~$`^=|<>～｀＄＾＋＝｜＜＞￥×]".toRegex(), "").trim { it <= ' ' }
    }
}