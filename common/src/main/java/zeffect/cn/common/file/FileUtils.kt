package zeffect.cn.common.file

import android.text.TextUtils
import zeffect.cn.common.assets.AssetsUtils
import java.io.File
import java.io.FileInputStream
import java.io.FileWriter
import java.io.IOException

/**
 * Created by Administrator on 2017/10/14.
 */
object FileUtils {
    /****
     * 读取文件内容
     * @param filePath 路径
     */
    fun read(filePath: String): String {
        if (TextUtils.isEmpty(filePath)) return ""
        val tempFile = File(filePath)
        if (!tempFile.exists() || tempFile.isDirectory) return ""
        val fileInput = FileInputStream(tempFile)
        return AssetsUtils.inputStream2String(fileInput)
    }

    /***
     * 写文件内容
     * @param filePath 路径
     * @param content 内容
     * @param append 是否追加
     */
    fun write(filePath: String, content: String, append: Boolean = false): Boolean {
        try {
            if (TextUtils.isEmpty(filePath)) return false
            val tempFile = File(filePath)
            if (!tempFile.exists() || tempFile.isDirectory) return false
            val writer = FileWriter(filePath, append)
            writer.write(content)
            writer.close()
            return true
        } catch (e: IOException) {
            e.printStackTrace()
            return false
        }


    }

}