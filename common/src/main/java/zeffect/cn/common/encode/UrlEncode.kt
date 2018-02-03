package zeffect.cn.common.encode

import android.net.Uri
import java.net.URLEncoder
import java.util.regex.Pattern

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
//        return Uri.encode(url, "-![.:/,%?&=]");//有一定的问题，如果链接时有%号需要转义就有问题。
        if (url.isNullOrBlank()) return ""
        val URL_REGEX = "((([A-Za-z]{3,9}:(?://)?)(?:[-;:&=+$,\\w]+@)?[A-Za-z0-9.-]+(:[0-9]+)?|(?:ww‌​w.|[-;:&=+$,\\w]+@)[A-Za-z0-9.-]+)((?:/[+~%/.\\w-_]*)?\\??(?:[-+=&;%@.\\w_]*)#?‌​(?:[\\w]*))?)"
        val pattern = Pattern.compile(URL_REGEX)
        val matcher = pattern.matcher(url)
        var start = 0
        var end = 0
        while (matcher.find()) {
//            for (i in 0 until matcher.groupCount()) {
//                println("i:" + i + ",value:" + matcher.group(i) + ",start:" + matcher.start(i) + ",end:"
//                        + matcher.end(i))
//            }
            start = matcher.start(0)
            end = matcher.end(0)
        }
        return if (start >= end || start != 0) {
            //没有网址，直接转义
            Uri.encode(url, "$-_.+!*'()")
        } else {
            val tempUrl = url.substring(end)
            val httpUrl = url.substring(start, end)
            httpUrl + Uri.encode(tempUrl, "$-_.+!*'()")
        }
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
