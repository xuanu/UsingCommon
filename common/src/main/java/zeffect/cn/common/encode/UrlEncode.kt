package zeffect.cn.common.encode

import android.net.Uri

/**
 * 链接进行编码：http://www.jianshu.com/p/9be694c8fee2
 * <pre>
 * author  ：zzx
 * e-mail  ：zhengzhixuan18@gmail.com
 * time    ：2017/09/04
 * desc    ：
 * version:：1.0
</pre> *
 *
 * @author zzx
 */

object UrlEncode {
    /***
     * 对链接进行编码。已测试解决：中文、空格，重复编码的问题。
     * @param url
     * @return
     */
    fun encodeUrl(url: String): String {
        return Uri.encode(url, "-![.:/,%?&=]")
    }

    /***
     * 未进行测试
     * @param s
     * @return
     */
    fun toUtf8String(s: String): String {
        val sb = StringBuffer()
        for (i in 0..s.length - 1) {
            val c = s[i]
            if (c.toInt() >= 0 && c.toInt() <= 255) {
                sb.append(c)
            } else {
                var b: ByteArray
                try {
                    b = c.toString().toByteArray(charset("utf-8"))
                } catch (ex: Exception) {
                    println(ex)
                    b = ByteArray(0)
                }

                for (j in b.indices) {
                    var k = b[j].toInt()
                    if (k < 0)
                        k += 256
                    sb.append("%" + Integer.toHexString(k).toUpperCase())
                }
            }
        }
        return sb.toString()
    }
}
