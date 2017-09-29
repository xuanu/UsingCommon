package zeffect.cn.common.assets

import android.content.Context
import android.text.TextUtils
import java.io.IOException
import java.io.InputStream


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
object AssetsUtils {
    fun fileString(pContext: Context, pName: String): String {
        if (pContext == null) {
            return ""
        }
        if (TextUtils.isEmpty(pName)) {
            return ""
        }
        var retuString = ""
        var inputs: InputStream? = null
        try {
            inputs = pContext.assets.open(pName)
            val buffer = ByteArray(10240)
            var len: Int = 0
            val sb = StringBuilder()
            while (inputs.read(buffer).apply { len = this } > 0) sb.append(String(buffer, 0, len))
            retuString = sb.toString()
            inputs.close()
        } catch (pE: IOException) {
            pE.printStackTrace()
        }

        return retuString
    }
}