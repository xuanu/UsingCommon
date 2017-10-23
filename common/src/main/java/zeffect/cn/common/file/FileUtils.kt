package zeffect.cn.common.file

import android.text.TextUtils
import java.io.*
import java.security.MessageDigest


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
        return inputStream2String(fileInput)
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


    /**
     * 计算文件的 MD5 值，用于比较两个文件是否相同。
     *
     * @param file
     * @return
     */
    fun getFileMD5(file: File): String? {
        if (file == null) return ""
        if (!file.isFile) {
            return ""
        }
        var `in`: FileInputStream? = null
        try {
            `in` = FileInputStream(file)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            return ""
        }

        return getFileMD5(`in`)
    }

    /**
     * 计算文件的 MD5 值
     *
     * @param file
     * @return
     */
    fun getFileMD5(`in`: InputStream?): String? {
        if (`in` == null) {
            return ""
        }
        val digest = MessageDigest.getInstance("MD5")
        val buffer = ByteArray(8192)
        try {
            var len = `in`.read(buffer)
            while (len != -1) {
                digest!!.update(buffer, 0, len)
                len = `in`.read(buffer)
            }
            return byteArrayToHexString(digest!!.digest())
        } catch (e: Exception) {
            e.printStackTrace()
            return ""
        } finally {
            try {
                `in`.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    private fun byteArrayToHexString(b: ByteArray): String {
        val resultSb = StringBuffer()
        for (i in b.indices)
            resultSb.append(byteToHexString(b[i]))

        return resultSb.toString()
    }

    private val hexDigits = arrayOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f")

    private fun byteToHexString(b: Byte): String {
        var n = b.toInt()
        if (n < 0)
            n += 256
        val d1 = n / 16
        val d2 = n % 16
        return hexDigits[d1] + hexDigits[d2]
    }

    private fun inputStream2String(inputs: InputStream): String {
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