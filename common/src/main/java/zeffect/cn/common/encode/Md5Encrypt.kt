package zeffect.cn.common.encode

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * <pre>
 *      author  ：zzx
 *      e-mail  ：zhengzhixuan18@gmail.com
 *      time    ：2017/09/29
 *      desc    ：
 *      version:：1.0
 * </pre>
 * @author zzx
 */
object Md5Encrypt {
    fun md5(plainText: String): String {
        var buf: StringBuffer? = null
        try {
            val md = MessageDigest.getInstance("MD5")
            md.update(plainText.toByte())
            val b = md.digest()
            var i: Int
            buf = StringBuffer("")
            for (offset in b.indices) {
                i = b[offset].toInt()
                if (i < 0) {
                    i += 256
                }
                if (i < 16) {
                    buf.append("0")
                }
                buf.append(Integer.toHexString(i))
            }
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return if (buf == null) {
            ""
        } else buf.toString()
    }
}