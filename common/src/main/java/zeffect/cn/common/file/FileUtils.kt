package zeffect.cn.common.file

import android.text.TextUtils
import zeffect.cn.common.assets.AssetsUtils
import java.io.*

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
    fun write(filePath: String, content: String, append: Boolean = false, charset: String = "UTF-8"): Boolean {
        try {
            if (TextUtils.isEmpty(filePath)) return false
            val tempFile = File(filePath)
            if (!tempFile.exists() || tempFile.isDirectory) return false
            val writer = BufferedWriter(OutputStreamWriter(FileOutputStream(filePath, append), charset))
            writer.write(content)
            writer.flush()
            writer.close()
            return true
        } catch (e: IOException) {
            e.printStackTrace()
            return false
        }
    }


    /**
     * 删除文件，或文件夹
     *
     * @param file 文件
     * @return 成功／失败
     */
    fun deleteFiles(file: File?): Boolean {
        if (file != null) {
            if (!file.exists()) {
                return true
            }
            if (file.isFile) {
                return file.delete()
            }
            if (!file.isDirectory) {
                return false
            }
            val listFiles = file.listFiles()
            if (listFiles != null) {
                for (f in listFiles) {
                    if (f.isFile) {
                        f.delete()
                    } else if (f.isDirectory) {
                        deleteFiles(f)
                    }
                }
                return file.delete()
            }
        }
        return false
    }

}