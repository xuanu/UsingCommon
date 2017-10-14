package zeffect.cn.common.assets

import android.content.Context
import android.text.TextUtils
import java.io.ByteArrayOutputStream
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
    /***
     * 文件 转String
     * @param pContext 上下文
     * @param pName 文件名，可带路径
     */
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
            retuString = inputStream2String(inputs)
            inputs.close()
        } catch (pE: IOException) {
            pE.printStackTrace()
        } finally {
            if (inputs != null) inputs.close()
            return retuString
        }
    }

    fun inputStream2String(inputs: InputStream): String {
        val baos = ByteArrayOutputStream()
        try {
            var i = inputs.read()
            while (i != -1) {
                baos.write(i)
                i = inputs.read()
            }
        } catch (e: IOException) {
        } finally {
            return baos.toString()
        }
    }
}